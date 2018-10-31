package crm;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.List;

import util.CrmConstants;


import crm.model.state.Calculation;
import crm.model.state.Element;
import crm.model.state.Ion;
import crm.model.state.Level;
import crm.model.transition.Parameter;
import crm.model.transition.Transition;
import crm.model.transition.TransitionParameter;
import crm.model.transition.TransitionProcess;
import db.sql.CrmDbManager;


public class CrmTransitionsBuilder 
{
	//private static final long serialVersionUID = 1L;
	
	private final static String SEPARATOR = System.getProperty("file.separator");

	private static Calculation 			m_calc;
	private static CrmDbManager 		m_factory;
	private static TransitionProcess 	m_process;
	//private static StringBuffer 		m_aiInBcfpBuffer = new StringBuffer();
	private static NumberFormat 		m_numFormatter = new DecimalFormat("##.#");
	
	private static String ms_sDir;
	
	public CrmTransitionsBuilder(final Calculation c, 
			final CrmDbManager f)  
	throws Exception
	{
		m_calc = c;
		m_factory = f;
		//m_aiInBcfpBuffer = new StringBuffer();
	}
	
	public void setTransitionProcess(final TransitionProcess process)
	throws SQLException
	{
		m_process = process;
		m_factory.setTransitionProcess(process);
	}
	
	public void writeTransitionsFile(final String dir) throws SQLException
	{
		//m_aiInBcfpBuffer = new StringBuffer();
		
		ms_sDir = dir;
		
		Element element = (Element)m_calc.getStateAt(0);
		StringBuffer dataBuffer = initDataBuffer();
		appendToFile(dataBuffer.toString());

		 int iIonCount = element.getChildCount();
		 
		 System.out.println("");
		 System.out.println("ion count " + iIonCount);
		 
    	 for(int i = 0; i <= iIonCount; i++)
    	 {
    		 Ion ion = (Ion)element.getStateAt(i);
    		 StringBuffer ionBuffer = new StringBuffer();
    		 StringBuffer ionAiBuffer = new StringBuffer();
    		 
    		 System.out.println("i " + i);
    		 
    		 if(i == iIonCount - 1) return;
    		 
    		 Ion nextIon = (Ion)element.getStateAt(i + 1);

    		 for(int j = 0; j < ion.getChildCount(); j++)
    		 {
    			 Level level = (Level)ion.getStateAt(j);
     			 List<Transition> transitions = 
    				 m_factory.getTransitionList(ion, nextIon, level);
     			 appendToBuffer(ionBuffer, ion, nextIon, transitions);    
     			 
     			 if(m_process == TransitionProcess.AUTOIONIZATION)
     			 {
     				appendAiPartToBuffer(ionAiBuffer, ion, nextIon, transitions);    
     			 }
     		 }
    		 appendToFile(ionBuffer.toString());
    		 
    		 if(m_process == TransitionProcess.AUTOIONIZATION)
    		 {
    			 appendToAiPartFile(ionAiBuffer.toString());
    		 }
    		 
    		 //if(i == (iIonCount - 2) && 
//    		 if(i == 2 && m_process == TransitionProcess.COLLISIONAL_IONIZATION)
//    		 {
//    			 //System.out.println("AUTO");
//    			 //System.out.println("AUTO " + m_aiInBcfpBuffer.toString());
//    			 
//    			 appendToFile(m_aiInBcfpBuffer.toString());
//    		 }
     	 }
	}
	
