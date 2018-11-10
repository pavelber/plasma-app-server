package db.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import converter.FacConfigurationConverter;
import fac2db.level.ILevelState;
import fac2db.level.LevelState;
import fac2db.record.EAbTransitionFields;
import fac2db.record.EAiTransitionFields;
import fac2db.record.ECiPointFields;
import fac2db.record.ECiTransitionFields;
import fac2db.record.EExPointFields;
import fac2db.record.EExTransitionFields;
import fac2db.record.ELevelFields;
import fac2db.record.EPiPointFields;
import fac2db.record.LevelRecord;
import fac2db.record.PointRecord;
import fac2db.record.TransitionRecord;
import fac2db.transition.Transition;

final class FacDbRecordManager 
{
	public static final double 	LAMBDA0 		= 12398.4282; // Wavelength in angstrom, associated with 1 eV
	
	public void setLevelFields(PreparedStatement ps, 
			  final ILevelState level)throws SQLException
	{
	    LevelRecord record = level.toRecord();
		//System.out.print(record + "...\n");
		ps.setInt(		1, Integer.parseInt(record.get(ELevelFields.lev_number)));
		ps.setString(	2, record.get(ELevelFields.complex));
		String sNrelConfig = record.get(ELevelFields.nrel_config);
		ps.setString(	3, sNrelConfig);
		String[] tokens = sNrelConfig.split("\\s+");
		if(tokens.length == 1)
		{
			String sConf = FacConfigurationConverter.convert(level);
		  	ps.setString(4, sConf);
		}
		else if(tokens.length == 3)
		{
			String sConf = tokens[1] + " " + tokens[2];
		  	ps.setString(4, sConf);
		}
		else
		{
		  	ps.setString(4, sNrelConfig);
		}
		ps.setString(	5, record.get(ELevelFields.rel_config));
		ps.setInt(		6, Integer.parseInt(record.get(ELevelFields.stat_weight)));
		ps.setInt(		7, Integer.parseInt(record.get(ELevelFields.parity)));
		ps.setDouble(	8, Double.parseDouble(record.get(ELevelFields.energy)));
		ps.setInt(		9, Integer.parseInt(record.get(ELevelFields.pr_id)));
		ps.setInt(		10, Integer.parseInt(record.get(ELevelFields.el_number)));
		ps.setInt(		11, Integer.parseInt(record.get(ELevelFields.ion_number)));
	}
	
	public void setExTransitionFields(PreparedStatement ps, 
			  final Transition transition)throws SQLException
	{
	  TransitionRecord record = transition.toRecord();
		//System.out.print(record + "...\n");
		ps.setInt(		1, (int)record.get(EExTransitionFields.source));
		ps.setInt(		2, (int)record.get(EExTransitionFields.target));
		ps.setDouble(	3, record.get(EExTransitionFields.threshold));
		ps.setDouble(	4, record.get(EExTransitionFields.bethe));
		ps.setDouble(	5, record.get(EExTransitionFields.born1));
		ps.setDouble(	6, record.get(EExTransitionFields.born2));
	}
	
	public void setCiTransitionFields(PreparedStatement ps, 
			  final Transition transition)throws SQLException
	{
	  TransitionRecord record = transition.toRecord();
		//System.out.print(record + "...\n");
		ps.setInt(		1, (int)record.get(ECiTransitionFields.source));
		ps.setInt(		2, (int)record.get(ECiTransitionFields.target));
		ps.setDouble(	3, record.get(ECiTransitionFields.threshold));
	}
	
	public void setAiTransitionFields(PreparedStatement ps, 
			  final Transition transition)throws SQLException
	{
	  TransitionRecord record = transition.toRecord();
		//System.out.print(record + "...\n");
		ps.setInt(		1, (int)record.get(EAiTransitionFields.source));
		ps.setInt(		2, (int)record.get(EAiTransitionFields.target));
		ps.setDouble(	3, record.get(EAiTransitionFields.threshold));
		ps.setDouble(	4, record.get(EAiTransitionFields.rate));
		ps.setDouble(	5, record.get(EAiTransitionFields.dc_strength));
	}
	
