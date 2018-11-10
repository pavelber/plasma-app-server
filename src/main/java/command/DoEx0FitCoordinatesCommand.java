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


public class DoEx0FitCoordinatesCommand implements Command
{
	NumberFormat formatterX = new DecimalFormat("#.##");
	NumberFormat formatterY = new DecimalFormat("0.000E00");
	
	private double m_dOscStrength;
	private double m_dThreshold;
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
			String sOscStrength = request.getParameter("oscstrength");
			String sThreshold = request.getParameter("threshold");
			
			if(sOscStrength == null || 
					sThreshold == null) return;
			
			m_dOscStrength = Double.parseDouble(sOscStrength);
			m_dThreshold = Double.parseDouble(sThreshold);

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

//			(8 * Math.PI / Math.sqrt(3.)) * 5.29 * 5.29 * 1E-18 * 
//			m_dOscStrength * ((13.6 * 13.6) / 
//			(m_dThreshold * m_dThreshold) * energy);
			
			double kOsc = (8 * Math.PI / Math.sqrt(3.)) * 5.29 * 5.29 * 1E-18 * m_dOscStrength * (13.6 * 13.6);
			if(Double.isInfinite(kOsc) || Double.isNaN(kOsc))
			{
				System.out.println("x val: " + xi);
				System.out.println("kOsc: " + kOsc);
				break;
			}

			double kThreshold = (m_dThreshold * m_dThreshold) * xi;
			if(Double.isInfinite(kThreshold) || Double.isNaN(kThreshold))
			{
				System.out.println("x val: " + xi);
				System.out.println("kThreshold: " + kThreshold);
				break;
			}
			
			double yval = kOsc / kThreshold;  
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
