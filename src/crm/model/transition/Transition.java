package crm.model.transition;


import crm.model.State;
import crm.model.state.Level;


public class Transition
{
	private Parameter m_parameter;
	private State m_source;
	private State m_target;
	
	private long m_transitionId;
	
	public Parameter getParameter() {	return m_parameter;	}
	public State getSourceState()	{	return m_source;	}
	public State getTargetState() 	{	return m_target;	}
	
	public long getTransitionId() 	{	return m_transitionId;	}
	public void setTransitionId(long transitionId) 	 {	m_transitionId = transitionId;	}
	
	public void setParameter(Parameter parameter){	m_parameter = parameter;}
	public void setSourceState(State source) 	 {	m_source = source;	}
	public void setTargetState(State target) 	 {	m_target = target;	}
	
	public TransitionDirection getDirection()
	{
	    return new TransitionDirection(((Level)m_source).getIonNumber(), 
	    		((Level)m_source).getNumber(),
	    		((Level)m_target).getIonNumber(), 
	    		((Level)m_target).getNumber());
	}
}
