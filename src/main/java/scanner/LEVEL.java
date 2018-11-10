package scanner;


public final class LEVEL 
{
	public final static String LEV_ILEV 			= "\\p{Blank}+\\d+\\p{Blank}+-1";
	public final static String LEV_ENERGY 			= "\\d\\.(\\d){8}E[+-](\\d){2}";
	public final static String LEV_TWIN_J 			= "\\p{Blank}+[01]\\p{Blank}+(\\d){3}\\p{Blank}+\\d+";
	public final static String LEV_COMPLEX 			= "(\\p{Blank}+\\d+\\*\\d+)+";
	public final static String LEV_CONFIGURATION	= "(\\p{Blank}+\\d+[spdfghik]\\d+)+";
	public final static String LEV_R_CONFIGURATION 	= "(\\p{Blank}+\\d+[spdfghik][+-]\\d+\\(\\d+\\)\\d+)+";
	public final static String LEV_PARITY 			= "\\s[01]\\s+";
	public final static String LEV_VNL 				= "[01]\\s+\\d\\d\\d\\s+";
    public final static String LEVELS_COUNT 		= "^NLEV\\s+\\=\\s+\\d+$";
    public final static String LEVEL 				= "^\\s+\\d+\\s+-1\\s+\\d\\.(\\d){8}E[+-]" +
															"(\\d){2}\\s+[01]\\s+(\\d){3}\\s+\\d+" +
															"(\\s+\\d+\\*\\d+)+(\\s+\\d+[spdfghik]\\d+)+" +
															"(\\s+\\d+[spdfghik][+-]\\d+\\(\\d+\\)\\d+)+";
}
