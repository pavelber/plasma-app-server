package db.sql;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import proxy.DbProxy;

import crm.ResultSetTableModel;
import crm.model.state.Calculation;
import crm.model.state.Element;
import crm.model.state.Ion;
import crm.model.state.Level;
import crm.model.transition.Parameter;
import crm.model.transition.Transition;
import crm.model.transition.TransitionParameter;
import crm.model.transition.TransitionProcess;


public class CrmDbManager 
{
	private Connection 			m_connection;
	private TransitionProcess 	m_process;

    public CrmDbManager()
    {
  	  DbProxy db = DbProxy.getInstance();
  	  m_connection = db.getConnection();
    }
    
	public void setTransitionProcess(final TransitionProcess process) 
	throws SQLException
	{
		m_process = process;
	}
	
	public void buildNistIon(final Element element, final Ion ion, final int ionNumber)
	throws SQLException
	{
		String sql = 
			 SqlQuery.GET_ION_BY_ELEMENT + element.getNumber() + " " +
			 SqlQuery.GET_ION_BY_ION_NUMBER + ionNumber;
		 
		ResultSetTableModel ionModel = getResultSetTableModel(sql);
		 
		double dPotential 	= (Double)ionModel.getValueAt(0, 0);
		int iAggr 			= (Integer)ionModel.getValueAt(0, 1);
		
		ion.setNumber(ionNumber);
		ion.setNistIonizationPotential(dPotential);
		ion.setAggrScaleFactor(iAggr);
		ion.setElementNumber(element.getNumber());
	}
	
	public List<Level> buildLevelsForIon(final Calculation calc, 
			final Element element, final Ion ion)
	throws SQLException
	{
	 	ResultSetTableModel levelsModel = getResultSetTableModel(
	 		SqlQuery.GET_LEVELS_BY_PROJECT + calc.getId() + 
	 		SqlQuery.GET_LEVELS_BY_ION_NUMBER + ion.getNumber());
	 	
	 	List<Level>  tempLevelList = new ArrayList<Level>();  
	 	for(int j = 0; j < levelsModel.getRowCount(); j++)
	 	{
	 		String sConf = (String)levelsModel.getValueAt(j, 2);
	 		
	 		int iLevId = (Integer)levelsModel.getValueAt(j, 0);
	 		int iLevNum = (Integer)levelsModel.getValueAt(j, 1);
	 		int iStWeight = (Integer)levelsModel.getValueAt(j, 3);
	 		double dEnergy = (Double)levelsModel.getValueAt(j, 4);
	 		double dHLikeOscStrength  = 0.0;
	 		
	 		if(levelsModel.getValueAt(j, 5) != null)
	 		{
	 			dHLikeOscStrength  = (Double)levelsModel.getValueAt(j, 5);
	 		}
	 		
	 		Level level = new Level(iLevNum + "");
	 		level.setId(iLevId);
	 		level.setNumber(iLevNum);
	 		level.setConfiguration(sConf);
	 		level.setStatWeight(iStWeight);
	 		level.setEnergy(dEnergy);
	 		level.setHLikeOscStrength(dHLikeOscStrength);
	 		level.setElementNumber(element.getNumber());
	 		tempLevelList.add(level);
	 	}
	 	return tempLevelList;
	}
	 		
/*	 		if(sConf.endsWith("5s1") || 
	 				sConf.endsWith("5p1") || 
	 				sConf.endsWith("5d1") || 
	 				sConf.endsWith("5f1") || 
	 				sConf.endsWith("5g1") ||
	 				sConf.endsWith("6s1") || 
	 				sConf.endsWith("6p1") || 
	 				sConf.endsWith("6d1") || 
	 				sConf.endsWith("6f1") || 
	 				sConf.endsWith("6g1") ||  
	 				sConf.endsWith("6h1"))
	 		{
	 			continue;
	 		}
	 		else
	 		{
		 		int iLevId = (Integer)levelsModel.getValueAt(j, 0);
		 		int iLevNum = (Integer)levelsModel.getValueAt(j, 1);
		 		int iStWeight = (Integer)levelsModel.getValueAt(j, 3);
		 		double dEnergy = (Double)levelsModel.getValueAt(j, 4);
		 		double dHLikeOscStrength  = 0.0;
		 		
		 		if(levelsModel.getValueAt(j, 5) != null)
		 		{
		 			dHLikeOscStrength  = (Double)levelsModel.getValueAt(j, 5);
		 		}
		 		
		 		Level level = new Level(iLevNum + "");
		 		level.setId(iLevId);
		 		level.setNumber(iLevNum);
		 		level.setConfiguration(sConf);
		 		level.setStatWeight(iStWeight);
		 		level.setEnergy(dEnergy);
		 		level.setHLikeOscStrength(dHLikeOscStrength);
		 		level.setElementNumber(element.getNumber());
		 		tempLevelList.add(level);
	 		}
	 	}
	 	return tempLevelList;
}
	}*/

