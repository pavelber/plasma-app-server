package fac2db.transition;


import java.util.EnumMap;
import java.util.Map;

import fac2db.property.EExTransitionCoefficients;



public class ExcitationCoefficients 
{
    private static final String COEFFS_TO_SQL = 
		" %s, %s, %s )";

    private static final String COEFFS_TO_STRING = "Coefficients[" +
    		"Bethe=%s, Born1=%s, Born2=%s]";
    
	private Map<EExTransitionCoefficients, Double> m_properties =
		new EnumMap<EExTransitionCoefficients, 
			Double>(EExTransitionCoefficients.class);
	
    public double get(final EExTransitionCoefficients propertyKey) 
    {
        return m_properties.get(propertyKey);
    }
    
    public void set(final double[] values) 
    {
	    final EExTransitionCoefficients[] 
	         coeffs = EExTransitionCoefficients.values();

	    for (int i = 0; i < values.length; i++)
	    {
	    	m_properties.put(coeffs[i], values[i]);
	    }
    }
    
    public final String toSql()
    {
        return String.format(COEFFS_TO_SQL, 
        		get(EExTransitionCoefficients.Bethe), 
        		get(EExTransitionCoefficients.Born1), 
        		get(EExTransitionCoefficients.Born2));
    }

    
    @Override
    public final String toString()
    {
        return String.format(COEFFS_TO_STRING, 
        		get(EExTransitionCoefficients.Bethe), 
        		get(EExTransitionCoefficients.Born1), 
        		get(EExTransitionCoefficients.Born2));
    }
}
