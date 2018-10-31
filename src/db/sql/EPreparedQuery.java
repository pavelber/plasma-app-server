package db.sql;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import proxy.DbProxy;


enum EPreparedQuery
{
	GetElement(SqlQuery.GET_ELEMENT_QUERY),
	GetAllProjects(SqlQuery.GET_ALL_PROJECTS),
	GetSelectedProject(SqlQuery.GET_PROJECT),
	GetUser(SqlQuery.GET_USERS_QUERY),
	UpdatePassword(SqlQuery.UPDATE_USERS_QUERY),
	
	// fac2db
	InsertFacIons(SqlQuery.INSERT_FAC_IONS_QUERY),
	UpdateFacIons(SqlQuery.UPDATE_FAC_IONS_QUERY),
	InsertProject(SqlQuery.INSERT_PROJECT_QUERY),
	SelectLevelPk(SqlQuery.SELECT_LEVEL_PK_QUERY),
    SelectProjectPk(SqlQuery.SELECT_PROJECT_PK_QUERY),
    InsertExTransition(SqlQuery.INSERT_EX_TRANSITION_QUERY),
    InsertCiTransition(SqlQuery.INSERT_CI_TRANSITION_QUERY),
    InsertAiTransition(SqlQuery.INSERT_AI_TRANSITION_QUERY),
    UpdateAbTransition(SqlQuery.UPDATE_AB_TRANSITION_QUERY),
    UpdateRtRate(SqlQuery.UPDATE_RT_RATE_QUERY),
    UpdateAiRate(SqlQuery.UPDATE_AI_RATE_QUERY),
    InsertAbTransition(SqlQuery.INSERT_AB_TRANSITION_QUERY),
    SelectIonizationTrPk(SqlQuery.SELECT_IONIZATION_TR_PK_QUERY),
    SelectIonPotential(SqlQuery.SELECT_ION_POTENTIAL_QUERY),
    SelectExcitationTrPk(SqlQuery.SELECT_EXCITATION_TR_PK_QUERY),
    SelectLevelsCount(SqlQuery.SELECT_LEVELS_COUNT_QUERY),
    InsertPiPoint(SqlQuery.INSERT_PI_POINT_QUERY),
    InsertCiPoint(SqlQuery.INSERT_CI_POINT_QUERY),
    InsertExPoint(SqlQuery.INSERT_EX_POINT_QUERY),
    InsertLevel(SqlQuery.INSERT_LEVEL_QUERY),
    UpdateLevels(SqlQuery.UPDATE_LEVELS_QUERY),
    GetLevels(SqlQuery.GET_LEVELS_QUERY),
    
    
  // fit2db  
    SelectExTransitionPk(SqlQuery.SELECT_EX_TRANSITION_PK_QUERY),
    SelectCiTransitionPk(SqlQuery.SELECT_CI_TRANSITION_PK_QUERY),
    SelectPiTransitionPk(SqlQuery.SELECT_PI_TRANSITION_PK_QUERY),
    InsertExCsFit(SqlQuery.INSERT_EX_CS_FIT_QUERY),
    InsertCiCsFit(SqlQuery.INSERT_CI_CS_FIT_QUERY),
    InsertPiCsFit(SqlQuery.INSERT_PI_CS_FIT_QUERY),
    SelectOscStrength(SqlQuery.SELECT_OSC_STRENGTH_QUERY),
    SelectStatWeight(SqlQuery.SELECT_STAT_WEIGHT_QUERY),
    UpdateExTransition(SqlQuery.UPDATE_EX_TRANSITIONS_QUERY),
    UpdateExCsFit(SqlQuery.UPDATE_EX_CS_FIT_QUERY),
    UpdateCiCsFit(SqlQuery.UPDATE_CI_CS_FIT_QUERY),
    UpdatePiCsFit(SqlQuery.UPDATE_PI_CS_FIT_QUERY),
    DeleteExCsFit(SqlQuery.DELETE_EX_CS_FIT_QUERY),
    DeleteCiCsFit(SqlQuery.DELETE_CI_CS_FIT_QUERY),
    DeletePiCsFit(SqlQuery.DELETE_PI_CS_FIT_QUERY),
    VanRegemorterExCsFit(SqlQuery.VAN_REGEMORTER_EX_CS_FIT_QUERY);
	

    private PreparedStatement m_preparedStatement;
    
    
    private EPreparedQuery(final String sSql)
    {
    	final Connection con = DbProxy.getInstance().getConnection();
    	
    	try
    	{
			m_preparedStatement = con.prepareStatement(sSql);
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    }
    
    PreparedStatement getStatement()
    {
    	return m_preparedStatement;
    }
}
