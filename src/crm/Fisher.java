package crm;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Formatter;

import converter.CrmConfigurationConverter;

import util.CrmConstants;


import crm.model.State;
import crm.model.state.Calculation;
import crm.model.state.Element;
import crm.model.state.Ion;
import crm.model.state.Level;
import db.sql.CrmDbManager;


public class Fisher 
{
	private static final long serialVersionUID = 1L;
	static CrmDbManager 	ms_factory;
	private static Calculation 			ms_calc;
	
	private static boolean 				ms_bIsLiLikeSelected = false;
	private static boolean 				ms_bIsHeLikeSelected = false;
	private static boolean 				ms_bIsBeLikeSelected = false;
	private static boolean 				ms_bIsBLikeSelected = false;
	
	private static boolean 				ms_bIsAuto = false;
	private static int 					ms_iAutoLiLikeLevelCount = 0;
	
	private static int 					ms_iAutoBeLikeLevelCount = 0;
	private static int 					ms_iAutoBLikeLevelCount = 0;
	
	
	private static int 					ms_iTotalNonAutoLevelsCount;
	
	private CrmConfigurationConverter m_confConverter;
	
	public Fisher(final Calculation c, final CrmDbManager f)
	{
		ms_calc = c;
		ms_factory = f;
		ms_iTotalNonAutoLevelsCount = getTotalNonAutoionizingLevelsCount();
		m_confConverter = new CrmConfigurationConverter();
	}
    	 
    public void writeStructure(final String sFileName)
    {
    	final StringBuffer sbIons = new StringBuffer(CrmConstants.FISHER_IN1_PRELUDE);
    	final StringBuffer sbLevels = new StringBuffer();
    	final StringBuffer sbHe = new StringBuffer();
    	final StringBuffer sbLi = new StringBuffer();
    	final StringBuffer sbBe = new StringBuffer();
    	final StringBuffer sbB = new StringBuffer();
		int iPrevIonMaxLevel = 0;
		
		Element element = (Element)ms_calc.getStateAt(0);
		
    	final Enumeration<State> enIons = element.getSubStates();
    	while(enIons.hasMoreElements())
    	{
    		Ion ion = (Ion)enIons.nextElement();
    		makeIonsDefinition(ion, sbIons);
    		makeLevelsDefinition(ion, sbLevels, sbHe, sbLi, sbBe, sbB, iPrevIonMaxLevel); 

    		int iLevelsCount = ion.getChildCount();
    		int iAutoionizingLevelsCount = getAutoionizingLevelsCountFor(ion);
    		int iNonAutoionizingLevelsCount = iLevelsCount - iAutoionizingLevelsCount;
     		iPrevIonMaxLevel += iNonAutoionizingLevelsCount;
    	}
    	write(sFileName, sbIons.toString() + sbLevels.toString()  + sbB.toString() + sbBe.toString() + sbLi.toString() + sbHe.toString());
    	
    	ms_bIsLiLikeSelected = false;
    	ms_bIsHeLikeSelected = false;
    	ms_bIsAuto = false;
    	ms_iAutoLiLikeLevelCount = 0;

    }
    
    private void makeLevelsDefinition(final Ion ion, 
    		final StringBuffer sb,  final StringBuffer sbHe, 
    		final StringBuffer sbLi, final StringBuffer sbBe, 
    		final StringBuffer sbB, int iPrevIonMaxLevel)
    {
		final Enumeration<State> enLevels = ion.getSubStates();
		while(enLevels.hasMoreElements())
		{
			Level level = (Level)enLevels.nextElement();
			level.setConfigurationConverter(m_confConverter);
			makeLevelDefinition(ion, level, sb, sbHe, sbLi, sbBe, sbB, iPrevIonMaxLevel);
		}
    }
    
