package command;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CsPoint;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;


public class DoEx11FitCoordinatesCommand implements Command
{
	NumberFormat formatterX = new DecimalFormat("#.##");
	NumberFormat formatterY = new DecimalFormat("0.000E00");
	
	private double m_dA;
	private double m_dB;
	private double m_dC;
	private double m_dD;
	private double m_dE;
	private List<CsPoint> m_points;
	
	private final static int NUMBER_OF_POINTS = 400;
	private final static double STEP_SIZE = 0.05;			


	@Override
	public void execute(CommandContext commandContext) throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			PrintWriter p = response.getWriter();
			HttpServletRequest request = commandContext.getRequest();
			String sA = request.getParameter("a");
			String sB = request.getParameter("b");
			String sC = request.getParameter("c");
			String sD = request.getParameter("d");
			String sE = request.getParameter("e");
			
			if(sA == null || sB == null || 
					sC == null || sD == null || 
					sE == null) return;
			
			m_dA = Double.parseDouble(sA);
			m_dB = Double.parseDouble(sB);
			m_dC = Double.parseDouble(sC);
			m_dD = Double.parseDouble(sD);
			m_dE = Double.parseDouble(sE);

			m_points = makeCoordinates();
			
			p.println("<?xml version=\"1.0\"?>\n");
			p.println("<coordinates>");
			
			for(int i = 0; i < m_points.size(); i++)
			{
				CsPoint point = m_points.get(i);
				double x = point.getX();
				double y = point.getY();
				
				String sX = formatterX.format(x);
				String sY = formatterY.format(y);
				
				System.out.println("x " + sX + " y " + sY);

				p.println("<item>");
			    p.println(" <xval>" + sX + "</xval>");
				p.println(" <yval>" + sY + "</yval>");
				p.println("</item>");
			}
			p.println("</coordinates>");
		} 
		catch(Throwable theException) 
		{ 
//			theException.printStackTrace(); 
		} 
	}
	
	private List<CsPoint> makeCoordinates()
	{
		List<CsPoint> res = new ArrayList<CsPoint>();
		double xi = 0.0;
		double yi = 0.0;
		
		for(int i = 0; i <= NUMBER_OF_POINTS; ++i)
		{
			xi = calcXi(i);

//			res = (a * energy * energy + b * energy + c) / 
//			Math.pow(energy + d, 4) / Math.pow(energy, e); 

			
			double kA = m_dA * xi * xi;
			if(Double.isInfinite(kA) || Double.isNaN(kA))
			{
				System.out.println("x val: " + xi);
				System.out.println("kA: " + kA);
				break;
			}
			
           	double kB = m_dB * xi;
			if(Double.isInfinite(kB) || Double.isNaN(kB))
			{
				System.out.println("x val: " + xi);
				System.out.println("kB: " + kB);
				break;
			}
			
           	double kC = m_dC;
			if(Double.isInfinite(kC) || Double.isNaN(kC))
			{
				System.out.println("x val: " + xi);
				System.out.println("kC: " + kC);
				break;
			}

           	double kD = Math.pow(xi + m_dD, 4.0);
			if(Double.isInfinite(kD) || Double.isNaN(kD))
			{
				System.out.println("x val: " + xi);
				System.out.println("kD: " + kD);
				break;
			}

           	double kE = Math.pow(xi, m_dE);
			if(Double.isInfinite(kE) || Double.isNaN(kE))
			{
				System.out.println("x val: " + xi);
				System.out.println("kE: " + kE);
				break;
			}

			double yval = (kA + kB + kC) / kD / kE;  
			if(Double.isInfinite(yval) || Double.isNaN(yval))
			{
				System.out.println("x val: " + xi);
				System.out.println("yval: " + yval);
				break;
			}
			
			yi = yval * 1E20;
			
			CsPoint point = new CsPoint(xi, yi);
			res.add(point);
		}
		return res;
	}
	
	private double calcXi(int i)
	{
		return 1 + i * STEP_SIZE;
	}
}
