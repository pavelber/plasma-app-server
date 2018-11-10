package fac2db.record;


import java.util.EnumMap;
import java.util.Map;

import fac2db.property.ETransitionProcess;



public class TransitionRecord 
{
    private static final String EXCITATION_TO_STRING 		= "ExTransition[ source=%d, target=%d, threshold=%s, bethe=%s, born1=%s, born2=%s ]";
    private static final String IONIZATION_TO_STRING 		= "CiTransition[ source=%d, target=%d, threshold=%s ]";
    private static final String PHOTOIONIZATION_TO_STRING 	= "PiTransition[ source=%d, target=%d, threshold=%s ]";
    private static final String ABSORBTION_TO_STRING 		= "AbTransition[ source=%d, target=%d, threshold=%s, osc_strength=%s, rate=%s, multipole=%s ]";
    private static final String AUTOIONIZATION_TO_STRING 	= "AiTransition[ source=%d, target=%d, threshold=%s, rate=%s, dc_strength=%s ]";
    
	private static ETransitionProcess m_process;
	
	private Map<EPiTransitionFields, Double> m_piRecord =
		new EnumMap<EPiTransitionFields, 
			Double>(EPiTransitionFields.class);

	private Map<ECiTransitionFields, Double> m_ciRecord =
		new EnumMap<ECiTransitionFields, 
			Double>(ECiTransitionFields.class);
	
	private Map<EExTransitionFields, Double> m_exRecord =
		new EnumMap<EExTransitionFields, 
			Double>(EExTransitionFields.class);
	
	private Map<EAbTransitionFields, Double> m_abRecord =
		new EnumMap<EAbTransitionFields, 
			Double>(EAbTransitionFields.class);
	
	private Map<EAiTransitionFields, Double> m_aiRecord =
		new EnumMap<EAiTransitionFields, 
			Double>(EAiTransitionFields.class);

    public double get(final EPiTransitionFields fieldKey) 
    {
        return m_piRecord.get(fieldKey);
    }

    public double get(final ECiTransitionFields fieldKey) 
    {
        return m_ciRecord.get(fieldKey);
    }

    public double get(final EExTransitionFields fieldKey) 
    {
        return m_exRecord.get(fieldKey);
    }
	
    public double get(final EAbTransitionFields fieldKey) 
    {
        return m_abRecord.get(fieldKey);
    }

    public double get(final EAiTransitionFields fieldKey) 
    {
        return m_aiRecord.get(fieldKey);
    }
    
    public void set(final EPiTransitionFields fieldKey, 
    		final double dValue) 
    {
    	m_piRecord.put(fieldKey, dValue);
    }
    
    public void set(final ETransitionProcess process, final double[] values) 
    {
    	m_process = process;

    	switch(process)
    	{
    	case Excitation:
    	    final EExTransitionFields[] exFieldKeys = EExTransitionFields.values();
    	    for (int i = 0; i < exFieldKeys.length; i++)
    	    {
    	    	m_exRecord.put(exFieldKeys[i], values[i]);
    	    }
    		break;
    	case Ionization:
    	    final ECiTransitionFields[] ciFieldKeys = ECiTransitionFields.values();
    	    for (int i = 0; i < ciFieldKeys.length; i++)
    	    {
    	    	m_ciRecord.put(ciFieldKeys[i], values[i]);
    	    }
    		break;
    	case Photoionization:
    	    final EPiTransitionFields[] piFieldKeys = EPiTransitionFields.values();
    	    for (int i = 0; i < piFieldKeys.length; i++)
    	    {
    	    	m_piRecord.put(piFieldKeys[i], values[i]);
    	    }
    		break;
    	case Autoionization:
    	    final EAiTransitionFields[] aiFieldKeys = EAiTransitionFields.values();
    	    for (int i = 0; i < aiFieldKeys.length; i++)
    	    {
    	    	m_aiRecord.put(aiFieldKeys[i], values[i]);
    	    }
    		break;
    	case Absorbtion:
    	    final EAbTransitionFields[] abFieldKeys = EAbTransitionFields.values();
    	    for (int i = 0; i < abFieldKeys.length; i++)
    	    {
    	    	m_abRecord.put(abFieldKeys[i], values[i]);
    	    }
    		break;
    	default:
    		break;
    	}
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
    	case Absorbtion:
    		res = absorptionToString();
    		break;
    	case Autoionization:
    		res = autoionizationToString();
    		break;
    	default:
    		break;
    	}
        return res;
    }


    private String autoionizationToString()
    {
      return String.format(AUTOIONIZATION_TO_STRING, 
    		  (int)m_aiRecord.get(EAiTransitionFields.source).doubleValue(),
    		  (int)m_aiRecord.get(EAiTransitionFields.target).doubleValue(),
    		  m_aiRecord.get(EAiTransitionFields.threshold),
    		  m_aiRecord.get(EAiTransitionFields.rate),
    		  m_aiRecord.get(EAiTransitionFields.dc_strength)
    		  );
    }

    private String absorptionToString()
    {
      return String.format(ABSORBTION_TO_STRING, 
    		  (int)m_abRecord.get(EAbTransitionFields.source).doubleValue(),
    		  (int)m_abRecord.get(EAbTransitionFields.target).doubleValue(),
    		  m_abRecord.get(EAbTransitionFields.threshold),
    		  m_abRecord.get(EAbTransitionFields.osc_strength),
    		  m_abRecord.get(EAbTransitionFields.rate),
    		  m_abRecord.get(EAbTransitionFields.multipole)
    		  );
    }

    private String excitationToString()
    {
        return String.format(EXCITATION_TO_STRING, 
        		(int)m_exRecord.get(EExTransitionFields.source).doubleValue(),
        		(int)m_exRecord.get(EExTransitionFields.target).doubleValue(),
        		m_exRecord.get(EExTransitionFields.threshold),
        		m_exRecord.get(EExTransitionFields.bethe), 
        		m_exRecord.get(EExTransitionFields.born1),
        		m_exRecord.get(EExTransitionFields.born2));
    }
    
    private String ionizationToString()
    {
        return String.format(IONIZATION_TO_STRING, 
        		(int)m_ciRecord.get(ECiTransitionFields.source).doubleValue(),
        		(int)m_ciRecord.get(ECiTransitionFields.target).doubleValue(),
        		m_ciRecord.get(ECiTransitionFields.threshold));
    }
    
    private String photoionizationToString()
    {
    	return String.format(PHOTOIONIZATION_TO_STRING, 
        		(int)m_piRecord.get(EPiTransitionFields.source).doubleValue(),
        		(int)m_piRecord.get(EPiTransitionFields.target).doubleValue(),
        		m_piRecord.get(EPiTransitionFields.threshold));
    }
}
