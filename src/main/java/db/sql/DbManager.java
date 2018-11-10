package db.sql;

import db.*;
import db.factory.AtomicDataBuilder;
import response.Atom;
import response.Calc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbManager
{

  public DbManager()
  {
  }
  
  public boolean updatePassword(final String sUsername, final String sPassword) 
  {
  	boolean res = false;
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement ps =
              EPreparedQuery.UpdatePassword.getStatement();
          conn = ps.getConnection();
          conn.setAutoCommit(false);
          ps.clearParameters();
    	  ps.setString(1, sPassword);
    	  ps.setString(2, sUsername);
          bIsAC = conn.getAutoCommit();
          ps.execute();
          conn.commit();
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
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

  
  public boolean isLoginValid(final String sUsername, final String sPassword) 
  {
  	boolean res = false;
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement ps =
              EPreparedQuery.GetUser.getStatement();
          conn = ps.getConnection();
          conn.setAutoCommit(false);
          ps.clearParameters();
    	  ps.setString(1, sUsername);
          bIsAC = conn.getAutoCommit();
          ResultSet result = ps.executeQuery();
          while(result.next())
          {
          	String sPassWord = result.getString(USERS.PASSWORD.toString());
          	
          	if(sPassWord.equals(sPassword))
          	{
          		res = true;
          		break;
          	}
          }
          conn.commit();
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
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

  public Atom getElement(final int iElement) 
  {
  	Atom atom = new Atom();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetElement =
              EPreparedQuery.GetElement.getStatement();
          conn = psGetElement.getConnection();
          conn.setAutoCommit(false);
          psGetElement.clearParameters();
    	  psGetElement.setInt(1, iElement);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetElement.executeQuery();
          while(result.next())
          {
          	String sName 	= result.getString(ELEMENTS.EL_NAME.toString());
          	double dWeight 	= result.getDouble(ELEMENTS.EL_WEIGHT.toString());
          	String sSymbol	= result.getString(ELEMENTS.EL_SYMBOL.toString());
          	
          	atom.setAtomName(sName);
          	atom.setAtomNum((short) iElement);
          	atom.setAtomSymbol(sSymbol);
          	atom.setAtomWeight(dWeight);
          }
          conn.commit();
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
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
      return atom;
  }
  
  public List<Calc> getCalcs() 
  {
  	List<Calc> calcs = new ArrayList<Calc>();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetAllProjects =
              EPreparedQuery.GetAllProjects.getStatement();
          conn = psGetAllProjects.getConnection();
          conn.setAutoCommit(false);
    	  psGetAllProjects.clearParameters();
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetAllProjects.executeQuery();
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString());
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	Calc calc = new Calc();
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	calcs.add(calc);
          }
          conn.commit();
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
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
      return calcs;
  }
  
  public Calc getCalc(final int iCalcId) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
          psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();

          ResultSet result = psGetSelectedProject.executeQuery();
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildStructure(calc);
          }
          conn.commit();
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }
  
  public Calc getStructure(final int iCalcId) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();

          ResultSet result = psGetSelectedProject.executeQuery();
          while(result.next())
          {
          	int iProjectId 	= result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildStructure(calc);
          }
          conn.commit();
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }
  
  public Calc getTransitions(final int iCalcId, final int iIonCharge, final int iLevelId) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetSelectedProject.executeQuery();
          
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildTransitionsForLevel(calc, iIonCharge, iLevelId, true);
          }
          
          conn.commit();
         
          
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }
  
  public Calc getTransitions(final int iCalcId) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetSelectedProject.executeQuery();
          
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildTransitions(calc, true);
          }
          
          conn.commit();
         
          
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }
  
  public Calc getExcitationTransitions(final int iCalcId) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetSelectedProject.executeQuery();
          
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildExcitationTransitions(calc, true);
          }
          
          conn.commit();
         
          
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }
  
  public Calc getExcitationTransitionsForIon(final int iCalcId, final int iIonCharge) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetSelectedProject.executeQuery();
          
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildExcitationTransitionsForIon(calc, iIonCharge, true);
          }
          
          conn.commit();
         
          
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }

  public Calc getIonizationTransitionsForIon(final int iCalcId, final int iIonCharge) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetSelectedProject.executeQuery();
          
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildIonizationTransitionsForIon(calc, iIonCharge, true);
          }
          
          conn.commit();
         
          
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }
  
  public Calc getPhotoionizationTransitionsForIon(final int iCalcId, final int iIonCharge) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetSelectedProject.executeQuery();
          
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildPhotoionizationTransitionsForIon(calc, iIonCharge, true);
          }
          
          conn.commit();
         
          
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }



  public Calc getIonizationTransitions(final int iCalcId) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetSelectedProject.executeQuery();
          
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildIonizationTransitions(calc, true);
          }
          
          conn.commit();
         
          
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }
  
  public Calc getPhotoionizationTransitions(final int iCalcId) 
  {
  	Calc calc = new Calc();
    boolean bIsAC = true;
    Connection conn = null;

      try 
      {
          final PreparedStatement psGetSelectedProject =
              EPreparedQuery.GetSelectedProject.getStatement();
          conn = psGetSelectedProject.getConnection();
          conn.setAutoCommit(false);
    	  psGetSelectedProject.clearParameters();
    	  psGetSelectedProject.setInt(1, iCalcId);
          bIsAC = conn.getAutoCommit();
          ResultSet result = psGetSelectedProject.executeQuery();
          
          while(result.next())
          {
          	int iProjectId = result.getInt(PROJECTS.PROJECT_PK.toString());
          	String sName = result.getString(PROJECTS.PR_NAME.toString());
          	int iElement = result.getInt(PROJECTS.EL_NUMBER.toString());
          	int iEnd = result.getInt(PROJECTS.END_ION_NUMBER.toString()) + 1;
          	int iStart = result.getInt(PROJECTS.START_ION_NUMBER.toString());
          	
          	calc.setCalcName(sName);
          	calc.setCalcId(iProjectId);
          	calc.setEndIonNum((short) iEnd);
          	calc.setStartIonNum((short)iStart);
          	calc.setAtom(getElement(iElement));
          	
          	AtomicDataBuilder adb = new AtomicDataBuilder(conn);
          	adb.buildPhotoionizationTransitions(calc, true);
          }
          
          conn.commit();
         
          
      } 
      catch(SQLException sqle) 
      {
          sqle.printStackTrace();
      }
      catch(ClassNotFoundException cnfe)
      {
    	  cnfe.printStackTrace();
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
      return calc;
  }

}
