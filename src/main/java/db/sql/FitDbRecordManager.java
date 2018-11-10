package db.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.CI_CS_FITS;
import db.EX_CS_FITS;
import db.PI_CS_FITS;
import fit2db.record.CiFitRecord;
import fit2db.record.ExFitRecord;
import fit2db.record.PiFitRecord;


final class FitDbRecordManager 
{
	
	  public void setCiFitFields(PreparedStatement ps, 
			  final CiFitRecord record)throws SQLException
	  {
		//System.out.print(record + "...");
	 	ps.setLong(		1, (long)record.get(CI_CS_FITS.TR_ID));
	  	ps.setInt(		2, (int)record.get(CI_CS_FITS.METHOD_NUMBER));
	  	ps.setDouble(	3, record.get(CI_CS_FITS.A));
	  	ps.setDouble(	4, record.get(CI_CS_FITS.B));
	  	ps.setDouble(	5, record.get(CI_CS_FITS.C));
	  	ps.setDouble(	6, record.get(CI_CS_FITS.D));
	  	ps.setString(	7, record.getIsFitValid());
	  }
	  
	  public void setPiFitFields(PreparedStatement ps, 
			  final PiFitRecord record)throws SQLException
	  {
		//System.out.print(record + "...");
	 	ps.setLong(		1, (long)record.get(PI_CS_FITS.TR_ID));
	  	ps.setInt(		2, (int)record.get(PI_CS_FITS.METHOD_NUMBER));
	  	ps.setDouble(	3, record.get(PI_CS_FITS.A));
	  	ps.setDouble(	4, record.get(PI_CS_FITS.B));
	  	ps.setDouble(	5, record.get(PI_CS_FITS.C));
	  	ps.setDouble(	6, record.get(PI_CS_FITS.D));
	  	ps.setString(	7, record.getIsFitValid());
	  }
	  
	  public void setExFitFields(PreparedStatement ps, 
			  final ExFitRecord record)throws SQLException
	  {
		//System.out.print(record + "...");
	 	ps.setLong(		1, (long)record.get(EX_CS_FITS.TR_ID));
	  	ps.setInt(		2, (int)record.get(EX_CS_FITS.METHOD_NUMBER));
	  	ps.setDouble(	3, record.get(EX_CS_FITS.A));
	  	ps.setDouble(	4, record.get(EX_CS_FITS.B));
	  	ps.setDouble(	5, record.get(EX_CS_FITS.C));
	  	ps.setDouble(	6, record.get(EX_CS_FITS.D));
	  	ps.setDouble(	7, record.get(EX_CS_FITS.E));
	  	ps.setString(	8, record.getIsFitValid());
	  }
}
