package model.transition;


import java.util.Formatter;

import model.rank.LevelWrapper;
import response.PiJump;

public class PiJumpWrapper extends JumpWrapper
{
	private static final String TO_STRING_FORMAT = "%1$25s %2$5d %3$7d %4$5d %5$5d";// %6$14s";
	private final PiJump m_piJump;
	private final ETransitionProcess m_process = ETransitionProcess.Photoionization;

	public PiJumpWrapper(final LevelWrapper source, final LevelWrapper target, 
			final PiJump piJump)
	{
		super();
		setSource(source);
		setTarget(target);
		m_piJump = piJump;
	}
	
	public PiJumpWrapper(final LevelWrapper source, final PiJump piJump)
	{
		super();
		setSource(source);
		m_piJump = piJump;
	}

	@Override
	public double getThreshold() 
	{
		double res = -1;
		res = m_piJump.getThreshold();
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
	    int iSrcIon = getSourceIonNumber(); 
		int iSrcLev = getSourceNumber();
		int iTrgIon = getTargetIonNumber(); 
		int iTrgLev = getTargetNumber();

	    StringBuilder stringBuilder = new StringBuilder();
	    Formatter formatter = new Formatter(stringBuilder);
	    
	    formatter.format(TO_STRING_FORMAT,
	    		m_process,
	    		iSrcIon, 
	    		iSrcLev,
	    		iTrgIon,
	    		iTrgLev
	    		);
	    
	    return stringBuilder.toString();
	}

}
