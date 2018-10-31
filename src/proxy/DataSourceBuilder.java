package proxy;


import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import util.FileConstants;



final class DataSourceBuilder
{
  private DataSource m_dataSource;

  public void build()
  {
	  try 
	  {
		m_dataSource = makeDataSource();
	  } 
	  catch (JAXBException e) 
	  {
		e.printStackTrace();
	  }
  }
  
  public DataSource getDataSource()
  {
	  return m_dataSource;
  }
  
  private DataSource makeDataSource() throws JAXBException 
  {
      JAXBContext ctx = JAXBContext.newInstance(new Class[] {DataSource.class});
      Unmarshaller um = ctx.createUnmarshaller();
      String path = getUserHomeDirectory();
      DataSource dataSource = (DataSource) um.unmarshal(new File(path + "/datasource.xml"));
//      System.out.println(path);
//      DataSource dataSource = (DataSource) um.unmarshal(new File("datasource.xml"));
      return dataSource;
  }
  
  private String getUserHomeDirectory()
  {
//      String userHome = "user.home";        
//      String path = System.getProperty(userHome);
//      // System.out.println("Your Home Path: " + path);
//      return path;
      
      return FileConstants.HOME_DIR_NAME;
  }

}