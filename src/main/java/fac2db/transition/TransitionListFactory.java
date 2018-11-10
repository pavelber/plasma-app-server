package fac2db.transition;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparator.TransitionDirectionComparator;
import comparator.TransitionSourceComparator;
import comparator.TransitionTargetComparator;

import db.sql.FitDbManager;

import fac2db.level.LevelState;
import fac2db.property.EAbTransitionProperty;
import fac2db.property.EOscTransitionProperty;
import fac2db.property.ETransitionProcess;




final class TransitionListFactory 
{
	private List<Transition> 	m_list;
	private List<LevelState> 	m_levels;
    private Transition 			m_transition;
    private int 				m_iProjectId;
    private int 				m_iIonNumber;
    private ETransitionProcess 	m_process;
    private Counter 			m_counter;
    private FitDbManager 			m_dbManager;
    
    TransitionListFactory(final ETransitionProcess process, 
    		final int iProjectId, final FitDbManager dbManager)
    {
    	m_process = process;
    	m_iProjectId = iProjectId;
    	m_dbManager = dbManager;
    	m_list = new ArrayList<Transition>();
    	
    	if(ETransitionProcess.Oscillation == m_process)
    	{
    		m_levels = new ArrayList<LevelState>();
    	}
    	
    	m_counter = new Counter(iProjectId, dbManager);
    }
    
    public void setIonNumber(final int iIonNumber)
    {
    	m_iIonNumber = iIonNumber;
    }
    
    public int getTransitionsListSize()
    {
    	return m_list.size();
    }
    
    final void createTransition(final double[] values, final int iIon)
    {
    	m_transition = new Transition(m_dbManager);
    	m_transition.setProcess(m_process);
    	m_transition.setProjectNumber(m_iProjectId);
    	m_transition.setSourceIonNumber(iIon);
    	
    	TransitionProperty property = new TransitionProperty();
    	m_transition.setProperty(property);

    	if(m_process == ETransitionProcess.Ionization || 
    	   m_process == ETransitionProcess.Photoionization ||
    	   m_process == ETransitionProcess.Autoionization)
    	{
        	double[] v = new double[values.length];
        	
        	for(int i = 0; i < values.length; i++)
        	{
        		if(i != 2)
        		{
        			v[i] = values[i];
        		}
        	}
        	
        	int iToLevNum = m_counter.getLevelNumber(m_iIonNumber, (int)values[2]);
        	v[2] = iToLevNum;
        	property.set(m_process, v);
    	}
    	else if(m_process == ETransitionProcess.Absorbtion ||
    			m_process == ETransitionProcess.Excitation ||
    			m_process == ETransitionProcess.Oscillation)
    	{
        	property.set(m_process, values);
    	}
    	
    	if(m_process == ETransitionProcess.Absorbtion ||
    			m_process == ETransitionProcess.Autoionization ||
    			m_process == ETransitionProcess.Oscillation)
    	{
    		addTransition();
    	}
    }
    
    final void setCoefficients(final double[] values)
    {
    	ExcitationCoefficients coeffs = new ExcitationCoefficients();
    	coeffs.set(values);
    	m_transition.setCoeffs(coeffs);
    }
    
    final void addPoint(final double[] values)
    {
    	CrossSectionPoint point = new CrossSectionPoint();
    	point.set(m_transition.getProcess(), values);
    	m_transition.addPoint(point);
    }
    
    final void addTransition()
    {
    	m_list.add(m_transition);
    }
    
    final void loadTransitions()
    {
        switch(m_process)
        {
    	case Excitation:
    	case Ionization:
    	case Photoionization:
    	case Autoionization:
    		m_dbManager.loadTransitions(m_list, m_process);
    		break;
    	case Absorbtion:
    		loadAbTransitions(); 
    		break;
    	case Oscillation:
    		updateLevels();
    		break;
    	default:
    		break;
        }
    }
    
    final void clearTransitionsList()
    {
		m_list.removeAll(m_list);////////////////////////////
    }
    
