package db.sql;


import fit2db.property.ECiFitProperty;
import fit2db.property.EExFitProperty;
import fit2db.property.EPiFitProperty;
import fit2db.record.CiFitRecord;
import fit2db.record.ExFitRecord;
import fit2db.record.ExTransitionRecord;
import fit2db.record.PiFitRecord;
import fit2db.scanner.CiFit;
import fit2db.scanner.ExFit;
import fit2db.scanner.PiFit;


final class FitDbRecordMaker 
{
	public ExTransitionRecord makeExTransitionRecord(final int iProject, final ExFit fit)
	{
		ExTransitionRecord record = null;
		final DbKeyRetriever keyRetriever = new DbKeyRetriever();
		
		int iIon = (int)fit.get(EExFitProperty.ION_NUMBER);
		int iSrcLevel = (int)fit.get(EExFitProperty.SOURCE_LEV_NUMBER);
		int iTrgLevel = (int)fit.get(EExFitProperty.TARGET_LEV_NUMBER);
		int iSource = (int)keyRetriever.getLevelPk(iProject, iIon, iSrcLevel);
		int iTarget = (int)keyRetriever.getLevelPk(iProject, iIon, iTrgLevel);
		long lTransitionId = keyRetriever.getExTransitionPk(iSource, iTarget);
		
		if(lTransitionId > 0)
		{
			record = fit.toTrRecord(lTransitionId);
		}
		
		return record;
	}
  
	public ExFitRecord makeFitRecord(final int iProject, final ExFit fit)
	{
		ExFitRecord record = null;
		final DbKeyRetriever keyRetriever = new DbKeyRetriever();
		
		int iIon = (int)fit.get(EExFitProperty.ION_NUMBER);
		int iSrcLevel = (int)fit.get(EExFitProperty.SOURCE_LEV_NUMBER);
		int iTrgLevel = (int)fit.get(EExFitProperty.TARGET_LEV_NUMBER);
		int iSource = keyRetriever.getLevelPk(iProject, iIon, iSrcLevel);
		int iTarget = keyRetriever.getLevelPk(iProject, iIon, iTrgLevel);
		long lTransitionId = keyRetriever.getExTransitionPk(iSource, iTarget);
		
		if(lTransitionId > 0)
		{
			record = fit.toRecord(lTransitionId);
		}
		
		return record;
	}

	public CiFitRecord makeFitRecord(final int iProject, final CiFit fit)
	{
		CiFitRecord record = null;
		final DbKeyRetriever keyRetriever = new DbKeyRetriever();

		int iIon = (int)fit.get(ECiFitProperty.ION_NUMBER);
		int iSrcLevel = (int)fit.get(ECiFitProperty.SOURCE_LEV_NUMBER);
		int iTrgLevel = (int)fit.get(ECiFitProperty.TARGET_LEV_NUMBER);
		int iSource = keyRetriever.getLevelPk(iProject, iIon, iSrcLevel);
		int iTarget = keyRetriever.getLevelPk(iProject, iIon + 1, iTrgLevel);
		long lTransitionId = keyRetriever.getCiTransitionPk(iSource, iTarget);
			  
		if(lTransitionId > 0)
		{
			record = fit.toRecord(lTransitionId);
		}
		return record;
	}
	
	public PiFitRecord makeFitRecord(final int iProject, final PiFit fit)
	{
		PiFitRecord record = null;
		final DbKeyRetriever keyRetriever = new DbKeyRetriever();

		int iIon = (int)fit.get(EPiFitProperty.ION_NUMBER);
		int iSrcLevel = (int)fit.get(EPiFitProperty.SOURCE_LEV_NUMBER);
		int iTrgLevel = (int)fit.get(EPiFitProperty.TARGET_LEV_NUMBER);
		int iSource = keyRetriever.getLevelPk(iProject, iIon, iSrcLevel);
		int iTarget = keyRetriever.getLevelPk(iProject, iIon + 1, iTrgLevel);
		long lTransitionId = keyRetriever.getCiTransitionPk(iSource, iTarget);
			  
		if(lTransitionId > 0)
		{
			record = fit.toRecord(lTransitionId);
		}
		return record;
	}

}
