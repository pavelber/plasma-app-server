package converter;


import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import util.FileConstants;

import crm.xml.configuration.View;
import crm.xml.configuration.Views;


public class CrmConfigurationConverter
{
  private Views m_configurationViews;

  public CrmConfigurationConverter()
  {
	  try 
	  {
		  m_configurationViews = makeConfigurationViews();
	  } 
	  catch (JAXBException e) 
	  {
		e.printStackTrace();
	  }
  }
  
  private Views makeConfigurationViews() throws JAXBException 
  {
      JAXBContext ctx = JAXBContext.newInstance(new Class[] {Views.class});
      Unmarshaller um = ctx.createUnmarshaller();
      String path = FileConstants.HOME_DIR_NAME;
      Views configurationViews = (Views) um.unmarshal(new File(path + "/configuration_views.xml"));
      return configurationViews;
  }
  
  public String convert(final String sConfiguration)
  {
	  String sRes = null; 
	  
	  List<View>  views = m_configurationViews.getView();
	  for(int i = 0; i < views.size(); i++)
	  {
		  View view = views.get(i);
		  if(sConfiguration.equals(view.getRalchenko()))
		  {
			  sRes = view.getFisher();
		  }
	  }
	  return sRes;
  }
}

