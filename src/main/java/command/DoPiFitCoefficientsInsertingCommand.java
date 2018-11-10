package command;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import db.sql.FitDbManager;



public class DoPiFitCoefficientsInsertingCommand implements Command
{
	private final static int FITTING_METHOD_NUMBER = 4; 

	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		double[] dCoeffs = {0.0, 0.0, 0.0, 0.0};

		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			HttpServletRequest request = commandContext.getRequest();
			String sTransitionPk = request.getParameter("transitionPk");
			String sA = request.getParameter("a");
			String sB = request.getParameter("b");
			String sC = request.getParameter("c");
			String sD = request.getParameter("d");
			
			if(sA == null || sB == null || 
					sC == null || sD == null ) return;
			
			long transPk = Long.parseLong(sTransitionPk);
			dCoeffs[0] = Double.parseDouble(sA);
			dCoeffs[1] = Double.parseDouble(sB);
			dCoeffs[2] = Double.parseDouble(sC);
			dCoeffs[3] = Double.parseDouble(sD);
			updateFit(transPk, dCoeffs, FITTING_METHOD_NUMBER);
		} 
		catch(Throwable theException) 
		{ 
			theException.printStackTrace(); 
		} 
	}
	
	private void updateFit(final long transPk, final double[] coeffs, final int iMethod)
	{
		try 
		{
			FitDbManager dbManager = new FitDbManager();
			dbManager.insertPiFitCoefficients(transPk, coeffs, iMethod);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}