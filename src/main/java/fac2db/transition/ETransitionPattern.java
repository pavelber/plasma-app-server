package fac2db.transition;


import java.util.regex.Pattern;


enum ETransitionPattern
{
	Element(scanner.ELEMENT.ELEMENT),
	ElectronsCount(scanner.ELEMENT.ELECTRONS_COUNT),
	BlocksCount(scanner.COMMON.BLOCKS_COUNT),
	TransCount(scanner.COMMON.TRANS_COUNT),
	FacQmode(scanner.COMMON.QMODE),
	PointsCount(scanner.COMMON.POINTS_COUNT),	
	CiTransition(scanner.TRANSITION.CI_TRANSITION),
	ExTransition(scanner.TRANSITION.EX_TRANSITION),
	PiTransition(scanner.TRANSITION.PH_TRANSITION),
	RTransition(scanner.TRANSITION.TR_TRANSITION),
	AiTransition(scanner.TRANSITION.AI_TRANSITION),
	CiCrossPoints(scanner.TRANSITION.CI_CROSS_POINTS),
	ExCrossPoints(scanner.TRANSITION.EX_CROSS_POINTS),
	PiCrossPoints(scanner.TRANSITION.PH_CROSS_POINTS),
	ExCoefficients(scanner.TRANSITION.EX_COEFFS);

    private Pattern m_pattern;
    
    private ETransitionPattern(final String s)
    {
		m_pattern = Pattern.compile(s);
    }
    
    Pattern getPattern()
    {
    	return m_pattern;
    }
}
