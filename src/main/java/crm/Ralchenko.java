package crm;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Formatter;

import util.CrmConstants;
//import java.util.List;


import crm.model.State;
import crm.model.state.Calculation;
import crm.model.state.Element;
import crm.model.state.Ion;
import crm.model.state.Level;
import db.sql.CrmDbManager;


public class Ralchenko 
{
	private static final long serialVersionUID = 1L;
	
	static CrmDbManager 	ms_factory;
	private static Calculation 			ms_calc;
	private static boolean 				ms_bIsAuto = false;

    private static final String ION_TO_STRING = "%1$3d %2$3d %3$3d %4$3d %5$7.2f %6$5d %7$7.2f";
    private static final String CALC_TO_STRING = "%1$-5s %2$2d %3$2d";

	public Ralchenko(final Calculation c, final CrmDbManager f)
	{
		ms_calc = c;
		ms_factory = f;
	}
    	 
    public void writeStructure(final String sFileName)
    {
    	final StringBuffer sbIons = new StringBuffer();
    	final StringBuffer sbLevels = new StringBuffer();
		Element element = (Element)ms_calc.getStateAt(0);

    	final Enumeration<State> enIons = element.getSubStates();
    	while(enIons.hasMoreElements())
    	{
    		Ion ion = (Ion)enIons.nextElement();
    		makeIonsDefinition(ion, sbIons);
    		makeLevelsDefinition(ion, sbLevels);    	
    	}
    	
		StringBuilder strBuilder     = new StringBuilder();
		Formatter     formatter      = new Formatter(strBuilder);
      
		formatter.format(CALC_TO_STRING,
				element.getSymbol() + " " + element.getNumber(), 
				ms_calc.getStartIonNumber(),
				ms_calc.getEndIonNumber());
    	
    	write(sFileName, strBuilder.toString() + 
    			CrmConstants.RALCHENKO_IN1_PRELUDE + 
    			sbIons.toString() + 
    			sbLevels.toString());
    }
    
    private void write(final String sFileName, final String sData)
    {
        try 
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(sFileName));
            out.write(sData);
            out.close();
        } 
        catch (IOException e) 
        {
        }
    }
	 
    private int getLevelsCountFor(final Ion ion)
    {
    	return ion.getChildCount();
    }
    
    private int getAutoionizingLevelsCountFor(final Ion ion)
    {
    	int res = 0;
		final Enumeration<State> en = ion.getSubStates();
		while(en.hasMoreElements())
		{
			Level level = (Level)en.nextElement();
			if(level.getEnergy() > ion.getFacIonizationPotential())
			{
				res += 1;
			}
		}
		return res;
    }

    private void makeIonsDefinition(final Ion ion, StringBuffer sb)
    {
		int iLevelsCount = getLevelsCountFor(ion);
		int iAutoionizingLevelsCount = getAutoionizingLevelsCountFor(ion);
		int iNonAutoionizingLevelsCount = iLevelsCount - iAutoionizingLevelsCount;
		
		String       newline      = System.getProperty( "line.separator" );
		NumberFormat numFormatter = new DecimalFormat("#.####");
		String       sFacIonizPot    = numFormatter.format(ion.getFacIonizationPotential()); 
		String       sNistIonizPot   = numFormatter.format(ion.getNistIonizationPotential()); 
      
		StringBuilder strBuilder     = new StringBuilder();
		Formatter     formatter      = new Formatter(strBuilder);
      
		formatter.format(ION_TO_STRING,
        		ion.getNumber(), 
        		iNonAutoionizingLevelsCount,
        		iAutoionizingLevelsCount, 
      		  	0,
      		  	Double.parseDouble(sFacIonizPot),
      		  	ion.getAggrScaleFactor(),
      		  	Double.parseDouble(sNistIonizPot));
      
      	sb.append(strBuilder.toString() + newline);
    }

    private void makeLevelDefinition(final Ion ion, final Level level, 
    		final StringBuffer sb)
    {
		String newline = System.getProperty( "line.separator" );

		if(level.getEnergy() == 0)
		{
			sb.append(ion.getNumber() + ""  + newline);
			ms_bIsAuto = false;
		}
		
		if(level.getEnergy() > ion.getFacIonizationPotential() && !ms_bIsAuto)
		{
			sb.append(CrmConstants.AUTOIONIZATION_TITLE  + newline);
			ms_bIsAuto = true;
		}

		sb.append(level.toRalchenko()  + newline);
    }
    
    private void makeLevelsDefinition(final Ion ion, 
    		final StringBuffer sb)
    {
		for(int i = 0; i < ion.getChildCount(); i++)
		{
			Level level = (Level)ion.getStateAt(i);
			makeLevelDefinition(ion, level, sb);
		}
    }
}
