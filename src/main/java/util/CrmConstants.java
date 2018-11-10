package util;

public class CrmConstants 
{
	public static final String AI_FILE_NAME 	= "AUTO.INP";
	public static final String AI_FILE_PART 	= "AUTO.PART";
	public static final String EX_FILE_NAME 	= "EXCIT.INP";
	public static final String CI_FILE_NAME 	= "BCFP.INP";
	public static final String PI_FILE_NAME 	= "RREC.INP";
	public static final String I_FILE_NAME 		= "IONS.INP";
	public static final String RT_FILE_NAME 	= "SPECTR.INP";
	public static final String IN1R_FILE_NAME 	= "IN1R.INP";
	public static final String IN1F_FILE_NAME 	= "IN1F.INP";
	
	public final static String LI_LIKE_TITLE 	= "Li-like";
	public final static String HE_LIKE_TITLE	= "He-like";
	public final static String BE_LIKE_TITLE 	= "Be-like";
	public final static String B_LIKE_TITLE	= "B-like";
	
	public static final String AUTOIONIZATION_TITLE = "Autoionizating states";
	
	public static final String LI_LIKE_AI_TITLE = 
		"Li-like AutoIonizing states             ---------------------------------- AI ------------------";

	public static final String RALCHENKO_IN1_PRELUDE = 
		"002 2 0-1 0 0 1e+50 0 000 0  0 0 0.0e+00 0 0 00.0e+001.0     2.0  0\n" +
		"Tolerances: I/FInt = 1.D-03: SystInt = 1.D-09: StMatr = 1.D-13\n" +
		"El Temp (eV) = a + b*t + c*t*t : a = 2.00000D+02; b = 0.00000D+10; c = 0.00000D+15\n" +
		"2d Temp (eV) = a + b*t + c*t*t : a = 0.00000D+00; b = 0.00000D+00; c = 0.00000D+15\n" +
		"% of 2nd T   = a + b*t + c*t*t : a = 0.00000D-02; b = 0.00000D+00; c = 0.00000D+15\n" +
		"Beam at (eV) = a + b*t + c*t*t : a = 0.00000D+00; b = 0.00000D+00; c = 0.00000D+15\n" +
		"FWHM (eV)    = a + b*t + c*t*t : a = 0.00000D+00; b = 0.00000D+00; c = 0.00000D+15\n" +
		"% of beam    = a + b*t + c*t*t : a = 0.00000D-00; b = 0.00000D+00; c = 0.00000D+15\n" +
		"IDens (cm-3) = A + B*t + C*t*t : a = 1.00000D+10; B = 0.00000D+00; C = 0.00000D+15\n" +
		"   or     A / (B + C*t + D*t*t)  D = 0.00000D+00\n" +
		"EDens (cm-3) = A + B*t + C*t*t : A = 1.00000D+10; B = 0.00000D+00; C = 0.00000D+15\n" +
		"   or     A / (B + C*t + D*t*t)  D = 0.00000D-03\n" +
		"Step= 1.0D-09 sec:No of steps=1\n";

	public static final String SPECTR_PRELUDE = 
		" 3.0 20.0 20000\n";
	
	public static final String BCFP_PRELUDE = 	
		"  iSS  iQS  fSS  fQS           D              -A               B               C\n" +
		"-----------------------------------------------------------------------------------\n";					
	
	public static final String IONS_PRELUDE = 
		"  iSS  iQS  fSS  fQS                     Electron Impact Ionization                    Mthd                         Photoionization                Threshold\n";	
	
	public static final String EXCIT_PRELUDE = 
		"  SS   #1   #2   Mthd        A          B            C            D            E            F          Osc.Strngth\n" +
		"------------------------------------------------------------------------------------------------------------------\n";
		
	public static final String AUTO_PRELUDE = 
		"  SSi AIQS#  SSf  QSf#       WAI          DE(eV)\n";

	public static final String FISHER_IN1_PRELUDE = 
		"SpS  QSs   AI   RY    FAC PI   NIST PI\n" +
		"------------------------------------------\n";

	public static final String RALCHENKO_LEVEL_FORMAT = 
    	"%1$8s %2$11d %3$14.3f %4$20s %5$7d";
	
	public static final String RALCHENKO_LEVEL_FORMAT_WITH_NOTES = 
    	"%1$8s %2$11d %3$14.3f %4$20s %5$7d %6$5s %7$-10s %8$14.3f";
	
	public static final String FISHER_LEVEL_FORMAT = 
    	"%1$8s %2$3d %3$10.3f %4$10.3f %5$4d %6$4d";
	
	public static final String FISHER_LEVEL_FORMAT_WITH_NOTES = 
    	"%1$8s %2$3d %3$10.3f %4$10.3f %5$4d %6$4d %7$5s %8$-9s %9$7.3f";

	public static final String BCFP_FORMAT = 
		"%1$5d %2$4d %3$4d %4$4d %5$14.3E %6$15.3E %7$15.3E %8$15.3E %9$14d";

	public static final String RREC_FORMAT = 
		"%1$5d %2$6d %3$6d %4$6d %5$13.3E %6$12.3E %7$12.3E %8$12.3E %10$15.3E %11$15.3E %12$15.3E %13$15.3E %14$15.3E %15$15.3E %16$14d";

	public static final String SPECTR_FORMAT = "%1$2d %2$4d %3$4d %4$8.3f %5$12.4f %6$11.3E %7$13.6E";

	public static final String EXCIT_FORMAT = "%1$4d %2$4d %3$4d %4$4d %5$13.3E %6$12.3E %7$12.3E %8$12.3E %9$12.3E %10$12s %11$15.3E %12$14d";

	public static final String IONS_FORMAT = "%1$5d %2$4d %3$4d %4$4d %5$14.3E %6$15.3E %7$15.3E %8$15.3E " +
		"%9$6d %10$13.3E %11$12.3E %12$12.3E %13$12.3E %14$14.6E";
	
	public static final String AUTO_FORMAT = 
		"%1$5d %2$4d %3$4d %4$4d %5$14.3E %6$14.6E";
	
	public static final String IN_BCFP_AUTO_FORMAT = 
		"%1$5d %2$4d %3$4d %4$4d %5$14.3E %7$15.3E %8$15.3E %9$15.3E";
}
