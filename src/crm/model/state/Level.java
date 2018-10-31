package crm.model.state;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import converter.CrmConfigurationConverter;

import util.CrmConstants;
import util.EqualsUtil;

import crm.model.State;
import crm.model.StateNode;
import crm.model.transition.Transition;
import crm.model.transition.TransitionProcess;


public class Level extends StateNode 
{
	private Map<TransitionProcess, List<Transition>> m_incomingTransitions;
	private Map<TransitionProcess, List<Transition>> m_outcomingTransitions;
    
	private int 	m_iId;
	private int 	m_iNumber;
	private String 	m_sConfiguration;
	private String 	m_sRelConfiguration;
	private int 	m_iStatWeight;
	private int 	m_iParity;
	private double 	m_dEnergy;
	private int 	m_iElementNumber;
	private int 	m_iIonNumber;
	private int 	m_iProjectId;
	
	private double m_dHLikeOscStrength;
	
	private double  m_dEnergyByNist;
	private String 	m_sTerm;
	private String 	m_sConfigurationByNist;
	
	private CrmConfigurationConverter m_confConverter;

	public Level(String name) 
    {
        super(name);
        
        List<Transition> excitationInList = new ArrayList<Transition>();
        List<Transition> excitationOutList = new ArrayList<Transition>();
        List<Transition> radiationInList = new ArrayList<Transition>();
        List<Transition> radiationOutList = new ArrayList<Transition>();
        List<Transition> photoionizationInList = new ArrayList<Transition>();
        List<Transition> photoionizationOutList = new ArrayList<Transition>();
        List<Transition> ionizationInList = new ArrayList<Transition>();
        List<Transition> ionizationOutList = new ArrayList<Transition>();
        List<Transition> autoionizationInList = new ArrayList<Transition>();
        List<Transition> autoionizationOutList = new ArrayList<Transition>();
        
        Class<TransitionProcess> klass = TransitionProcess.class;
        
        m_incomingTransitions =	
        	new EnumMap<TransitionProcess, List<Transition>>(klass);
        
        m_incomingTransitions.put(TransitionProcess.EXCITATION, excitationInList);
        m_incomingTransitions.put(TransitionProcess.RADIATION, radiationInList);
        m_incomingTransitions.put(TransitionProcess.PHOTOIONIZATION, photoionizationInList);
        m_incomingTransitions.put(TransitionProcess.COLLISIONAL_IONIZATION, ionizationInList);
        m_incomingTransitions.put(TransitionProcess.AUTOIONIZATION, autoionizationInList);
        
        m_outcomingTransitions = 
        	new EnumMap<TransitionProcess, List<Transition>>(klass);
        
        m_outcomingTransitions.put(TransitionProcess.EXCITATION, excitationOutList);
        m_outcomingTransitions.put(TransitionProcess.RADIATION, radiationOutList);
        m_outcomingTransitions.put(TransitionProcess.PHOTOIONIZATION, photoionizationOutList);
        m_outcomingTransitions.put(TransitionProcess.COLLISIONAL_IONIZATION, ionizationOutList);
        m_outcomingTransitions.put(TransitionProcess.AUTOIONIZATION, autoionizationOutList);
    }
	
    public Enumeration getSubUnits() 
    {
         return new Enumeration ()  
         {
            public boolean hasMoreElements() { return false; }
            
	        public Object nextElement() 
	          {
		            throw new NoSuchElementException("No more units");
	          }
          };
    }
    
    protected boolean isLegalParent(State parentOrg ) 
    {
         boolean result = (parentOrg instanceof Ion )?  true : false;//Ion
         return result;
    }
    
	public double getHLikeOscStrength() 
	{
		return m_dHLikeOscStrength;
	}

	public void setHLikeOscStrength(double HLikeOscStrength) 
	{
		m_dHLikeOscStrength = HLikeOscStrength;
	}

	public int getId() { return m_iId; }
	public int getIonNumber() { return m_iIonNumber; }
	public int getNumber() { return m_iNumber; }
	public int getParity() { return m_iParity; }
	public int getProjectId() { return m_iProjectId; }
	public int getStatWeight() { return m_iStatWeight; }
	public int getElementNumber() { return m_iElementNumber; }
	public double getEnergy() { return m_dEnergy;}
	public String getConfiguration() { return m_sConfiguration; }
	public String getRelConfiguration() { return m_sRelConfiguration; }

