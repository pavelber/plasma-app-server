package fit2db.record;


import java.util.EnumMap;
import java.util.Map;

import db.CI_CS_FITS;


public class CiFitRecord 
{
    private static final String FIT_TO_STRING = 
    	"Fit[ transition=%d, method=%d, A=%s, B=%s, C=%s, D=%s, E=%s, F=%s ]";

	private Map<CI_CS_FITS, Double> m_fitRecord =
		new EnumMap<CI_CS_FITS, 
			Double>(CI_CS_FITS.class);

	private String m_sIsFitValid = "YES";
	
	public String getIsFitValid()
	{
		return m_sIsFitValid;
	}

	public double get(final CI_CS_FITS fieldKey) 
    {
        return m_fitRecord.get(fieldKey);
    }

	public void set(final double[] values) 
	{
		final CI_CS_FITS[] fitprops = CI_CS_FITS.values();
		for (int i = 0; i < fitprops.length; i++)
		{
			m_fitRecord.put(fitprops[i], values[i]);
		}
	}

	@Override
    public String toString()
    {
        return String.format(FIT_TO_STRING, 
        		(long)m_fitRecord.get(CI_CS_FITS.TR_ID).doubleValue(),
        		(int)m_fitRecord.get(CI_CS_FITS.METHOD_NUMBER).doubleValue(),
        		m_fitRecord.get(CI_CS_FITS.A),
        		m_fitRecord.get(CI_CS_FITS.B), 
        		m_fitRecord.get(CI_CS_FITS.C),
        		m_fitRecord.get(CI_CS_FITS.D),
        		m_fitRecord.get(CI_CS_FITS.IS_FIT_VALID));
    }
}