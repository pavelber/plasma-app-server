package fac2db.transition;


import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;
import java.util.regex.Pattern;

import util.FileConstants;

import db.sql.FitDbManager;

import fac2db.property.ETransitionProcess;


public final class TransitionScanner 
{
	public static final Pattern SEPARATOR_PATTERN = Pattern.compile("\\s+");
	
	private TransitionListFactory m_factory;
	
	private static int	ms_iElementNumber;
	private static int	ms_iElectronsCount;
	private static int	ms_iBlocksCount;
	private static int	ms_iTransitionsCount;
	private static int 	ms_iPointsCount;
	private static int	ms_iCurrentBlock = 1;
	private static int	ms_iCurrentTransition;
	private static int 	ms_iCurrentPoint;
	private static int 	ms_iTrHeaderStrNum;
	private static int	ms_iQmode;

    public void scan(final java.io.File file, final int iProjectId, final FitDbManager dbManager) throws IOException
    {
    	System.out.println(file.getCanonicalPath());

        final LineNumberReader reader =
                new LineNumberReader(new FileReader(file));
 
    	final ETransitionProcess process;
    	
        if(file.getName().equals(FileConstants.EX_INPUT_FILE_NAME))
        {
        	process = ETransitionProcess.Excitation;
        }
        else if(file.getName().equals(FileConstants.CI_INPUT_FILE_NAME))
        {
        	process = ETransitionProcess.Ionization;
        }
        else if(file.getName().equals(FileConstants.PH_INPUT_FILE_NAME))
        {
        	process = ETransitionProcess.Photoionization;
        }
        else if(file.getName().equals(FileConstants.TR_INPUT_FILE_NAME))
        {
        	process = ETransitionProcess.Absorbtion;
        }
        else if(file.getName().equals(FileConstants.TR_INPUT_OSC_FILE_NAME))
        {
        	process = ETransitionProcess.Oscillation;
        }
        else if(file.getName().equals(FileConstants.AI_INPUT_FILE_NAME))
        {
        	process = ETransitionProcess.Autoionization;
        }
        else
        {
        	return;
        }
        
    	m_factory = new TransitionListFactory(process, iProjectId, dbManager);

        for (String sLine = reader.readLine();
             sLine != null; sLine = reader.readLine())
        {
        	parseLine(process, sLine);
        }
        
        m_factory.loadTransitions();
        
        
        reader.close();
    }
    
	private void parseLine(final ETransitionProcess process, 
			final String sLine)
	{
		setCommonParameters(sLine);
    	setBlockParameters(sLine);

        switch(process)
        {
    	case Excitation:
    		parseExcitationLine(sLine);
    		break;
    	case Ionization:
    		parseIonizationLine(sLine);    		
    		break;
    	case Photoionization:
    		parsePhotoionizationLine(sLine); 
    		break;
    	case Autoionization:
    		parseAutoionizationLine(sLine); 
    		break;
    	case Absorbtion:
    		parseAbsorbtionLine(sLine); 
    		break;
    	case Oscillation:
    		parseOscillationLine(sLine); 
    		break;
    	default:
    		break;
        }
	}
    
	private void setCommonParameters(final String sLine)
	{
		final Pattern p1 = ETransitionPattern.Element.getPattern();
		final Pattern p2 = ETransitionPattern.BlocksCount.getPattern();
        Scanner scr = new Scanner(sLine);
        setElement(scr, sLine, p1);
        setBlocksCount(scr, sLine, p2);
	}
	
	private void setBlockParameters(final String sLine)
	{
		final Pattern p1 = ETransitionPattern.ElectronsCount.getPattern();
		final Pattern p2 = ETransitionPattern.TransCount.getPattern();
		final Pattern p3 = ETransitionPattern.FacQmode.getPattern();
		final Pattern p4 = ETransitionPattern.PointsCount.getPattern();
		
        Scanner scr = new Scanner(sLine);
        
        setElectronsCount(scr, sLine, p1);
        setTransitionsCount(scr, sLine, p2);
        setQmode(scr, sLine, p3);
        setPointsCount(scr, sLine, p4);
	}
	
	private void parseExcitationLine(final String sLine)
	{
        Scanner scr = new Scanner(sLine);
		final Pattern transitionPattern = ETransitionPattern.ExTransition.getPattern();
        createTransition(scr, sLine, transitionPattern);
        
        if(ms_iTrHeaderStrNum == 1)
        {
    		final Pattern exCoefficientsPattern = ETransitionPattern.ExCoefficients.getPattern();
        	addCoefficients(scr, sLine, exCoefficientsPattern);
        }
        
        if(ms_iQmode == 2)
        {
            if(ms_iTrHeaderStrNum == 2)
            {
            }
        }
        else
        {
            if(ms_iTrHeaderStrNum == 2)
            {
            	ms_iTrHeaderStrNum = 3;
            }
        }

        if(ms_iTrHeaderStrNum == 3)
        {
    		final Pattern crossPointsPattern = ETransitionPattern.ExCrossPoints.getPattern();
            addPoint(scr, sLine, crossPointsPattern, ETransitionProcess.Excitation);
        }
	}
	
	private void parseIonizationLine(final String sLine)
	{
        Scanner scr = new Scanner(sLine);
		final Pattern transitionPattern = ETransitionPattern.CiTransition.getPattern();
        createTransition(scr, sLine, transitionPattern);
        
        if(ms_iTrHeaderStrNum == 1)
        {
        	ms_iTrHeaderStrNum = 3;
        }

        if(ms_iTrHeaderStrNum == 3)
        {
    		final Pattern crossPointsnPattern = ETransitionPattern.CiCrossPoints.getPattern();
            addPoint(scr, sLine, crossPointsnPattern, ETransitionProcess.Ionization);
        }
	}

