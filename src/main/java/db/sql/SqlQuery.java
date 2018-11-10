package db.sql;


final class SqlQuery 
{
	public static final String GET_ION = 
		"SELECT " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.IONS 					+ "." +
			db.IONS.IONIZATION_POTENTIAL 	+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.IONS 					+ "." +
			db.IONS.AGGR_SCALE_FACTOR 		+ 
		" FROM " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.IONS 					+
		" WHERE " 							+
			db.IONS.EL_NUMBER 				+ " = ? " +
		" AND " 							+
			db.IONS.ION_NUMBER 				+ " = ? ";
    
	public static final String GET_LEVELS = 
		"SELECT " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+ "." +
			db.LEVELS.LEVEL_PK 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+ "." +
			db.LEVELS.LEV_NUMBER 			+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+ "." +
			db.LEVELS.CONFIGURATION 		+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+ "." +
			db.LEVELS.STAT_WEIGHT 			+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+ "." +
			db.LEVELS.ENERGY 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+ "." +
			db.LEVELS.H_LIKE_OSC_STRENGTH 	+ 
		" FROM " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+
		" WHERE " 							+
			db.LEVELS.PR_ID 				+ " = ? " +
		" AND " 							+
			db.LEVELS.ION_NUMBER 			+ " = ? ";

	public static final String EX_POINTS_QUERY = 
		"SELECT * " 						+
		" FROM " 							+ 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_POINTS 			+
		" WHERE " 							+ 
			db.EX_CS_POINTS.TR_ID 			+ " = ? ";
	
	public static final String CI_POINTS_QUERY = 
		"SELECT * " 						+
		" FROM " 							+ 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_POINTS 			+
		" WHERE " 							+ 
			db.CI_CS_POINTS.TR_ID 			+ " = ? ";

	public static final String PI_POINTS_QUERY = 
		"SELECT * " 						+
		" FROM " 							+ 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_POINTS 			+
		" WHERE " 							+ 
			db.PI_CS_POINTS.TR_ID 			+ " = ? ";

	public static final String CI_FIT_QUERY = 
		"SELECT " 							+ 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_FITS 			+ "." +
			db.CI_CS_FITS.A 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_FITS 			+ "." +
			db.CI_CS_FITS.B 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_FITS 			+ "." +
			db.CI_CS_FITS.C 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_FITS 			+ "." +
			db.CI_CS_FITS.D 				+ 
		" FROM " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_FITS 			+
		" WHERE " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_FITS 			+ "." + 
			db.CI_CS_FITS.TR_ID 			+ " = ? " +
		" AND " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_FITS 			+ "." + 
			db.CI_CS_FITS.METHOD_NUMBER 	+ " = 2 " +
		" AND " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.CI_CS_FITS 			+ "." + 
			db.CI_CS_FITS.IS_FIT_VALID 		+ " = 'YES' ";


	public static final String PI_FIT_QUERY = 
		"SELECT " 						+ 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+ "." +
			db.PI_CS_FITS.A 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+ "." +
			db.PI_CS_FITS.B 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+ "." +
			db.PI_CS_FITS.C 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+ "." +
			db.PI_CS_FITS.D 				+  ", " + 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+ "." +
			db.PI_CS_FITS.METHOD_NUMBER 	+ 
		" FROM " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+
		" WHERE " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+ "." + 
			db.PI_CS_FITS.TR_ID 			+ " = ? " +
		" AND " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+ "." + 
			db.PI_CS_FITS.METHOD_NUMBER 	+ " = 4 " +
		" AND " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.PI_CS_FITS 			+ "." + 
			db.PI_CS_FITS.IS_FIT_VALID 		+ " = 'YES' ";

