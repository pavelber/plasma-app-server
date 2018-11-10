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


public class DoPiFitCoordinatesCommand implements Command
{
	NumberFormat formatterX = new DecimalFormat("#.##");
	NumberFormat formatterY = new DecimalFormat("0.000E00");
	
	private double m_dA;
	private double m_dB;
	private double m_dC;
	private double m_dD;
	private List<CsPoint> m_points;
	
	private final static int NUMBER_OF_POINTS = 2000;
	private final static double STEP_SIZE = 0.01;			


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
			
			if(sA == null || sB == null || 
					sC == null || sD == null) return;
			
			m_dA = Double.parseDouble(sA);
			m_dB = Double.parseDouble(sB);
			m_dC = Double.parseDouble(sC);
			m_dD = Double.parseDouble(sD);

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

			double kB = m_dB / xi;
			if(Double.isInfinite(kB) || Double.isNaN(kB))
			{
				System.out.println("x val: " + xi);
				System.out.println("kB: " + kB);
				break;
			}
			
           	double kC = m_dC / (xi * xi);
			if(Double.isInfinite(kC) || Double.isNaN(kC))
			{
				System.out.println("x val: " + xi);
				System.out.println("kC: " + kC);
				break;
			}
			
           	double kD = Math.pow(xi,(3.5 + m_dD));
			if(Double.isInfinite(kD) || Double.isNaN(kD))
			{
				System.out.println("x val: " + xi);
				System.out.println("kD: " + kD);
				break;
			}

			double yval = (m_dA + kB + kC) / kD;  
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
