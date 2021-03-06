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

import db.sql.FitDbManager;

import fac2db.transition.TransitionScanner;


public class DoTransitionsToDbCommand implements Command
{
	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			HttpServletRequest request = commandContext.getRequest();
			String sCalcName = request.getParameter("calcName");
			String sIonName = request.getParameter("ionName");
			String sFileName = request.getParameter("fileName");
			File file = makeFile(sCalcName, sIonName, sFileName);
			PrintWriter p = response.getWriter();
			loadTransitions(file, p, sCalcName);
		} 
		catch(Throwable theException) 
		{ 
			theException.printStackTrace(); 
		} 
	}
	
	private void loadTransitions(final File file, final PrintWriter p, final String sCalcName)
	{
		try 
		{
			FitDbManager dbManager = new FitDbManager();
			int iProjectId = dbManager.getProjectPk(sCalcName);
			TransitionScanner trScanner = new TransitionScanner();
			trScanner.scan(file, iProjectId, dbManager);
			
			p.println("<?xml version=\"1.0\"?>\n");
			p.println("<states>");
			p.println("<state>");
			p.println("<size>" + file.length() + "</size>");
			p.println("</state>");
			p.println("</states>");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	
	private File makeFile(final String sCalcName, final String sIonName, final String sFileName)
	{
		return new File(FileConstants.HOME_DIR_NAME + FileConstants.SEPARATOR + 
				FileConstants.TOP_DIR_NAME + FileConstants.SEPARATOR + 
				FileConstants.USER_DIR_NAME + FileConstants.SEPARATOR + 
				sCalcName + FileConstants.SEPARATOR + 
				FileConstants.FAC_DIR_NAME + FileConstants.SEPARATOR + 
				sIonName + FileConstants.SEPARATOR + sFileName );
	}
}
