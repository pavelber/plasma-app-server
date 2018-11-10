package model.rank;


import model.Rank;
import model.RankNode;
import model.transition.ETransitionProcess;
import model.transition.JumpWrapper;

import request.Request;
import response.Level;
import util.EqualsUtil;
import util.HashCodeUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


public class LevelWrapper extends RankNode 
{
	private static final String TO_STRING_FORMAT = 
		"%1$5d %2$7d %3$10s %4$5d %5$14s";// %6$14s %7$14s %8$14s %9$14s %10$14s %11$14s %12$14s %13$14s";

	private Map<ETransitionProcess, List<JumpWrapper>> m_incomingTransitions;
	private Map<ETransitionProcess, List<JumpWrapper>> m_outcomingTransitions;
    
	private int 	m_iElementNumber;
	private int		m_iIonNumber;
	private Level 	m_level;
	private Request m_request;

	public LevelWrapper(String name) 
    {
        super(name);
        
        List<JumpWrapper> excitationInList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> excitationOutList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> radiationInList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> radiationOutList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> photoionizationInList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> photoionizationOutList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> ionizationInList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> ionizationOutList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> autoionizationInList = new ArrayList<JumpWrapper>();
        List<JumpWrapper> autoionizationOutList = new ArrayList<JumpWrapper>();
        
        Class<ETransitionProcess> klass = ETransitionProcess.class;
        
        m_incomingTransitions =	
        	new EnumMap<ETransitionProcess, List<JumpWrapper>>(klass);
        
        m_incomingTransitions.put(ETransitionProcess.Excitation, excitationInList);
        m_incomingTransitions.put(ETransitionProcess.Radiation, radiationInList);
        m_incomingTransitions.put(ETransitionProcess.Photoionization, photoionizationInList);
        m_incomingTransitions.put(ETransitionProcess.Ionization, ionizationInList);
        m_incomingTransitions.put(ETransitionProcess.Autoionization, autoionizationInList);
        
        m_outcomingTransitions = 
        	new EnumMap<ETransitionProcess, List<JumpWrapper>>(klass);
        
        m_outcomingTransitions.put(ETransitionProcess.Excitation, excitationOutList);
        m_outcomingTransitions.put(ETransitionProcess.Radiation, radiationOutList);
        m_outcomingTransitions.put(ETransitionProcess.Photoionization, photoionizationOutList);
        m_outcomingTransitions.put(ETransitionProcess.Ionization, ionizationOutList);
        m_outcomingTransitions.put(ETransitionProcess.Autoionization, autoionizationOutList);
    }
	
    public Enumeration<Object> getSubUnits() 
    {
         return new Enumeration<Object> ()  
         {
            public boolean hasMoreElements() { return false; }
            
	        public Object nextElement() 
	          {
		            throw new NoSuchElementException("No more units");
	          }
          };
    }
    
    protected boolean isLegalParent(Rank parentOrg ) 
    {
         boolean result = (parentOrg instanceof IonWrapper )?  true : false;//Ion
         return result;
    }
    
    public Level getLevel()			{ return m_level;				}
	public int getId() 				{ return m_level.getLevId(); 	}
	public int getIonNumber() 		{ return m_iIonNumber; 			}
	public int getNumber() 			{ return m_level.getLevNum(); 	}
	public int getStatWeight() 		{ return m_level.getG(); 		}
	public int getElementNumber() 	{ return m_iElementNumber; 		}
	public double getEnergy() 		{ return m_level.getLevEnergy();}
	public String getConfiguration(){ return m_level.getLevConfig();}
    public Request getRequest()		{ return m_request;				}
	
	public void setElementNumber(final int elementNumber) 	{ m_iElementNumber = elementNumber; }
	public void setIonNumber(final int ionNumber)			{ m_iIonNumber = ionNumber; 		}
	public void setLevel(final Level level)					{ m_level = level; 					}
	public void setRequest(final Request request)			{ m_request = request; 				}
	
    public boolean equals(Object that)
    {
    	  if ( this == that ) return true;
    	  if ( !(that instanceof LevelWrapper) ) return false;
    	  LevelWrapper thatLevel = (LevelWrapper)that;
    	  return
    	    EqualsUtil.areEqual(this.m_level.getLevId(), thatLevel.m_level.getLevId());
    }

    public int hashCode()
    {
    	int result = HashCodeUtil.SEED;
    	result = HashCodeUtil.hash(result, m_level.getLevId());
    	return result;
    }

    /**
    * returns empty enumeration
    */
    public Enumeration<Rank> getSubRanks() 
    {
	     return new Enumeration<Rank> ()  
	     {
	        public boolean hasMoreElements() 
	        { 
	        	return false; 
	        }
	        
	        public Rank nextElement() 
	        {
		        throw new NoSuchElementException("No more sublevels");
	        }
	      };
    }

    public List<JumpWrapper> getIncomingTransitionsFor(final ETransitionProcess processType) 
    {
    	List<JumpWrapper> res = new ArrayList<JumpWrapper>();
    	
    	final Iterator<JumpWrapper> 
    	transitions = m_incomingTransitions.get(processType).iterator();
    	while(transitions.hasNext())
    	{
    		JumpWrapper transition = transitions.next();
    		res.add(transition);
    	}
        return res;
    }

    public List<JumpWrapper> getOutcomingTransitionsFor(final ETransitionProcess processType) 
    {
    	List<JumpWrapper> res = new ArrayList<JumpWrapper>();
    	
    	final Iterator<JumpWrapper> 
    	transitions = m_outcomingTransitions.get(processType).iterator();
    	while(transitions.hasNext())
    	{
    		JumpWrapper transition = transitions.next();
    		res.add(transition);
    	}
        return res;
    }
    
    public void addIncomingTransitionsFor(final ETransitionProcess processType, final List<JumpWrapper> transitions)
    {
    	List<JumpWrapper> currentTransitions = m_incomingTransitions.get(processType);
    	currentTransitions.addAll(transitions);
    }
    
    public void addOutcomingTransitionsFor(final ETransitionProcess processType, final List<JumpWrapper> transitions)
    {
    	List<JumpWrapper> currentTransitions = m_outcomingTransitions.get(processType);
    	if(transitions != null) currentTransitions.addAll(transitions);
    }
    
    public void addIncomingTransition(final JumpWrapper transition)
    {
    	final ETransitionProcess process = transition.getProcessType(); 
    	List<JumpWrapper> transitions = m_incomingTransitions.get(process);
    	transitions.add(transition);
    }
    
    public void addOutcomingTransition(final JumpWrapper transition)
    {
    	final ETransitionProcess process = transition.getProcessType();
    	List<JumpWrapper> transitions = m_outcomingTransitions.get(process);
    	transitions.add(transition);
    }
    
	@Override
	public String toString()
	{
	    NumberFormat numFormatter = new DecimalFormat("0.00000E00");
	    StringBuilder stringBuilder = new StringBuilder();
	    Formatter formatter = new Formatter(stringBuilder);
	    
	      
	    formatter.format(TO_STRING_FORMAT,
	    		m_iIonNumber, 
	    		m_level.getLevNum(),
	    		m_level.getLevConfig(),
	    		m_level.getG(),
	    		numFormatter.format(m_level.getLevEnergy())
				);
	      
	    return stringBuilder.toString();
	}
}


