package db.sql;


import db.*;
import db.factory.EParameter;
import db.factory.EProcess;
import db.factory.Parameter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import response.*;


public class AtomicFactory 
{
	private Connection 			m_connection;
	private PreparedStatement 	m_exTransitionPs;
	private PreparedStatement 	m_aiTransitionPs;
	private PreparedStatement 	m_abTransitionPs;
	private PreparedStatement 	m_iTransitionPs;
	private PreparedStatement 	m_ciFitPs;
	private PreparedStatement 	m_piFitPs;
	private PreparedStatement 	m_exFitPs;
	private PreparedStatement 	m_ionsPs;
	private PreparedStatement 	m_levelsPs;
	
	private PreparedStatement 	m_ciPointsPs;
	private PreparedStatement 	m_piPointsPs;
	private PreparedStatement 	m_exPointsPs;

   
    public AtomicFactory(final Connection conn)
    throws ClassNotFoundException, SQLException
    {
		m_connection = conn;
		initPreparedStatements();
    }
	
	public void populateIon(final Ion ion, 
			final short iElement, final int iIon)
	throws SQLException
	{
		m_ionsPs.clearParameters();
		m_ionsPs.setInt(1, iElement);
		m_ionsPs.setInt(2, iIon);

		ResultSet r = m_ionsPs.executeQuery();	
		if(r.next())
		{
			double dPotential = r.getDouble(IONS.IONIZATION_POTENTIAL.toString());
			int iAggr = r.getInt(IONS.AGGR_SCALE_FACTOR.toString());
			
			ion.setPotential(dPotential);
			ion.setScaleFactor((short) iAggr);
		}
	}
	
	public void populateLevels(final Calc calc, final Ion ion, 
			final List<Level> levels)
	throws SQLException
	{
		m_levelsPs.clearParameters();
		m_levelsPs.setInt(1, calc.getCalcId());
		m_levelsPs.setInt(2, ion.getCharge());
		ResultSet r = m_levelsPs.executeQuery();

	 	while(r.next())
	 	{
	 		int iLevId 		= r.getInt(LEVELS.LEVEL_PK.toString());
	 		int iLevNum 	= r.getInt(LEVELS.LEV_NUMBER.toString());
	 		String sConf 	= r.getString(LEVELS.CONFIGURATION.toString());
	 		int iStWeight 	= r.getInt(LEVELS.STAT_WEIGHT.toString());
	 		double dEnergy 	= r.getDouble(LEVELS.ENERGY.toString());

	 		Level level = new Level();
	 		level.setLevNum(iLevNum);
	 		level.setLevId(iLevId);
	 		levels.add(level);
	 		
	 		if(ion.getCharge() == calc.getAtom().getAtomNum() + 1)
	 		{
   		 		level.setLevConfig("Nucleus");
   		 		level.setG((short) 0);
	 		}
	 		else
	 		{
   		 		level.setLevConfig(sConf);
   		 		level.setG((short)iStWeight);
	 		}
	 		
	 		level.setLevEnergy(dEnergy);
	 	}
	}
	
