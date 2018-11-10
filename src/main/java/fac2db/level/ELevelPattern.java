package fac2db.level;

import java.util.regex.Pattern;


enum ELevelPattern
{
	Element(scanner.ELEMENT.ELEMENT),
	ElectronsCount(scanner.ELEMENT.ELECTRONS_COUNT),
	LevILev(scanner.LEVEL.LEV_ILEV),
    LevEnergy(scanner.LEVEL.LEV_ENERGY),
    LevTwinJ(scanner.LEVEL.LEV_TWIN_J),
    LevComplex(scanner.LEVEL.LEV_COMPLEX),
    LevConfiguration(scanner.LEVEL.LEV_CONFIGURATION),
    LevRConfiguration(scanner.LEVEL.LEV_R_CONFIGURATION),
    LevParity(scanner.LEVEL.LEV_PARITY),
    LevVnl(scanner.LEVEL.LEV_VNL),
    LevelsCount(scanner.LEVEL.LEVELS_COUNT),
    Level(scanner.LEVEL.LEVEL);
    

    private Pattern m_pattern;
    
    
    private ELevelPattern(final String s)
    {
		m_pattern = Pattern.compile(s);
    }
    
    Pattern getPattern()
    {
    	return m_pattern;
    }
}
