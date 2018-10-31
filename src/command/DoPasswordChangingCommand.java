package command;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import db.sql.DbManager;


public class DoPasswordChangingCommand implements Command
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
			
			if(sUsername == null || sPassword == null) return;
			
			changePassword(sUsername, sPassword);
			
			boolean wasChanged = true;
			
			PrintWriter p = response.getWriter();
			p.println("<?xml version=\"1.0\"?>\n");
			p.println("<password>");
			p.println("<wasChanged>" + String.valueOf(wasChanged) + "</wasChanged>");
			p.println("</password>");
			
			
		} 
		catch(Throwable theException) 
		{ 
			theException.printStackTrace(); 
		} 
	}
	
	private boolean changePassword(final String sUsername, final String sPassword)
	{
		boolean res = false;
		DbManager dbManager = new DbManager();
		res = dbManager.updatePassword(sUsername, sPassword);
		return res;
	}
}