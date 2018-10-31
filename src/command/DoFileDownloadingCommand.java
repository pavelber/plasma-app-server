package command;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import util.FileConstants;



public class DoFileDownloadingCommand implements Command
{
	private static final int BUFFER_SIZE = 16384;

	@Override
	public void execute(CommandContext commandContext) 
	throws ServletException, IOException 
	{
		HttpServletResponse response = commandContext.getResponse();
		HttpServletRequest request = commandContext.getRequest();
		String sCalcName = request.getParameter("calcName");
		System.out.println(sCalcName);
		String sFileName = request.getParameter("fileName");
		System.out.println(sFileName);

		File file = new File(makePath(sCalcName, sFileName));
        prepareResponseFor(response, file);
        streamFileTo(response, file);
	}

    private void streamFileTo(HttpServletResponse response, File file)  
    throws IOException 
    {
    	OutputStream os = response.getOutputStream();
    	FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
        while ((bytesRead = fis.read(buffer)) > 0) 
        {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        fis.close();
        os.close();
    }

    private void prepareResponseFor(HttpServletResponse response, File file) 
    {
        StringBuilder type = new StringBuilder("attachment; filename=");
        type.append(file.getName());
        response.setContentLength((int) file.length());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", type.toString());
    }
    
   private String makePath(final String sCalcName, final String sFileName)
    {
        return FileConstants.HOME_DIR_NAME + FileConstants.SEPARATOR + 
                FileConstants.TOP_DIR_NAME + FileConstants.SEPARATOR + 
                FileConstants.USER_DIR_NAME + FileConstants.SEPARATOR + 
                sCalcName + FileConstants.SEPARATOR + 
                FileConstants.CRM_DIR_NAME + FileConstants.SEPARATOR + sFileName;
    }
}