	private void parsePhotoionizationLine(final String sLine)
	{
        Scanner scr = new Scanner(sLine);
		final Pattern transitionPattern = ETransitionPattern.PiTransition.getPattern();
        createTransition(scr, sLine, transitionPattern);
        
        if(ms_iTrHeaderStrNum == 1)
        {
        	ms_iTrHeaderStrNum = 3;
        }

        if(ms_iTrHeaderStrNum == 3)
        {
    		final Pattern crossPointsnPattern = ETransitionPattern.PiCrossPoints.getPattern();
            addPoint(scr, sLine, crossPointsnPattern, ETransitionProcess.Photoionization);
        }
	}
	
	private void parseAbsorbtionLine(final String sLine)
	{
        Scanner scr = new Scanner(sLine);
		final Pattern transitionPattern = ETransitionPattern.RTransition.getPattern();
        createTransition(scr, sLine, transitionPattern);
	}
	
	private void parseOscillationLine(final String sLine)
	{
        Scanner scr = new Scanner(sLine);
		final Pattern transitionPattern = ETransitionPattern.RTransition.getPattern();
        createOscTransition(scr, sLine, transitionPattern);
	}


	private void parseAutoionizationLine(final String sLine)
	{
        Scanner scr = new Scanner(sLine);
		final Pattern transitionPattern = ETransitionPattern.AiTransition.getPattern();
        createTransition(scr, sLine, transitionPattern);
	}
 
//-------------------------  Common Parameters --------------------------------  
    
	private void setElement(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	String[] fields = sLine.split("\\s+");
        	ms_iElementNumber = ((int)Double.parseDouble(fields[3]));
        }
        else
        {
        	return;
        }
	}
	
	private void setBlocksCount(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	String[] fields = sLine.split("\\s+");
        	ms_iBlocksCount = Integer.parseInt(fields[2]);
        }
        else
        {
        	return;
        }
	}

//-------------------------  Block Parameters --------------------------------  

	private void setElectronsCount(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	String[] fields = sLine.split("\\s+");
        	ms_iElectronsCount = Integer.parseInt(fields[2]);
        	m_factory.setIonNumber(ms_iElementNumber - ms_iElectronsCount + 1);
        }
        else
        {
        	return;
        }
	}
	
	private void setTransitionsCount(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	String[] fields = sLine.split("\\s+");
        	ms_iTransitionsCount = Integer.parseInt(fields[2]);
        }
        else
        {
        	return;
        }
	}
	
	private void setQmode(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	String[] fields = sLine.split("\\s+");
        	ms_iQmode = Integer.parseInt(fields[2]);
        }
        else
        {
        	return;
        }
	}
	
	private void setPointsCount(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	String[] fields = sLine.split("\\s+");
        	ms_iPointsCount = Integer.parseInt(fields[2]);
        }
        else
        {
        	return;
        }
	}
	
//-------------------------  Parse Line --------------------------------  
	private void createOscTransition(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	//0  0    101  1  3.3183E+02  0
        	String[] fields = sLine.trim().split("\\s+");
        	double[] values = new double[fields.length];
        	
        	for(int i = 0; i < fields.length; i++)
        	{
        		values[i] = Double.parseDouble(fields[i]);
        	}
        	m_factory.createTransition(values, ms_iElementNumber - ms_iElectronsCount + 1);
        }
        else
        {
        	return;
        }
	}

	private void createTransition(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	//0  0    101  1  3.3183E+02  0
        	String[] fields = sLine.trim().split("\\s+");
        	double[] values = new double[fields.length];
        	
        	for(int i = 0; i < fields.length; i++)
        	{
        		values[i] = Double.parseDouble(fields[i]);
        	}

			ms_iCurrentTransition += 1;
        	m_factory.createTransition(values, ms_iElementNumber - ms_iElectronsCount + 1);
        	ms_iTrHeaderStrNum = 1;
        }
        else
        {
        	return;
        }
	}
	
	private void addCoefficients(final Scanner scr, final String sLine, 
			final Pattern pattern)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	String[] fields = sLine.trim().split("\\s+");
        	double[] values = new double[fields.length];
        	for(int i = 0; i < fields.length; i++)
        	{
        		values[i] = Double.parseDouble(fields[i]);
        	}
        	m_factory.setCoefficients(values);
        	ms_iTrHeaderStrNum = 2;
        }
        else
        {
        	return;
        }
	}

	private void addPoint(final Scanner scr, final String sLine, 
			final Pattern pattern, final ETransitionProcess process)
	{
        String s = scr.findInLine(pattern);
        if(s != null)
        {
        	ms_iCurrentPoint += 1;
        	String[] fields = sLine.trim().split("\\s+");
        	double[] values = new double[fields.length + 1];
        	values[0] = ms_iCurrentPoint;
        	
        	for(int i = 0; i < fields.length; i++)
        	{
        		int k = i + 1;
        		values[k] = Double.parseDouble(fields[i]);
        	}

			m_factory.addPoint(values);
			
			if(ms_iPointsCount <= ms_iCurrentPoint)
			{
				m_factory.addTransition();

				
				
				if(m_factory.getTransitionsListSize() >= 2000)
				{
					m_factory.loadTransitions();
					m_factory.clearTransitionsList();
				}
				
				
				ms_iTrHeaderStrNum = 0;
				ms_iCurrentPoint = 0;
				
				if(ms_iTransitionsCount <= ms_iCurrentTransition)
				{
					ms_iCurrentTransition = 0;
					
					if(ms_iBlocksCount <= ms_iCurrentBlock)
					{
						ms_iCurrentBlock = 1;
					}
					else
					{
			    		ms_iCurrentBlock += 1;
					}
				}
			}
        }
	}
}
