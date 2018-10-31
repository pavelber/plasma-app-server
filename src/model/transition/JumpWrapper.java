package model.transition;


import java.util.Formatter;

import model.rank.LevelWrapper;
import request.Request;


public abstract class JumpWrapper
{
	private static final String TO_STRING_FORMAT = "%1$5d %2$7d %3$5d %4$5d";
	private LevelWrapper m_source;
	private LevelWrapper m_target;
	
	public void setSource(final LevelWrapper source)
	{
		m_source = source;
	}
	
	public void setTarget(final LevelWrapper target)
	{
		m_target = target;
	}

	public LevelWrapper getSource()
	{
		return m_source;
	}
	
	public LevelWrapper getTarget()
	{
		return m_target;
	}

	public int getSourceStatWeight()
	{
		int res = m_source.getStatWeight();
		return res;
	}

	public int getTargetStatWeight()
	{
		int res = -1;
		if(m_target != null)
		{
			res = m_target.getStatWeight();
		}
		return res;
	}
	
	public TransitionDirection getDirection()
	{
	    return new TransitionDirection(
	    		getSourceIonNumber(), 
	    		getSourceNumber(),
	    		getTargetIonNumber(), 
	    		getTargetNumber());
	}
	
	public Request getRequest()
	{
		return m_source.getRequest();
	}
	
	public int getSourceIonNumber()
	{
		return m_source.getIonNumber();
	}

	public int getTargetIonNumber()
	{
		int res = -1;
		if(m_target != null)
		{
			res = m_target.getIonNumber();
		}
		return res;
	}

	public int getSourceId()
	{
		return m_source.getId();
	}
	
	public int getTargetId()
	{
		int res = 0;
		if(m_target != null)
		{
			res = m_target.getId();
		}
		return res;
	}

	public int getSourceNumber()
	{
		return m_source.getNumber();
	}
	
	public int getTargetNumber()
	{
		int res = 0;
		if(m_target != null)
		{
			res = m_target.getNumber();
		}
		return res;
	}

	public abstract double getThreshold();	
	public abstract ETransitionProcess getProcessType();
	public abstract double getOscillationStrength();	
	
	@Override
	public String toString()
	{
	    StringBuilder stringBuilder = new StringBuilder();
	    Formatter formatter = new Formatter(stringBuilder);
	    
	    formatter.format(TO_STRING_FORMAT,
	    		getSourceIonNumber(), 
	    		getSourceNumber(),
	    		getTargetIonNumber(),
	    		getTargetNumber());
	    
	    return stringBuilder.toString();
	}
}
