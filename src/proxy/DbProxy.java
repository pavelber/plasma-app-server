package proxy;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public final class DbProxy 
{
    private Connection 		m_connection;
    private static DbProxy 	m_instance = new DbProxy(); 
    
    public static DbProxy getInstance()
    {
		return m_instance;
    }
    
    public Connection getConnection()
    {
    	return m_connection;
    }

    private DbProxy()
    {
    	connect();
    }
    
    public void disconnect()
    {
    	try 
    	{
			m_connection.close();
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    }
    
    private void connect()
    {
    	DataSourceBuilder dataSourceBuilder = new DataSourceBuilder();
        try
        {
        	dataSourceBuilder.build();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        
        DataSource dataSource = dataSourceBuilder.getDataSource();
        
    	System.out.println("Starting DbClient for database " + dataSource.getName());
    	//System.out.println("\thost name - " + dataSource.getHostName());
    	//System.out.println("\tport number - " + dataSource.getPortNumber());
    	
    	
    	
    	try 
    	{
    		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
    		String url = dataSource.getUrl() + dataSource.getName() + ";create=true";
    		m_connection = DriverManager.getConnection(url);
    	} 
    	catch (InstantiationException e) 
    	{
    		e.printStackTrace();
    	} 
    	catch (IllegalAccessException e) 
    	{
    		e.printStackTrace();
    	} 
    	catch (ClassNotFoundException e) 
    	{
    		e.printStackTrace();
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
  	  	}
   	
//    	String url = dataSource.getUrl() + dataSource.getName() + ";create=true";
//    	
//    	try 
//    	{
//    		m_connection = DriverManager.getConnection(url);
//    		//m_connection.setAutoCommit(false);///////////////////////////////////////
//    	} 
//    	catch (SQLException e) 
//    	{
//    		e.printStackTrace();
//  	  	}
    }
}

