package fac2db.record;


import java.util.EnumMap;
import java.util.Map;

import fac2db.property.ETransitionProcess;



public class PointRecord 
{
    private static final String EXCITATION_TO_STRING 		= "ExPoint[ transition=%d, point=%d, energy=%s, coll-strength=%s, cross-section=%s ]";
    private static final String IONIZATION_TO_STRING 		= "CiPoint[ transition=%d, point=%d, energy=%s, coll-strength=%s, cross-section=%s ]";
    private static final String PHOTOIONIZATION_TO_STRING 	= "PiPoint[ transition=%d, point=%d, energy=%s, rr-cross-section=%s, pi-cross-section=%s, osc-strength=%s ]";

	private static ETransitionProcess m_process;

	private Map<EPiPointFields, Double> m_piRecord =
		new EnumMap<EPiPointFields, 
			Double>(EPiPointFields.class);

	private Map<ECiPointFields, Double> m_ciRecord =
		new EnumMap<ECiPointFields, 
			Double>(ECiPointFields.class);
	
	private Map<EExPointFields, Double> m_exRecord =
		new EnumMap<EExPointFields, 
			Double>(EExPointFields.class);

    public double get(final EPiPointFields fieldKey) 
    {
        return m_piRecord.get(fieldKey);
    }

    public double get(final ECiPointFields fieldKey) 
    {
        return m_ciRecord.get(fieldKey);
    }

    public double get(final EExPointFields fieldKey) 
    {
        return m_exRecord.get(fieldKey);
    }
    
    public void set(final EPiPointFields fieldKey, 
    		final double dValue) 
    {
    	m_piRecord.put(fieldKey, dValue);
    }
    
    public void set(final ETransitionProcess process, final double[] values) 
    {
    	m_process = process;

	    final EExPointFields[] exFieldKeys = EExPointFields.values();
	    final ECiPointFields[] ciFieldKeys = ECiPointFields.values();
	    final EPiPointFields[] piFieldKeys = EPiPointFields.values();

    	switch(process)
    	{
    	case Excitation:
    	    for (int i = 0; i < exFieldKeys.length; i++)
    	    {
    	    	m_exRecord.put(exFieldKeys[i], values[i]);
    	    }
    		break;
    	case Ionization:
    	    for (int i = 0; i < ciFieldKeys.length; i++)
    	    {
    	    	m_ciRecord.put(ciFieldKeys[i], values[i]);
    	    }
    		break;
    	case Photoionization:
    	    for (int i = 0; i < piFieldKeys.length; i++)
    	    {
    	    	m_piRecord.put(piFieldKeys[i], values[i]);
    	    }
    		break;
    	default:
    		break;
    	}
    }
    
    public void set(final Map<ECiPointFields, Double> map) 
    {
    	m_ciRecord = map;
    }

    
    public void set(final ECiPointFields fieldKey, 
    		final double dValue) 
    {
    	m_ciRecord.put(fieldKey, dValue);
    }
    
    public void set(final EExPointFields fieldKey, 
    		final double dValue) 
    {
    	m_exRecord.put(fieldKey, dValue);
    }
    
    @Override
    public final String toString()
    {
    	String res = null;
    	switch(m_process)
    	{
    	case Excitation:
    		res = excitationToString();
    		break;
    	case Ionization:
    		res = ionizationToString();
    		break;
    	case Photoionization:
    		res = photoionizationToString();
    		break;
    	default:
    		break;
    	}
        return res;
    }
    
    private String excitationToString()
    {
        return String.format(EXCITATION_TO_STRING, 
        		(int)m_exRecord.get(EExPointFields.tr_id).doubleValue(),
        		(int)m_exRecord.get(EExPointFields.point_number).doubleValue(),
        		m_exRecord.get(EExPointFields.energy),
        		m_exRecord.get(EExPointFields.coll_strength),
        		m_exRecord.get(EExPointFields.cross_section));
    }

    private String ionizationToString()
    {
        return String.format(IONIZATION_TO_STRING, 
        		(int)m_ciRecord.get(ECiPointFields.tr_id).doubleValue(),
        		(int)m_ciRecord.get(ECiPointFields.point_number).doubleValue(),
        		m_ciRecord.get(ECiPointFields.energy),
        		m_ciRecord.get(ECiPointFields.coll_strength),
        		m_ciRecord.get(ECiPointFields.cross_section));
    }
    
    private String photoionizationToString()
    {
        return String.format(PHOTOIONIZATION_TO_STRING, 
        		(int)m_piRecord.get(EPiPointFields.tr_id).doubleValue(),
        		(int)m_piRecord.get(EPiPointFields.point_number).doubleValue(),
        		m_piRecord.get(EPiPointFields.energy),
        		m_piRecord.get(EPiPointFields.rr_cross_section), 
        		m_piRecord.get(EPiPointFields.pi_cross_section),
        		m_piRecord.get(EPiPointFields.osc_strength));
    }

}
