package crm;


import java.sql.*;

import javax.swing.table.*;
import javax.swing.event.*;


public class ResultSetTableModel implements TableModel 
{
    ResultSet 			m_results;
    ResultSetMetaData 	m_metadata;
    int 				m_iNumCols, 
    					m_iNumRows;

    public ResultSetTableModel(ResultSet results) throws SQLException 
    {
		this.m_results 	= results;
		m_metadata 		= results.getMetaData(); 
		m_iNumCols 		= m_metadata.getColumnCount();
		results.last();
		m_iNumRows 		= results.getRow();
    }
    
    public void close() 
    {
		try 
		{ 
			m_results.getStatement().close(); 
		}
		catch(SQLException e) 
		{
			
		};
    }

    protected void finalize() 
    { 
    	close(); 
    }

    public int getColumnCount() 
    { 
    	return m_iNumCols; 
    }
    
    public int getRowCount() 
    { 
    	return m_iNumRows; 
    }

    public String getColumnName(int column) 
    {
		try 
		{
		    return m_metadata.getColumnLabel(column + 1);
		} 
		catch (SQLException e) 
		{ 
			return e.toString(); 
		}
    }

    // This TableModel method specifies the data type for each column.  
    // We could map SQL types to Java types, but for this example, we'll just
    // convert all the returned data to strings.
    public Class<?> getColumnClass(int column)
    { 
    	Class<?> res = null;
    	String type;
		try 
		{
			type = m_metadata.getColumnTypeName(column + 1);
			
			if(type.equals("INTEGER"))
			{
				res = Integer.class;
			}
			else if(type.equals("VARCHAR"))
			{
				res = String.class;
			}
			else if(type.equals("DOUBLE"))
			{
				res = Double.class;
			}
			else if(type.equals("BIGINT"))
			{
				res = Long.class;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
    	return res;//String.class; 
    }
    
    public Object getValueAt(int row, int column) 
    {
		try 
		{
		    m_results.absolute(row + 1);                // Go to the specified row
		    Object o = m_results.getObject(column + 1); // Get value of the column
		    if (o == null)
		    {
		    	return null; 
		    }
		    else 
		    {
		    	return o;//.toString();               // Convert it to a string
		    }
		} 
		catch (SQLException e) 
		{ 
			return e.toString(); 
		}
    }

    public boolean isCellEditable(int row, int column) 
    { 
    	return false; 
    } 

    public void setValueAt(Object value, int row, int column) {}
    public void addTableModelListener(TableModelListener l) {}
    public void removeTableModelListener(TableModelListener l) {}
}
