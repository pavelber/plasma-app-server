package db.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

final class DbKeyRetriever 
{
	  public int getProjectPk(final String sProjectName)
	  {
		  int res = -1;
		  try 
		  {
			  final PreparedStatement ps =
				  	EPreparedQuery.SelectProjectPk.getStatement();
			  
		      ps.clearParameters();
		      ps.setString(1, sProjectName);
		      ResultSet rs = ps.executeQuery();
		      
		      if (rs.next()) 
		      {
		          res = rs.getInt("PROJECT_PK");
		      }
		  } 
		  catch(SQLException sqle) 
		  {
		      sqle.printStackTrace();
		  }
		  
		  return res;
	  }

	  public int getLevelPk(final int iProject, final int iIon, final int iLevel) 
	  {
		  int res = -1;
		  try 
		  {
		      final PreparedStatement ps =
		  		  	EPreparedQuery.SelectLevelPk.getStatement();

		      ps.clearParameters();
		      ps.setInt(1, iProject);
		      ps.setInt(2, iIon);
		      ps.setInt(3, iLevel);
		      ResultSet rs = ps.executeQuery();
		      
		      if (rs.next()) 
		      {
		          res = rs.getInt("LEVEL_PK");
		      }
		  } 
		  catch(SQLException sqle) 
		  {
		      sqle.printStackTrace();
		  }
		  
		  return res;
	  }

	  public long getExTransitionPk(final int iSource, final int iTarget) 
	  {
		  long res = -1;
		  try 
		  {
			  final PreparedStatement ps =
				  	EPreparedQuery.SelectExTransitionPk.getStatement();
			  
		      ps.clearParameters();
		      ps.setInt(1, iSource);
		      ps.setInt(2, iTarget);
		      ResultSet rs = ps.executeQuery();
		      
		      if (rs.next()) 
		      {
		          res = rs.getLong("TR_PK");
		      }
		  } 
		  catch(SQLException sqle) 
		  {
		      sqle.printStackTrace();
		  }
		  
		  return res;
	  }
	  
	  public long getCiTransitionPk(final int iSource, final int iTarget) 
	  {
		  long res = -1;
		  try 
		  {
			  final PreparedStatement ps =
				  	EPreparedQuery.SelectCiTransitionPk.getStatement();
			  
		      ps.clearParameters();
		      ps.setInt(1, iSource);
		      ps.setInt(2, iTarget);
		      ResultSet rs = ps.executeQuery();
		      
		      if (rs.next()) 
		      {
		          res = rs.getLong("TR_PK");
		      }
		  } 
		  catch(SQLException sqle) 
		  {
		      sqle.printStackTrace();
		  }
		  
		  return res;
	  }

	  public long getPiTransitionPk(final int iSource, final int iTarget) 
	  {
		  long res = -1;
		  try 
		  {
			  final PreparedStatement ps =
				  	EPreparedQuery.SelectPiTransitionPk.getStatement();
			  
		      ps.clearParameters();
		      ps.setInt(1, iSource);
		      ps.setInt(2, iTarget);
		      ResultSet rs = ps.executeQuery();
		      
		      if (rs.next()) 
		      {
		          res = rs.getLong("TR_PK");
		      }
		  } 
		  catch(SQLException sqle) 
		  {
		      sqle.printStackTrace();
		  }
		  
		  return res;
	  }
	  
	  public long getIonizationTrPk(final int iSource, final int iTarget) 
	  {
		  long res = -1;
		  try 
		  {
		      final PreparedStatement ps =
		  		  	EPreparedQuery.SelectIonizationTrPk.getStatement();
	
		      ps.clearParameters();
			  ps.setInt(1, iSource);
			  ps.setInt(2, iTarget);
		      ResultSet rs = ps.executeQuery();
		      
		      if (rs.next()) 
		      {
		          res = rs.getInt("TR_PK");
		      }
		  } 
		  catch(SQLException sqle) 
		  {
		      sqle.printStackTrace();
		  }
		  
		  return res;
	  }

}