	public static final String EX_FIT_QUERY = 
		"SELECT " 							+ 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+ "." +
			db.EX_CS_FITS.METHOD_NUMBER 	+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+ "." +
			db.EX_CS_FITS.A 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+ "." +
			db.EX_CS_FITS.B 				+ ", " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+ "." +
			db.EX_CS_FITS.C					+  ", " + 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+ "." +
			db.EX_CS_FITS.D 				+  ", " +  
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+ "." +
			db.EX_CS_FITS.E 				+ 
		" FROM " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+
		" WHERE " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+ "." + 
			db.EX_CS_FITS.TR_ID 			+ " = ? " +
		" AND " 							+
		"( " 								+
				db.SCHEMA.APP 				+ "." + 
				db.TABLE.EX_CS_FITS 		+ "." + 
				db.EX_CS_FITS.METHOD_NUMBER + " = 0 " + 
			" OR " 							+ 
				db.SCHEMA.APP 				+ "." + 
				db.TABLE.EX_CS_FITS 		+ "." + 
				db.EX_CS_FITS.METHOD_NUMBER + " = 5 " + 
			" OR " 							+ 
				db.SCHEMA.APP 				+ "." + 
				db.TABLE.EX_CS_FITS 		+ "." + 
				db.EX_CS_FITS.METHOD_NUMBER + " = 11 " +
		")" 								+
		" AND " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.EX_CS_FITS 			+ "." + 
			db.EX_CS_FITS.IS_FIT_VALID 		+ " = 'YES' ";

        
	public static final String IONIZATION_TR_QUERY = 
		"SELECT " + 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.I_TRANSITIONS 			+ "." +
			db.I_TRANSITIONS.TR_PK			+  ", " + 
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.I_TRANSITIONS 			+ "." +
			db.I_TRANSITIONS.TARGET 		+  ", " +  
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.I_TRANSITIONS 			+ "." +
			db.I_TRANSITIONS.THRESHOLD 		+ 			
		" FROM " 							+
			db.TABLE.I_TRANSITIONS 			+
		" WHERE " 							+
			db.I_TRANSITIONS.SOURCE 		+ " = ? ";

	public static final String EXCITATION_TR_QUERY = 
		"SELECT " + 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.TR_PK 			+ ", " +
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC	+  ", " + 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.THRESHOLD 		+  ", " +  
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.EX_OSC_STRENGTH + 
		" FROM " 								+
			db.TABLE.EXAB_TRANSITIONS 			+
		" WHERE " 								+
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+ " = ? ";

	public static final String ABSORPTION_TR_QUERY = 
		"SELECT " + 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.TR_PK 			+ ", " +
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG	+  ", " + 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.AB_WAVELENGTH 	+  ", " +  
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.AB_RATE 		+ 
		" FROM " 								+ 
			db.TABLE.EXAB_TRANSITIONS 			+
		" WHERE " 								+
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+ " = ?";

	public static final String AUTOIONIZATION_TR_QUERY = 
		"SELECT " + 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.AI_TRANSITIONS 			+ "." +
			db.AI_TRANSITIONS.TARGET			+  ", " + 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.AI_TRANSITIONS 			+ "." +
			db.AI_TRANSITIONS.RATE 				+  ", " +  
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.AI_TRANSITIONS 			+ "." +
			db.AI_TRANSITIONS.THRESHOLD 		+ 
		" FROM " 								+ 
			db.TABLE.AI_TRANSITIONS 			+
		" WHERE " 								+ 
			db.AI_TRANSITIONS.SOURCE 			+ " = ? ";

	public static final String GET_ELEMENT_QUERY =	
		"SELECT * " 							+
		" FROM " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.ELEMENTS 					+
		" WHERE " 								+ 
			db.ELEMENTS.EL_NUMBER 				+ " = ? " ;

	public static final String GET_ALL_PROJECTS =	
		"SELECT * FROM " 						+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.PROJECTS;

	public static final String GET_PROJECT =	
		"SELECT * FROM " 						+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.PROJECTS 					+
		" WHERE " 								+ 
			db.PROJECTS.PROJECT_PK 				+ " = ? ";
	
	public static final String GET_USERS_QUERY =	
		"SELECT * " 							+
		" FROM " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.USERS 						+
		" WHERE " 								+ 
			db.USERS.USER_NAME 					+ " = ? " ;

	public static final String UPDATE_USERS_QUERY =	
		"UPDATE " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.USERS 						+
		" SET " 								+
			db.USERS.PASSWORD 					+ " = ?" + 
		" WHERE " 								+
			db.USERS.USER_NAME 					+ " = ?";













// fac2db

	public static final String UPDATE_FAC_IONS_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.FAC_IONS					+
		" SET " 								+
			db.FAC_IONS.FAC_POTENTIAL 			+ " = ? " +
		" WHERE " 								+
			db.FAC_IONS.PR_ID 					+ " = ? " + 
		" AND " 								+ 	
			db.FAC_IONS.ION_NUMBER 				+ " = ? ";
	
