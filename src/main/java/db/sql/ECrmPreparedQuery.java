package db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proxy.DbProxy;


enum ECrmPreparedQuery
{
    SelectExTransitions(SqlQuery.CRM_EXCITATION_TR_QUERY),
    SelectITransitions(SqlQuery.IONIZATION_TR_QUERY),
    SelectAiTransitions(SqlQuery.AUTOIONIZATION_TR_QUERY),
    SelectRtTransitions(SqlQuery.RADIATIVE_TR_QUERY),
    SelectCiFits(SqlQuery.CI_FIT_QUERY),
    SelectPiFits(SqlQuery.PI_FIT_QUERY),
    SelectExFits(SqlQuery.EX_FIT_QUERY);
    //SelectIonPotential(SqlQuery.SELECT_POTENTIAL_BY_ION);
    

    private PreparedStatement m_preparedStatement;
    
    
    private ECrmPreparedQuery(final String sSql)
    {
    	final Connection con = DbProxy.getInstance().getConnection();
    	
    	try
    	{
			m_preparedStatement = con.prepareStatement(sSql, 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
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