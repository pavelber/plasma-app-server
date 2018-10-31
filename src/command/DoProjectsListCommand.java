package command;


import db.sql.DbManager;
import response.Calc;
import response.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

public class DoProjectsListCommand implements Command
{

	@Override
	public void execute(CommandContext commandContext) 
	throws ServletException, IOException 
	{
		try 
		{ 
			HttpServletResponse response = commandContext.getResponse();
			response.setContentType("text/xml");
			Response responseContent = execute();
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
	
	private Response execute()
	{
		Response res = null;
		DbManager dbManager = new DbManager();
		res = onCalcsRequest(dbManager);
		return res;
	}
	
	private Response onCalcsRequest(final DbManager dbManager)
	{
		List<Calc> dbCalcs = dbManager.getCalcs();
		Response response = new Response();
		response.setName("calcs");
		List<Calc> responseCalcs = response.getCalc();
		
		for(int i = 0; i < dbCalcs.size(); i++)
		{
			Calc dbCalc = dbCalcs.get(i);
			responseCalcs.add(dbCalc);
		}
		return response;
	}
}