	public static final String INSERT_FAC_IONS_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.FAC_IONS 					+ 
			"(" 								+ 
				db.FAC_IONS.ION_NUMBER 			+ "," +  
				db.FAC_IONS.FAC_POTENTIAL		+ "," +  
				db.FAC_IONS.PR_ID 				+ "," +  
				db.FAC_IONS.EL_NUMBER  			+  
			")" 								+  
			" VALUES (?, ?, ?, ?)";
	
	public static final String INSERT_PROJECT_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.PROJECTS 					+ 
			"(" 								+ 
				db.PROJECTS.PR_NAME 			+ "," +  
				db.PROJECTS.EL_NUMBER			+ "," +  
				db.PROJECTS.START_ION_NUMBER 	+ "," +  
				db.PROJECTS.END_ION_NUMBER  	+  
			")" 								+  
			" VALUES (?, ?, ?, ?)";

	
	public static final String INSERT_EX_TRANSITION_QUERY = 
		"INSERT INTO " 									+ 
			db.SCHEMA.APP 								+ "." + 
			db.TABLE.EXAB_TRANSITIONS 					+ 
			"(" 										+ 
				db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 		+ "," + 
				db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 		+ "," +  
				db.EXAB_TRANSITIONS.THRESHOLD 			+ "," +  
				db.EXAB_TRANSITIONS.EX_BETHE 			+ "," +  
				db.EXAB_TRANSITIONS.EX_BORN1 			+ "," +  
				db.EXAB_TRANSITIONS.EX_BORN2  			+  
			")" 										+  
			" VALUES (?, ?, ?, ?, ?, ?)";

	public static final String UPDATE_EX_TRANSITION_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS			+
		" SET " 								+
			db.EXAB_TRANSITIONS.EX_BETHE 		+ " = ?, " +		
			db.EXAB_TRANSITIONS.EX_BORN1 		+ " = ?, " +
			db.EXAB_TRANSITIONS.EX_BORN2 		+ " = ? " +
		" WHERE " 								+
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+ " = ? " + 
		" AND " 								+ 	
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+ " = ? ";

	public static final String UPDATE_CI_TRANSITION_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS				+
		" SET " 								+
			db.I_TRANSITIONS.THRESHOLD 			+ " = ? " +		
		" WHERE " 								+
			db.I_TRANSITIONS.SOURCE 			+ " = ? " + 
		" AND " 								+ 	
			db.I_TRANSITIONS.TARGET 			+ " = ? ";

	public static final String INSERT_CI_TRANSITION_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS 				+ 
			"(" 								+ 
				db.I_TRANSITIONS.SOURCE 		+ "," + 
				db.I_TRANSITIONS.TARGET 		+ "," +  
				db.I_TRANSITIONS.THRESHOLD 		+   
			")" 								+  
			" VALUES (?, ?, ?)";

	public static final String INSERT_PI_TRANSITION_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS 				+ 
			"(" 								+ 
				db.I_TRANSITIONS.SOURCE 		+ "," + 
				db.I_TRANSITIONS.TARGET 		+ "," +  
				db.I_TRANSITIONS.THRESHOLD 		+   
			")" 								+  
			" VALUES (?, ?, ?)";

	public static final String UPDATE_PI_TRANSITION_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS				+
		" SET " 								+
			db.I_TRANSITIONS.THRESHOLD 			+ " = ? " +		
		" WHERE " 								+
			db.I_TRANSITIONS.SOURCE 			+ " = ? " + 
		" AND " 								+ 	
			db.I_TRANSITIONS.TARGET 			+ " = ? ";

	public static final String UPDATE_AB_TRANSITION_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS			+
		" SET " 								+
			db.EXAB_TRANSITIONS.AB_WAVELENGTH 	+ " = ?, " +		
			db.EXAB_TRANSITIONS.AB_OSC_STRENGTH + " = ?, " +
			db.EXAB_TRANSITIONS.AB_RATE 		+ " = ?, " +
			db.EXAB_TRANSITIONS.AB_MULTIPOLE  	+ " = ? " +  
		" WHERE " 								+
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+ " = ? " + 
		" AND " 								+ 	
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+ " = ? ";