	public void setPiPointFields(PreparedStatement ps, 
			  PointRecord record)throws SQLException
	{
		//System.out.print(record + "...\n");
		ps.setLong(		1, (long)record.get(EPiPointFields.tr_id));
		ps.setInt(		2, (int)record.get(EPiPointFields.point_number));
		ps.setDouble(	3, record.get(EPiPointFields.energy));
		ps.setDouble(	4, record.get(EPiPointFields.rr_cross_section));
		ps.setDouble(	5, record.get(EPiPointFields.pi_cross_section));
		ps.setDouble(	6, record.get(EPiPointFields.osc_strength));
	}
	
	public void setCiPointFields(PreparedStatement ps, 
			  final PointRecord record)throws SQLException
	{
		//System.out.print(record + "...\n");
		ps.setLong(		1, (long)record.get(ECiPointFields.tr_id));
		ps.setInt(		2, (int)record.get(ECiPointFields.point_number));
		ps.setDouble(	3, record.get(ECiPointFields.energy));
		ps.setDouble(	4, record.get(ECiPointFields.coll_strength));
		ps.setDouble(	5, record.get(ECiPointFields.cross_section));
	}
	
	public void setExPointFields(PreparedStatement ps, 
			  final PointRecord record)throws SQLException
	{
		//System.out.print(record + "...\n");
		ps.setLong(		1, (long)record.get(EExPointFields.tr_id));
		ps.setInt(		2, (int)record.get(EExPointFields.point_number));
		ps.setDouble(	3, record.get(EExPointFields.energy));
		ps.setDouble(	4, record.get(EExPointFields.coll_strength));
		ps.setDouble(	5, record.get(EExPointFields.cross_section));
	}
	
	public void setAbTransitionFields(PreparedStatement ps, 
			  final Transition transition)throws SQLException
	{
	  TransitionRecord record = transition.toRecord();
		//System.out.print(record + "...\n");
		ps.setDouble(	1, LAMBDA0 / record.get(EAbTransitionFields.threshold));////////////////////////
		ps.setDouble(	2, record.get(EAbTransitionFields.osc_strength));
		ps.setDouble(	3, record.get(EAbTransitionFields.rate));
		ps.setDouble(	4, record.get(EAbTransitionFields.multipole));
		ps.setInt(		5, (int)record.get(EAbTransitionFields.source));
		ps.setInt(		6, (int)record.get(EAbTransitionFields.target));
	}
	
	//private void setAbTransitionFields(PreparedStatement ps, 
	//		  final Transition transition)throws SQLException
	//{
	//  TransitionRecord record = transition.toRecord();
	//	//System.out.print(record + "...\n");
	//	ps.setInt(		1, (int)record.get(EAbTransitionFields.source));
	//	ps.setInt(		2, (int)record.get(EAbTransitionFields.target));
	//	ps.setDouble(	3, record.get(EAbTransitionFields.threshold));////////////////////////
	//	ps.setDouble(	4, LAMBDA0 / record.get(EAbTransitionFields.threshold));////////////////////////
	//	ps.setDouble(	5, record.get(EAbTransitionFields.osc_strength));
	//	ps.setDouble(	6, record.get(EAbTransitionFields.rate));
	//	ps.setDouble(	7, record.get(EAbTransitionFields.multipole));
	//}
	
	public void setLevParams(PreparedStatement ps, final LevelState level)
	throws SQLException
	{
		ps.setDouble(	1, level.getHLikeOscStrength());
		ps.setInt(		2, level.getNumber());
		ps.setInt(		3, level.getProjectNumber());
		ps.setInt(		4, level.getIonNumber());
	}
}