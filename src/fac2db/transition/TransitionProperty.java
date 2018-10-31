package fac2db.transition;


import java.util.EnumMap;
import java.util.Map;

import fac2db.property.EAbTransitionProperty;
import fac2db.property.EAiTransitionProperty;
import fac2db.property.ECiTransitionProperty;
import fac2db.property.EExTransitionProperty;
import fac2db.property.EOscTransitionProperty;
import fac2db.property.EPiTransitionProperty;
import fac2db.property.ETransitionProcess;



public class TransitionProperty 
{
	private Map<EPiTransitionProperty, Double> m_piProperties =
		new EnumMap<EPiTransitionProperty, 
			Double>(EPiTransitionProperty.class);

	private Map<ECiTransitionProperty, Double> m_ciProperties =
		new EnumMap<ECiTransitionProperty, 
			Double>(ECiTransitionProperty.class);
	
	private Map<EExTransitionProperty, Double> m_exProperties =
		new EnumMap<EExTransitionProperty, 
			Double>(EExTransitionProperty.class);
	
	private Map<EAbTransitionProperty, Double> m_abProperties =
		new EnumMap<EAbTransitionProperty, 
			Double>(EAbTransitionProperty.class);
	
	private Map<EAiTransitionProperty, Double> m_aiProperties =
		new EnumMap<EAiTransitionProperty, 
			Double>(EAiTransitionProperty.class);

	private Map<EOscTransitionProperty, Double> m_oscProperties =
		new EnumMap<EOscTransitionProperty, 
			Double>(EOscTransitionProperty.class);

    public double get(final EPiTransitionProperty propertyKey) 
    {
        return m_piProperties.get(propertyKey);
    }

    public double get(final ECiTransitionProperty propertyKey) 
    {
        return m_ciProperties.get(propertyKey);
    }

    public double get(final EExTransitionProperty propertyKey) 
    {
        return m_exProperties.get(propertyKey);
    }
	
    public double get(final EAbTransitionProperty propertyKey) 
    {
        return m_abProperties.get(propertyKey);
    }

    public double get(final EAiTransitionProperty propertyKey) 
    {
        return m_aiProperties.get(propertyKey);
    }
    
    public double get(final EOscTransitionProperty propertyKey) 
    {
        return m_oscProperties.get(propertyKey);
    }

    
    public void set(final EPiTransitionProperty propertyKey, 
    		final double dValue) 
    {
    	m_piProperties.put(propertyKey, dValue);
    }
    
    public void set(final ETransitionProcess process, final double[] values) 
    {
	    final EExTransitionProperty[] excitprops = EExTransitionProperty.values();
	    final ECiTransitionProperty[] ionizprops = ECiTransitionProperty.values();
	    final EPiTransitionProperty[] photoprops = EPiTransitionProperty.values();
	    final EAiTransitionProperty[] autoprops = EAiTransitionProperty.values();
	    final EAbTransitionProperty[] spectrprops = EAbTransitionProperty.values();
	    final EOscTransitionProperty[] oscprops = EOscTransitionProperty.values();


    	switch(process)
    	{
    	case Excitation:
    	    for (int i = 0; i < excitprops.length; i++)
    	    {
    	    	m_exProperties.put(excitprops[i], values[i]);
    	    }
    		break;
    	case Ionization:
    	    for (int i = 0; i < ionizprops.length; i++)
    	    {
    	    	m_ciProperties.put(ionizprops[i], values[i]);
    	    }
    		break;
    	case Photoionization:
    	    for (int i = 0; i < photoprops.length; i++)
    	    {
    	    	m_piProperties.put(photoprops[i], values[i]);
    	    }
    		break;
    	case Autoionization:
    	    for (int i = 0; i < autoprops.length; i++)
    	    {
    	    	m_aiProperties.put(autoprops[i], values[i]);
    	    }
    		break;
    	case Absorbtion:
    	    for (int i = 0; i < spectrprops.length; i++)
    	    {
    	    	m_abProperties.put(spectrprops[i], values[i]);
    	    }
    		break;
    	case Oscillation:
    	    for (int i = 0; i < oscprops.length; i++)
    	    {
//    	    	System.out.println(spectrprops[i] + " " + values[i]);
    	    	m_oscProperties.put(oscprops[i], values[i]);
    	    }
    		break;
    	default:
    		break;
    	}
    }
    
    public void set(final Map<ECiTransitionProperty, Double> map) 
    {
    	m_ciProperties = map;
    }

    
    public void set(final ECiTransitionProperty propertyKey, 
    		final double dValue) 
    {
    	m_ciProperties.put(propertyKey, dValue);
    }
    
    public void set(final EExTransitionProperty propertyKey, 
    		final double dValue) 
    {
    	m_exProperties.put(propertyKey, dValue);
    }
    
    public void set(final EAbTransitionProperty propertyKey, 
    		final double dValue) 
    {
    	m_abProperties.put(propertyKey, dValue);
    }

    public void set(final EAiTransitionProperty propertyKey, 
    		final double dValue) 
    {
    	m_aiProperties.put(propertyKey, dValue);
    }
    
    public void set(final EOscTransitionProperty propertyKey, 
    		final double dValue) 
    {
    	m_oscProperties.put(propertyKey, dValue);
    }

}
