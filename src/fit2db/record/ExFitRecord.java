package fit2db.record;


import java.util.EnumMap;
import java.util.Map;

import db.EX_CS_FITS;


public class ExFitRecord 
{
    private static final String FIT_TO_STRING = 
    	"Fit[ transition=%d, method=%d, A=%s, B=%s, C=%s, D=%s, E=%s, F=%s ]";

	private Map<EX_CS_FITS, Double> m_fitRecord =
		new EnumMap<EX_CS_FITS, 
			Double>(EX_CS_FITS.class);

	private String m_sIsFitValid = "YES";
	
	public String getIsFitValid()
	{
		return m_sIsFitValid;
	}

	public void setIsFitValid(final String flagText)
	{
		m_sIsFitValid = flagText;
	}

	public double get(final EX_CS_FITS fieldKey) 
    {
        return m_fitRecord.get(fieldKey);
    }

	public void set(final double[] values) 
	{
		final EX_CS_FITS[] fitprops = EX_CS_FITS.values();
		for (int i = 0; i < fitprops.length; i++)
		{
			m_fitRecord.put(fitprops[i], values[i]);
		}
	}

	@Override
    public String toString()
    {
        return String.format(FIT_TO_STRING, 
        		(long)m_fitRecord.get(EX_CS_FITS.TR_ID).doubleValue(),
        		(int)m_fitRecord.get(EX_CS_FITS.METHOD_NUMBER).doubleValue(),
        		m_fitRecord.get(EX_CS_FITS.A),
        		m_fitRecord.get(EX_CS_FITS.B), 
        		m_fitRecord.get(EX_CS_FITS.C),
        		m_fitRecord.get(EX_CS_FITS.D),
        		m_fitRecord.get(EX_CS_FITS.E),
        		getIsFitValid());
        		//m_fitRecord.get(EX_CS_FITS.IS_FIT_VALID));
    }
}