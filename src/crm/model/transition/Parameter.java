package crm.model.transition;


import java.util.EnumMap;
import java.util.Map;


public class Parameter 
{
	private Map<TransitionParameter, Double> m_parameters =
		new EnumMap<TransitionParameter, Double>(TransitionParameter.class);
	
    public double get(final TransitionParameter parameterKey) 
    {
        return m_parameters.get(parameterKey);
    }
    
    public void set(final TransitionParameter parameterKey, final double dValue) 
    {
        m_parameters.put(parameterKey, dValue);
    }
}