	public static final String UPDATE_RT_RATE_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS			+
		" SET " 								+
			db.EXAB_TRANSITIONS.AB_WAVELENGTH 	+ " = ?, " +		
			db.EXAB_TRANSITIONS.AB_RATE 		+ " = ? " +
		" WHERE " 								+
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+ " = ? " + 
		" AND " 								+ 	
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+ " = ? ";

	public static final String UPDATE_AI_RATE_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.AI_TRANSITIONS				+
		" SET " 								+
			db.AI_TRANSITIONS.THRESHOLD	 		+ " = ?, " +		
			db.AI_TRANSITIONS.RATE 				+ " = ? " +
		" WHERE " 								+
			db.AI_TRANSITIONS.SOURCE 			+ " = ? " + 
		" AND " 								+ 	
			db.AI_TRANSITIONS.TARGET 			+ " = ? ";

	public static final String INSERT_AB_TRANSITION_QUERY = 
		"INSERT INTO " 									+ 
			db.SCHEMA.APP 								+ "." + 
			db.TABLE.EXAB_TRANSITIONS 					+ 
			"(" 										+ 
				db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 		+ "," +  
				db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 		+ "," + 
				db.EXAB_TRANSITIONS.THRESHOLD 			+ "," +  
				db.EXAB_TRANSITIONS.AB_WAVELENGTH 		+ "," +  
				db.EXAB_TRANSITIONS.AB_OSC_STRENGTH 	+ "," +  
				db.EXAB_TRANSITIONS.AB_RATE  		 	+ "," +  
				db.EXAB_TRANSITIONS.AB_MULTIPOLE  		+  
			")" 										+  
			" VALUES (?, ?, ?, ?, ?, ?, ?)";

	public static final String INSERT_AI_TRANSITION_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.AI_TRANSITIONS 			+ 
			"(" 								+ 
				db.AI_TRANSITIONS.SOURCE 		+ "," + 
				db.AI_TRANSITIONS.TARGET 		+ "," +  
				db.AI_TRANSITIONS.THRESHOLD 	+ "," +  
				db.AI_TRANSITIONS.RATE 			+ "," +  
				db.AI_TRANSITIONS.DC_STRENGTH 	+   
			")" 								+  
			" VALUES (?, ?, ?, ?, ?)";

	public static final String UPDATE_AI_TRANSITION_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.AI_TRANSITIONS				+
		" SET " 								+
			db.AI_TRANSITIONS.RATE 				+ " = ?, " +		
			db.AI_TRANSITIONS.DC_STRENGTH 		+ " = ? " +
		" WHERE " 								+
			db.AI_TRANSITIONS.SOURCE 			+ " = ? " + 
		" AND " 								+ 	
			db.AI_TRANSITIONS.TARGET 			+ " = ? ";

	public static final String SELECT_IONIZATION_TR_PK_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS 				+ "." +
			db.I_TRANSITIONS.TR_PK 				+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS 				+
		" WHERE " 								+
			db.I_TRANSITIONS.SOURCE 			+ " = ? " + 
		" AND " 								+
			db.I_TRANSITIONS.TARGET 			+ " = ? ";

	public static final String SELECT_EXCITATION_TR_PK_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.TR_PK 			+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+
		" WHERE " 								+
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+ " = ? " + 
		" AND " 								+
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+ " = ? ";

	public static final String SELECT_LEVELS_COUNT_QUERY = 
		"SELECT COUNT " 						+
		"( " 									+ 
			db.LEVELS.LEV_NUMBER 				+ 
		" ), " 									+
			db.LEVELS.ION_NUMBER 				+ 
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS 					+
		" WHERE " 								+
			db.LEVELS.PR_ID 					+ " = ? " + 
		" GROUP BY " 							+
			db.LEVELS.ION_NUMBER;

	public static final String SELECT_ION_POTENTIAL_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.IONS 						+ "." +
			db.IONS.IONIZATION_POTENTIAL 		+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.IONS 						+
		" WHERE " 								+
			db.IONS.EL_NUMBER					+ " = ? " + 
		" AND " 								+ 
			db.IONS.ION_NUMBER					+ " = ? ";