    private void makeLevelDefinition(final Ion ion, final Level level, 
    		final StringBuffer sb, final StringBuffer sbHe, 
    		final StringBuffer sbLi, final StringBuffer sbBe, 
    		final StringBuffer sbB, int iPrevIonMaxLevel)
    {
		String newline = System.getProperty( "line.separator" );
		int iElectronsCount = ms_calc.getElementNumber() - ion.getNumber() + 1; 
		String sElementSymbol = ((Element)ms_calc.getStateAt(0)).getSymbol();
		
		if(level.getEnergy() == 0.0)
		{
			sb.append(ion.getNumber() + ""  + newline);
			ms_bIsAuto = false;
		}
		
		//System.out.println("ion " + ion.getNumber() + " lev # " + level.getNumber() + " energy " + level.getEnergy() + " potential " + ion.getFacIonizationPotential());
		
		if(level.getEnergy() <= ion.getFacIonizationPotential())
		{
			//System.out.println("ion " + ion.getNumber() + " lev # " + level.getNumber() + " energy " + level.getEnergy() + " potential " + ion.getFacIonizationPotential());
			sb.append(level.toFisher(iPrevIonMaxLevel)  + newline);
		}
		else if(level.getEnergy() > ion.getFacIonizationPotential())
		{
			System.out.println("ion " + ion.getNumber() + " lev # " + level.getNumber() + " energy " + level.getEnergy() + " potential " + ion.getFacIonizationPotential());
			if(!ms_bIsAuto)
			{
		        if(iElectronsCount == 2 && !ms_bIsHeLikeSelected)
		        {
		        	sbHe.append(CrmConstants.HE_LIKE_TITLE + " " + 
		        			sElementSymbol + " QSs" + newline);
		        	ms_bIsHeLikeSelected = true;
		        	ms_bIsAuto = true;
		        }
		        else if(iElectronsCount == 3 && !ms_bIsLiLikeSelected)
		        {
		        	sbLi.append(CrmConstants.LI_LIKE_TITLE + " " + 
		        			sElementSymbol + " QSs" + newline);
		        	ms_bIsLiLikeSelected = true;
		        	ms_bIsAuto = true;
		        }
		        else if(iElectronsCount == 4 && !ms_bIsBeLikeSelected)
		        {
		        	sbLi.append(CrmConstants.BE_LIKE_TITLE + " " + 
		        			sElementSymbol + " QSs" + newline);
		        	ms_bIsBeLikeSelected = true;
		        	ms_bIsAuto = true;
		        }
		        else if(iElectronsCount == 5 && !ms_bIsBLikeSelected)
		        {
		        	sbLi.append(CrmConstants.B_LIKE_TITLE + " " + 
		        			sElementSymbol + " QSs" + newline);
		        	ms_bIsBLikeSelected = true;
		        	ms_bIsAuto = true;
		        }

			}
			if(ms_bIsAuto)
			{
		        if(iElectronsCount == 2 && ms_bIsHeLikeSelected)
		        {
		        	System.out.println("He-like");
		    		System.out.println("ion " + ion.getNumber() + " lev # " + level.getNumber() + " energy " + level.getEnergy() + " potential " + ion.getFacIonizationPotential());
		        	sbHe.append(level.toFisher( ms_iTotalNonAutoLevelsCount - (ms_calc.getAiStateInitialNumber() - 1) + ms_iAutoLiLikeLevelCount + ms_iAutoBeLikeLevelCount + ms_iAutoBLikeLevelCount)  + newline);

		        }
		        else if(iElectronsCount == 3 && ms_bIsLiLikeSelected)
		        {
		        	System.out.println("Li-like");
		    		System.out.println("ion " + ion.getNumber() + " lev # " + level.getNumber() + " energy " + level.getEnergy() + " potential " + ion.getFacIonizationPotential());
		        	//sbLi.append(level.toFisher(ms_iTotalNonAutoLevelsCount - (ms_calc.getAiStateInitialNumber() - 1))  + newline);	
		    		sbLi.append(level.toFisher( ms_iTotalNonAutoLevelsCount - (ms_calc.getAiStateInitialNumber() - 1) + ms_iAutoBeLikeLevelCount + ms_iAutoBLikeLevelCount)  + newline);
		        	ms_iAutoLiLikeLevelCount++;
		        }
		        else if(iElectronsCount == 4 && ms_bIsBeLikeSelected)
		        {
		        	System.out.println("Be-like");
		    		System.out.println("ion " + ion.getNumber() + " lev # " + level.getNumber() + " energy " + level.getEnergy() + " potential " + ion.getFacIonizationPotential());
		        	//sbBe.append(level.toFisher(ms_iTotalNonAutoLevelsCount - (ms_calc.getAiStateInitialNumber() - 1))  + newline);	
		    		sbBe.append(level.toFisher( ms_iTotalNonAutoLevelsCount - (ms_calc.getAiStateInitialNumber() - 1) + ms_iAutoBLikeLevelCount)  + newline);
		        	ms_iAutoBeLikeLevelCount++;
		        }
		        else if(iElectronsCount == 5 && ms_bIsBLikeSelected)
		        {
		        	System.out.println("B-like");
		    		System.out.println("ion " + ion.getNumber() + " lev # " + level.getNumber() + " energy " + level.getEnergy() + " potential " + ion.getFacIonizationPotential());
		        	//sbB.append(level.toFisher(ms_iTotalNonAutoLevelsCount - (ms_calc.getAiStateInitialNumber() - 1))  + newline);		
		    		sbB.append(level.toFisher( ms_iTotalNonAutoLevelsCount - (ms_calc.getAiStateInitialNumber() - 1) )  + newline);
		        	ms_iAutoBLikeLevelCount++;
		        }

			}
		}
    }
    
