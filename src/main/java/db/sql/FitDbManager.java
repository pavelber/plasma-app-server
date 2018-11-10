package db.sql;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import comparator.LevelEnergyComparator;

import proxy.DbProxy;
import db.*;
import fac2db.level.ILevelState;
import fac2db.level.LevelState;
import fac2db.property.ETransitionProcess;
import fac2db.record.PointRecord;
import fac2db.transition.CrossSectionPoint;
import fac2db.transition.CsPointList;
import fac2db.transition.IonLevelsCount;
import fac2db.transition.Transition;
import fit2db.record.*;
import fit2db.scanner.*;


public class FitDbManager
{
    private Connection m_conn;
    private static final int SQL_BATCH_SIZE = 65534;
	
	public FitDbManager()
	{
		DbProxy db = DbProxy.getInstance();
		m_conn = db.getConnection();
	}
  
	public void close()
	{
		if(m_conn != null)
		{
			try 
			{
				m_conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
  public int getLevelPk(final int iProject, final int iIon, final int iLevel) 
  {
		int res = -1;
		final DbKeyRetriever keyRetriever = new DbKeyRetriever();
		res = keyRetriever.getLevelPk(iProject, iIon, iLevel);
		return res;
  }
	
  public int getProjectPk(final String sProjectName)
  {
	int res = -1;
	final DbKeyRetriever keyRetriever = new DbKeyRetriever();
	res = keyRetriever.getProjectPk(sProjectName);
	return res;
  }
  
  public void updateExFitCoefficients(final long transPK, double[] coeffs) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.UpdateExCsFit.getStatement();
  		
      	ps.setDouble(	1, coeffs[0]);
      	ps.setDouble(	2, coeffs[1]);
      	ps.setDouble(	3, coeffs[2]);
      	ps.setDouble(	4, coeffs[3]);
      	ps.setDouble(	5, coeffs[4]);
      	ps.setLong(		6, transPK);
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }

  public void updateCiFitCoefficients(final long transPK, double[] coeffs) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.UpdateCiCsFit.getStatement();
  		
      	ps.setDouble(	1, coeffs[0]);
      	ps.setDouble(	2, coeffs[1]);
      	ps.setDouble(	3, coeffs[2]);
      	ps.setDouble(	4, coeffs[3]);
      	ps.setLong(		5, transPK);
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }

  public void updatePiFitCoefficients(final long transPK, double[] coeffs) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.UpdatePiCsFit.getStatement();
  		
      	ps.setDouble(	1, coeffs[0]);
      	ps.setDouble(	2, coeffs[1]);
      	ps.setDouble(	3, coeffs[2]);
      	ps.setDouble(	4, coeffs[3]);
      	ps.setLong(		5, transPK);
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }
  
  public void insertExFitCoefficients(final long transPK, final double[] coeffs, final int iMethod) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.InsertExCsFit.getStatement();
  		