	public double getFacIonizationPotential(final Calculation calc, final int iIon)
	throws SQLException
	{
		double dRes = -1; 
		int iProject = calc.getId();
		
		final String sql = 
		"SELECT " 					+ 
			db.FAC_IONS.FAC_POTENTIAL 	+  
		" FROM " 						+
			db.SCHEMA.APP 				+ "." + 
			db.TABLE.FAC_IONS 			+
		" WHERE " 						+
			db.SCHEMA.APP 				+ "." + 
			db.TABLE.FAC_IONS 			+ "." + 
			db.FAC_IONS.PR_ID 			+ " = " + iProject +
		" AND " 						+
			db.SCHEMA.APP 				+ "." + 
			db.TABLE.FAC_IONS 			+ "." + 
			db.FAC_IONS.ION_NUMBER 		+ " = " + iIon;

		ResultSetTableModel transModel = getResultSetTableModel(sql);
		dRes = (Double)transModel.getValueAt(0, 0);
		return dRes;
	}

	public List<Level> makeNistLevelsList(final Element element, final Ion ion ) 
	{
	 	String nistQuery = SqlQuery.SELECT_LEVEL_BY_ELEMENT + element.getNumber() + 
		SqlQuery.AND_ION + ion.getNumber() + 
		SqlQuery.AND_ENERGY_LESS_POTENTIAL + ion.getNistIonizationPotential();

		List<Level> levels = new ArrayList<Level>();
      
      try 
      {
		ResultSetTableModel model = getResultSetTableModel(nistQuery);
		
		for(int i = 0; i < model.getRowCount(); i++)
		{
			int number = (Integer)model.getValueAt(i, 0);
			String configuration = (String)model.getValueAt(i, 1);
			int statWeight 	= -1;
			
			if(model.getValueAt(i, 2) != null)
			{
				statWeight 	= (Integer)model.getValueAt(i, 2);
			}
			
			double energy 	= -1;
			if(model.getValueAt(i, 3) != null )
			{
				energy 	= (Double)model.getValueAt(i, 3);
			}
			
			String term = (String)model.getValueAt(i, 4);
			
			if(energy > -1 )
			{
				Level lev = new Level(number + "");
				lev.setConfiguration(configuration);
				lev.setNumber(number);
				lev.setEnergy(energy);
				lev.setStatWeight(statWeight);
				lev.setTerm(term);
				levels.add(lev);
			}
		}
	  } 
      catch (SQLException e) 
      {
		e.printStackTrace();
	  }
      return levels;
	}	
	