	public void setEnergy(double energy) { 	m_dEnergy = energy; }
	public void setElementNumber(int elementNumber) { m_iElementNumber = elementNumber; }
	public void setId(int id) { m_iId = id;	}
	public void setIonNumber(int ionNumber)	{ m_iIonNumber = ionNumber; }
	public void setNumber(int number) { m_iNumber = number; }
	public void setParity(int parity) { m_iParity = parity; }
	public void setProjectId(int projectId) { m_iProjectId = projectId; }
	public void setStatWeight(int statWeight) { m_iStatWeight = statWeight; }
	public void setConfiguration(String configuration) { m_sConfiguration = configuration; }
	public void setRelConfiguration(String relConfiguration) { 	m_sRelConfiguration = relConfiguration; }

	public void setConfigurationConverter(CrmConfigurationConverter converter)
	{
		m_confConverter = converter;
	}
	
    public boolean equals(Object that)
    {
    	  if ( this == that ) return true;
    	  if ( !(that instanceof Level) ) return false;
    	  Level thatLevel = (Level)that;
    	  return
    	    EqualsUtil.areEqual(this.m_sConfiguration, thatLevel.m_sConfiguration) &&
    	    EqualsUtil.areEqual(this.m_iStatWeight, thatLevel.m_iStatWeight);
    }

    public double getEnergyByNist() 			{ return m_dEnergyByNist;	}
	public String getConfigurationByNist() 		{ return m_sConfigurationByNist;	}
	public String getTerm() 					{ return m_sTerm;	}
    
	public void setEnergyByNist(double energy) 						{ m_dEnergyByNist = energy;	}
	public void setConfigurationByNist(String configurationByNist) 	{ m_sConfigurationByNist = configurationByNist;	}
	public void setTerm(String term) 								{ m_sTerm = term;	}

    /**
    * returns empty enumeration
    */
    public Enumeration<State> getSubStates() 
    {

     return new Enumeration<State> ()  
     {
        public boolean hasMoreElements() 
        { 
        	return false; 
        }
        
        public State nextElement() 
        {
	        throw new NoSuchElementException("No more sublevels");
        }
      };
    }

    public List<Transition> getIncomingTransitionsFor(final TransitionProcess processType) 
    {
    	List<Transition> res = new ArrayList<Transition>();
    	
    	final Iterator<Transition> 
    	transitions = m_incomingTransitions.get(processType).iterator();
    	while(transitions.hasNext())
    	{
    		Transition transition = transitions.next();
    		res.add(transition);
    	}
        return res;
    }

    public List<Transition> getOutcomingTransitionsFor(final TransitionProcess processType) 
    {
    	List<Transition> res = new ArrayList<Transition>();
    	
    	final Iterator<Transition> 
    	transitions = m_outcomingTransitions.get(processType).iterator();
    	while(transitions.hasNext())
    	{
    		Transition transition = transitions.next();
    		res.add(transition);
    	}
        return res;
    }
    
    public void addIncomingTransitionsFor(final TransitionProcess processType, final List<Transition> transitions)
    {
    	List<Transition> currentTransitions = m_incomingTransitions.get(processType);
    	currentTransitions.addAll(transitions);
    }
    
    public void addOutcomingTransitionsFor(final TransitionProcess processType, final List<Transition> transitions)
    {
    	List<Transition> currentTransitions = m_outcomingTransitions.get(processType);
    	if(transitions != null) currentTransitions.addAll(transitions);
    }
    
    public void addIncomingTransitionFor(final TransitionProcess processType, final Transition transition)
    {
    	List<Transition> transitions = m_incomingTransitions.get(processType);
    	transitions.add(transition);
    }
    
    public void addOutcomingTransitionFor(final TransitionProcess processType, final Transition transition)
    {
    	List<Transition> transitions = m_outcomingTransitions.get(processType);
    	transitions.add(transition);
    }
    
