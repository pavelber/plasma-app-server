package command;


import db.sql.DbManager;
import response.Atom;
import response.Calc;
import response.Fac;
import response.Ion;
import response.Level;
import response.Nist;
import response.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

public class DoExcitationTransitionsCommand implements Command
{

	@Override
	public void execute(CommandContext commandContext) 
	throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			HttpServletRequest request = commandContext.getRequest();
			String sCalcId = request.getParameter("calcId");
			int iCalcId = Integer.parseInt(sCalcId);
			
			System.out.println("iCalcId " + iCalcId);
			
			Response responseContent = execute(iCalcId);
			PrintWriter p = response.getWriter();
			send(responseContent, p);
			
		} 
		catch(Throwable theException) 
		{ 
			//theException.printStackTrace(); 
		} 
	}
	
    public void send( final Response response, final PrintWriter writer) 
    {
        try 
        {
			JAXBContext ctx = JAXBContext.newInstance(Response.class);
			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(response, writer);
		} 
        catch (PropertyException e) 
        {
			e.printStackTrace();
		} 
        catch (JAXBException e) 
        {
			e.printStackTrace();
		}
	}
	
	private Response execute(final int iCalcId)
	{
		Response res = null;
		DbManager dbManager = new DbManager();
		res = onTransitionsRequest(dbManager, iCalcId);
		System.out.println(res.toString());
		return res;
	}
	
	private Response onTransitionsRequest(final DbManager dbManager, 
			final int iCalcId)
	{
		Calc calc = dbManager.getExcitationTransitions(iCalcId);
        Response response = new Response();
        response.setName("transitions");
        List<Calc> calcs = response.getCalc();
        calcs.add(calc);
        System.out.println(calc);
        
        for(int i = 0; i < calcs.size(); i++)
        {
        	Calc c = calcs.get(i);
    		Atom a = c.getAtom();
    		System.out.println(a);
/*    		List<Ion> ions = a.getIon();
    		for(int j = 0; j < ions.size(); j++)
    		{
    			Ion ion = ions.get(j);
    			Fac fac = ion.getFac();
    			List<Level> levels = fac.getLevel();
    		}*/
    		
        }
        
        return response;
	}
}
