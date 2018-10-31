package fit2db.scanner;


import java.util.ArrayList;
import java.util.List;

import fit2db.property.ETransitionProcess;
import fit2db.record.ExTransitionRecord;


final class FitFactory 
{
    private ETransitionProcess 	m_process;
    
    private List<ExFit> m_exFits;
    private List<CiFit> m_ciFits;
    private List<PiFit> m_piFits;
    private List<ExTransitionRecord> m_exTransitions;

    
    FitFactory(final ETransitionProcess process)
    {
    	m_process = process;
    	
    	switch(m_process)
    	{
    	case Excitation:
    		m_exFits = new ArrayList<ExFit>();
    		m_exTransitions = new ArrayList<ExTransitionRecord>();
    		break;
    	case Ionization:
    		m_ciFits = new ArrayList<CiFit>();
    		break;
    	case Photoionization:
    		m_piFits = new ArrayList<PiFit>();
    		break;
    		default:
    			break;
    	}
    }
    
    public List<CiFit> getCiFits()
    {
    	return m_ciFits;
    }
    
    public List<PiFit> getPiFits()
    {
    	return m_piFits;
    }
    
    public List<ExFit> getExFits()
    {
    	return m_exFits;
    }
    
    public List<ExTransitionRecord> getExTransitions()
    {
    	return m_exTransitions;
    }

    final void addFit(final double[] values)
    {
    	switch(m_process)
    	{
    	case Excitation:
    		ExFit exFit = new ExFit();
    		exFit.set(values);
    		m_exFits.add(exFit);
    		//ExTransitionRecord record = exFit.toTrRecord(lTransitionId)
    		
    		break;
    	case Ionization:
    		CiFit ciFit = new CiFit();
    		ciFit.set(values);
    		m_ciFits.add(ciFit);
    		break;
    	case Photoionization:
    		PiFit piFit = new PiFit();
    		piFit.set(values);
    		m_piFits.add(piFit);
    		break;
    		default:
    			break;
    	}
    }
//    final void addExTransition(final double[] values)
//    {
//		ExTransitionRecord record = new ExTransitionRecord();
//		record.set(values);
//		m_exTransitions.add(record);
//    }
    
    
}
