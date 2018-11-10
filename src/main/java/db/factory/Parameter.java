package db.factory;

import java.util.EnumMap;
import java.util.Map;



public class Parameter 
{
	private Map<EParameter, Double> m_parameters =
		new EnumMap<EParameter, Double>(EParameter.class);
	
    public double get(final EParameter parameterKey) 
    {
        return m_parameters.get(parameterKey);
    }
    
    public void set(final EParameter parameterKey, final double dValue) 
    {
        m_parameters.put(parameterKey, dValue);
    }
}
