package fac2db.transition;


import java.util.EnumMap;
import java.util.Map;

import fac2db.property.ECiPointProperty;
import fac2db.property.EExPointProperty;
import fac2db.property.EPiPointProperty;
import fac2db.property.ETransitionProcess;
import fac2db.record.PointRecord;



public class CrossSectionPoint 
{
    private static final String EXCITATION_TO_STRING = "Point[" +
		"point=%d, energy=%s, coll. strength=%s, cross-section=%s]";
    
    private static final String IONIZATION_TO_STRING = "Point[" +
    	"point=%d, energy=%s, coll. strength=%s, cross-section=%s]";
    
    private static final String PHOTOIONIZATION_TO_STRING = "Point[" +
		"point=%d, energy=%s, rr_cross-section=%s, pi_cross-section=%s, osc. strength=%s]";

    private Map<EPiPointProperty, Double> m_photoPoint =
		new EnumMap<EPiPointProperty, 
			Double>(EPiPointProperty.class);

	private Map<ECiPointProperty, Double> m_ionizPoint =
		new EnumMap<ECiPointProperty, 
			Double>(ECiPointProperty.class);
	
	private Map<EExPointProperty, Double> m_excitPoint =
		new EnumMap<EExPointProperty, 
			Double>(EExPointProperty.class);
	
	private ETransitionProcess m_process;

	public double get(final EPiPointProperty propertyKey) 
    {
        return m_photoPoint.get(propertyKey);
    }

    public double get(final ECiPointProperty propertyKey) 
    {
        return m_ionizPoint.get(propertyKey);
    }

    public double get(final EExPointProperty propertyKey) 
    {
        return m_excitPoint.get(propertyKey);
    }
    
    public void set(final ETransitionProcess process, final double[] values) 
    {
    	m_process = process;
    	
	    final EExPointProperty[] excitprops = EExPointProperty.values();
	    final ECiPointProperty[] ionizprops = ECiPointProperty.values();
	    final EPiPointProperty[] photoprops = EPiPointProperty.values();

    	switch(process)
    	{
    	case Excitation:
    	    for (int i = 0; i < excitprops.length; i++)
    	    {
    	    	m_excitPoint.put(excitprops[i], values[i]);
    	    }
    		break;
    	case Ionization:
    	    for (int i = 0; i < ionizprops.length; i++)
    	    {
    	    	m_ionizPoint.put(ionizprops[i], values[i]);
    	    }
    		break;
    	case Photoionization:
    	    for (int i = 0; i < photoprops.length; i++)
    	    {
    	    	m_photoPoint.put(photoprops[i], values[i]);
    	    }
    		break;
    	default:
    		break;
    	}
    }
    
    public final PointRecord toRecord(final long iTransitionId)
    {
    	PointRecord record = null;
    	switch(m_process)
    	{
    	case Excitation:
    		record = exPointToDb(ETransitionProcess.Excitation, iTransitionId);
    		break;
    	case Ionization:
    		record = ciPointToDb(ETransitionProcess.Ionization, iTransitionId);
    		break;
    	case Photoionization:
    		record = piPointToDb(ETransitionProcess.Photoionization, iTransitionId);
    		break;
    	default:
    		break;
    	}
    	return record;
    }

    private PointRecord exPointToDb(final ETransitionProcess process, 
    		final long iTransitionId)
    {
		double[] values = new double[5];
		values[0] = iTransitionId;
		values[1] = get(EExPointProperty.POINT_NUMBER);
		values[2] = get(EExPointProperty.ENERGY);
		values[3] = get(EExPointProperty.COLL_STRENGTH);
		values[4] = get(EExPointProperty.CROSS_SECTION);
		
		PointRecord record = new PointRecord();
		record.set(process, values);
		return record;
    }
    
    private PointRecord ciPointToDb(final ETransitionProcess process, 
    		final long iTransitionId)
    {
		double[] values = new double[5];
		values[0] = iTransitionId;
		values[1] = get(ECiPointProperty.POINT_NUMBER);
		values[2] = get(ECiPointProperty.ENERGY);
		values[3] = get(ECiPointProperty.COLL_STRENGTH);
		values[4] = get(ECiPointProperty.CROSS_SECTION);
		
		PointRecord record = new PointRecord();
		record.set(process, values);
		return record;
    }

    private PointRecord piPointToDb(final ETransitionProcess process, 
    		final long iTransitionId)
    {
		double[] values = new double[6];
		values[0] = iTransitionId;
		values[1] = get(EPiPointProperty.POINT_NUMBER);
		values[2] = get(EPiPointProperty.ENERGY);
		values[3] = get(EPiPointProperty.RR_CROSS_SECTION);
		values[4] = get(EPiPointProperty.PI_CROSS_SECTION);
		values[5] = get(EPiPointProperty.OSC_STRENGTH);
		
		PointRecord record = new PointRecord();
		record.set(process, values);
		return record;
    }
    
    @Override
    public final String toString()
    {
    	String sResult = null;
    	if(m_process == ETransitionProcess.Excitation)
    	{
            sResult = String.format(EXCITATION_TO_STRING, 
            		(int)get(EExPointProperty.POINT_NUMBER),
            		get(EExPointProperty.ENERGY),
            		get(EExPointProperty.COLL_STRENGTH), 
            		get(EExPointProperty.CROSS_SECTION));
    	}
    	else if(m_process == ETransitionProcess.Ionization)
    	{
            sResult = String.format(IONIZATION_TO_STRING, 
            		(int)get(ECiPointProperty.POINT_NUMBER),
            		get(ECiPointProperty.ENERGY),
            		get(ECiPointProperty.COLL_STRENGTH), 
            		get(ECiPointProperty.CROSS_SECTION));
    	}
    	else if(m_process == ETransitionProcess.Photoionization)
    	{
            sResult = String.format(PHOTOIONIZATION_TO_STRING, 
            		(int)get(EPiPointProperty.POINT_NUMBER),
            		get(EPiPointProperty.ENERGY),
            		get(EPiPointProperty.RR_CROSS_SECTION), 
            		get(EPiPointProperty.PI_CROSS_SECTION), 
            		get(EPiPointProperty.OSC_STRENGTH));
    	}
        
        return sResult;
    }
}
