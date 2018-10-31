package command;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import db.sql.FitDbManager;

import util.FileConstants;
import fit2db.scanner.FitScanner;


public class DoFitsToDbCommand implements Command
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
			p.println("<?xml version=\"1.0\"?>\n");
			p.println("<states>");
			
			loadFits(file, p, sCalcName);
		} 
		catch(Throwable theException) 
		{ 
			theException.printStackTrace(); 
		} 
	}
	
	private void loadFits(final File file, final PrintWriter p, final String sCalcName)
	{
		try 
		{
			FitDbManager 	dbManager = new FitDbManager();
			FitScanner 	fitScanner = new FitScanner();
			int iProjectId = dbManager.getProjectPk(sCalcName);
			fitScanner.scan(file, iProjectId, dbManager);
			
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
				FileConstants.FIT_DIR_NAME + FileConstants.SEPARATOR + 
				sIonName + FileConstants.SEPARATOR + sFileName );
	}
}