	private void appendToAiPartFile(final String data)
	{
		 appendToFile(ms_sDir + SEPARATOR + CrmConstants.AI_FILE_PART, data);
	}

	
	private void appendToFile(final String data)
	{
		 
		 switch(m_process)
		 {
		 case AUTOIONIZATION:
    		 appendToFile(ms_sDir + SEPARATOR + CrmConstants.AI_FILE_NAME, data);
    		 break;
		 case EXCITATION:
    		 appendToFile(ms_sDir + SEPARATOR + CrmConstants.EX_FILE_NAME, data);
			 break;
		 case COLLISIONAL_IONIZATION:
			 //System.out.println(data);
    		 appendToFile(ms_sDir + SEPARATOR + CrmConstants.CI_FILE_NAME, data);
			 break;
		 case PHOTOIONIZATION:
    		 appendToFile(ms_sDir + SEPARATOR + CrmConstants.PI_FILE_NAME, data);
			 break;
		 case IONIZATION:
    		 appendToFile(ms_sDir + SEPARATOR + CrmConstants.I_FILE_NAME, data);
			 break;
		 case RADIATION:
    		 appendToFile(ms_sDir + SEPARATOR + CrmConstants.RT_FILE_NAME, data);
			 break;
			 default:
				 break;
		 }
	}

	private StringBuffer initDataBuffer()
	{
		StringBuffer res = new StringBuffer();

		 switch(m_process)
		 {
		 case AUTOIONIZATION:
			 res.append(CrmConstants.AUTO_PRELUDE);
    		 break;
		 case EXCITATION:
			 res.append(CrmConstants.EXCIT_PRELUDE);
			 break;
		 case COLLISIONAL_IONIZATION:
			 res.append(CrmConstants.BCFP_PRELUDE);
			 break;
		 case PHOTOIONIZATION:
			 res.append("");
			 break;
		 case IONIZATION:
			 res.append(CrmConstants.IONS_PRELUDE);
			 break;
		 case RADIATION:
			 Element element = (Element)m_calc.getStateAt(0);
			 double elementWeight = element.getWeight();
		     String sElementWeight = m_numFormatter.format(elementWeight);
			 res.append(sElementWeight + CrmConstants.SPECTR_PRELUDE);
    		 break;
			 default:
				 break;
		 }
		 return res;
	}
	
	private void appendAiPartToBuffer(final StringBuffer sb, final Ion ion, final Ion nextIon, 
			final List<Transition> transitions)
	{
		 sb.append(convertAiDataPart(ion, nextIon, transitions));
	}

	
	private void appendToBuffer(final StringBuffer sb, final Ion ion, final Ion nextIon, 
			final List<Transition> transitions)
	{
		 switch(m_process)
		 {
		 case AUTOIONIZATION:
			 sb.append(convert(ion, nextIon, transitions));
    		 break;
		 case EXCITATION:
			 String sDataE = convert(ion, nextIon, transitions);
			 sb.append(sDataE);
			 break;
		 case COLLISIONAL_IONIZATION:
			 //String sAiDataR = m_aiInBcfpBuffer.toString();
			 String sDataR = convertR(ion, nextIon, transitions);
			 sb.append(sDataR);
			 //sb.append(sAiDataR);
			 break;
		 case PHOTOIONIZATION:
			 String sDataP = convert(ion, nextIon, transitions);
			 sb.append(sDataP);
			 break;
		 case IONIZATION:
			 sb.append(convert(ion, nextIon, transitions));
			 break;
		 case RADIATION:
			 sb.append(convert(ion, nextIon, transitions));
    		 break;
			 default:
				 break;
		 }
	}
	
	private String convertR(final Ion ion, final Ion nextIon, 
			final List<Transition> transitions)
	{
		 StringBuffer sbR = new StringBuffer();

		 for(int k = 0; k < transitions.size(); k++)
		 {
			 Transition transition = transitions.get(k);
			 
			 Object[] ciFieldsR = convertCiTransitionR(ion, nextIon, transition);
			 collectData(sbR, ciFieldsR);

		 }
		 return sbR.toString();
	 }
	
	private String convertAiDataPart(final Ion ion, final Ion nextIon, 
			final List<Transition> transitions)
	{
		 String res = null;
		 StringBuffer sb = new StringBuffer();
		 
		 for(int k = 0; k < transitions.size(); k++)
		 {
			 Transition transition = transitions.get(k);
			 
			 if(convertAiTransitionR(ion, nextIon, transition) == null)
			 {
				 continue;
			 }
			 else
			 {
				 Object[] aiFields = convertAiTransitionR(ion, nextIon, transition);
				 collectAiDataPart(sb, aiFields);
			 }
		 }
		 res = sb.toString();
		 return res;
	 }
	
