package crm;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparator.LevelComparator;
import db.sql.CrmDbManager;
import util.RomanNumeral;
import crm.model.state.Calculation;
import crm.model.state.Element;
import crm.model.state.Ion;
import crm.model.state.Level;


public class CrmStructureBuilder 
{
	private static final long serialVersionUID = 1L;
	
	private static CrmDbManager ms_factory;
	private static Calculation 	ms_calc;
	
	public CrmStructureBuilder(final Calculation c, 
			final CrmDbManager f)  
	throws Exception
	{
		ms_calc = c;
		ms_factory = f;
	}
	
	public void build() throws SQLException
	{
		
    	 Element element = (Element)ms_calc.getStateAt(0);
    	 for(int i = ms_calc.getStartIonNumber(); i <= ms_calc.getEndIonNumber(); i++)
    	 {
        	RomanNumeral converter = new RomanNumeral(i) ;
        	String sRoman = converter.toRoman();
    		Ion ion = new Ion(element.getSymbol() + sRoman);
    		element.addState(ion);
    		ms_factory.buildNistIon(element, ion, i);

    		if(i < ms_calc.getEndIonNumber())
    		{
        		double dPotential = ms_factory.getFacIonizationPotential(ms_calc, ion.getNumber());
        		ion.setFacIonizationPotential(dPotential);
    		}
    		
    		List<Level>  tempLevelList = ms_factory.buildLevelsForIon(ms_calc, element, ion);
    		sortLevelsList(tempLevelList, ion);
   		 	List<Level>  facLevelList = renumerateLevelList(tempLevelList);
   		 	compareEnergies(element, ion, facLevelList);
    	 }
	}
	
	private void sortLevelsList(final List<Level>  tempLevelList, final Ion ion)
	{
	 	Collections.sort(tempLevelList, new LevelComparator(true));
	 	for(int j = 0; j < tempLevelList.size(); j++)
	 	{
	 		Level level = tempLevelList.get(j);
	 		level.setNumber(j + 1);
	 		ion.addState(level);
	 	}
	}

	private List<Level> renumerateLevelList(final List<Level> list)
	{
	 	List<Level>  res = new ArrayList<Level>();  
		boolean bAuto = false;
		int iMaxNonAuto = -1;
		
	 	for(int j = 0; j < list.size(); j++)
	 	{
	 		Level level = list.get(j);
   		 	int iLevNum = level.getNumber();
   		 	double dEnergy = level.getEnergy();
   		 	Ion ion = (Ion)level.getParentState();
   		 	double dPotential = ion.getFacIonizationPotential();
   		 	int iIonNum = ion.getNumber();
   		 	Element element = (Element)ion.getParentState();
  		    int iElectronsCount = element.getNumber() - iIonNum + 1;
	 		
	 		if(iElectronsCount == 0)
	 		{
  		 		level.setConfiguration("Nucleus");
  		 		level.setStatWeight(0);
  		 		level.setNumber(1);
	 		}
	 		else
	 		{
				if(dEnergy < dPotential)
				{
					res.add(level);
				}
				else
				{
	   				if(!bAuto)
	   				{
	   					iMaxNonAuto = j;
	   					bAuto = true;
	   				}
	   		 		
	   		 		if( bAuto )
	   		 		{
	   		 			if(ms_calc.getAiStateInitialNumber() == 0)
	   		 			{
		   		 			iLevNum = -1 *(iLevNum - 
		   		 					iMaxNonAuto + 
		   		 					ms_calc.getAiStateInitialNumber());
	   		 			}
	   		 			else
	   		 			{
		   		 			iLevNum = 1 *(iLevNum - 
		   		 					iMaxNonAuto + 
		   		 					ms_calc.getAiStateInitialNumber());
	   		 			}
	   		 			
	   	  		 		level.setNumber(iLevNum);
	   	  			 	res.add(level);
	   		 		}
				}
	 		}
	 	}
	 	return res;
	}

	private void compareEnergies(final Element element, final Ion ion, final List<Level>  facLevelList)
	{
		List<Level> nistList = ms_factory.makeNistLevelsList(element, ion);
		correctEnergy(facLevelList, nistList);
	}

	private static void correctEnergy(final List<Level> facList, 
			final List<Level> nistList)
	{
		for(int i = 0; i < facList.size(); i++)
		{
			Level facLev = facList.get(i);
			for(int j = 0; j < nistList.size(); j++)
			{
				Level nistLev = nistList.get(j);
				if(nistLev.equals(facLev) && !nistLev.isSelected())
				{
					nistLev.setSelected(true);
					int levelsCount = 0;
					for(int k = j + 1; k < nistList.size(); k++)
					{
						Level lev = nistList.get(k);
						if(lev.equals(nistLev))
						{
							lev.setSelected(true);
							levelsCount++;
						}
					}
					if(levelsCount == 0 && facLev.getNumber() != 1)
					{
						//double fE = facLev.getEnergy();
						double nE = nistLev.getEnergy();
						String sTerm = nistLev.getTerm();
						
						facLev.setEnergyByNist(nE);
						facLev.setTerm(sTerm);

//						double abs = Math.abs(((fE - nE) / nE) * 100);
						
//						if(abs < 2)
//						{
//							facLev.setEnergyByNist(nE);
//							facLev.setTerm(sTerm);

//						}
					}
					break;
				}
			}
		}
	}
}