	public Calculation makeCalculation(final int iCalcId)
	{
		Calculation res = null;
		try 
		{
			String sql = 
				 SqlQuery.GET_CALCULATION_BY_ID + iCalcId;
			 
			ResultSetTableModel model = getResultSetTableModel(sql);
			
			int projectId 			= (Integer)model.getValueAt(0, 0);
			String name 			= (String)model.getValueAt(0, 1);
			int elementNumber 		= (Integer)model.getValueAt(0, 2);
			int startIonNumber 		= (Integer)model.getValueAt(0, 3);
			int endIonNumber 		= (Integer)model.getValueAt(0, 4);
			
			res = new Calculation(name);
			res.setId(projectId);
			res.setElementNumber(elementNumber);
			res.setStartIonNumber(startIonNumber);
			res.setEndIonNumber(endIonNumber);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return res;
	}

	public Element makeElement(final int iElementNumber)
	{
		Element res = null;
		try 
		{
			String sql = 
				 SqlQuery.GET_ELEMENT_BY_NUMBER + iElementNumber;
			 
			ResultSetTableModel model = getResultSetTableModel(sql);
			
			String name 			= (String)model.getValueAt(0, 0);
			String symbol 			= (String)model.getValueAt(0, 1);
			int number 				= (Integer)model.getValueAt(0, 2);
			String configuration 	= (String)model.getValueAt(0, 3);
			double weight 			= (Double)model.getValueAt(0, 4);
			
			res = new Element(name);
			res.setSymbol(symbol);
			res.setNumber(number);
			res.setConfiguration(configuration);
			res.setWeight(weight);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return res;
	}	

	public List<Transition> getTransitionList(final Ion ion, final Ion nextIon, 
    		final Level srcLevel)
    {
		if (m_connection == null)
		{
		    throw new IllegalStateException("Connection already closed.");
		}

    	List<Transition> res = null;
    	ResultSet rs = null;
		try 
		{
			rs = makeTransitionsResultSet(srcLevel);
			res = buildTransitionsList(ion, nextIon, 
					srcLevel, rs);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
        }
		return res;
    }

	private ResultSet makeTransitionsResultSet(final Level level)
	{
    	ResultSet rs = null;
		switch(m_process)
		{
		case AUTOIONIZATION:
			rs = makeAiTransitionsResultSet(level);	
			break;
		case EXCITATION:
			rs = makeExTransitionsResultSet(level);			
			break;
		case COLLISIONAL_IONIZATION:
		case PHOTOIONIZATION:
		case IONIZATION:
			rs = makeITransitionsResultSet(level);			
			break;
		case RADIATION:
			rs = makeRtTransitionsResultSet(level);			
			break;
			default:
				break;
		}
		return rs;
	}

    private List<Transition> buildTransitionsList(final Ion ion, 
    		final Ion nextIon, final Level srcLevel, final ResultSet rs)
    throws SQLException
    {
    	List<Transition> res = new ArrayList<Transition>();

		while(rs.next())
		{
			long[] iValues = new long[2];
			double[] dValues = new double[3];
			Level trgLevel = retrieveTransitionData(rs, ion, 
					nextIon, iValues, dValues);
			
			if(trgLevel != null)
			{
				Transition transition = new Transition();
				transition.setSourceState(srcLevel);
				transition.setTargetState(trgLevel);
				Parameter parameter =  new Parameter();
				transition.setParameter(parameter);
				long lTrId = iValues[1];
				
				transition.setTransitionId(lTrId);
				
				boolean isPopulatedTransition = 
					populateTransition(dValues, lTrId, parameter);
				
				if(isPopulatedTransition)
				{
					res.add(transition);
				}
			}
		}
		return res;
    }

	private ResultSet makeAiTransitionsResultSet(final Level level)
	{
        Connection conn = null;
        boolean bIsAC = true;
		ResultSet res = null;
        try 
        {
			final PreparedStatement ps =
			  	ECrmPreparedQuery.SelectAiTransitions.getStatement();
			conn = ps.getConnection();
			bIsAC = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps.setInt(1, level.getId());

			res = ps.executeQuery();

			conn.commit();
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}	
        finally
        {
            if (conn != null)
            {
                try
                {
                	conn.setAutoCommit(bIsAC);
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
        }
        return res;
	}
	
	private ResultSet makeExTransitionsResultSet(final Level level)
	{
        Connection conn = null;
        boolean bIsAC = true;
		ResultSet res = null;
        try 
        {
			final PreparedStatement ps =
			  	ECrmPreparedQuery.SelectExTransitions.getStatement();
			conn = ps.getConnection();
			bIsAC = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps.setInt(1, level.getId());

			res = ps.executeQuery();
			
			conn.commit();

		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}	
        finally
        {
            if (conn != null)
            {
                try
                {
                	conn.setAutoCommit(bIsAC);
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
        }
        return res;
	}
	
	private ResultSet makeITransitionsResultSet(final Level level)
	{
        Connection conn = null;
        boolean bIsAC = true;
		ResultSet res = null;
        try 
        {
			final PreparedStatement ps =
			  	ECrmPreparedQuery.SelectITransitions.getStatement();
			conn = ps.getConnection();
			bIsAC = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps.setInt(1, level.getId());
			res = ps.executeQuery();
			conn.commit();
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}	
        finally
        {
            if (conn != null)
            {
                try
                {
                	conn.setAutoCommit(bIsAC);
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
        }
        return res;
	}

	private ResultSet makeRtTransitionsResultSet(final Level level)
	{
        Connection conn = null;
        boolean bIsAC = true;
		ResultSet res = null;
        try 
        {
			final PreparedStatement ps =
			  	ECrmPreparedQuery.SelectRtTransitions.getStatement();
			conn = ps.getConnection();
			bIsAC = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps.setInt(1, level.getId());
			res = ps.executeQuery();
			conn.commit();
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}	
        finally
        {
            if (conn != null)
            {
                try
                {
                	conn.setAutoCommit(bIsAC);
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
        }
        return res;
	}
	
	private boolean populateTransition(final double[] values, 
			final long lTrId, Parameter parameter)
	{
		boolean res = false;
		switch(m_process)
		{
		case AUTOIONIZATION:
			res = populateAiTransition(values[1], values[0], parameter);
			break;
		case EXCITATION:
			res = populateExTransition(lTrId, values[0], parameter);
			break;
		case COLLISIONAL_IONIZATION:
			res = populateCiTransition(lTrId, parameter);
			break;
		case PHOTOIONIZATION:
			res = populatePiTransition(lTrId, values[0], parameter);
			break;
		case IONIZATION:
			res = populateITransition(lTrId, values[0], parameter);
			break;
		case RADIATION:
			res = populateRtTransition(values[0], values[1], values[2], parameter);
			break;
			default:
				break;
		}
		return res;
	}
	
	private boolean populateITransition(final long lTrId, 
			final double dThreshold, final Parameter parameter)
	{
		boolean res = false;
    	boolean ciRes = false;
    	boolean piRes = false;
        try 
        {
			final PreparedStatement ciIPs =
			  	ECrmPreparedQuery.SelectCiFits.getStatement();
			final PreparedStatement piIPs =
			  	ECrmPreparedQuery.SelectPiFits.getStatement();
			
			ciIPs.setLong(1, lTrId);
			piIPs.setLong(1, lTrId);
			
			parameter.set(TransitionParameter.Threshold, dThreshold);
			ciRes = populateCiFit(parameter, ciIPs);
			piRes =  populatePiFit(parameter, piIPs); 
			
			if(ciRes && piRes)
			{
				res = true;
			}
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
		return res;
	}
	
	private boolean populateRtTransition(final double dWavelength, 
			final double dRate, final double dMultipole, final Parameter parameter)
	{
		boolean res = false;
		parameter.set(TransitionParameter.Wavelength, dWavelength);
		parameter.set(TransitionParameter.Multipole, dMultipole);
		parameter.set(TransitionParameter.TransitionRate, dRate);
		res = true;
		return res;
	}

	private boolean populatePiTransition(final long lTrId, 
			final double dThreshold, final Parameter parameter)
	{
		boolean res = false;
        try 
        {
	        final PreparedStatement ps =
			  	ECrmPreparedQuery.SelectPiFits.getStatement();
	        ps.setLong(1, lTrId);
			parameter.set(TransitionParameter.Threshold, dThreshold);
			res = populatePiFit(parameter, ps);
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
		return res;
	}
	
	private boolean populateCiTransition(final long lTrId, final Parameter parameter)
	{
		boolean res = false;
        try 
        {
	        final PreparedStatement ps =
			  	ECrmPreparedQuery.SelectCiFits.getStatement();
	       ps.setLong(1, lTrId);
	       res =  populateCiFit(parameter, ps);
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
		return res;
	}

	private boolean populateExTransition(final long lTrId, 
			final double dOscillationStrength,  final Parameter parameter)
	{
		boolean res = false;
        try 
        {
	        final PreparedStatement ps =
			  	ECrmPreparedQuery.SelectExFits.getStatement();
	        ps.setLong(1, lTrId);			
			parameter.set(TransitionParameter.OscillationStrength, dOscillationStrength);
			res = populateExFit(parameter, ps);
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
		return res;
	}

	private boolean populateAiTransition(final double dThreshold, 
			final double dRate, final Parameter parameter)
	{
		boolean res = false;
		parameter.set(TransitionParameter.AutoionizationRate, dRate);
		parameter.set(TransitionParameter.Threshold, dThreshold);
		res = true;
		return res;
	}
    
    private Level retrieveTransitionData(final ResultSet transitionResultSet, 
    		final Ion ion, final Ion nextIon, 
    		final long[] iValues, final double[] dValues)
    throws SQLException
    {
    	Level trgLevel = null;
		switch(m_process)
		{
		case AUTOIONIZATION:
			iValues[0] = transitionResultSet.getInt("TARGET");
			trgLevel = getLevel(nextIon, iValues[0]);
			dValues[0] = transitionResultSet.getDouble("RATE");
			dValues[1] = transitionResultSet.getDouble("THRESHOLD");
			break;
		case EXCITATION:
			iValues[1] = transitionResultSet.getInt("TR_PK");
			iValues[0] = transitionResultSet.getInt("EX_TRG_AB_SRC");
			trgLevel = getLevel(ion, iValues[0]);
			dValues[0] = transitionResultSet.getDouble("EX_OSC_STRENGTH");
			break;
		case COLLISIONAL_IONIZATION:
			iValues[1] = transitionResultSet.getInt("TR_PK");
			iValues[0] = transitionResultSet.getInt("TARGET");
			trgLevel = getLevel(nextIon, iValues[0]);
			break;
		case PHOTOIONIZATION:
			iValues[1] = transitionResultSet.getInt("TR_PK");
			iValues[0] = transitionResultSet.getInt("TARGET");
			dValues[0] = transitionResultSet.getDouble("THRESHOLD");
			trgLevel = getLevel(nextIon, iValues[0]);
			break;
		case IONIZATION:
			iValues[1] = transitionResultSet.getInt("TR_PK");
			iValues[0] = transitionResultSet.getInt("TARGET");
			dValues[0] = transitionResultSet.getDouble("THRESHOLD");
			trgLevel = getLevel(nextIon, iValues[0]);
			break;
		case RADIATION:
			iValues[0] = transitionResultSet.getInt("EX_SRC_AB_TRG");
			dValues[0] = transitionResultSet.getDouble("AB_WAVELENGTH");
			dValues[1] = transitionResultSet.getDouble("AB_RATE");
			dValues[2] = transitionResultSet.getDouble("AB_MULTIPOLE");

			if(dValues[0] < 10000 && dValues[1] >= 10E5)
			{
				trgLevel = getLevel(ion, iValues[0]);
			}
			//trgLevel = getLevel(ion, iValues[0]);
			
			break;
			default:
				break;
		}
		return trgLevel;
    }

    private boolean populateCiFit(final Parameter parameter, final PreparedStatement ps)
    {
        Connection conn = null;
        boolean bIsAC = true;

        ResultSet rs = null;
		boolean res = false;
		
        try 
        {
			conn = ps.getConnection();
			bIsAC = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
            rs = ps.executeQuery();
            
			conn.commit();

			if (rs.next()) 
            {
				parameter.set(TransitionParameter.ciACoefficient, rs.getDouble("A"));
				parameter.set(TransitionParameter.ciBCoefficient, rs.getDouble("B"));
				parameter.set(TransitionParameter.ciCCoefficient, rs.getDouble("C"));
				parameter.set(TransitionParameter.ciDCoefficient, rs.getDouble("D"));
				res = true;
            }
        } 
        catch(SQLException sqle) 
        {
            sqle.printStackTrace();
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
            
            if (conn != null)
            {
                try
                {
                	conn.setAutoCommit(bIsAC);
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
            
        }
		return res;
    }
    
    private boolean populatePiFit(final Parameter parameter, final PreparedStatement ps)
    {
        Connection conn = null;
        boolean bIsAC = true;

        ResultSet rs = null;
		boolean res = false;
		
        try 
        {
			conn = ps.getConnection();
			bIsAC = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
            rs = ps.executeQuery();
            
			conn.commit();
            
            if (rs.next()) 
            {
				parameter.set(TransitionParameter.MethodNumber, rs.getInt("METHOD_NUMBER"));
				parameter.set(TransitionParameter.piACoefficient, rs.getDouble("A"));
				parameter.set(TransitionParameter.piBCoefficient, rs.getDouble("B"));
				parameter.set(TransitionParameter.piCCoefficient, rs.getDouble("C"));
				parameter.set(TransitionParameter.piDCoefficient, rs.getDouble("D"));
				res = true;
            }
        } 
        catch(SQLException sqle) 
        {
            sqle.printStackTrace();
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
                if (conn != null)
                {
                    try
                    {
                    	conn.setAutoCommit(bIsAC);
                    }
                    catch (SQLException e)
                    {
                        // Do nothing
                    }
                }
           }
        }
		return res;
    }
    
    private boolean populateExFit(final Parameter parameter, final PreparedStatement ps)
    {
        Connection conn = null;
        boolean bIsAC = true;

        ResultSet rs = null;
		boolean res = false;
		
        try 
        {
			conn = ps.getConnection();
			bIsAC = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
            rs = ps.executeQuery();
            
			conn.commit();
            
            if (rs.next()) 
            {
    			parameter.set(TransitionParameter.MethodNumber, rs.getInt("METHOD_NUMBER"));
    			parameter.set(TransitionParameter.exACoefficient, rs.getDouble("A"));
    			parameter.set(TransitionParameter.exBCoefficient, rs.getDouble("B"));
    			parameter.set(TransitionParameter.exCCoefficient, rs.getDouble("C"));
    			parameter.set(TransitionParameter.exDCoefficient, rs.getDouble("D"));
    			parameter.set(TransitionParameter.exECoefficient, rs.getDouble("E"));
    			res = true;
            }
        } 
        catch(SQLException sqle) 
        {
            sqle.printStackTrace();
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
            if (conn != null)
            {
                try
                {
                	conn.setAutoCommit(bIsAC);
                }
                catch (SQLException e)
                {
                    // Do nothing
                }
            }
        }
		return res;
    }
    
    private Level getLevel(final Ion ion, final long iPatternNumber)
    {
    	int iLevelsCount = ion.getChildCount();
    	Level res = null;
    	for(int i = 0; i < iLevelsCount; i++)
    	{
    		Level level = (Level)ion.getStateAt(i);
    		if(level.getId() == iPatternNumber)
    		{
    			res = level;
    			break;
    		}
    	}
    	return res;
    }

    public ResultSetTableModel getResultSetTableModel(String query)
    throws SQLException
    {
		if (m_connection == null)
		{
		    throw new IllegalStateException("Connection already closed.");
		}
	
		Statement statement = m_connection.createStatement(
		    		ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		
		ResultSet r = statement.executeQuery(query);
		return new ResultSetTableModel(r);
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
