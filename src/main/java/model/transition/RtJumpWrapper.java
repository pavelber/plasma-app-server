package model.transition;


import java.util.Formatter;
import model.rank.LevelWrapper;
import response.RtJump;


public class RtJumpWrapper extends JumpWrapper
{
	private final RtJump m_rtJump;

	private static final String TO_STRING_FORMAT = "%1$25s %2$5d %3$7d %4$5d %5$5d";
	private final ETransitionProcess m_process = ETransitionProcess.Radiation;
	
	public RtJumpWrapper(final LevelWrapper source, final LevelWrapper target,
			final RtJump rtJump)
	{
		super();
		setSource(source);
		setTarget(target);
		m_rtJump = rtJump;
//		System.out.println(m_rtJump.getEinstein());
	}
	
	public RtJumpWrapper(final LevelWrapper source, final RtJump rtJump)
	{
		super();
		setSource(source);
		m_rtJump = rtJump;
	}

	@Override
	public double getOscillationStrength()
	{
		double res = -1;
		res = m_rtJump.getOscStrength();
		return res;
	}
	
	public double getWavelength()
	{
		double res = -1;
		res = m_rtJump.getWavelength();
		return res;
	}

	@Override
	public double getThreshold() 
	{
		double res = -1;
		res = 12400 / m_rtJump.getWavelength();
		return res;
	}

	@Override
	public ETransitionProcess getProcessType() 
	{
		return m_process;
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