	public static final String SELECT_PROJECT_PK_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.PROJECTS 					+ "." +
			db.PROJECTS.PROJECT_PK 				+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.PROJECTS 					+
		" WHERE " 								+
			db.PROJECTS.PR_NAME					+ " = ? ";

	public static final String INSERT_PI_POINT_QUERY = 
		"INSERT INTO " 								+ 
			db.SCHEMA.APP 							+ "." + 
			db.TABLE.PI_CS_POINTS 					+ 
			"(" 									+ 
				db.PI_CS_POINTS.TR_ID 				+ "," + 
				db.PI_CS_POINTS.POINT_NUMBER 		+ "," +  
				db.PI_CS_POINTS.ENERGY 				+ "," +  
				db.PI_CS_POINTS.RR_CROSS_SECTION 	+ "," +  
				db.PI_CS_POINTS.PI_CROSS_SECTION	+ "," +   
				db.PI_CS_POINTS.OSC_STRENGTH 		+   
			")" 									+  
			" VALUES (?, ?, ?, ?, ?, ?)";
		
	public static final String INSERT_CI_POINT_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.CI_CS_POINTS 				+ 
			"(" 								+ 
				db.CI_CS_POINTS.TR_ID 			+ "," + 
				db.CI_CS_POINTS.POINT_NUMBER 	+ "," +  
				db.CI_CS_POINTS.ENERGY 			+ "," +  
				db.CI_CS_POINTS.COLL_STRENGTH 	+ "," +  
				db.CI_CS_POINTS.CROSS_SECTION 	+   
			")" 								+  
			" VALUES (?, ?, ?, ?, ?)";
	
	public static final String INSERT_EX_POINT_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EX_CS_POINTS 				+ 
			"(" 								+ 
				db.EX_CS_POINTS.TR_ID 			+ "," + 
				db.EX_CS_POINTS.POINT_NUMBER 	+ "," +  
				db.EX_CS_POINTS.ENERGY 			+ "," +  
				db.EX_CS_POINTS.COLL_STRENGTH 	+ "," +  
				db.EX_CS_POINTS.CROSS_SECTION 	+   
			")" 								+  
			" VALUES (?, ?, ?, ?, ?)";

	public static final String INSERT_LEVEL_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS 					+ 
			"(" 								+ 
				db.LEVELS.LEV_NUMBER 			+ "," + 
				db.LEVELS.COMPLEX 				+ "," +  
				db.LEVELS.NREL_CONFIG 			+ "," +  
				db.LEVELS.CONFIGURATION 		+ "," +  
				db.LEVELS.REL_CONFIG 			+ "," + 
				db.LEVELS.STAT_WEIGHT 			+ "," +  
				db.LEVELS.PARITY 				+ "," +  
				db.LEVELS.ENERGY 				+ "," +  
				db.LEVELS.PR_ID 				+ "," + 
				db.LEVELS.EL_NUMBER 			+ "," +  
				db.LEVELS.ION_NUMBER 			+  
			")" 								+  
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String UPDATE_LEVELS_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS						+
		" SET " 								+
			db.LEVELS.H_LIKE_OSC_STRENGTH		+ " = ? " +		
		" WHERE " 								+
			db.LEVELS.LEV_NUMBER 				+ " = ? " + 
		" AND " 								+ 	
			db.LEVELS.PR_ID 					+ " = ? " +
		" AND " 								+ 	
			db.LEVELS.ION_NUMBER 				+ " = ? ";
	
	public static final String GET_LEVELS_QUERY = 
		"SELECT * " 							+ 
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS 					+
		" WHERE " 								+
			db.LEVELS.ION_NUMBER				+ " = ? " +
		" AND " 								+ 	
			db.LEVELS.PR_ID 					+ " = ? ";
	
	public static final String UPDATE_LEVEL_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS						+
		" SET " 								+
			db.LEVELS.COMPLEX 					+ " = ?, " +		
			db.LEVELS.NREL_CONFIG 				+ " = ?, " +
			db.LEVELS.REL_CONFIG 				+ " = ?, " +		
			db.LEVELS.STAT_WEIGHT 				+ " = ?, " +
			db.LEVELS.PARITY 					+ " = ?, " +		
			db.LEVELS.ENERGY 					+ " = ? " +
		" WHERE " 								+
			db.LEVELS.LEV_NUMBER 				+ " = ? " + 
		" AND " 								+ 	
			db.LEVELS.PR_ID 					+ " = ? " +
		" AND " 								+ 	
			db.LEVELS.ION_NUMBER 				+ " = ? ";
	
