package command;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import db.sql.FitDbManager;



public class DoRtRateUpdatingCommand implements Command
{
	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			
			HttpServletRequest request = commandContext.getRequest();
			String sSource = request.getParameter("source");
			String sTarget = request.getParameter("target");
			String sEnergy = request.getParameter("energy");
			String sRate = request.getParameter("rate");
			
			if(sSource == null || sTarget == null || 
					sEnergy == null || sRate == null) return;
			
			int iSource = Integer.parseInt(sSource);
			int iTarget = Integer.parseInt(sTarget);
			double dEnergy = Double.parseDouble(sEnergy);
			double dRate = Double.parseDouble(sRate);

			updateRate(iSource, iTarget, dEnergy, dRate);
		} 
		catch(Throwable theException) 
		{ 
			theException.printStackTrace(); 
		} 
	}
	
	private void updateRate(final int iSource, final int iTarget, final double dEnergy, final double dRate)
	{
		try 
		{
			FitDbManager dbManager = new FitDbManager();
			dbManager.updateRtRate(iSource, iTarget, dEnergy, dRate);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}