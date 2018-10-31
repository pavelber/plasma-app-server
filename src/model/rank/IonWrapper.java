package model.rank;


//import java.text.DecimalFormat;
//import java.text.NumberFormat;
import java.util.Formatter;

import model.CompositeRankNode;
import model.Rank;
import response.Ion;


public class IonWrapper extends CompositeRankNode 
{
	private int 	m_iElementNumber;
	private Ion     m_ion;

    public IonWrapper ( String aName ) 
    {
        super ( aName );
    }

    public boolean isLegalParent( Rank parentOrg ) 
    {
        boolean result = (parentOrg instanceof ElementWrapper)?  true : false;
        return result;
    }
    
    public Ion getIon()
    {
    	return m_ion;
    }

	public double getIonizationPotential() 
	{
		return m_ion.getPotential();
	}

	public int getElementNumber() 
	{
		return m_iElementNumber;
	}

	public int getNumber() 
	{
		return m_ion.getCharge();
	}

	public void setElementNumber(int elementNumber) 
	{
		m_iElementNumber = elementNumber;
	}

	public void setIon(Ion ion) 
	{
		m_ion = ion;
	}

    public String toString()
    {
    	StringBuffer sb 			= new StringBuffer();
		String       newline      	= System.getProperty( "line.separator" );
//		NumberFormat numFormatter 	= new DecimalFormat("#.####");
//		String       sIonizPot    	= numFormatter.format(m_ion.getPotential()); 
      
		StringBuilder strBuilder     = new StringBuilder();
		Formatter     formatter      = new Formatter(strBuilder);
      
		formatter.format("%1$4s %2$3d",
        		"Ion ",
        		m_ion.getCharge());
      
      	sb.append(strBuilder.toString() + newline);
      	return sb.toString();
    }

}