	private String convert(final Ion ion, final Ion nextIon, 
			final List<Transition> transitions)
	{
		 StringBuffer sb = new StringBuffer();
		 
		 for(int k = 0; k < transitions.size(); k++)
		 {
			 Transition transition = transitions.get(k);

			 switch(m_process)
			 {
			 case PHOTOIONIZATION:
				 Object[] piFields = convertPiTransition(ion, transition);
				 collectData(sb, piFields);
				 break;
			 case COLLISIONAL_IONIZATION:
				 Object[] ciFields = convertCiTransition(ion, nextIon, transition);
				 collectData(sb, ciFields);
				 break;
			 case IONIZATION:
				 Object[] iFields = convertIonizationTransition(ion, nextIon, transition);
				 collectData(sb, iFields);
				 break;
			 case EXCITATION:
				 Object[] exFields = convertExTransition(ion, transition);
				 if(exFields != null) collectData(sb, exFields);
				 break;
			 case AUTOIONIZATION:
				 Object[] aiFields = convertAiTransitionF(ion, nextIon, transition);
				 collectData(sb, aiFields);
				 //collectAiDataPart(ai, aiFields);
				 break;
			case RADIATION:
				 Object[] abFields = convertAbTransition(ion, transition);
				 collectData(sb, abFields);
				 break;
				 default:
					 break;
			 }
		 }
		 return sb.toString();
	 }
	
	
	private void collectAiDataPart(final StringBuffer sb, final Object[] fields)
	{
		 StringBuilder strBuilder = new StringBuilder();
		 Formatter formatter = new Formatter(strBuilder);
		 
		 formatter.format(CrmConstants.IN_BCFP_AUTO_FORMAT, fields);
		 sb.append(strBuilder.toString() + "\n");
		 
		 formatter.close();
	}

	
	private void collectData(final StringBuffer sb, final Object[] fields)
	{
		 StringBuilder strBuilder = new StringBuilder();
		 Formatter formatter = new Formatter(strBuilder);
		 
//		 StringBuilder aiStrBuilder = new StringBuilder();
//		 Formatter aiFormatter = new Formatter(aiStrBuilder);
		 
		 switch(m_process)
		 {
		 case PHOTOIONIZATION:
			 formatter.format(CrmConstants.RREC_FORMAT, fields);
			 sb.append(strBuilder.toString() + "\n");
			 break;
		 case COLLISIONAL_IONIZATION:
			 formatter.format(CrmConstants.BCFP_FORMAT, fields);
			 sb.append(strBuilder.toString() + "\n");
			 break;
		 case IONIZATION:
			 formatter.format(CrmConstants.IONS_FORMAT, fields);
			 sb.append(strBuilder.toString() + "\n");
			 break;
		 case EXCITATION:
			 formatter.format(CrmConstants.EXCIT_FORMAT, fields);
			 sb.append(strBuilder.toString() + "\n");
			 break;
		 case AUTOIONIZATION:
			 formatter.format(CrmConstants.AUTO_FORMAT, fields);
			 sb.append(strBuilder.toString() + "\n");
			 
			 //System.out.println("MAKE AI_IN_BCFP");
			 
			 //aiFormatter.format(CrmConstants.IN_BCFP_AUTO_FORMAT, fields);
			 //m_aiInBcfpBuffer.append(aiStrBuilder.toString() + "\n");
			 break;
		case RADIATION:
			 formatter.format(CrmConstants.SPECTR_FORMAT, fields);
			 sb.append(strBuilder.toString() + "\n");
			 break;
			 default:
				 break;
		 }
		 formatter.close();
	}
	
