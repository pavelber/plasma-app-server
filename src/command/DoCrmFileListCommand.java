package command;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import util.FileConstants;



public class DoCrmFileListCommand implements Command
{
	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			PrintWriter p = response.getWriter();
			HttpServletRequest request = commandContext.getRequest();
			String sCalcName = request.getParameter("calcName");
			
			if(sCalcName == null) return;
			
			File[] files = process(sCalcName);
	         for(int i = 0; i < files.length; i++)
	            {
                    System.out.println("");
	                System.out.println("name " + files[i].getName());
	                System.out.println("uri " + files[i].toURI());

	            }

			
			p.println("<?xml version=\"1.0\"?>\n");
			p.println("<files>");
			
			for(int i = 0; i < files.length; i++)
			{
				String name = files[i].getName();
				long size = files[i].length();
				
				p.println("<file>");
			    p.println(" <name>" + name + "</name>");
				p.println(" <size>" + size + "</size>");
				p.println("</file>");
			}
			p.println("</files>");
			//p.flush();
			
		} 
		catch(Throwable theException) 
		{ 
//			theException.printStackTrace(); 
		} 
	}
	
	private File[] process(String sCalcName)
	{
		// C:\Documents and Settings\weizmann\atomic\leonidw\Aluminum-13-10-13\crm
		String directoryName = FileConstants.HOME_DIR_NAME + FileConstants.SEPARATOR + 
				FileConstants.TOP_DIR_NAME + FileConstants.SEPARATOR + 
				FileConstants.USER_DIR_NAME + FileConstants.SEPARATOR + 
				sCalcName + FileConstants.SEPARATOR + 
				FileConstants.CRM_DIR_NAME;
		System.out.println(directoryName);
		File dir = new File(directoryName);
		File[] files = dir.listFiles();
		return files;
	}
}
