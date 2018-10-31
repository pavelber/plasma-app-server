package model.transition;


import java.util.Formatter;

import model.rank.LevelWrapper;
import response.AiJump;

public class AiJumpWrapper extends JumpWrapper
{
	private static final String TO_STRING_FORMAT = "%1$25s %2$5d %3$7d %4$5d %5$5d";
	private final AiJump m_aiJump;
	private final ETransitionProcess m_process = ETransitionProcess.Autoionization;

	public AiJumpWrapper(final LevelWrapper source, final LevelWrapper target, 
			final AiJump aiJump)
	{
		super();
		setSource(source);
		setTarget(target);
		m_aiJump = aiJump;
	}

	public AiJumpWrapper(final LevelWrapper source, final AiJump aiJump)
	{
		super();
		setSource(source);
		m_aiJump = aiJump;
	}

	@Override
	public double getThreshold() 
	{
		double res = -1;
		res = m_aiJump.getThreshold();
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
	    		iTrgLev);
	    
	    return stringBuilder.toString();
	}


}