	public static final String GET_NIST_LEVELS_QUERY = 
		"SELECT * " 							+ 
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.NIST_LEVELS;
	
	public static final String GET_NIST_TRANSITIONS_QUERY = 
		"SELECT * " 							+ 
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.NIST_TRANSITIONS;



// fit2db
	
	public static final String SELECT_EX_TRANSITION_PK_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.TR_PK 			+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+
		" WHERE " 								+
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+ " = ? " + 
		" AND " 								+
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+ " = ? ";

	public static final String SELECT_CI_TRANSITION_PK_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS 				+ "." +
			db.I_TRANSITIONS.TR_PK 				+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS 				+
		" WHERE " 								+
			db.I_TRANSITIONS.SOURCE 			+ " = ? " + 
		" AND " 								+
			db.I_TRANSITIONS.TARGET 			+ " = ? ";

	public static final String SELECT_PI_TRANSITION_PK_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS 				+ "." +
			db.I_TRANSITIONS.TR_PK 				+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.I_TRANSITIONS 				+
		" WHERE " 								+
			db.I_TRANSITIONS.SOURCE 			+ " = ? " + 
		" AND " 								+
			db.I_TRANSITIONS.TARGET 			+ " = ? ";

	public static final String SELECT_LEVEL_PK_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS 					+ "." +
			db.LEVELS.LEVEL_PK 					+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS 					+
		" WHERE " 								+
			db.LEVELS.PR_ID 					+ " = ? " + 
		" AND " 								+
			db.LEVELS.ION_NUMBER 				+ " = ? " +
		" AND " 								+
			db.LEVELS.LEV_NUMBER 				+ " = ? ";

	public static final String INSERT_EX_CS_FIT_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EX_CS_FITS 				+ 
			"(" 								+ 
				db.EX_CS_FITS.TR_ID 			+ "," + 
				db.EX_CS_FITS.METHOD_NUMBER 	+ "," +  
				db.EX_CS_FITS.A 				+ "," +  
				db.EX_CS_FITS.B 				+ "," +  
				db.EX_CS_FITS.C 				+ "," +  
				db.EX_CS_FITS.D 				+ "," +  
				db.EX_CS_FITS.E 				+ "," +  
				db.EX_CS_FITS.IS_FIT_VALID		+ 
			")" 								+  
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String INSERT_CI_CS_FIT_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.CI_CS_FITS 				+ 
			"(" 								+ 
				db.CI_CS_FITS.TR_ID 			+ "," + 
				db.CI_CS_FITS.METHOD_NUMBER 	+ "," +  
				db.CI_CS_FITS.A 				+ "," +  
				db.CI_CS_FITS.B 				+ "," +  
				db.CI_CS_FITS.C 				+ "," +  
				db.CI_CS_FITS.D 				+ "," +  
				db.CI_CS_FITS.IS_FIT_VALID		+ 
			")" 								+  
			" VALUES (?, ?, ?, ?, ?, ?, ?)";

	public static final String INSERT_PI_CS_FIT_QUERY = 
		"INSERT INTO " 							+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.PI_CS_FITS 				+ 
			"(" 								+ 
				db.PI_CS_FITS.TR_ID 			+ "," + 
				db.PI_CS_FITS.METHOD_NUMBER 	+ "," +  
				db.PI_CS_FITS.A 				+ "," +  
				db.PI_CS_FITS.B 				+ "," +  
				db.PI_CS_FITS.C 				+ "," +  
				db.PI_CS_FITS.D 				+ "," +  
				db.PI_CS_FITS.IS_FIT_VALID		+ 
			")" 								+  
			" VALUES (?, ?, ?, ?, ?, ?, ?)";

	public static final String UPDATE_EX_CS_FIT_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EX_CS_FITS					+
		" SET " 								+
			db.EX_CS_FITS.A 					+ " = ?, " +
			db.EX_CS_FITS.B 					+ " = ?, " +
			db.EX_CS_FITS.C 					+ " = ?, " +
			db.EX_CS_FITS.D 					+ " = ?, " +
			db.EX_CS_FITS.E 					+ " = ? "  +
		" WHERE " 								+
			db.EX_CS_FITS.TR_ID 				+ " = ? ";

