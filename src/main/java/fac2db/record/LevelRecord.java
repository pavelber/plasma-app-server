package fac2db.record;


import java.util.EnumMap;
import java.util.Map;


public class LevelRecord 
{
    private static final String LEVEL_TO_STRING = 
    	"Level[ number=%d, complex=%s, nrel=%s, rel=%s, statweight=%d, parity=%d, energy=%s, project=%d, element=%d, ion=%d ]";

	private Map<ELevelFields, String> m_levelRecord =
		new EnumMap<ELevelFields, String>(ELevelFields.class);

    public String get(final ELevelFields fieldKey) 
    {
        return m_levelRecord.get(fieldKey);
    }
    
    public void set(final String[] values) 
    {
	    final ELevelFields[] fieldKeys = ELevelFields.values();

	    for (int i = 0; i < fieldKeys.length; i++)
	    {
	    	m_levelRecord.put(fieldKeys[i], values[i]);
	    }
    }
    
    @Override
    public String toString()
    {
      return String.format(LEVEL_TO_STRING, 
    		  Integer.parseInt(m_levelRecord.get(ELevelFields.lev_number)),
    		  m_levelRecord.get(ELevelFields.complex),
    		  m_levelRecord.get(ELevelFields.nrel_config),
    		  m_levelRecord.get(ELevelFields.rel_config),
    		  Integer.parseInt(m_levelRecord.get(ELevelFields.stat_weight)),
    		  Integer.parseInt(m_levelRecord.get(ELevelFields.parity)),
    		  Double.parseDouble(m_levelRecord.get(ELevelFields.energy)),
    		  Integer.parseInt(m_levelRecord.get(ELevelFields.pr_id)),
    		  Integer.parseInt(m_levelRecord.get(ELevelFields.el_number)),
    		  Integer.parseInt(m_levelRecord.get(ELevelFields.ion_number))
    		  );
    }
}
