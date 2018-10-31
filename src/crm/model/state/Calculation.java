package crm.model.state;


import crm.model.CompositeStateNode;
import crm.model.State;


public class Calculation extends CompositeStateNode 
{
	private int 	m_iId;
	private int 	m_iElementNumber;
	private int 	m_iStartIonNumber;
	private int 	m_iEndIonNumber;
	private int 	m_iAiStateInitialNumber;

     public Calculation( String aName ) 
     {
        super(aName);
     }

     public boolean isLegalParent( State parentOrg )
     {
         return false;
     }
     
 	public int getElementNumber() 	{ return m_iElementNumber; }
	public int getEndIonNumber() 	{ return m_iEndIonNumber; }
	public int getAiStateInitialNumber() 	{ return m_iAiStateInitialNumber; }
	public int getId() 				{ return m_iId; }
	public int getStartIonNumber() 	{ return m_iStartIonNumber; }

	public Calculation setElementNumber(int elementNumber) 					
	{ 
		m_iElementNumber = elementNumber;	
		return this;
	}
	public Calculation setEndIonNumber(int endIonNumber) 						
	{ 
		m_iEndIonNumber = endIonNumber; 
		return this;
	}
	public Calculation setAiStateInitialNumber(int number) 					
	{ 
		m_iAiStateInitialNumber = number;
		return this;
	}
	public Calculation setId(int projectId) 							
	{ 
		m_iId = projectId; 
		return this;
	}
	public Calculation setStartIonNumber(int startIonNumber) 					
	{ 
		m_iStartIonNumber = startIonNumber;
		return this;
	}
}