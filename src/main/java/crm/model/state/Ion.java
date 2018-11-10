package crm.model.state;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;

import crm.model.CompositeStateNode;
import crm.model.State;



public class Ion extends CompositeStateNode 
{
	private int 	m_iId;
	private int 	m_iNumber;
	private double 	m_dFacIonizationPotential;
	private double 	m_dNistIonizationPotential;
	
	private int 	m_iAggrScaleFactor;
	private int 	m_iElementNumber;

    public Ion ( String aName ) 
    {
        super ( aName );
    }

    public boolean isLegalParent( State parentOrg ) 
    {
        boolean result = (parentOrg instanceof Element)?  true : false;
        return result;
    }

	public double getNistIonizationPotential() 
	{
		return m_dNistIonizationPotential;
	}

	public double getFacIonizationPotential() 
	{
		return m_dFacIonizationPotential;
	}

	public int getAggrScaleFactor() 
	{
		return m_iAggrScaleFactor;
	}

	public int getElementNumber() 
	{
		return m_iElementNumber;
	}

	public int getId() 
	{
		return m_iId;
	}

	public int getNumber() 
	{
		return m_iNumber;
	}

	public void setNistIonizationPotential(double ionizationPotential) 
	{
		m_dNistIonizationPotential = ionizationPotential;
	}

	public void setFacIonizationPotential(double ionizationPotential) 
	{
		m_dFacIonizationPotential = ionizationPotential;
	}

	public void setAggrScaleFactor(int aggrScaleFactor) 
	{
		m_iAggrScaleFactor = aggrScaleFactor;
	}

	public void setElementNumber(int elementNumber) 
	{
		m_iElementNumber = elementNumber;
	}

	public void setId(int id) 
	{
		m_iId = id;
	}

	public void setNumber(int number) 
	{
		m_iNumber = number;
	}
	
    public String toString()
    {
    	StringBuffer sb 			= new StringBuffer();
		String       newline      	= System.getProperty( "line.separator" );
		NumberFormat numFormatter 	= new DecimalFormat("#.####");
		String       sIonizPot    	= numFormatter.format(m_dNistIonizationPotential); 
      
		StringBuilder strBuilder     = new StringBuilder();
		Formatter     formatter      = new Formatter(strBuilder);
      
//		formatter.format("%1$4s %2$3d %3$22s %4$7.2f",
//        		"Ion ",
//        		m_iNumber, 
//        		" ionization potential ",
//      		  	Double.parseDouble(sIonizPot));
		
		formatter.format("%1$4s %2$3d",
        		"Ion ",
        		m_iNumber);

      
      	sb.append(strBuilder.toString() + newline);
      	return sb.toString();
    }

}