	private Object[] convertCiTransition(Ion ion, Ion nextIon, Transition transition)
	{
		 Parameter parameter = transition.getParameter();
		 Object[] fields = new Object[8];// dep from process
		 
		 //Object[] fields = new Object[9];
		 
		 fields[0] = ion.getNumber();
		 fields[1] = ((Level)transition.getSourceState()).getNumber();
		 fields[2] = nextIon.getNumber();
		 fields[3] = ((Level)transition.getTargetState()).getNumber();
		 fields[4] = parameter.get(TransitionParameter.ciACoefficient);
		 fields[5] = parameter.get(TransitionParameter.ciBCoefficient);
		 fields[6] = parameter.get(TransitionParameter.ciCCoefficient);
		 fields[7] = parameter.get(TransitionParameter.ciDCoefficient);
		 
		 //fields[8] = transition.getTransitionId();
		 
		 return fields;
	}
	
	private Object[] convertIonizationTransition(Ion ion, Ion nextIon, Transition transition)
	{
		Object[] res = new Object[14];
		Object[] piFields = convertPiTransition(ion, transition);
		Object[] ciFields = convertCiTransition(ion, nextIon, transition);
		
		for(int i = 0; i < res.length; i++)
		{
			if(i < ciFields.length)
			{
				res[i] = ciFields[i];
			}
			else
			{
				res[i] = piFields[i - 5];
			}
		}
		return res;
		/*
		
		"%1$5d %2$4d %3$4d %4$4d %5$14.3E %6$15.3E %7$15.3E %8$15.3E " +
	    "%9$6d %10$13.3E %11$12.3E %12$12.3E %13$12.3E %14$12.3E";

		*/
	}
	
	private Object[] convertCiTransitionR(Ion ion, Ion nextIon, Transition transition)
	{
		 Parameter parameter = transition.getParameter();
		// Object[] fields = new Object[8];// dep from process
		 
		 Object[] fields = new Object[9];
		 
		 fields[0] = ion.getNumber();
		 fields[1] = ((Level)transition.getSourceState()).getNumber();
		 fields[2] = nextIon.getNumber();
		 fields[3] = ((Level)transition.getTargetState()).getNumber();
		 fields[4] = parameter.get(TransitionParameter.ciDCoefficient);
		 fields[5] = (-1) * parameter.get(TransitionParameter.ciACoefficient);
		 fields[6] = parameter.get(TransitionParameter.ciBCoefficient);
		 fields[7] = parameter.get(TransitionParameter.ciCCoefficient);
		 
		 fields[8] = transition.getTransitionId();
		 
		 return fields;
	}
	
	private Object[] convertAiTransitionR(Ion ion, Ion nextIon, Transition transition)
	{
		 Object[] res = null;
		 Parameter parameter = transition.getParameter();
		 if(parameter.get(TransitionParameter.AutoionizationRate) > 10E5)
		 {
			 res = new Object[9];// dep from process
			 res[0] = ion.getNumber();
			 res[1] = ((Level)transition.getSourceState()).getNumber();
			 res[2] = nextIon.getNumber();
			 res[3] = ((Level)transition.getTargetState()).getNumber();
			 res[4] = parameter.get(TransitionParameter.AutoionizationRate);
			 res[6] = 0.000E+00;
			 res[7] = 0.000E+00;
			 res[8] = 0.000E+00;
			 
			 double dSourceE = ((Level)transition.getSourceState()).getEnergy();
			 double dTargetE = ((Level)transition.getTargetState()).getEnergy();
			 res[5] = dSourceE - dTargetE - ion.getFacIonizationPotential();
		 }
		 return res;
	}
	
