package model.transition;


import java.util.Formatter;

import model.rank.LevelWrapper;
import response.ExJump;


public class ExJumpWrapper extends JumpWrapper
{
	private static final String TO_STRING_FORMAT = "%1$25s %2$5d %3$7d %4$5d %5$5d %6$14s %7$14s";
	private final ExJump m_exJump;
	private final ETransitionProcess m_process = ETransitionProcess.Excitation;

	public ExJumpWrapper(final LevelWrapper source, final LevelWrapper target, 
			final ExJump exJump)
	{
		super();
		setSource(source);
		setTarget(target);
		m_exJump = exJump;
	}
	
	public ExJumpWrapper(final LevelWrapper source, final ExJump exJump)
	{
		super();
		setSource(source);
		m_exJump = exJump;
	}

	@Override
	public double getThreshold() 
	{
		double res = -1;
		res = m_exJump.getThreshold();
		return res;
	}

	@Override
	public ETransitionProcess getProcessType() 
	{
		return m_process;
	}

	@Override
	public double getOscillationStrength() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String toString()
	{
	    StringBuilder stringBuilder = new StringBuilder();
	    Formatter formatter = new Formatter(stringBuilder);
	    
	    formatter.format(TO_STRING_FORMAT,
	    		m_process,
	    		getSourceIonNumber(), 
	    		getSourceNumber(),
	    		getTargetIonNumber(),
	    		getTargetNumber()
	    		);
	    
	    return stringBuilder.toString();
	}

}