    public String toRalchenko()
    {
      StringBuffer sb = new StringBuffer();
      NumberFormat numFormatter = new DecimalFormat("#.###");
      String sEnergy = numFormatter.format(m_dEnergy);
      String[] configParts = m_sConfiguration.split(" ");
      String sConfiguration = null;
      if(configParts.length > 2)
      {
    	  sConfiguration = configParts[configParts.length - 2] + "  " + configParts[configParts.length - 1];
      }
      else if(configParts.length == 2)
      {
    	  sConfiguration = configParts[0] + "  " + configParts[1];
      }
      else if(configParts.length < 2)
      {
    	  sConfiguration = configParts[0];
      }
     
      
      StringBuilder stringBuilder = new StringBuilder();
      Formatter formatter = new Formatter(stringBuilder);
      sb.append(" ");
      
      if(m_sTerm == null) 
      {
          formatter.format(CrmConstants.RALCHENKO_LEVEL_FORMAT,
	  			sConfiguration, 
  	    		m_iStatWeight,
  	    		Double.parseDouble(sEnergy), 
  	    		"  0.00e+00 0.00e+00",
  	    		m_iNumber,
  	    		"     ");
      }
      else
      {
          formatter.format(CrmConstants.RALCHENKO_LEVEL_FORMAT_WITH_NOTES,
	  				sConfiguration, 
  	    		m_iStatWeight,
  	    		Double.parseDouble(sEnergy), 
  	    		"   0.00e+00 0.00e+00",
  	    		m_iNumber,
  	    		"     ",
  	    		m_sTerm,
  	    		m_dEnergyByNist);
      }
      
      sb.append(stringBuilder.toString());
      return sb.toString();
    }
    
    public String toFisher(int iPrevIonMaxLevel)
    {
      StringBuffer sb = new StringBuffer();
      NumberFormat numFormatter = new DecimalFormat("#.###");
      String sEnergy = numFormatter.format(m_dEnergy);
      String[] configParts = m_sConfiguration.split(" ");
      String sConfiguration = null;
      
      if(configParts.length > 2)
      {
    	  sConfiguration = m_confConverter.convert(configParts[configParts.length - 2] + 
    			  "  " + configParts[configParts.length - 1]);
      }
      else if(configParts.length == 2)
      {
    	  sConfiguration = m_confConverter.convert(configParts[0] + 
    			  "  " + configParts[1]);
      }
      else if(configParts.length < 2)
      {
    	  sConfiguration = configParts[0];
      }
      
      StringBuilder stringBuilder = new StringBuilder();
      Formatter formatter = new Formatter(stringBuilder);
      sb.append(" ");
      
      if(m_sTerm == null) 
      {
         formatter.format(CrmConstants.FISHER_LEVEL_FORMAT,
	  			sConfiguration, 
  	    		m_iStatWeight,
  	    		Double.parseDouble(sEnergy), 
  	    		m_dHLikeOscStrength,
  	    		m_iNumber,
  	    		m_iNumber > 0 ? (m_iNumber + iPrevIonMaxLevel) : (iPrevIonMaxLevel + (-1) * m_iNumber));
      }
      else
      {
          formatter.format(CrmConstants.FISHER_LEVEL_FORMAT_WITH_NOTES,
	  			sConfiguration, 
  	    		m_iStatWeight,
  	    		Double.parseDouble(sEnergy), 
  	    		m_dHLikeOscStrength,
  	    		m_iNumber,
  	    		m_iNumber > 0 ? (m_iNumber + iPrevIonMaxLevel) : (iPrevIonMaxLevel + (-1) * m_iNumber) ,
  	    		" ",
  	    		m_sTerm,
  	    		m_dEnergyByNist);
      }
      
      sb.append(stringBuilder.toString());
      return sb.toString();
    }

    
    public String toString()
    {
      StringBuffer sb = new StringBuffer();
      //NumberFormat numFormatter = new DecimalFormat("#.###");
      //String sEnergy = numFormatter.format(m_dEnergy);

      if(m_sTerm == null) m_sTerm = ""; 
      StringBuilder stringBuilder = new StringBuilder();
      Formatter formatter = new Formatter(stringBuilder);
      
      formatter.format("%1$1s %2$4d",
	    		"#", 
	    		m_iNumber);

//      if(m_sTerm == null)
//      {
//          formatter.format("%1$1s %2$4d %3$13s %4$2d %5$8s %6$9.3f",
//    	    		"#", 
//    	    		m_iNumber,
//    	    		"  st. weight ",
//    	    		m_iStatWeight,
//    	    		" energy ", 
//    	    		Double.parseDouble(sEnergy));
//
//      }
//      else
//      {
//          formatter.format("%1$1s %2$4d %3$13s %4$2d %5$8s %6$9.3f %7$1s %8$-10s",
//  	    		"#", 
//  	    		m_iNumber,
//  	    		"  st. weight ",
//  	    		m_iStatWeight,
//  	    		" energy ", 
//  	    		Double.parseDouble(sEnergy), 
//  	    		" ",
//  	    		m_sTerm);
//
//      }

      sb.append(stringBuilder.toString());
      return sb.toString();
    }
}