	public static final String UPDATE_CI_CS_FIT_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.CI_CS_FITS					+
		" SET " 								+
			db.CI_CS_FITS.A 					+ " = ?, " +
			db.CI_CS_FITS.B 					+ " = ?, " +
			db.CI_CS_FITS.C 					+ " = ?, " +
			db.CI_CS_FITS.D 					+ " = ? " +
		" WHERE " 								+
			db.CI_CS_FITS.TR_ID 				+ " = ? " + 
		" AND " 								+
			db.CI_CS_FITS.METHOD_NUMBER 		+ " = 2 ";

	public static final String UPDATE_PI_CS_FIT_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.PI_CS_FITS					+
		" SET " 								+
			db.PI_CS_FITS.A 					+ " = ?, " +
			db.PI_CS_FITS.B 					+ " = ?, " +
			db.PI_CS_FITS.C 					+ " = ?, " +
			db.PI_CS_FITS.D 					+ " = ? " +
		" WHERE " 								+
			db.PI_CS_FITS.TR_ID 				+ " = ? " + 
		" AND " 								+
			db.PI_CS_FITS.METHOD_NUMBER 		+ " = 4 ";
	
	public static final String DELETE_EX_CS_FIT_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EX_CS_FITS					+
		" SET " 								+
			db.EX_CS_FITS.IS_FIT_VALID 			+ " = 'NO' " +		
		" WHERE " 								+
			db.EX_CS_FITS.TR_ID 				+ " = ? ";

	public static final String DELETE_CI_CS_FIT_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.CI_CS_FITS					+
		" SET " 								+
			db.CI_CS_FITS.IS_FIT_VALID			+ " = 'NO' " +
		" WHERE " 								+
			db.CI_CS_FITS.TR_ID 				+ " = ? " + 
		" AND " 								+
			db.CI_CS_FITS.METHOD_NUMBER 		+ " = 2 ";

	public static final String DELETE_PI_CS_FIT_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.PI_CS_FITS					+
		" SET " 								+
			db.PI_CS_FITS.IS_FIT_VALID			+ " = 'NO' " +
		" WHERE " 								+
			db.PI_CS_FITS.TR_ID 				+ " = ? " + 
		" AND " 								+
			db.PI_CS_FITS.METHOD_NUMBER 		+ " = 4 ";

	public static final String VAN_REGEMORTER_EX_CS_FIT_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EX_CS_FITS					+
		" SET " 								+
			db.EX_CS_FITS.METHOD_NUMBER 		+ " = 0 " +		
		" WHERE " 								+
			db.EX_CS_FITS.TR_ID 				+ " = ? ";

	public static final String SELECT_OSC_STRENGTH_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." +
			db.EXAB_TRANSITIONS.AB_OSC_STRENGTH	+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+
		" WHERE " 								+
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+ " = ? " + 
		" AND " 								+
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+ " = ? ";

	public static final String SELECT_STAT_WEIGHT_QUERY = 
		"SELECT " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS 					+ "." +
			db.LEVELS.STAT_WEIGHT				+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.LEVELS 					+
		" WHERE " 								+
			db.LEVELS.LEVEL_PK 					+ " = ? ";

	public static final String UPDATE_EX_TRANSITIONS_QUERY = 
		"UPDATE " 								+ 
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS			+
		" SET " 								+
			db.EXAB_TRANSITIONS.EX_OSC_STRENGTH + " = ? " +
		" WHERE " 								+
			db.EXAB_TRANSITIONS.TR_PK 			+ " = ? ";
	
	
// crm
	
	
	public static final String GET_CALCULATION_BY_ID = 
		"SELECT * " 				+ 
		" FROM " 					+
			db.SCHEMA.APP 			+ "." + 
			db.TABLE.PROJECTS 		+
		" WHERE " 					+
			db.SCHEMA.APP 			+ "." + 
			db.TABLE.PROJECTS 		+ "." + 
			db.PROJECTS.PROJECT_PK	+ " = ";

	public static final String GET_ELEMENT_BY_NUMBER = 
		"SELECT * " 				+ 
		" FROM " 					+
			db.SCHEMA.APP 			+ "." + 
			db.TABLE.ELEMENTS 		+
		" WHERE " 					+
			db.SCHEMA.APP 			+ "." + 
			db.TABLE.ELEMENTS 		+ "." + 
			db.ELEMENTS.EL_NUMBER	+ " = ";