      	ps.setLong(		1, transPK);
      	ps.setInt(		2, iMethod);
      	ps.setDouble(	3, coeffs[0]);
      	ps.setDouble(	4, coeffs[1]);
      	ps.setDouble(	5, coeffs[2]);
      	ps.setDouble(	6, coeffs[3]);
      	ps.setDouble(	7, coeffs[4]);
      	ps.setString(	8, "YES");
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }

  public void insertCiFitCoefficients(final long transPK, double[] coeffs, final int iMethod) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.InsertCiCsFit.getStatement();
  		
      	ps.setLong(		1, transPK);
      	ps.setInt(		2, iMethod);
      	ps.setDouble(	3, coeffs[0]);
      	ps.setDouble(	4, coeffs[1]);
      	ps.setDouble(	5, coeffs[2]);
      	ps.setDouble(	6, coeffs[3]);
      	ps.setString(	7, "YES");
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }

  public void insertPiFitCoefficients(final long transPK, double[] coeffs, final int iMethod)  
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.InsertPiCsFit.getStatement();
  		
      	ps.setLong(		1, transPK);
      	ps.setInt(		2, iMethod);
      	ps.setDouble(	3, coeffs[0]);
      	ps.setDouble(	4, coeffs[1]);
      	ps.setDouble(	5, coeffs[2]);
      	ps.setDouble(	6, coeffs[3]);
      	ps.setString(	7, "YES");
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }
  
  public void deleteExFit(final long transPK) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.DeleteExCsFit.getStatement();

      	ps.setLong(	1, transPK );
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }

  public void deleteCiFit(final long transPK) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.DeleteCiCsFit.getStatement();
  		
      	ps.setLong(	1, transPK );
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }

  public void deletePiFit(final long transPK) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.DeletePiCsFit.getStatement();
  		
      	ps.setLong(	1, transPK );
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }
  
  public void vanRegemorterExFit(final long transPK) 
  throws SQLException
  {
	try 
	{
		m_conn.setAutoCommit(false);
		final PreparedStatement ps =
			  	EPreparedQuery.VanRegemorterExCsFit.getStatement();
  		
      	ps.setLong(	1, transPK );
      	ps.execute();
	    m_conn.commit();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
  }
	
	public void loadExFits(final int iProject, final List<ExFit> fits)
	{
		final FitDbRecordMaker recordMaker = new FitDbRecordMaker();
		final List<ExTransitionRecord> trRecords = new ArrayList<ExTransitionRecord>();
		final List<ExFitRecord> records = new ArrayList<ExFitRecord>();
		for(int i = 0; i < fits.size(); i++)
		{
			ExFit fit = fits.get(i);
			ExFitRecord record = recordMaker.makeFitRecord(iProject, fit);
			ExTransitionRecord trRecord = recordMaker.makeExTransitionRecord(iProject, fit);
			if(record != null) records.add(record);
			if(trRecord != null) trRecords.add(trRecord);
		}
		
		try 
		{
			batchExFitRecords(records);
			batchExTransitionRecords(trRecords);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void loadPiFits(final int iProject, final List<PiFit> fits)
	{
		final FitDbRecordMaker recordMaker = new FitDbRecordMaker();
		final List<PiFitRecord> records = new ArrayList<PiFitRecord>();
		for(int i = 0; i < fits.size(); i++)
		{
			PiFit fit = fits.get(i);
			PiFitRecord record = recordMaker.makeFitRecord(iProject, fit);
			if(record != null) records.add(record);
		}
		
		try 
		{
			batchPiFitRecords(records);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void loadCiFits(final int iProject, final List<CiFit> fits)
	{
		final FitDbRecordMaker recordMaker = new FitDbRecordMaker();
		final List<CiFitRecord> records = new ArrayList<CiFitRecord>();
		for(int i = 0; i < fits.size(); i++)
		{
			CiFit fit = fits.get(i);
			CiFitRecord record = recordMaker.makeFitRecord(iProject, fit);
			if(record != null) records.add(record);
		}
		
		try 
		{
			batchCiFitRecords(records);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
  private void batchCiFitRecords(final List<CiFitRecord> records) throws SQLException
  {
		try 
		{
			final FitDbRecordManager recordManager = new FitDbRecordManager();
			m_conn.setAutoCommit(false);

			final PreparedStatement ps =
				  	EPreparedQuery.InsertCiCsFit.getStatement();
	  		ps.clearBatch();
	  		int index = 0;
	  		
			for(int j = 0; j < records.size(); j++)
			{
				index += 1;
				
				if(index < SQL_BATCH_SIZE)
				{
			    	CiFitRecord record = records.get(j);
			    	recordManager.setCiFitFields(ps, record);
			        ps.addBatch();
				}
				else
				{
					ps.executeBatch();
					index = 1;
			  		ps.clearBatch();
			    	CiFitRecord record = records.get(j);
			    	recordManager.setCiFitFields(ps, record);
			        ps.addBatch();
				}
			}

		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting CI Cross-Section Fits");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
  private void batchPiFitRecords(final List<PiFitRecord> records) 
  throws SQLException
  {
		try 
		{
			final FitDbRecordManager recordManager = new FitDbRecordManager();
			m_conn.setAutoCommit(false);
			final PreparedStatement ps =
				  	EPreparedQuery.InsertPiCsFit.getStatement();
	  		int index = 0;
	  		
			for(int j = 0; j < records.size(); j++)
			{
				index += 1;
				
				if(index < SQL_BATCH_SIZE)
				{
			    	PiFitRecord record = records.get(j);
			    	recordManager.setPiFitFields(ps, record);
			        ps.addBatch();
				}
				else
				{
					ps.executeBatch();
					index = 1;
			  		ps.clearBatch();
			    	PiFitRecord record = records.get(j);
			    	recordManager.setPiFitFields(ps, record);
			        ps.addBatch();
				}
			}
			
		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting PI Cross-Section Fits");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

  private void batchExFitRecords(final List<ExFitRecord> records) 
  throws SQLException
  {
		try 
		{
			final FitDbRecordManager recordManager = new FitDbRecordManager();
			m_conn.setAutoCommit(false);
			final PreparedStatement ps =
				  	EPreparedQuery.InsertExCsFit.getStatement();
	  		int index = 0;
	  		
			for(int j = 0; j < records.size(); j++)
			{
				index += 1;
				
				if(index < SQL_BATCH_SIZE)
				{
			    	ExFitRecord record = records.get(j);
			    	recordManager.setExFitFields(ps, record);
			        ps.addBatch();
				}
				else
				{
					ps.executeBatch();
					index = 1;
			  		ps.clearBatch();
			    	ExFitRecord record = records.get(j);
			    	recordManager.setExFitFields(ps, record);
			        ps.addBatch();
				}
			}
			
		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting EX Cross-Section Fits");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

  private void batchExTransitionRecords(final List<ExTransitionRecord> records) 
  throws SQLException
  {
		try 
		{
			m_conn.setAutoCommit(false);
			final PreparedStatement ps =
				  	EPreparedQuery.UpdateExTransition.getStatement();
	  		int index = 0;
	  		
			for(int j = 0; j < records.size(); j++)
			{
				index += 1;
				
				if(index < SQL_BATCH_SIZE)
				{
			    	ExTransitionRecord record = records.get(j);
			      	ps.setDouble(	1, record.get(EXAB_TRANSITIONS.EX_OSC_STRENGTH));
			      	ps.setLong(		2, (long)record.get(EXAB_TRANSITIONS.TR_PK));
			        ps.addBatch();
				}
				else
				{
					ps.executeBatch();
					index = 1;
			  		ps.clearBatch();
			    	ExTransitionRecord record = records.get(j);
			      	ps.setDouble(	1, record.get(EXAB_TRANSITIONS.EX_OSC_STRENGTH));
			      	ps.setLong(		2, (long)record.get(EXAB_TRANSITIONS.TR_PK));
			        ps.addBatch();
				}
			}

		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Updating EX Transitions");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	  
  private static void processUpdateCounts(final BatchUpdateException e, 
		  final String title) 
  {
//		  	int [] updateCounts = e.getUpdateCounts(); 
//			System.err.println(title + " - Contents of BatchUpdateException:");
//		    System.err.println(" Update counts: ");
		    
//		    for (int k = 0; k < updateCounts.length; k++) 
//		    {
//		      System.err.println("  Statement " + k + ":" + updateCounts[k]);
//		    }
		    
    System.err.println(" Message: " + e.getMessage());     
    System.err.println(" SQLSTATE: " + e.getSQLState());
    System.err.println(" Error code: " + e.getErrorCode());
    SQLException ex = e.getNextException();  
    
    while (ex != null) 
    {                                       
      System.err.println("SQL exception:");
      System.err.println(" Message: " + ex.getMessage());
      System.err.println(" SQLSTATE: " + ex.getSQLState());
      System.err.println(" Error code: " + ex.getErrorCode());
      ex = ex.getNextException();
    }
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
	public void loadTransitions(final List<Transition> list,
			final ETransitionProcess process)
	{
		try 
		{
			switch(process)
			{
			case Absorbtion:
				batchUpdateAbTransitions(list);
				break;
			case Autoionization:
				batchInsertAiTransitions(list);
				break;
			case Excitation:
				List<CsPointList> exPoints = batchInsertExTransitions(list);
				batchInsertExPoints(exPoints);
				break;
			case Ionization:
				List<CsPointList> ciPoints = batchInsertCiTransitions(list);
				batchInsertCiPoints(ciPoints);
				break;
			case Photoionization:
				List<CsPointList> piPoints = makePiPointsCollection(list);
				batchInsertPiPoints(piPoints);
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

	  private int getInsertedProjectId(PreparedStatement ps) throws SQLException
	  {
		int res = -1;
		int rowsAffected = ps.executeUpdate();
		if (rowsAffected == 1)
		{
			ResultSet resultset = ps.getGeneratedKeys();
			
			if (resultset != null && resultset.next())
			{
				res = resultset.getInt(1);
			}
		}
		return res;
	  }

	  public int insertProject(final String projectName, final int elNumber, 
			  final int startIon, final int endIon) 
	  {
	      int res = -1;
	      final DbKeyRetriever keyRetriever = new DbKeyRetriever();

	      try 
	      {
	        final PreparedStatement ps =
	    		  	EPreparedQuery.InsertProject.getStatement();
	    	  
	      	ps.clearParameters();
			ps.setString(1, projectName);
			ps.setInt(2, elNumber);
			ps.setInt(3, startIon);
			ps.setInt(4, endIon);
	      	res = getInsertedProjectId(ps);
			
			if (res > 0)
			{
				//System.out.println("OK");
				insertFacIons(res, elNumber, startIon, endIon); 
			}
			else
			{
				//System.out.println("");
				res = keyRetriever.getProjectPk(projectName);
				insertFacIons(res, elNumber, startIon, endIon); 
			}
	      } 
	      catch(SQLException sqle) 
	      {
	    	  res = keyRetriever.getProjectPk(projectName);
	    	  try 
	    	  {
				insertFacIons(res, elNumber, startIon, endIon);
	    	  } 
	    	  catch (SQLException e) 
	    	  {
				e.printStackTrace();
	    	  }
	      }
	       
	      return res;
	  }

	  public void insertFacIons(final int prID, final int elNumber, 
			  final int startIon, final int endIon) 
	  throws SQLException
	  {
	      try 
	      {
	    	
			m_conn.setAutoCommit(false);
	        final PreparedStatement ps =
	    		  	EPreparedQuery.InsertFacIons.getStatement();

			for(int j = startIon; j <= endIon; j++)
			{
				double potential = getIonPotential(elNumber, j);
				ps.clearParameters();
				ps.setInt(1, j);
				ps.setDouble(2, potential);
				ps.setInt(3, prID);
				ps.setInt(4, elNumber);
				ps.addBatch();
			}
		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting FAC Ions");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	  }

	  public void batchUpdateFacIons(final int iProjectId, final int iIon, double dPotential)
	  throws SQLException
	  {
			try 
			{
				m_conn.setAutoCommit(false);
		  		final PreparedStatement ps =
				  	EPreparedQuery.UpdateFacIons.getStatement();
		  		
		  		ps.setDouble(1, dPotential);
		  		ps.setInt(2, iProjectId);
		  		ps.setInt(3, iIon);
		  		ps.executeUpdate();
		  		m_conn.commit();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
	  }

	  private void batchInsertAiTransitions(final List<Transition> list)
	  throws SQLException
	  {
			try 
			{
				final FacDbRecordManager recordManager = new FacDbRecordManager();
				m_conn.setAutoCommit(false);
				final PreparedStatement ps =
				  	EPreparedQuery.InsertAiTransition.getStatement();
		  		ps.clearBatch();
		  		int index = 0;

				for(int j = 0; j < list.size(); j++)
				{
					index += 1;
					
					if(index < SQL_BATCH_SIZE)
					{
						Transition transition = list.get(j);
					  	ps.clearParameters();
					  	recordManager.setAiTransitionFields(ps, transition);
					    ps.addBatch();
					}
					else
					{
						ps.executeBatch();
						index = 1;
				  		ps.clearBatch();
					    Transition transition = list.get(j);
				  	    ps.clearParameters();
				  	    recordManager.setAiTransitionFields(ps, transition);
				        ps.addBatch();
					}
				}

			    ps.executeBatch();
			    m_conn.commit();
			} 
			catch (BatchUpdateException e) 
			{
			    processUpdateCounts(e, "Inserting AI Transitions");
			    m_conn.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
	  }

	  private List<CsPointList> batchInsertExTransitions(final List<Transition> list)
	  throws SQLException
	  {
		    List<CsPointList> res = new ArrayList<CsPointList>();
		    
			try 
			{
				final FacDbRecordManager recordManager = new FacDbRecordManager();
				m_conn.setAutoCommit(false);
		  		final PreparedStatement ps =
				  	EPreparedQuery.InsertExTransition.getStatement();
		  		ps.clearBatch();

		  		int index = 0;

				for(int j = 0; j < list.size(); j++)
				{
					index += 1;
					
					if(index < SQL_BATCH_SIZE)
					{
						Transition transition = list.get(j);
						res.add(transition.getCsPointList());
					  	ps.clearParameters();
					  	recordManager.setExTransitionFields(ps, transition);
					    ps.addBatch();
					}
					else
					{
						ps.executeBatch();
						index = 1;
				  		ps.clearBatch();
						Transition transition = list.get(j);
						res.add(transition.getCsPointList());
					  	ps.clearParameters();
					  	recordManager.setExTransitionFields(ps, transition);
					    ps.addBatch();
					}
				}

			    ps.executeBatch();
			    m_conn.commit();
			} 
			catch (BatchUpdateException e) 
			{
			    processUpdateCounts(e, "Inserting EX Transitions");
			    m_conn.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			return res;
	  }


  public void batchUpdateLevels(final List<LevelState> list)
  throws SQLException
  {
		try 
		{
			final FacDbRecordManager recordManager = new FacDbRecordManager();
	        m_conn.setAutoCommit(false);
			final PreparedStatement ps =
			  	EPreparedQuery.UpdateLevels.getStatement();
	  		ps.clearBatch();

			for(int j = 0; j < list.size(); j++)
			{
			  LevelState level = list.get(j);
			  ps.clearParameters();
			  recordManager.setLevParams(ps, level);
		      ps.addBatch();
			}

		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Updating Levels");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
  }

  private List<CsPointList> batchInsertCiTransitions(final List<Transition> list)
  throws SQLException
  {
	    List<CsPointList> res = new ArrayList<CsPointList>();

	    try 
		{
			final FacDbRecordManager recordManager = new FacDbRecordManager();
	        m_conn.setAutoCommit(false);
	  		final PreparedStatement ps =
			  	EPreparedQuery.InsertCiTransition.getStatement();
	  		ps.clearBatch();
	  		int index = 0;

			for(int j = 0; j < list.size(); j++)
			{
				index += 1;
				
				if(index < SQL_BATCH_SIZE)
				{
					Transition transition = list.get(j);
					res.add(transition.getCsPointList());
				  	ps.clearParameters();
				  	recordManager.setCiTransitionFields(ps, transition);
				    ps.addBatch();
				}
				else
				{
					ps.executeBatch();
					index = 1;
			  		ps.clearBatch();
					Transition transition = list.get(j);
					res.add(transition.getCsPointList());
				  	ps.clearParameters();
				  	recordManager.setCiTransitionFields(ps, transition);
				    ps.addBatch();
				}
			}

		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting CI Transitions");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return res;
  }

  private void batchInsertCiPoints(final List<CsPointList> list)
  throws SQLException
  {
	  Connection conn = null;
      final DbKeyRetriever keyRetriever = new DbKeyRetriever();
      
		try 
		{
			final FacDbRecordManager recordManager = new FacDbRecordManager();
	        m_conn.setAutoCommit(false);
	  		final PreparedStatement ps =
			  	EPreparedQuery.InsertCiPoint.getStatement();
	  		ps.clearBatch();
	  		int index = 0;
	  		
			for(int j = 0; j < list.size(); j++)
			{
			    CsPointList pointList = list.get(j);
				int iSource = (int)pointList.getSource();
				int iTarget = (int)pointList.getTarget();
				long iTransitionId = keyRetriever.getIonizationTrPk(iSource, iTarget);
				
				index += pointList.getSize();
				
				if(index < SQL_BATCH_SIZE)
				{
					final Iterator<CrossSectionPoint> points = pointList.getPoints();
					while(points.hasNext())
					{
						CrossSectionPoint point = points.next();
						PointRecord record = point.toRecord(iTransitionId);
				        ps.clearParameters();
				        recordManager.setCiPointFields(ps, record);
					    ps.addBatch();
					}
				}
				else
				{
					ps.executeBatch();
					index = pointList.getSize();
			  		ps.clearBatch();
					final Iterator<CrossSectionPoint> points = pointList.getPoints();
					while(points.hasNext())
					{
						CrossSectionPoint point = points.next();
						PointRecord record = point.toRecord(iTransitionId);
				        ps.clearParameters();
				        recordManager.setCiPointFields(ps, record);
					    ps.addBatch();
					}
				}
			}

		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting CI Points");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
  }

  
  private void batchInsertExPoints(final List<CsPointList> list)
  throws SQLException
  {
      final DbKeyRetriever keyRetriever = new DbKeyRetriever();

      try 
		{
			final FacDbRecordManager recordManager = new FacDbRecordManager();
	  		final PreparedStatement ps =
			  	EPreparedQuery.InsertExPoint.getStatement();
	        m_conn.setAutoCommit(false);
	  		ps.clearBatch();
	  		int index = 0;
	  		
			for(int j = 0; j < list.size(); j++)
			{
			    CsPointList pointList = list.get(j);
				int iSource = (int)pointList.getSource();
				int iTarget = (int)pointList.getTarget();
				long iTransitionId = keyRetriever.getExTransitionPk(iSource, iTarget);
				
				index += pointList.getSize();
				
				if(index < SQL_BATCH_SIZE)
				{
					final Iterator<CrossSectionPoint> points = pointList.getPoints();
					while(points.hasNext())
					{
						CrossSectionPoint point = points.next();
						PointRecord record = point.toRecord(iTransitionId);
				        ps.clearParameters();
				        recordManager.setExPointFields(ps, record);
					    ps.addBatch();
					}
				}
				else
				{
					ps.executeBatch();
					index = pointList.getSize();
			  		ps.clearBatch();
					final Iterator<CrossSectionPoint> points = pointList.getPoints();
					while(points.hasNext())
					{
						CrossSectionPoint point = points.next();
						PointRecord record = point.toRecord(iTransitionId);
				        ps.clearParameters();
				        recordManager.setExPointFields(ps, record);
					    ps.addBatch();
					}
				}
			}

		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting EX Points");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
  }

 
  private void batchInsertPiPoints(final List<CsPointList> list)
  throws SQLException
  {
      final DbKeyRetriever keyRetriever = new DbKeyRetriever();

		try 
		{
			final FacDbRecordManager recordManager = new FacDbRecordManager();
	  		final PreparedStatement ps =
			  	EPreparedQuery.InsertPiPoint.getStatement();
	        m_conn.setAutoCommit(false);
	  		ps.clearBatch();
	  		int index = 0;
	  		
			for(int j = 0; j < list.size(); j++)
			{
			    CsPointList pointList = list.get(j);
				int iSource = (int)pointList.getSource();
				int iTarget = (int)pointList.getTarget();
				long iTransitionId = keyRetriever.getIonizationTrPk(iSource, iTarget);
				
				index += pointList.getSize();
				
				if(index < SQL_BATCH_SIZE)
				{
					final Iterator<CrossSectionPoint> points = pointList.getPoints();
					while(points.hasNext())
					{
						CrossSectionPoint point = points.next();
						PointRecord record = point.toRecord(iTransitionId);
				        ps.clearParameters();
				        recordManager.setPiPointFields(ps, record);
					    ps.addBatch();
					}
				}
				else
				{
					ps.executeBatch();
					index = pointList.getSize();
			  		ps.clearBatch();
					final Iterator<CrossSectionPoint> points = pointList.getPoints();
					while(points.hasNext())
					{
						CrossSectionPoint point = points.next();
						PointRecord record = point.toRecord(iTransitionId);
				        ps.clearParameters();
				        recordManager.setPiPointFields(ps, record);
					    ps.addBatch();
					}
				}
			}
		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting PI Points");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
  }
  
  public List<CsPointList> makePiPointsCollection(final List<Transition> list)
  throws SQLException
  {
	    List<CsPointList> res = new ArrayList<CsPointList>();
	    
		for(int j = 0; j < list.size(); j++)
		{
		  Transition transition = list.get(j);
		  transition.toRecord();
		  res.add(transition.getCsPointList());
		}
		return res;
  }
  
  public void updateAiRate(final int source, final int target, 
		  final double dEnergy, final double dRate )
  throws SQLException
  {
		try 
		{
	  		final PreparedStatement ps =
	  			EPreparedQuery.UpdateAiRate.getStatement();
	  		if(ps == null) return;////////////////////////////////////////////
	        m_conn.setAutoCommit(false);
	        
		  	ps.clearParameters();
		    ps.setDouble(1, dEnergy);
		    ps.setDouble(2, dRate);
		    ps.setInt(3, source);
		    ps.setInt(4, target);
		    ps.executeUpdate();
		    
		    m_conn.commit();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
  }


  public void updateRtRate(final int source, final int target, 
		  final double dEnergy, final double dRate )
  throws SQLException
  {
	  Connection conn = null;
		try 
		{
	  		final PreparedStatement ps =
	  			EPreparedQuery.UpdateRtRate.getStatement();
	  		if(ps == null) return;////////////////////////////////////////////
	        m_conn.setAutoCommit(false);
	        
		  	ps.clearParameters();
		    ps.setDouble(1, dEnergy);
		    ps.setDouble(2, dRate);
		    ps.setInt(3, source);
		    ps.setInt(4, target);
		    ps.executeUpdate();
		    
		    m_conn.commit();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
  }

  public void batchUpdateAbTransitions(final List<Transition> list)
  throws SQLException
  {
		try 
		{
			final FacDbRecordManager recordManager = new FacDbRecordManager();
	  		final PreparedStatement ps =
	  			EPreparedQuery.UpdateAbTransition.getStatement();
	  		if(ps == null) return;////////////////////////////////////////////
	        m_conn.setAutoCommit(false);
	  		
//			final PreparedStatement ps =
//				EPreparedQuery.InsertAbTransition.getStatement();
	  		ps.clearBatch();
	  		int index = 0;

			for(int j = 0; j < list.size(); j++)
			{
				index += 1;
				
				if(index < SQL_BATCH_SIZE)
				{
					Transition transition = list.get(j);
				  	ps.clearParameters();
				  	recordManager.setAbTransitionFields(ps, transition);
				    ps.addBatch();
				}
				else
				{
					ps.executeBatch();
					index = 1;
			  		ps.clearBatch();
					Transition transition = list.get(j);
				  	ps.clearParameters();
				  	recordManager.setAbTransitionFields(ps, transition);
				    ps.addBatch();
				}
			}

		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting Radiative Transitions");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
  }
  
  private double getIonPotential(final int elNumber, final int ionNumber)
  {
	  double res = -1;
	  try 
	  {
	      final PreparedStatement ps =
	  		  	EPreparedQuery.SelectIonPotential.getStatement();
	      //Connection conn = ps.getConnection();
	        //conn.setAutoCommit(false);

	      ps.clearParameters();
	      ps.setInt(1, elNumber);
	      ps.setInt(2, ionNumber);
	      ResultSet rs = ps.executeQuery();
	      
	      if (rs.next()) 
	      {
	          res = rs.getInt("IONIZATION_POTENTIAL");
	      }
	  } 
	  catch(SQLException sqle) 
	  {
	      sqle.printStackTrace();
	  }
	  
	  return res;
  }
  
  public void batchInsertLevels(final List<ILevelState> levels) 
  throws SQLException
  {
	  Collections.sort(levels, new LevelEnergyComparator());
	  
		try 
		{
			final FacDbRecordManager recordManager = new FacDbRecordManager();
	    	final PreparedStatement ps =
	  		  	EPreparedQuery.InsertLevel.getStatement();
	        m_conn.setAutoCommit(false);
	  		ps.clearBatch();

			for(int j = 0; j < levels.size(); j++)
			{
			  ILevelState level = levels.get(j);
		      ps.clearParameters();
		      recordManager.setLevelFields(ps, level);
		      ps.addBatch();
			}

		    ps.executeBatch();
		    m_conn.commit();
		} 
		catch (BatchUpdateException e) 
		{
		    processUpdateCounts(e, "Inserting Levels");
		    m_conn.rollback();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
  
//  private static void processUpdateCounts(final BatchUpdateException e, 
//		  final String title) 
//	{
////	  	int [] updateCounts = e.getUpdateCounts(); 
////		System.err.println(title + " - Contents of BatchUpdateException:");
////	    System.err.println(" Update counts: ");
////	    
////	    for (int k = 0; k < updateCounts.length; k++) 
////	    {
////	      System.err.println("  Statement " + k + ":" + updateCounts[k]);
////	    }
//	    
//	    System.err.println(" Message: " + e.getMessage());     
//	    System.err.println(" SQLSTATE: " + e.getSQLState());
//	    System.err.println(" Error code: " + e.getErrorCode());
//	    SQLException ex = e.getNextException();  
//	    
//	    while (ex != null) 
//	    {                                       
//	      System.err.println("SQL exception:");
//	      System.err.println(" Message: " + ex.getMessage());
//	      System.err.println(" SQLSTATE: " + ex.getSQLState());
//	      System.err.println(" Error code: " + ex.getErrorCode());
//	      ex = ex.getNextException();
//	    }
//	}
  
  public List<ILevelState> getLevels(final int iIon, final int iProjectId) 
  {
  	List<ILevelState> levels = new ArrayList<ILevelState>();

      try 
      {
    	  final PreparedStatement ps =
    		  	EPreparedQuery.GetLevels.getStatement();

    	  ps.clearParameters();
          ps.setInt(1, iIon);
          ps.setInt(2, iProjectId);
          ResultSet result = ps.executeQuery();
          while(result.next())
          {
          	int iPrjId 				= iProjectId;
          	int iLevelId 			= result.getInt("LEVEL_PK");
          	String sComplex 		= result.getString("COMPLEX");
          	String sConfig 			= result.getString("NREL_CONFIG");
          	String sConfiguration 	= result.getString("CONFIGURATION");
          	String sRelConfig		= result.getString("REL_CONFIG");
          	int iStatWeight 		= result.getInt("STAT_WEIGHT");
          	int iParity 			= result.getInt("PARITY");
          	double dEnergy 			= result.getDouble("ENERGY");
          	int iElementNumber 		= result.getInt("EL_NUMBER");
          	int iIonNumber 			= result.getInt("ION_NUMBER");
          	
          	ILevelState level = new LevelState();
          	level.setComplexName(sComplex);
          	level.setNonRelativsticName(sConfig);
          	level.setElementNumber(iElementNumber);
          	level.setEnergy(dEnergy);
          	level.setIonNumber(iIonNumber);
          	level.setConfiguration(sConfiguration);
          	level.setNumber(iLevelId);
          	level.setParity(iParity);
          	level.setProjectNumber(iPrjId);
          	level.setRelativsticName(sRelConfig);
          	level.setStatisticalWeight(iStatWeight);
          	level.setSelected(false);

          	levels.add(level);
          }
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      
      return levels;
  }
  
  public List<IonLevelsCount> getIonLevelsCount(final int iProject) 
  {
	  List<IonLevelsCount> res = new ArrayList<IonLevelsCount>();

	  try 
	  {
	      final PreparedStatement ps =
	  		  	EPreparedQuery.SelectLevelsCount.getStatement();

	      ps.clearParameters();
	      ps.setInt(1, iProject);
	      ResultSet rs = ps.executeQuery();
	      
	      while (rs.next()) 
	      {
	    		int iIonNumber = rs.getInt("ION_NUMBER");
	    		int iLevelsCount = rs.getInt(1);
	    		IonLevelsCount count = new IonLevelsCount(iIonNumber, iLevelsCount);
	    		res.add(count);
	      }
	  } 
	  catch(SQLException sqle) 
	  {
	      sqle.printStackTrace();
	  }
	  
	  return res;
  }  
  
  
  
  
  
  
  
  
  
  
  

}