    final void processAbTransitions()
    {
		TransitionSourceComparator srcComparator = new TransitionSourceComparator();
		TransitionTargetComparator trgComparator = new TransitionTargetComparator();
		TransitionDirectionComparator 
			dirComparator = new TransitionDirectionComparator(srcComparator, trgComparator);
		
	    Collections.sort(m_list, dirComparator);
	    
	    List<Transition> forDelete = new ArrayList<Transition>();
	    Transition oldTr = null;
	    Transition newTr = null;
	    TransitionDirection oldDir = null;
	    TransitionDirection newDir = null;
	    
	    for(int i = 0; i < m_list.size(); i++)
	    {
	    	Transition transition = m_list.get(i);
	    	TransitionDirection dir = transition.getDirection();
	    	
	    	if(i == 0)
	    	{
	    		oldTr = transition;
	    		oldDir = dir;
	    	}
	    	else
	    	{
	    		newTr = transition;
	    		newDir = dir;
	    		if(oldDir.equals(newDir))
	    		{
	    			TransitionProperty newPrm = newTr.getProperty();
	    			TransitionProperty oldPrm = oldTr.getProperty();
	    			
	    			double dRate = oldPrm.get(EAbTransitionProperty.RATE) + 
	    				newPrm.get(EAbTransitionProperty.RATE);
	    			double dOscStrength = oldPrm.get(EAbTransitionProperty.OSC_STRENGTH) + 
	    				newPrm.get(EAbTransitionProperty.OSC_STRENGTH);
	    			
	    			double dMultipole;
	    			if(oldPrm.get(EAbTransitionProperty.RATE) > 
	    					newPrm.get(EAbTransitionProperty.RATE))
	    			{
	    				dMultipole = oldPrm.get(EAbTransitionProperty.MULTIPOLE);
	    			}
	    			else
	    			{
	    				dMultipole = newPrm.get(EAbTransitionProperty.MULTIPOLE);
	    			}
	    			newPrm.set(EAbTransitionProperty.MULTIPOLE, dMultipole);
	    			newPrm.set(EAbTransitionProperty.RATE, dRate);
	    			newPrm.set(EAbTransitionProperty.OSC_STRENGTH, dOscStrength);

	    			forDelete.add(oldTr);
	    		}
				oldTr = transition;
				oldDir = dir;
	    	}
	    }
	    
	    for(int j = 0; j < forDelete.size(); j++)
	    {
	    	m_list.remove(forDelete.get(j));
	    }
	    
    }
    
    private void printTransitions(String label)
    {
    	System.out.println("========== " + label + " ==========");
	    for(int i = 0; i < m_list.size(); i++)
	    {
	    	System.out.println(i + "    " + m_list.get(i));
	    }
    }

    private final void loadAbTransitions()
    {
//    	printTransitions("before processing");
    	processAbTransitions();
    	printTransitions("after processing");
    	m_dbManager.loadTransitions(m_list, m_process);
    }
    
    private final void updateLevels()
    {
    	try 
    	{
			processOscTransitions();
			m_dbManager.batchUpdateLevels(m_levels);
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    }
    
    final void processOscTransitions()
    {
		TransitionTargetComparator trgComparator = new TransitionTargetComparator();
	    Collections.sort(m_list, trgComparator);
	    
	    int iToOld 	= 0;
	    double dOscStrTotal = 0.0;
	    
	    for(int k = 0; k < m_list.size(); k++)
	    {
	    	Transition ot = m_list.get(k);
	    	
	    	TransitionProperty property = ot.getProperty();
	    	double dOscStrength = property.get(EOscTransitionProperty.OSC_STRENGTH);
	    	double dStatWeight = property.get(EOscTransitionProperty.TARGET_STAT_WEIGHT) + 1;
	    	double dOscStr = dOscStrength / dStatWeight;
	    	int iToNew 	= (int)ot.getProperty().get(EOscTransitionProperty.TARGET_NUMBER);
	    	
	    	if(k == 0) iToOld = iToNew;
	    	
	    	if(iToNew != iToOld || k == m_list.size() - 1)
	    	{
		        if(k == m_list.size() - 1)
		        {
		        	dOscStrTotal += dOscStr;
		        }
		        
		        if((iToOld + 1) <= m_counter.getLevelsCountForIon(m_iIonNumber))
		        {
		        	LevelState level = new LevelState();
		        	level.setProjectNumber(m_iProjectId);
		        	level.setNumber(iToOld + 1);
		        	level.setIonNumber(m_iIonNumber);
		        	level.setHLikeOscStrength(dOscStrTotal);
		        	m_levels.add(level);
		        }

		        if(k != m_list.size() - 1)
		        {
			        iToOld = iToNew;
			        dOscStrTotal = dOscStr;
		        }
	    	}
	    	else
	    	{
	    		dOscStrTotal += dOscStr;
	    	}
	    }
    }
}
