package fac2db.level;


import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.LineNumberReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

//import db.sql.FacDbManager;
import db.sql.FitDbManager;



public final class LevelScanner
{
	private static boolean 				m_bElementFound 			= false;
	private static boolean 				m_bTopElectronsCountFound 	= false;
	private static boolean 				m_bTopLevelsCountFound 		= false;
    private static int 					m_iElementNumber;
    private static int 					m_iTopElectronsCount;
    private static int 					m_iBottomElectronsCount;
    private static int 					m_iProjectId;
    private static List<ILevelState> 	m_fromLevels;
    private FitDbManager 					m_dbManager;
    
    private void parseLine(final String sLine)
    {
        final String[] fields = rawParseLine(sLine);
        
        if(fields == null) return;
        
        final LevelFactory factory = new LevelFactory();
        final ELevelProperty[] props = ELevelProperty.values();
        Map<ELevelProperty, String> map = new EnumMap<ELevelProperty, String>(ELevelProperty.class);
        
        for (int i = 0; i < props.length; i++)
        {
    		map.put(props[i], fields[i].trim());
        }
        
        LevelState level = null;
        
    	if(m_bTopElectronsCountFound || m_iBottomElectronsCount == 0)
    	{
    		if(m_bTopElectronsCountFound)
    		{
    	        level = factory.createLevel(map, 
    	        		m_iProjectId, m_iElementNumber, 
    	        		(m_iElementNumber - m_iTopElectronsCount + 1));
    		}
    		else
    		{
    	        level = factory.createLevel(map, 
    	        		m_iProjectId, m_iElementNumber, 
    	        		(m_iElementNumber  + 1));
    		}
    	}
        m_fromLevels.add(level);
    }
    
    private String[] rawParseLine(final String sLine)//, final boolean bIsTopPart)
    {
    	String[] res = null;
        Scanner scr = new Scanner(sLine);
        
        //System.out.println(sLine);
        
        if(!m_bElementFound && isElement(scr))
        {
        	setElement(sLine);
        	m_bElementFound = true;
        }
        else if(isElectronsCount(scr))
        {
        	setElectronsCount(sLine);
        }
        else if(isLevelsCount(scr))
        {
        	setLevelsCount(sLine);
        }
        else if(isLevel(scr))
        {
        	res = setLevel(sLine);
        }
        return res;
    }
    
