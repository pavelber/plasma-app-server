package scanner;


public final class TRANSITION
{
	public final static int    CI_POINT_SIZE	= 3;
	public final static int    EX_POINT_SIZE	= 3;
	public final static int    PH_POINT_SIZE	= 4;

	public final static String CI_TRANSITION 	= "([ ]+\\d+){4}[ ]+\\d\\.\\d+E[+-](\\d){2}[ ]+[0-3]";
	public final static String EX_TRANSITION 	= "([ ]+\\d+){4}[ ]+\\d\\.\\d+E[+-](\\d){2}[ ]+[0-3]";
	public final static String PH_TRANSITION 	= "([ ]+\\d+){4}[ ]+\\d\\.\\d+E[+-](\\d){2}[ ]+[0-3]";
	public final static String TR_TRANSITION 	= "(\\p{Blank}+\\d+){4}(\\p{Blank}+-?\\d\\.\\d+E[+-]\\d\\d){4}";
	public final static String AI_TRANSITION 	= "(\\p{Blank}+\\d+){4}(\\p{Blank}+-?\\d\\.\\d+E[+-]\\d\\d){3}";
	
	public final static String CI_CROSS_POINTS 	= "([ ]+-?\\d\\.\\d+E[+-](\\d){2}){3}";
	public final static String EX_CROSS_POINTS 	= "([ ]?[ ]?-?\\d\\.\\d+E[+-](\\d){2}){3}";
	public final static String PH_CROSS_POINTS 	= "([ ]+-?\\d\\.\\d+E[+-](\\d){2}){4}";

	public final static String EX_COEFFS 		= "([ ]?[ ]?-?\\d\\.\\d+E[+-](\\d){2}){3}";
}
