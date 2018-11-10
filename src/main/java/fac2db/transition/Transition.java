package fac2db.transition;


import java.sql.SQLException;
import java.util.Iterator;

import db.sql.FitDbManager;

import fac2db.property.*;
import fac2db.record.TransitionRecord;


public final class Transition
{
    private static final String EXCITATION_TO_STRING = "Transition[" +
	"source=%d, src_stat. weight=%d, target=%d, trg_stat. weight=%d, energy=%s]";

    private static final String IONIZATION_TO_STRING = "Transition[" +
	"source=%d, src_stat. weight=%d, target=%d, trg_stat. weight=%d, energy=%s]";

    private static final String PHOTOIONIZATION_TO_STRING = "Transition[" +
	"source=%d, src_stat. weight=%d, target=%d, trg_stat. weight=%d, energy=%s]";

    private static final String AUTOIONIZATION_TO_STRING = "Transition[" +
	"source=%d, src_stat. weight=%d, target=%d, trg_stat. weight=%d, energy=%s, rate=%s, dc. strength=%s]";

/*    private static final String ABSORBTION_TO_STRING = "Transition[" +
	"source=%d, src_stat. weight=%d, target=%d, trg_stat. weight=%d, energy=%s, osc. strength=%s, einstein=%s, multipole=%s]";
*/
    private static final String ABSORBTION_TO_STRING = "" +
	"%d, %d, %d, %d, %s, %s, %s, %s";

    private static final String OSCILLATION_TO_STRING = "Transition[" +
	"source=%d, src_stat. weight=%d, target=%d, trg_stat. weight=%d, energy=%s, osc. strength=%s, einstein=%s, multipole=%s]";

    private ETransitionProcess  	m_process;
	private TransitionProperty 		m_property;
//	private CrossSectionFit 		m_fit;
	private ExcitationCoefficients 	m_coeffs;
//	private List<CrossSectionPoint> m_points = new ArrayList<CrossSectionPoint>();
	
	private CsPointList m_pointList = new CsPointList();
	
    private int 					m_iProjectId;
    private int 					m_iSourceIonNumber;
    private FitDbManager m_dbManager;
	
    Transition(final FitDbManager dbManager)
    {
    	m_dbManager = dbManager;
    }
    
	public ETransitionProcess getProcess() 					{ return m_process;			}
	public TransitionProperty getProperty() 				{ return m_property;		}
//	public CrossSectionFit getFit() 						{ return m_fit; 			}
	public ExcitationCoefficients getCoeffs() 				{ return m_coeffs; 			}
	public Iterator<CrossSectionPoint> getPoints() 			{ return m_pointList.getPoints();}

	public void setProcess(ETransitionProcess process)			{ m_process = process;		}
	public void setProperty(TransitionProperty parameter)		{ m_property = parameter;	}
//	public void setFit(final CrossSectionFit fit) 				{ m_fit = fit;				}
	public void setCoeffs(final ExcitationCoefficients coeffs) 	{ m_coeffs = coeffs;		}
	public void addPoint(final CrossSectionPoint point) 		{ m_pointList.addPoint(point);		}
	
	public CsPointList getCsPointList()
	{
		return m_pointList;
	}
	
	
	
	
    public void setProjectNumber(final int iProjectNumber) 		{m_iProjectId = iProjectNumber;}
    public void setSourceIonNumber(final int iIonNumber)		{m_iSourceIonNumber = iIonNumber;}
    
    public TransitionDirection getDirection()
    {
    	TransitionDirection res = null;
    	switch(m_process)
    	{
    	case Excitation:
    		res = getExTransitionDirection();
    		break;
    	case Ionization:
    		res = getCiTransitionDirection();
    		break;
    	case Photoionization:
    		res = getPiTransitionDirection();
    		break;
    	case Absorbtion:
    		res = getAbTransitionDirection();
    		break;
    	case Oscillation:
    		res = getOscTransitionDirection();
    		break;
    	case Autoionization:
    		res = getAiTransitionDirection();
    		break;
    	default:
    		break;
    	}
        return res;
    }
    