	private Object[] convertAiTransitionF(Ion ion, Ion nextIon, Transition transition)
	{
		 Parameter parameter = transition.getParameter();
		 Object[] fields = new Object[9];// dep from process
		 fields[0] = ion.getNumber();
		 fields[1] = ((Level)transition.getSourceState()).getNumber();
		 fields[2] = nextIon.getNumber();
		 fields[3] = ((Level)transition.getTargetState()).getNumber();
		 fields[4] = parameter.get(TransitionParameter.AutoionizationRate);
		 fields[6] = 0.000E+00;
		 fields[7] = 0.000E+00;
		 fields[8] = 0.000E+00;
		 
		 double dSourceE = ((Level)transition.getSourceState()).getEnergy();
		 double dTargetE = ((Level)transition.getTargetState()).getEnergy();
		 fields[5] = dSourceE - dTargetE - ion.getFacIonizationPotential();
		 return fields;
	}

	
	private Object[] convertPiTransition(Ion ion, Transition transition)
	{
		 Parameter parameter = transition.getParameter();
		 //Object[] fields = new Object[15];// dep from process
		 
		 Object[] fields = new Object[16];
		 
		 fields[0] = ion.getNumber();
		 fields[1] = ((Level)transition.getSourceState()).getNumber();
		 fields[2] = ((Level)transition.getTargetState()).getNumber();
		 fields[3] = (int)parameter.get(TransitionParameter.MethodNumber);
		 fields[4] = parameter.get(TransitionParameter.piACoefficient);
		 fields[5] = parameter.get(TransitionParameter.piBCoefficient);
		 fields[6] = parameter.get(TransitionParameter.piCCoefficient);
		 fields[7] = parameter.get(TransitionParameter.piDCoefficient);
		 
		 double dSourceE = ((Level)transition.getSourceState()).getEnergy();
		 double dTargetE = ((Level)transition.getTargetState()).getEnergy();
		 fields[8] = dTargetE + ion.getFacIonizationPotential() - dSourceE;

		 fields[9] = 0.000E+00;
		 fields[10] = 0.000E+00;
		 fields[11] = 0.000E+00;
		 fields[12] = 0.000E+00;
		 fields[13] = 0.000E+00;
		 fields[14] = 0.000E+00;
		 
		 fields[15] = transition.getTransitionId();
		 
		 return fields;
	}

	private Object[] convertExTransition(Ion ion, Transition transition)
	{
		 Parameter parameter = transition.getParameter();
		 //Object[] fields = new Object[11];// dep from process
		 
		 Object[] fields = new Object[12];
		 
		 fields[0] = ion.getNumber();
		 fields[1] = ((Level)transition.getSourceState()).getNumber();
		 fields[2] = ((Level)transition.getTargetState()).getNumber();
		 fields[3] = (int)parameter.get(TransitionParameter.MethodNumber);
		 fields[4] = parameter.get(TransitionParameter.exACoefficient);
		 fields[5] = parameter.get(TransitionParameter.exBCoefficient);
		 fields[6] = parameter.get(TransitionParameter.exCCoefficient);
		 fields[7] = parameter.get(TransitionParameter.exDCoefficient);
		 fields[8] = parameter.get(TransitionParameter.exECoefficient);
		 fields[9] = "0.000E+00";
		 fields[10] = parameter.get(TransitionParameter.OscillationStrength);
		 
		 fields[11] = transition.getTransitionId();
		 
		 return fields;
	}
	
	private Object[] convertAbTransition(Ion ion, Transition transition)
	{
		 Parameter parameter = transition.getParameter();
		 Object[] fields = new Object[7];// dep from process
		 fields[0] = ion.getNumber();
		 fields[1] = ((Level)transition.getSourceState()).getNumber();
		 fields[2] = ((Level)transition.getTargetState()).getNumber();
		 fields[3] = new Double(1.000);
		 fields[4] = parameter.get(TransitionParameter.Wavelength);
		 fields[5] = parameter.get(TransitionParameter.TransitionRate);
		 fields[6] = parameter.get(TransitionParameter.Multipole);
		 return fields;
	}

    public void appendToFile(final String sFileName, final String sData)
    {
        try 
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(sFileName, true));
            out.write(sData);
            out.close();
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
    }
    
    public void write(final String fileName, final String data)
    {
    	try 
    	{
    	    BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
    	    out.write(data);
    	    out.close();
    	} 
    	catch (IOException e) 
    	{
    		e.printStackTrace();
    	}
    }
}