	public static final String CRM_EXCITATION_TR_QUERY = // CRM
		"SELECT " 								+ 
			db.EXAB_TRANSITIONS.TR_PK 			+  ", " + 
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+  ", " + 
			db.EXAB_TRANSITIONS.EX_OSC_STRENGTH + 
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+
		" WHERE " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." + 
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+ " = ? ";

	public static final String RADIATIVE_TR_QUERY = 
		"SELECT " 								+ 
			db.EXAB_TRANSITIONS.TR_PK 			+  ", " + 
			db.EXAB_TRANSITIONS.EX_SRC_AB_TRG 	+  ", " + 
			db.EXAB_TRANSITIONS.AB_WAVELENGTH 	+  ", " + 
			db.EXAB_TRANSITIONS.AB_RATE 		+  ", " + 
			db.EXAB_TRANSITIONS.AB_MULTIPOLE 	+
		" FROM " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+
		" WHERE " 								+
			db.SCHEMA.APP 						+ "." + 
			db.TABLE.EXAB_TRANSITIONS 			+ "." + 
			db.EXAB_TRANSITIONS.EX_TRG_AB_SRC 	+ " = ? ";

	public static final String GET_ION_BY_ELEMENT = 
		"SELECT " + 
			db.IONS.IONIZATION_POTENTIAL 	+ "," + 
			db.IONS.AGGR_SCALE_FACTOR 		+  
		" FROM " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.IONS 					+
		" WHERE " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.IONS 					+ "." + 
			db.IONS.EL_NUMBER 				+ " = ";

	public static final String GET_ION_BY_ION_NUMBER = 
		" AND " +
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.IONS 					+ "." + 
			db.IONS.ION_NUMBER 				+ " = ";
	
	public static final String GET_LEVELS_BY_PROJECT = 
		"SELECT " 							+ 
			db.LEVELS.LEVEL_PK 				+ ", " +   
			db.LEVELS.LEV_NUMBER 			+ ", " +   
			db.LEVELS.CONFIGURATION 		+ ", " +   
			db.LEVELS.STAT_WEIGHT 			+ ", " +   
			db.LEVELS.ENERGY 				+ ", " +   
			db.LEVELS.H_LIKE_OSC_STRENGTH 	+    
		" FROM " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+
		" WHERE " 							+
			db.SCHEMA.APP 					+ "." + 
			db.TABLE.LEVELS 				+ "." + 
			db.LEVELS.PR_ID 				+ " = ";
	
	public static final String GET_LEVELS_BY_ION_NUMBER = 
		" AND " 					+
			db.SCHEMA.APP 			+ "." + 
			db.TABLE.LEVELS 		+ "." + 
			db.LEVELS.ION_NUMBER 	+ " = ";

	public static final String SELECT_LEVEL_BY_ELEMENT 	= 
		"SELECT " 						+
			db.NIST_LEVELS.LEV_NUMBER 	+ ", " +   
			db.NIST_LEVELS.CRM_CONFIG 	+ ", " +   
			db.NIST_LEVELS.STAT_WEIGHT 	+ ", " +   
			db.NIST_LEVELS.ENERGY 		+ ", " +   
			db.NIST_LEVELS.TERM 		+    
		" FROM " 						+
			db.SCHEMA.APP 				+ "." + 
			db.TABLE.NIST_LEVELS 		+
		" WHERE " 						+
			db.SCHEMA.APP 				+ "." + 
			db.TABLE.NIST_LEVELS 		+ "." + 
			db.NIST_LEVELS.EL_NUMBER 	+ " = ";


	public static final String AND_ION = 
		" AND " 						+
			db.SCHEMA.APP 				+ "." + 
			db.TABLE.NIST_LEVELS 		+ "." + 
			db.NIST_LEVELS.ION_NUMBER 	+ " = ";
		
	public static final String AND_ENERGY_LESS_POTENTIAL = 
		" AND " 					+
			db.SCHEMA.APP 			+ "." + 
			db.TABLE.NIST_LEVELS 	+ "." + 
			db.NIST_LEVELS.ENERGY 	+ " < ";

}
