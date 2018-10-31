package command;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import db.sql.FitDbManager;



public class DoPiFitDeletingCommand implements Command
{
	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			
			HttpServletRequest request = commandContext.getRequest();
			String sTransitionPk = request.getParameter("transitionPk");
			
			if(sTransitionPk == null) return;
			
			long transPk = Long.parseLong(sTransitionPk);
			deleteFit(transPk);
		} 
		catch(Throwable theException) 
		{ 
			theException.printStackTrace(); 
		} 
	}
	
	private void deleteFit(final long transPk)
	{
		try 
		{
			FitDbManager dbManager = new FitDbManager();
			dbManager.deletePiFit(transPk);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}