package fac2db.level;


import fac2db.record.LevelRecord;


public final class LevelState implements ILevelState
{
    private static final String TO_STRING_FORMAT = "LevelState[" +
            "number=%d, complex=%s, nrconfig=%s, rconfig=%s, statweight=%d, " +
            "parity=%d, energy=%s, project=%d, " +
            "element=%d, ion=%d]";

    private int 	m_iNumber;
    private int 	m_iBaseIndex;
    private double 	m_dEnergy;
    private int 	m_iParity;
    private int 	m_iVnl;
    private int 	m_iStatisticalWeight;
    private String 	m_sComplexName;
    private String 	m_sNonRelativsticName;
    private String 	m_sRelativsticName;
    private int 	m_iElementNumber;
    private int 	m_iIonNumber;
    private int 	m_iProjectNumber;
    private String 	m_sConfiguration;
    private boolean m_selected;
    private double 	m_dHLikeOscStrength;
    private int     m_iCrmNumber;
    
    public int getCrmNumber()
    {
        return m_iCrmNumber;
    }
    
    public LevelState setCrmNumber(final int num)
    {
    	m_iCrmNumber = num;
        return this;
    }
    
    
    public double getHLikeOscStrength()
    {
        return m_dHLikeOscStrength;
    }
    
    public LevelState setHLikeOscStrength(final double d)
    {
    	m_dHLikeOscStrength = d;
        return this;
    }

    public String getConfiguration() 
    {
		return m_sConfiguration;
	}

	public void setConfiguration(String configuration) 
	{ 
		m_sConfiguration = configuration;
	}

	public boolean isSelected() 
    {
		return m_selected;
	}

	public LevelState setSelected(boolean selected) 
	{
		m_selected = selected;
        return this;
	}

	public int getProjectNumber() 
    {
		return m_iProjectNumber;
	}

	public LevelState setProjectNumber(int projectNumber) 
	{
		m_iProjectNumber = projectNumber;
        return this;
	}

	public int getElementNumber() 
    {
		return m_iElementNumber;
	}

	public LevelState setElementNumber(int elementNumber) 
	{
		m_iElementNumber = elementNumber;
        return this;
	}

	public int getIonNumber() 
	{
		return m_iIonNumber;
	}

	public LevelState setIonNumber(int ionNumber) 
	{
		m_iIonNumber = ionNumber;
        return this;
	}

	public int getVnl()
    {
        return m_iVnl;
    }
    
    public LevelState setVnl(final int iVnl)
    {
        m_iVnl = iVnl;
        return this;
    }

    public int getParity()
    {
        return m_iParity;
    }
    
    public LevelState setParity(final int iParity)
    {
        m_iParity = iParity;
        return this;
    }

    public int getBaseIndex()
    {
        return m_iBaseIndex;
    }
    
    public LevelState setBaseIndex(final int iIndex)
    {
        m_iBaseIndex = iIndex;
        return this;
    }

    public int getNumber()
    {
        return m_iNumber;
    }
    
    public LevelState setNumber(final int iNumber)
    {
        m_iNumber = iNumber;
        return this;
    }

    public double getEnergy()
    {
        return m_dEnergy;
    }
    
    public boolean hasEnergy()
    {
        final boolean bResult = (m_dEnergy != Double.NaN);
        
        return bResult;
    }

    public LevelState setEnergy(final double dEnergy)
    {
        m_dEnergy = dEnergy;
        return this;
    }
    
    public int getStatisticalWeight()
    {
        return m_iStatisticalWeight;
    }
    
    public LevelState setStatisticalWeight(final int iWeight)
    {
        m_iStatisticalWeight = iWeight;
        return this;
    }
    
    public String getComplexName()
    {
        return m_sComplexName;
    }
    
    public LevelState setComplexName(final String sName)
    {
        m_sComplexName = sName;
        return this;
    }
    
    public String getNonRelativsticName()
    {
        return m_sNonRelativsticName;
    }
    
    public LevelState setNonRelativsticName(final String sName)
    {
        m_sNonRelativsticName = sName;
        return this;
    }
    
    public String getRelativsticName()
    {
        return m_sRelativsticName;
    }
    
    public LevelState setRelativsticName(final String sName)
    {
        m_sRelativsticName = sName;
        return this;
    }
    
    public LevelRecord toRecord()
    {
		String[] values = new String[10];
		values[0] = (m_iNumber + 1) + "";
		values[1] = m_sComplexName;
		values[2] = m_sNonRelativsticName;
		values[3] = m_sRelativsticName;
		values[4] = m_iStatisticalWeight + "";
		values[5] = m_iParity + "";
		values[6] = m_dEnergy + "";
		values[7] = m_iProjectNumber + "";
		values[8] = m_iElementNumber + "";
		values[9] = m_iIonNumber + "";
		
		LevelRecord record = new LevelRecord();
		record.set(values);
		return record;
    }
    
    @Override
    public final String toString()
    {
        final String sResult = String.format(TO_STRING_FORMAT, m_iNumber,
        		m_sComplexName, 
        		m_sNonRelativsticName, m_sRelativsticName, 
        		m_iStatisticalWeight, m_iParity,
        		Double.toString(m_dEnergy),  
        		m_iProjectNumber, m_iElementNumber, m_iIonNumber);
        
        return sResult;
    }
}
