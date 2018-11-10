package fit2db.record;

import java.util.EnumMap;
import java.util.Map;

import db.EXAB_TRANSITIONS;
import db.EX_CS_FITS;

public class ExTransitionRecord 
{
    private static final String TRANSITION_TO_STRING = 
    	"Transition[ transition=%d, osc_strength=%s ]";

	private Map<EXAB_TRANSITIONS, Double> m_trRecord =
		new EnumMap<EXAB_TRANSITIONS, 
			Double>(EXAB_TRANSITIONS.class);


	public double get(final EXAB_TRANSITIONS fieldKey) 
    {
        return m_trRecord.get(fieldKey);
    }

	public void set(final double[] values) 
	{
//		final db.EXAB_TRANSITIONS[] trprops = db.EXAB_TRANSITIONS.values();
//		for (int i = 0; i < trprops.length; i++)
//		{
//			m_trRecord.put(trprops[i], values[i]);
//		}
		
		m_trRecord.put(EXAB_TRANSITIONS.TR_PK, values[0]);
		m_trRecord.put(EXAB_TRANSITIONS.EX_OSC_STRENGTH, values[1]);
		
	}

	@Override
    public String toString()
    {
        return String.format(TRANSITION_TO_STRING, 
        		(long)m_trRecord.get(EX_CS_FITS.TR_ID).doubleValue(),
        		m_trRecord.get(EXAB_TRANSITIONS.EX_OSC_STRENGTH));
    }
}