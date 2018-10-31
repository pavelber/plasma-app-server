package crm.model.state;

import crm.model.CompositeStateNode;
import crm.model.State;


public class Element extends CompositeStateNode  
{
	private String 	m_sSymbol;
	private int 	m_iNumber;
	private String 	m_sConfiguration;
	private double 	m_dWeight;

    public Element( String aName ) 
    {
        super(aName);
    }

    public boolean isLegalParent( State parentOrg ) 
    {
        boolean result = ( parentOrg instanceof Calculation )?  true : false;
            return result;
    }
    
	public double getWeight() 
	{
		return m_dWeight;
	}
	public int getNumber() 
	{
		return m_iNumber;
	}
	public String getConfiguration() 
	{
		return m_sConfiguration;
	}

	public String getSymbol() 
	{
		return m_sSymbol;
	}
	public void setWeight(double weight) 
	{
		m_dWeight = weight;
	}
	public void setNumber(int number) 
	{
		m_iNumber = number;
	}
	public void setConfiguration(String configuration) 
	{
		m_sConfiguration = configuration;
	}

	public void setSymbol(String symbol) 
	{
		m_sSymbol = symbol;
	}
}