	private void initPreparedStatements() throws SQLException
	{
		m_ionsPs = m_connection.prepareStatement(
				SqlQuery.GET_ION, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 

		m_levelsPs = m_connection.prepareStatement(
				SqlQuery.GET_LEVELS, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 
		
		m_aiTransitionPs = m_connection.prepareStatement(
				SqlQuery.AUTOIONIZATION_TR_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 

		m_exTransitionPs = m_connection.prepareStatement(
				SqlQuery.EXCITATION_TR_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 

		m_exFitPs = m_connection.prepareStatement(
				SqlQuery.EX_FIT_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 

		m_iTransitionPs = m_connection.prepareStatement(
				SqlQuery.IONIZATION_TR_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 

		m_ciFitPs = m_connection.prepareStatement(
				SqlQuery.CI_FIT_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 

		m_piFitPs = m_connection.prepareStatement(
				SqlQuery.PI_FIT_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 

		m_abTransitionPs = m_connection.prepareStatement(
				SqlQuery.ABSORPTION_TR_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 
		
		m_ciPointsPs = m_connection.prepareStatement(
				SqlQuery.CI_POINTS_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 
		
		m_piPointsPs = m_connection.prepareStatement(
				SqlQuery.PI_POINTS_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 
		
		m_exPointsPs = m_connection.prepareStatement(
				SqlQuery.EX_POINTS_QUERY, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); 

	}
    
    public List<ExJump> getExTransitionList(final Ion ion, 
    		final Level srcLevel, boolean withPoints)
    throws SQLException
    {
		if (m_connection == null)
		{
		    throw new IllegalStateException("Connection already closed.");
		}
		
      	setLevelId(srcLevel, EProcess.EXCITATION);
    	List<ExJump> res = buildExTransitionsList(ion, srcLevel, withPoints);
		return res;
    }
    
    public List<RtJump> getRtTransitionList(final Ion ion, final Level srcLevel)
    throws SQLException
    {
		if (m_connection == null)
		{
		    throw new IllegalStateException("Connection already closed.");
		}
		
      	setLevelId(srcLevel, EProcess.RADIATION);
    	List<RtJump> res = buildRtTransitionsList(ion, srcLevel);
		return res;
    }
    
    public List<PiJump> getPiTransitionList(final Ion nextIon, 
    		final Level srcLevel, boolean withPoints)
    throws SQLException
    {
		if (m_connection == null)
		{
		    throw new IllegalStateException("Connection already closed.");
		}
		
      	setLevelId(srcLevel, EProcess.PHOTOIONIZATION);
    	List<PiJump> res = buildPiTransitionsList(nextIon, srcLevel, withPoints);
		return res;
    }

    public List<CiJump> getCiTransitionList(final Ion nextIon, 
    		final Level srcLevel, boolean withPoints)
    throws SQLException
    {
		if (m_connection == null)
		{
		    throw new IllegalStateException("Connection already closed.");
		}
		
      	setLevelId(srcLevel, EProcess.COLLISIONAL_IONIZATION);
    	List<CiJump> res = buildCiTransitionsList(nextIon, srcLevel, withPoints);
		return res;
    }
    
    public List<AiJump> getAiTransitionList(final Ion nextIon, final Level srcLevel)
    throws SQLException
    {
		if (m_connection == null)
		{
		    throw new IllegalStateException("Connection already closed.");
		}
		
      	setLevelId(srcLevel, EProcess.AUTOIONIZATION);
    	List<AiJump> res = buildAiTransitionsList(nextIon, srcLevel);
		return res;
    }
    
    private List<ExJump> buildExTransitionsList(final Ion ion, 
    		final Level srcLevel, boolean withPoints)
    throws SQLException
    {
    	List<ExJump> res = new ArrayList<ExJump>();
    	ResultSet r = buildResultSet(EProcess.EXCITATION);

		while(r.next())
		{
			Parameter parameter = new Parameter();
			boolean isExist = retrieveExData(r, ion, parameter);
			if(isExist)
			{
				ExJump transition = new ExJump();
				setTransitionId((long)parameter.get(EParameter.TR_ID), 
						EProcess.EXCITATION);
				transition.setJumpId((int)parameter.get(EParameter.TR_ID));
				transition.setSource(srcLevel.getLevNum());
				transition.setTarget((int)parameter.get(EParameter.TARGET));
				transition.setThreshold(parameter.get(EParameter.THRESHOLD));
				transition.setOscstrength(parameter.get(EParameter.OSC_STRENGTH));
				addExFit(transition);
				
				if(withPoints)
				{
					addExPoints(transition);
				}
				
				res.add(transition);
			}
		}
		return res;
    }
    
    private List<PiJump> buildPiTransitionsList(final Ion nextIon, 
    		final Level srcLevel, boolean withPoints)
    throws SQLException
    {
    	List<PiJump> res = new ArrayList<PiJump>();
    	ResultSet r = buildResultSet(EProcess.PHOTOIONIZATION);

		while(r.next())
		{
			Parameter parameter = new Parameter();
			boolean isExist = retrievePiData(r, nextIon, parameter);
			if(isExist)
			{
				PiJump transition = new PiJump();
				setTransitionId((long)parameter.get(EParameter.TR_ID), 
						EProcess.PHOTOIONIZATION);
				transition.setJumpId((int)parameter.get(EParameter.TR_ID));
				transition.setSource(srcLevel.getLevNum());
				transition.setTarget((int)parameter.get(EParameter.TARGET));
				transition.setThreshold(parameter.get(EParameter.THRESHOLD));
				addPiFit(transition);
				
				if(withPoints)
				{
					addPiPoints(transition);
				}
				
				res.add(transition);
			}
		}
		return res;
    }
    
    private List<CiJump> buildCiTransitionsList(final Ion nextIon, 
    		final Level srcLevel, boolean withPoints)
    throws SQLException
    {
    	List<CiJump> res = new ArrayList<CiJump>();
    	ResultSet r = buildResultSet(EProcess.COLLISIONAL_IONIZATION);

		while(r.next())
		{
			Parameter parameter = new Parameter();
			boolean isExist = retrieveCiData(r, nextIon, parameter);
			if(isExist)
			{
				CiJump transition = new CiJump();
				setTransitionId((long)parameter.get(EParameter.TR_ID), 
						EProcess.COLLISIONAL_IONIZATION);
				transition.setJumpId((int)parameter.get(EParameter.TR_ID));
				transition.setSource(srcLevel.getLevNum());
				transition.setTarget((int)parameter.get(EParameter.TARGET));
				transition.setThreshold(parameter.get(EParameter.THRESHOLD));
				addCiFit(transition);
				
				if(withPoints)
				{
					addCiPoints(transition);
				}

				res.add(transition);
			}
		}
		return res;
    }
    
    private List<AiJump> buildAiTransitionsList(final Ion nextIon, final Level srcLevel)
    throws SQLException
    {
    	List<AiJump> res = new ArrayList<AiJump>();
    	ResultSet r = buildResultSet(EProcess.AUTOIONIZATION);

		while(r.next())
		{
			Parameter parameter = new Parameter();
			boolean isExist = retrieveAiData(r, nextIon, parameter);
			if(isExist)
			{
				AiJump transition = new AiJump();
				transition.setSource(srcLevel.getLevNum());
				transition.setTarget((int)parameter.get(EParameter.TARGET));
				transition.setAiProbability(parameter.get(EParameter.AI_PROBABILITY));
				transition.setThreshold(parameter.get(EParameter.THRESHOLD));
				res.add(transition);
			}
		}
		return res;
    }

    private List<RtJump> buildRtTransitionsList(final Ion ion, final Level srcLevel)
    throws SQLException
    {
    	List<RtJump> res = new ArrayList<RtJump>();
    	ResultSet r = buildResultSet(EProcess.RADIATION);

		while(r.next())
		{
			Parameter parameter = new Parameter();
			boolean isExist = retrieveRtData(r, ion, parameter);
			if(isExist)
			{
				RtJump transition = new RtJump();
				transition.setSource(srcLevel.getLevNum());
				transition.setTarget((int)parameter.get(EParameter.TARGET));
				transition.setWavelength(parameter.get(EParameter.WAVELENGTH));
				transition.setEinstein(parameter.get(EParameter.EINSTEIN_COEFFICIENT));
				res.add(transition);
			}
		}
		return res;
    }
    
    private ResultSet buildResultSet(final EProcess process)
    throws SQLException
    {
    	ResultSet transitions = null;
		switch(process)
		{
		case AUTOIONIZATION:
			transitions = m_aiTransitionPs.executeQuery();	
			break;
		case EXCITATION:
			transitions = m_exTransitionPs.executeQuery();			
			break;
		case COLLISIONAL_IONIZATION:
			transitions = m_iTransitionPs.executeQuery();
			break;
		case PHOTOIONIZATION:
			transitions = m_iTransitionPs.executeQuery();			
			break;
		case RADIATION:
			transitions = m_abTransitionPs.executeQuery();			
			break;
			default:
				break;
		}
		return transitions;
    }
    
    private boolean retrieveAiData(final ResultSet r,  
    		final Ion nextIon, Parameter parameter)
    throws SQLException
    {
    	boolean isExist = false;
    	Level trgLevel = null;
    	int iTarget = r.getInt(AI_TRANSITIONS.TARGET.toString());
    	double dRate = r.getDouble(AI_TRANSITIONS.RATE.toString());
    	double dThreshold = r.getDouble(AI_TRANSITIONS.THRESHOLD.toString());
		trgLevel = getLevel(nextIon, iTarget);

    	parameter.set(EParameter.AI_PROBABILITY, dRate);
    	parameter.set(EParameter.THRESHOLD, dThreshold);
    	parameter.set(EParameter.TARGET, trgLevel.getLevNum());
    	
		if(trgLevel != null) isExist = true;
		return isExist;
    }

    private boolean retrieveExData(final ResultSet r, 
    		final Ion ion, final Parameter parameter)
    throws SQLException
    {
    	boolean isExist = false;
    	Level trgLevel = null;
    	int iTarget = r.getInt(EXAB_TRANSITIONS.EX_TRG_AB_SRC.toString());
    	trgLevel = getLevel(ion, iTarget);
		if(trgLevel != null)
		{
			int lTrId = r.getInt(EXAB_TRANSITIONS.TR_PK.toString());
	    	double dOscStrength = r.getDouble(EXAB_TRANSITIONS.EX_OSC_STRENGTH.toString());
	    	double dThreshold = r.getDouble(EXAB_TRANSITIONS.THRESHOLD.toString());
	    	parameter.set(EParameter.TR_ID, lTrId);
	    	parameter.set(EParameter.TARGET, trgLevel.getLevNum());
	    	parameter.set(EParameter.THRESHOLD, dThreshold);
	    	
	    	if(dOscStrength != 0) 
	    	{
	    		dOscStrength *= -1;
	    	}
	    	
			parameter.set(EParameter.OSC_STRENGTH, dOscStrength);
			isExist = true;
		}
		return isExist;
    }
    
    private boolean retrieveCiData(final ResultSet r,  
    		final Ion nextIon, Parameter parameter)
    throws SQLException
    {
    	boolean isExist = false;
    	Level trgLevel = null;
    	int iTarget = r.getInt(I_TRANSITIONS.TARGET.toString());
		trgLevel = getLevel(nextIon, iTarget);
		if(trgLevel != null)
		{
	    	int lTrId = r.getInt(I_TRANSITIONS.TR_PK.toString());
	    	double dThreshold = r.getDouble(I_TRANSITIONS.THRESHOLD.toString());
	    	parameter.set(EParameter.TR_ID, lTrId);
	    	parameter.set(EParameter.TARGET, trgLevel.getLevNum());
	    	parameter.set(EParameter.THRESHOLD, dThreshold);
	    	isExist = true;
		}
		return isExist;
    }
    
    private boolean retrievePiData(final ResultSet r,  
    		final Ion nextIon, final Parameter parameter)
    throws SQLException
    {
    	boolean isExist = false;
    	Level trgLevel = null;
    	int iTarget = r.getInt(I_TRANSITIONS.TARGET.toString());
		trgLevel = getLevel(nextIon, iTarget);
		if(trgLevel != null)
		{
	    	int lTrId = r.getInt(I_TRANSITIONS.TR_PK.toString());
	    	double dThreshold = r.getDouble(I_TRANSITIONS.THRESHOLD.toString());
	    	parameter.set(EParameter.TR_ID, lTrId);
	    	parameter.set(EParameter.TARGET, trgLevel.getLevNum());
	    	parameter.set(EParameter.THRESHOLD, dThreshold);
	    	isExist = true;
		}
		return isExist;
    }
    
    private boolean retrieveRtData(final ResultSet r, final Ion ion, 
    		Parameter parameter)
    throws SQLException
    {
    	boolean isExist = false;
    	Level trgLevel = null;
    	int iTarget = r.getInt(EXAB_TRANSITIONS.EX_SRC_AB_TRG.toString());
    	trgLevel = getLevel(ion, iTarget);
    	if(trgLevel != null)
    	{
        	double dWavelength = r.getDouble(EXAB_TRANSITIONS.AB_WAVELENGTH.toString());
        	double dEinstein = r.getDouble(EXAB_TRANSITIONS.AB_RATE.toString());
        	parameter.set(EParameter.WAVELENGTH, dWavelength);
        	parameter.set(EParameter.EINSTEIN_COEFFICIENT, dEinstein);
        	parameter.set(EParameter.TARGET, trgLevel.getLevNum());
    		if(dWavelength < 10000 && dEinstein >= 10E5)
    		{
    			isExist = true;
    		}
    	}
		return isExist;
    }
    
    private void addCiFit(final CiJump transition)
    throws SQLException
    {
    	boolean isExist = false;
		Fit ciFit = new Fit();
		isExist = populateCiFit(ciFit);
		if(isExist)
		{
			transition.setFit(ciFit);
		}
    }

    private void addPiFit(final PiJump transition)
    throws SQLException
    {
    	boolean isExist = false;
		Fit piFit = new Fit();
		isExist = populatePiFit(piFit);
		if(isExist)
		{
			transition.setFit(piFit);
		}
    }
    
    private void addExFit(final ExJump transition)
    throws SQLException
    {
    	boolean isExist = false;
    	Fit exFit = new Fit();
		isExist = populateExFit(exFit);
		if(isExist)
		{
			transition.setFit(exFit);
		}
    }

    private boolean populateCiFit(final Fit cross)
    throws SQLException
    {
		boolean res = false;
		if(m_ciFitPs == null) return res;
		ResultSet fr = m_ciFitPs.executeQuery();

		if(fr.next())
		{
			cross.setA(fr.getDouble(CI_CS_FITS.A.toString()));
			cross.setB(fr.getDouble(CI_CS_FITS.B.toString()));
			cross.setC(fr.getDouble(CI_CS_FITS.C.toString()));
			cross.setD(fr.getDouble(CI_CS_FITS.D.toString()));
			res = true;
		}
		return res;
    }
    
    private boolean populatePiFit(final Fit cross)
    throws SQLException
    {
		boolean res = false;
		if(m_piFitPs == null) return res;
		ResultSet fr = m_piFitPs.executeQuery();

		if(fr.next())
		{
			cross.setMethodNum((short) fr.getInt(PI_CS_FITS.METHOD_NUMBER.toString()));
			cross.setA(fr.getDouble(PI_CS_FITS.A.toString()));
			cross.setB(fr.getDouble(PI_CS_FITS.B.toString()));
			cross.setC(fr.getDouble(PI_CS_FITS.C.toString()));
			cross.setD(fr.getDouble(PI_CS_FITS.D.toString()));
			res = true;
		}
		return res;
    }
    
    private boolean populateExFit(final Fit cross)
    throws SQLException
    {
		boolean res = false;
		if(m_exFitPs == null) return res;
		
		ResultSet fr = m_exFitPs.executeQuery();
		if(fr.next())
		{
			cross.setMethodNum((short) fr.getInt(EX_CS_FITS.METHOD_NUMBER.toString()));
			cross.setA(fr.getDouble(EX_CS_FITS.A.toString()));
			cross.setB(fr.getDouble(EX_CS_FITS.B.toString()));
			cross.setC(fr.getDouble(EX_CS_FITS.C.toString()));
			cross.setD(fr.getDouble(EX_CS_FITS.D.toString()));
			cross.setE(fr.getDouble(EX_CS_FITS.E.toString()));
			res = true;
		}
		return res;
    }
    
    private void addExPoints(final ExJump transition)
    throws SQLException
    {
    	List<ExPoint> exPoints = transition.getExPoint();
		populateExPoints(exPoints, transition.getThreshold());
    }

    private void addCiPoints(final CiJump transition)
    throws SQLException
    {
    	List<CiPoint> ciPoints = transition.getCiPoint();
		populateCiPoints(ciPoints, transition.getThreshold());
    }

    private void addPiPoints(final PiJump transition)
    throws SQLException
    {
    	List<PiPoint> piPoints = transition.getPiPoint();
		populatePiPoints(piPoints, transition.getThreshold());
    }

    private void populateExPoints(final List<ExPoint> points, final double dThreshold)
    throws SQLException
    {
		if(m_exPointsPs == null) return;
		
		ResultSet r = m_exPointsPs.executeQuery();
		while(r.next())
		{
			double dCollStrenght = r.getDouble(EX_CS_POINTS.COLL_STRENGTH.toString());
			double dCrossSection = r.getDouble(EX_CS_POINTS.CROSS_SECTION.toString());
			double dEnergy = r.getDouble(EX_CS_POINTS.ENERGY.toString());
			int iNumber = r.getInt(EX_CS_POINTS.POINT_NUMBER.toString());
			
			ExPoint point = new ExPoint();
			point.setCollStrengthValue(dCollStrenght);
			point.setCrossValue(dCrossSection);
			point.setPointEnergy(1 + (dEnergy / dThreshold));
			point.setPointNum((short) iNumber);
			points.add(point);
		}
    }
    
    private void populateCiPoints(final List<CiPoint> points, final double dThreshold)
    throws SQLException
    {
		if(m_ciPointsPs == null) return;
		// NumberFormat numFormatter = new DecimalFormat("0.000E00");

		ResultSet r = m_ciPointsPs.executeQuery();
		while(r.next())
		{
			double dCollStrenght = r.getDouble(CI_CS_POINTS.COLL_STRENGTH.toString());
			double dCrossSection = r.getDouble(CI_CS_POINTS.CROSS_SECTION.toString());
			double dEnergy = r.getDouble(CI_CS_POINTS.ENERGY.toString());
			int iNumber = r.getInt(CI_CS_POINTS.POINT_NUMBER.toString());
			
			CiPoint point = new CiPoint();
			point.setCollStrengthValue(dCollStrenght);
			point.setCrossValue(dCrossSection);
			point.setPointEnergy(1 + (dEnergy / dThreshold));
			point.setPointNum((short) iNumber);
			points.add(point);
		}
    }
    
    private void populatePiPoints(final List<PiPoint> points, final double dThreshold)
    throws SQLException
    {
		if(m_piPointsPs == null) return;
		
		ResultSet r = m_piPointsPs.executeQuery();
		while(r.next())
		{
			double dOscStrenght = r.getDouble(PI_CS_POINTS.OSC_STRENGTH.toString());
			double dPiCrossSection = r.getDouble(PI_CS_POINTS.PI_CROSS_SECTION.toString());
			double dRrCrossSection = r.getDouble(PI_CS_POINTS.RR_CROSS_SECTION.toString());
			double dEnergy = r.getDouble(PI_CS_POINTS.ENERGY.toString());
			int iNumber = r.getInt(PI_CS_POINTS.POINT_NUMBER.toString());
			
			PiPoint point = new PiPoint();
			point.setOscStrengthValue(dOscStrenght);
			point.setCrossValue(dPiCrossSection);
			point.setRrCrossValue(dRrCrossSection);
			point.setPointEnergy(1 + (dEnergy / dThreshold));
			point.setPointNum((short) iNumber);
			points.add(point);
		}
    }
    
    private Level getLevel(final Ion ion, final long iPatternNumber)
    {
    	List<Level> levels = ion.getFac().getLevel();
    	Level res = null;
    	for(int i = 0; i < levels.size(); i++)
    	{
    		Level level = levels.get(i);
    		if(level.getLevId() == iPatternNumber)
    		{
    			res = level;
    			break;
    		}
    	}
    	return res;
    }
    
	private void setLevelId(final Level srcLevel, 
			final EProcess process)
	{
		try 
		{
			switch(process)
			{
			case AUTOIONIZATION:
				m_aiTransitionPs.clearParameters();
				m_aiTransitionPs.setInt(1, (int) srcLevel.getLevId());
				break;
			case EXCITATION:
				m_exTransitionPs.clearParameters();
				m_exTransitionPs.setInt(1, (int) srcLevel.getLevId());				
				break;
			case COLLISIONAL_IONIZATION:
			case PHOTOIONIZATION:
				m_iTransitionPs.clearParameters();
				m_iTransitionPs.setInt(1, (int) srcLevel.getLevId());
				break;
			case RADIATION:
				m_abTransitionPs.clearParameters();
				m_abTransitionPs.setInt(1, (int) srcLevel.getLevId());
				break;
				default:
					break;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	private void setTransitionId(final long lTrId, 
			final EProcess process)
	{
		try 
		{
			switch(process)
			{
			case EXCITATION:
				m_exFitPs.clearParameters();
				m_exFitPs.setLong(1, lTrId);			
				m_exPointsPs.setLong(1, lTrId);			
				break;
			case COLLISIONAL_IONIZATION:
				m_ciFitPs.clearParameters();
				m_ciFitPs.setLong(1, lTrId);
				m_ciPointsPs.setLong(1, lTrId);			
				break;
			case PHOTOIONIZATION:
				m_piFitPs.clearParameters();
				m_piFitPs.setLong(1, lTrId);
				m_piPointsPs.setLong(1, lTrId);			
				break;
				default:
					break;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
    
    public void close() 
    {
		try 
		{ 
			m_connection.close(); 
		} 
		catch (Exception e) 
		{
			// Do nothing on error. At least we tried.
		}      
		m_connection = null; 
    }
}
