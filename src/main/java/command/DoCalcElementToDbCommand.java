package command;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import db.sql.FitDbManager;



public class DoCalcElementToDbCommand implements Command
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
			PrintWriter p = response.getWriter();
			p.println("<?xml version=\"1.0\"?>\n");
			p.println("<states>");
			
			makeProject(sCalcName);
			
			p.println("<state>");
			p.println("<calc>" + sCalcName + "</calc>");
			p.println("<ion></ion>");
			p.println("<process>Insert new Calculation to DB</process>");
			p.println("<file></file>");
			p.println("</state>");

			p.println("</states>");
		} 
		catch(Throwable theException) 
		{ 
			theException.printStackTrace(); 
		} 
	}

    private void makeProject(final String dir)
    {
		String[] params = dir.split("-");
		String projectName = dir;
		int elNumber = Integer.parseInt(params[1]);
		int startIon = Integer.parseInt(params[2]);
		int endIon = Integer.parseInt(params[3]) + 1;
		FitDbManager dbManager = new FitDbManager();
		dbManager.insertProject(projectName, 
				elNumber, startIon, endIon);
    }
}