    public void scan(final File file, final int iProjectId,
    		final FitDbManager dbManager) throws IOException
    {
    	m_iProjectId = iProjectId;
    	m_fromLevels = new ArrayList<ILevelState>();
    	m_dbManager = dbManager;
    	
        final LineNumberReader reader =
                new LineNumberReader(new FileReader(file));
        
        for (String sLine = reader.readLine();
        sLine != null; sLine = reader.readLine())
        {
          	parseLine(sLine);
        }
        m_bElementFound = false;
        try 
        {
			m_dbManager.batchInsertLevels(m_fromLevels);
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
        reader.close();
    }
    
    private boolean isElement(final Scanner scr)
    {
    	boolean res = false;
		final Pattern p = ELevelPattern.Element.getPattern();
        String s = scr.findInLine(p);
        
        if(s != null)
        {
        	res = true;
        }
        return res;
    }
    
    private boolean isElectronsCount(final Scanner scr)
    {
    	boolean res = false;
		final Pattern p = ELevelPattern.ElectronsCount.getPattern();
        String s = scr.findInLine(p);
        if(s != null)
        {
        	res = true;
        }
        return res;
    }
    
    private boolean isLevelsCount(final Scanner scr)
    {
    	boolean res = false;
		final Pattern p = ELevelPattern.LevelsCount.getPattern();
        String s = scr.findInLine(p);
        
        if(s != null)
        {
        	res = true;
        }
        return res;
    }

    private boolean isLevel(final Scanner scr)
    {
    	boolean res = false;
		final Pattern p = ELevelPattern.Level.getPattern();
        String s = scr.findInLine(p);
        if(s != null)
        {
        	res = true;
        }
        return res;
    }
    
    private void setElement(final String sLine)
    {
    	  String[] fields = sLine.split("\\s+");
          m_iElementNumber = (int)Double.parseDouble(fields[3]);
    }
    
    private void setElectronsCount(final String sLine)
    {
    	  String[] fields = sLine.split("\\s+");
      	  if(!m_bTopElectronsCountFound)
      	  {
        	  m_iTopElectronsCount = (int)Double.parseDouble(fields[2]);
              m_bTopElectronsCountFound = true;
      	  }
      	  else
      	  {
              m_bTopElectronsCountFound = false;
        	  m_iBottomElectronsCount = (int)Double.parseDouble(fields[2]);
      	  }
    }
    
    private void setLevelsCount(final String sLine)
    {
       	  if(!m_bTopLevelsCountFound)
       	  {
              m_bTopLevelsCountFound = true;
       	  }
       	  else
       	  {
              m_bTopLevelsCountFound = false;
       	  }
    }
    
    private String[] setLevel(final String s)
    {
    	String[] res = new String[9];
    	if(m_bTopElectronsCountFound || m_iBottomElectronsCount == 0)
    	{
    		if(m_bTopElectronsCountFound)
    		{
    			res[0] = getLevNum(s);
              	res[1] = -1 + "";
              	res[2] = getEnergy(s);
              	res[3] = getParity(s);
              	res[4] = getVnl(s);
              	res[5] = getStatWeight(s);
              	res[6] = getComplex(s);
              	res[7] = getConfiguration(s);
              	res[8] = getRConfiguration(s);
    		}
    		else
    		{
    			res[0] = 0 + "";
              	res[1] = -1 + "";
              	res[2] = 0.000 + "";
              	res[3] = getParity(s);
              	res[4] = getVnl(s);
              	res[5] = getStatWeight(s);
              	res[6] = getComplex(s);
              	res[7] = getConfiguration(s);
              	res[8] = getRConfiguration(s);
    		}
    	}
		else
		{
			res = null;
		}

      	return res;
    }
    
    private String getVnl(final String s)
    {
    	String res = null;
    	Scanner scr = new Scanner(s);
		final Pattern p = ELevelPattern.LevVnl.getPattern();
    	String sPattern = scr.findInLine(p).trim();
    	
    	if(sPattern != null)
    	{
        	String[] fields = sPattern.split("\\s+");

    		res = fields[1];
    	}
    	return res;
    }
    
    private String getParity(final String s)
    {
    	String res = null;
    	Scanner scr = new Scanner(s);
		final Pattern p = ELevelPattern.LevParity.getPattern();
    	String sPattern = scr.findInLine(p).trim();
    	if(sPattern != null)
    	{
    		res = sPattern;
    	}
    	return res;
    }
    
    private String getRConfiguration(final String s)
    {
    	String res = null;
    	Scanner scr = new Scanner(s);
		final Pattern p = ELevelPattern.LevRConfiguration.getPattern();
    	String sPattern = scr.findInLine(p).trim();
    	if(sPattern != null)
    	{
    		res = sPattern;
    	}
    	return res;
    }
    
    private String getConfiguration(final String s)
    {
    	String res = null;
    	Scanner scr = new Scanner(s);
		final Pattern p = ELevelPattern.LevConfiguration.getPattern();
    	String sPattern = scr.findInLine(p).trim();
    	if(sPattern != null)
    	{
    		res = sPattern;
    	}
    	return res;
    }
    
    private String getComplex(final String s)
    {
    	String res = null;
    	Scanner scr = new Scanner(s);
		final Pattern p = ELevelPattern.LevComplex.getPattern();
    	String sPattern = scr.findInLine(p).trim();
    	if(sPattern != null)
    	{
    		res = sPattern;
    	}
    	return res;
    }
    
    private String getLevNum(final String s)
    {
		final Pattern p = ELevelPattern.LevILev.getPattern();
    	String res = null;
    	Scanner scr = new Scanner(s);
    	String sPattern = scr.findInLine(p).trim();
    	
    	if(sPattern != null)
    	{
    		res =  sPattern.substring(0, sPattern.indexOf("-1")).trim();
    	}
    	return res;
    }
    
    private String getEnergy(final String s)
    {
		final Pattern p = ELevelPattern.LevEnergy.getPattern();
    	String res = null;
    	Scanner scr = new Scanner(s);
    	String sEnergy = scr.findInLine(p).trim();
    	
    	if(sEnergy != null)
    	{
    		res = sEnergy;
    	}
    	return res;
    }
    
    private String getStatWeight(final String s)
    {
		final Pattern p = ELevelPattern.LevTwinJ.getPattern();
		String res = null;
    	Scanner scr = new Scanner(s);
    	String sPattern = scr.findInLine(p).trim();
    	
	  	if(sPattern != null)
	  	{
	  		String[] value = sPattern.split("\\p{Blank}+");
	  		int iStWeight = Integer.parseInt(value[2]) + 1;
	  		res = iStWeight + "";
	  	}
        return res;
    }  
}