    public final TransitionRecord toRecord()
    {
    	TransitionRecord res = null;
    	switch(m_process)
    	{
    	case Excitation:
    		res = excitationToDb();
    		break;
    	case Ionization:
    		res = ionizationToDb();
    		break;
    	case Photoionization:
    		res = photoionizationToDb();
    		break;
    	case Absorbtion:
    		res = absorptionToDb();
    		break;
    	case Autoionization:
    		res = autoionizationToDb();
    		break;
    	default:
    		break;
    	}
        return res;
    }
    
    private TransitionRecord excitationToDb()
    {
		int iSrcLev = (int)m_property.get(EExTransitionProperty.SOURCE_NUMBER) + 1;
		int iSrc = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber, iSrcLev);
		
		int iTrgLev = (int)m_property.get(EExTransitionProperty.TARGET_NUMBER) + 1;
		int iTrg = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber, iTrgLev);
		
		m_pointList.setSourceLevelPk(iSrc);
		m_pointList.setTargetLevelPk(iTrg);
		
		double[] values = new double[6];
		values[0] = iSrc;
		values[1] = iTrg;
		values[2] = m_property.get(EExTransitionProperty.ENERGY_THRESHOLD);
		values[3] = m_coeffs.get(EExTransitionCoefficients.Bethe);
		values[4] = m_coeffs.get(EExTransitionCoefficients.Born1);
		values[5] = m_coeffs.get(EExTransitionCoefficients.Born2);
		
		TransitionRecord dbTransition = new TransitionRecord();
		dbTransition.set(ETransitionProcess.Excitation, values);
		return dbTransition;
    }
    
    private TransitionRecord ionizationToDb()
    {
		int iSrcLev = (int)m_property.get(ECiTransitionProperty.SOURCE_NUMBER) + 1;
		int iSrc = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber, iSrcLev);
		
		int iTrgLev = (int)m_property.get(ECiTransitionProperty.TARGET_NUMBER);
		int iTrg = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber + 1, iTrgLev);
		
		m_pointList.setSourceLevelPk(iSrc);
		m_pointList.setTargetLevelPk(iTrg);

		double[] values = new double[3];
		values[0] = iSrc;
		values[1] = iTrg;
		values[2] = m_property.get(ECiTransitionProperty.ENERGY_THRESHOLD);
		
		try 
		{
			if(iSrcLev == 1 && iTrgLev == 1)
			{
				m_dbManager.batchUpdateFacIons(m_iProjectId, m_iSourceIonNumber, values[2]);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		TransitionRecord dbTransition = new TransitionRecord();
		dbTransition.set(ETransitionProcess.Ionization, values);
		return dbTransition;
    }
    
    private TransitionRecord photoionizationToDb()
    {
		int iSrcLev = (int)m_property.get(EPiTransitionProperty.SOURCE_NUMBER) + 1;
		int iSrc = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber, iSrcLev);
		
		int iTrgLev = (int)m_property.get(EPiTransitionProperty.TARGET_NUMBER);
		int iTrg = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber + 1, iTrgLev);
		
		m_pointList.setSourceLevelPk(iSrc);
		m_pointList.setTargetLevelPk(iTrg);

		double[] values = new double[3];
		values[0] = iSrc;
		values[1] = iTrg;
		values[2] = m_property.get(EPiTransitionProperty.ENERGY_THRESHOLD);
		
		TransitionRecord dbTransition = new TransitionRecord();
		dbTransition.set(ETransitionProcess.Photoionization, values);
		return dbTransition;
    }
    
    private TransitionRecord autoionizationToDb()
    {
		int iSrcLev = (int)m_property.get(EAiTransitionProperty.SOURCE_NUMBER) + 1;
		//System.out.println("Project " + m_iProjectId + " source ion " + m_iSourceIonNumber + " iSrcLev " + iSrcLev);
		int iSrc = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber, iSrcLev);
		
		int iTrgLev = (int)m_property.get(EAiTransitionProperty.TARGET_NUMBER);
		//System.out.println("Project " + m_iProjectId + " target ion " + (m_iSourceIonNumber + 1) + " iSrcLev " + iTrgLev);
		int iTrg = m_dbManager.getLevelPk(m_iProjectId, (m_iSourceIonNumber + 1), iTrgLev);
		
		double[] values = new double[5];
		values[0] = iSrc;
		values[1] = iTrg;
		values[2] = m_property.get(EAiTransitionProperty.ENERGY_THRESHOLD);
		values[3] = m_property.get(EAiTransitionProperty.RATE);
		values[4] = m_property.get(EAiTransitionProperty.DC_STRENGTH);
		
		TransitionRecord dbTransition = new TransitionRecord();
		dbTransition.set(ETransitionProcess.Autoionization, values);
		return dbTransition;
    }
    
    private TransitionRecord absorptionToDb()
    {
		int iSrcLev = (int)m_property.get(EAbTransitionProperty.SOURCE_NUMBER) + 1;
		int iSrc = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber, iSrcLev);
		
		int iTrgLev = (int)m_property.get(EAbTransitionProperty.TARGET_NUMBER) + 1;
		int iTrg = m_dbManager.getLevelPk(m_iProjectId, m_iSourceIonNumber, iTrgLev);

		double[] values = new double[6];
		values[0] = iSrc;
		values[1] = iTrg;
		values[2] = m_property.get(EAbTransitionProperty.ENERGY_THRESHOLD);
		values[3] = m_property.get(EAbTransitionProperty.OSC_STRENGTH);
		values[4] = m_property.get(EAbTransitionProperty.RATE);
		values[5] = m_property.get(EAbTransitionProperty.MULTIPOLE);
		
		TransitionRecord dbTransition = new TransitionRecord();
		dbTransition.set(ETransitionProcess.Absorbtion, values);
		return dbTransition;
    }
    
    private TransitionDirection getExTransitionDirection()
    {
        return new TransitionDirection(
        		m_iSourceIonNumber, 
        		(int)m_property.get(EExTransitionProperty.SOURCE_NUMBER),
                m_iSourceIonNumber, 
                (int)m_property.get(EExTransitionProperty.TARGET_NUMBER));
    }
    
    private TransitionDirection getCiTransitionDirection()
    {
        return new TransitionDirection(
        		m_iSourceIonNumber, 
        		(int)m_property.get(ECiTransitionProperty.SOURCE_NUMBER),
                m_iSourceIonNumber + 1, 
                (int)m_property.get(ECiTransitionProperty.TARGET_NUMBER));
    }
    
    private TransitionDirection getPiTransitionDirection()
    {
        return new TransitionDirection(
        		m_iSourceIonNumber, 
        		(int)m_property.get(EPiTransitionProperty.SOURCE_NUMBER),
                m_iSourceIonNumber + 1, 
                (int)m_property.get(EPiTransitionProperty.TARGET_NUMBER));
    }
    
    private TransitionDirection getAiTransitionDirection()
    {
        return new TransitionDirection(
        		m_iSourceIonNumber, 
        		(int)m_property.get(EAiTransitionProperty.SOURCE_NUMBER),
                m_iSourceIonNumber + 1, 
                (int)m_property.get(EAiTransitionProperty.TARGET_NUMBER));
    }
    
    private TransitionDirection getAbTransitionDirection()
    {
        return new TransitionDirection(
        		m_iSourceIonNumber, 
        		(int)m_property.get(EAbTransitionProperty.SOURCE_NUMBER),
                m_iSourceIonNumber, 
                (int)m_property.get(EAbTransitionProperty.TARGET_NUMBER));
    }
    
    private TransitionDirection getOscTransitionDirection()
    {
        return new TransitionDirection(
        		m_iSourceIonNumber, 
        		(int)m_property.get(EOscTransitionProperty.SOURCE_NUMBER),
                m_iSourceIonNumber, 
                (int)m_property.get(EOscTransitionProperty.TARGET_NUMBER));
    }

    
    @Override
    public final String toString()
    {
    	StringBuffer sb = new StringBuffer();
    	String sResult = null;
    	if(m_process == ETransitionProcess.Excitation)
    	{
    		final Iterator<CrossSectionPoint> points = getPoints();
    		while(points.hasNext())
    		{
    			CrossSectionPoint point = points.next();
    			sb.append(point);
    			sb.append("\n");
    		}
    		
            sResult = String.format(EXCITATION_TO_STRING, 
            		(int)m_property.get(EExTransitionProperty.SOURCE_NUMBER),
            		(int)m_property.get(EExTransitionProperty.SOURCE_STAT_WEIGHT), 
            		(int)m_property.get(EExTransitionProperty.TARGET_NUMBER),
            		(int)m_property.get(EExTransitionProperty.TARGET_STAT_WEIGHT), 
            		m_property.get(EExTransitionProperty.ENERGY_THRESHOLD));
    	}
    	else if(m_process == ETransitionProcess.Ionization)
    	{
    		final Iterator<CrossSectionPoint> points = getPoints();
    		while(points.hasNext())
    		{
    			CrossSectionPoint point = points.next();
    			sb.append(point);
    			sb.append("\n");
    		}

            sResult = String.format(IONIZATION_TO_STRING, 
            		(int)m_property.get(ECiTransitionProperty.SOURCE_NUMBER),
            		(int)m_property.get(ECiTransitionProperty.SOURCE_STAT_WEIGHT), 
            		(int)m_property.get(ECiTransitionProperty.TARGET_NUMBER),
            		(int)m_property.get(ECiTransitionProperty.TARGET_STAT_WEIGHT), 
            		m_property.get(ECiTransitionProperty.ENERGY_THRESHOLD)) + "\n" +
    				sb.toString();
    	}
    	else if(m_process == ETransitionProcess.Photoionization)
    	{
    		final Iterator<CrossSectionPoint> points = getPoints();
    		while(points.hasNext())
    		{
    			CrossSectionPoint point = points.next();
    			sb.append(point);
    			sb.append("\n");
    		}

            sResult = String.format(PHOTOIONIZATION_TO_STRING, 
            		(int)m_property.get(EPiTransitionProperty.SOURCE_NUMBER),
            		(int)m_property.get(EPiTransitionProperty.SOURCE_STAT_WEIGHT), 
            		(int)m_property.get(EPiTransitionProperty.TARGET_NUMBER),
            		(int)m_property.get(EPiTransitionProperty.TARGET_STAT_WEIGHT), 
            		m_property.get(EPiTransitionProperty.ENERGY_THRESHOLD)) + "\n" +
    				sb.toString();
    	}
    	
    	else if(m_process == ETransitionProcess.Autoionization)
    	{
            sResult = String.format(AUTOIONIZATION_TO_STRING, 
            		(int)m_property.get(EAiTransitionProperty.SOURCE_NUMBER),
            		(int)m_property.get(EAiTransitionProperty.SOURCE_STAT_WEIGHT), 
            		(int)m_property.get(EAiTransitionProperty.TARGET_NUMBER),
            		(int)m_property.get(EAiTransitionProperty.TARGET_STAT_WEIGHT), 
            		m_property.get(EAiTransitionProperty.ENERGY_THRESHOLD),
            		m_property.get(EAiTransitionProperty.RATE),
            		m_property.get(EAiTransitionProperty.DC_STRENGTH));
    	}
    	else if(m_process == ETransitionProcess.Absorbtion)
    	{
            sResult = String.format(ABSORBTION_TO_STRING, 
            		(int)m_property.get(EAbTransitionProperty.SOURCE_NUMBER),
            		(int)m_property.get(EAbTransitionProperty.SOURCE_STAT_WEIGHT), 
            		(int)m_property.get(EAbTransitionProperty.TARGET_NUMBER),
            		(int)m_property.get(EAbTransitionProperty.TARGET_STAT_WEIGHT), 
            		m_property.get(EAbTransitionProperty.ENERGY_THRESHOLD),
            		m_property.get(EAbTransitionProperty.OSC_STRENGTH),
            		m_property.get(EAbTransitionProperty.RATE),
            		m_property.get(EAbTransitionProperty.MULTIPOLE));
    	}
    	else if(m_process == ETransitionProcess.Oscillation)
    	{
            sResult = String.format(OSCILLATION_TO_STRING, 
            		(int)m_property.get(EOscTransitionProperty.SOURCE_NUMBER),
            		(int)m_property.get(EOscTransitionProperty.SOURCE_STAT_WEIGHT), 
            		(int)m_property.get(EOscTransitionProperty.TARGET_NUMBER),
            		(int)m_property.get(EOscTransitionProperty.TARGET_STAT_WEIGHT), 
            		m_property.get(EOscTransitionProperty.ENERGY_THRESHOLD),
            		m_property.get(EOscTransitionProperty.OSC_STRENGTH),
            		m_property.get(EOscTransitionProperty.RATE),
            		m_property.get(EOscTransitionProperty.MULTIPOLE));
    	}
        return sResult;
    }
}
