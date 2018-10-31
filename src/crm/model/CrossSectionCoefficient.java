package crm.model;


public  class CrossSectionCoefficient  
{
	private String m_sNumber;
 	private String m_sValue;
 	
 	public CrossSectionCoefficient() {}
 
    public String getNumber() { return m_sNumber; }
    public String getValue()  { return m_sValue;  }

    public void setNumber( String rhs ) { m_sNumber = rhs; }
    public void setValue(  String rhs ) { m_sValue  = rhs; }
    
    public String toString()
    {
    	return "CrossSection coefficient: number='" + m_sNumber + "' value='" + m_sValue + "'";
    }
}		