    private void makeIonsDefinition(final Ion ion, StringBuffer sb)
    {
		int iLevelsCount = getLevelsCountFor(ion);
		int iAutoionizingLevelsCount = getAutoionizingLevelsCountFor(ion);
		int iNonAutoionizingLevelsCount = iLevelsCount - iAutoionizingLevelsCount;
		
		String       newline       = System.getProperty( "line.separator" );
		NumberFormat numFormatter  = new DecimalFormat("#.####");
		String       sNistIonizPot = numFormatter.format(ion.getNistIonizationPotential()); 
		String       sFacIonizPot  = numFormatter.format(ion.getFacIonizationPotential()); 
		String       sStageTitle = "";
		int iElectronCount = ms_calc.getElementNumber() - ion.getNumber() + 1;
		
		switch(iElectronCount)
		{
			case 6:
				sStageTitle = "[C]";
				break;
			case 5:
				sStageTitle = "[B]";
				break;
			case 4:
				sStageTitle = "[Be]";
				break;
			case 3:
				sStageTitle = "[Li]";
				break;
			case 2:
				sStageTitle = "[He]";
				break;
			case 1:
				sStageTitle = "[H]";
				break;
			case 0:
				sStageTitle = "[Nucleus]";
				break;
				default:
					break;
		}
		
		
      
		StringBuilder strBuilder     = new StringBuilder();
		Formatter     formatter      = new Formatter(strBuilder);
      
		formatter.format("%1$3d %2$4d %3$4d %4$4d %5$9.2f %6$9.2f %7$-9s",
        		ion.getNumber(), 
        		iNonAutoionizingLevelsCount,
        		iAutoionizingLevelsCount, 
      		  	0,
      		  	Double.parseDouble(sFacIonizPot),
      		  	Double.parseDouble(sNistIonizPot),
      		  	sStageTitle);
      
      	sb.append(strBuilder.toString() + newline);
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
			if(level.getEnergy() > ion.getNistIonizationPotential())
			{
				res += 1;
			}
		}
		return res;
    }
    
    private int getTotalNonAutoionizingLevelsCount()
    {
    	int res = 0;
    	Element element = (Element)ms_calc.getStateAt(0);
    	for(int i = 0; i < element.getChildCount(); i++)
    	{
    		Ion ion = (Ion)element.getStateAt(i);
    		final Enumeration<State> en = ion.getSubStates();
    		while(en.hasMoreElements())
    		{
    			Level level = (Level)en.nextElement();
    			if(level.getEnergy() < ion.getNistIonizationPotential())
    			{
    				res += 1;
    			}
    		}
    	}
		return res;
    }

}
