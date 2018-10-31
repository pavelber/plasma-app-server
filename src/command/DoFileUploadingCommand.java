package command;


//Core classes
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// Servlet classes
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Commons classes
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

// Command
import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import util.FileConstants;



public class DoFileUploadingCommand implements Command
{
	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		File				disk = null;
		FileItem			item = null;
		FileItemFactory		factory = new DiskFileItemFactory();
		Iterator			iter = null;
		List<FileItem>		items = null;
		ServletFileUpload	upload = new ServletFileUpload( factory );
		ServletOutputStream	out = null;
		
		try 
		{
			HttpServletResponse res = commandContext.getResponse();
			HttpServletRequest req = commandContext.getRequest();
			String sDirName = makeDir(req);

			items = upload.parseRequest( req );		
			iter = items.iterator();
			res.setContentType( "text/xml" );
			out = res.getOutputStream();
			out.println( "<response>" );
			
			while( iter.hasNext() )
			{
				item = ( FileItem )iter.next();
				
				if( item.isFormField() )
				{
					out.println( "<field name=\"" + item.getFieldName() + 
								"\" value=\"" + item.getString() + "\" />"  );
				} 
				else 
				{
					disk = new File( sDirName + item.getName() );
					item.write( disk );
					out.println( "<file name=\"" + item.getName() + 
								"\" size=\"" + item.getSize() + "\" />"  );
				}
			}
			
			out.println( "</response>" );
			out.close();
		} 
		catch( FileUploadException fue ) 
		{
			fue.printStackTrace();
		} 
		catch( IOException ioe ) 
		{
			ioe.printStackTrace();
		} 
		catch( Exception e ) 
		{
			e.printStackTrace();
		}
	}
	
	private String makeDir(final HttpServletRequest req)
	{
		System.out.println("Uploading HOME_DIR_NAME " + FileConstants.HOME_DIR_NAME);
		String sCalcName = req.getParameter("calc");
		String sIonName = req.getParameter("ion");
		String sTypeName = req.getParameter("type");
		String dirName = FileConstants.HOME_DIR_NAME + FileConstants.SEPARATOR + 
						FileConstants.TOP_DIR_NAME + FileConstants.SEPARATOR + 
						FileConstants.USER_DIR_NAME + FileConstants.SEPARATOR +
						sCalcName + FileConstants.SEPARATOR + 
						sTypeName + FileConstants.SEPARATOR + 
						sIonName + FileConstants.SEPARATOR;

		boolean success = (new File(dirName)).mkdirs();
		if (!success) 
		{
			// Directory creation failed
		}
		return dirName;
	}
}
