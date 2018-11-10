package command;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import util.FileConstants;


public class DoCalcDirsCommand implements Command
{
	static String sCalcName;
	
	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			PrintWriter p = response.getWriter();
			HttpServletRequest request = commandContext.getRequest();
			//String 
			sCalcName = request.getParameter("calcName");
			
			if(sCalcName == null) return;
			
			List<String> projectNames = new ArrayList<String>();
			
			visitAllDirs(new File(FileConstants.HOME_DIR_NAME + 
					FileConstants.SEPARATOR + 
					FileConstants.TOP_DIR_NAME), projectNames); 
			
			p.println("<?xml version=\"1.0\"?>\n");
			p.println("<calculations>");
			
			for(int i = 0; i < projectNames.size(); i++)
			{
				String name = projectNames.get(i);

				p.println("<calc>");
			    p.println("<name>" + name + "</name>");
				p.println("</calc>");
			}
			p.println("</calculations>");
		} 
		catch(Throwable theException) 
		{ 
//			theException.printStackTrace(); 
		} 
	}

	public void visitAllDirs(File dir, List<String> list) 
	{
	    if (dir.isDirectory()) 
	    {
	        process(dir, list);

	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) 
	        {
	            visitAllDirs(new File(dir, children[i]), list);
	        }
	    }
	}
	
	private void process(File dir, List<String> list)
	{
		String sDirName = dir.getPath();
		String[] dirs = sDirName.split("\\\\");
		if(dirs.length > 0)
		{
			for(int i = 0; i < dirs.length; i++)
			{
				if(dirs[i].contains("-"))
				{
					list.add(dirs[i]);
					//System.out.println(sDirName);
				}
				
/*				if(sDirName.contains(sCalcName))
				{
					list.add(sDirName);
				}*/

			}
		}
	}
}
