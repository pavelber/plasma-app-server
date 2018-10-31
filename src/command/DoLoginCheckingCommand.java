package command;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import db.sql.DbManager;


public class DoLoginCheckingCommand implements Command
{
	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			
			HttpServletRequest request = commandContext.getRequest();
			String sUsername = request.getParameter("username");
			String sPassword = request.getParameter("password");
			
			//System.out.println("username " + sUsername);
			//System.out.println("password " + sPassword);
			
			if(sUsername == null || sPassword == null) return;
			
			boolean isLoginValid = isLoginValid(sUsername, sPassword);
			
			PrintWriter p = response.getWriter();
			p.println("<?xml version=\"1.0\"?>\n");
			p.println("<login>");
			p.println("<isValid>" + String.valueOf(isLoginValid) + "</isValid>");
			p.println("</login>");
			
			
		} 
		catch(Throwable theException) 
		{ 
			theException.printStackTrace(); 
		} 
	}
	
	private boolean isLoginValid(final String sUsername, final String sPassword)
	{
		boolean res = false;
		DbManager dbManager = new DbManager();
		res = dbManager.isLoginValid(sUsername, sPassword);
		return res;
	}
}