package fac2db.transition;

import java.util.List;

import db.sql.FitDbManager;



public class Counter 
{
	private List<IonLevelsCount> m_counts;
	
	public Counter(final int iProject, final FitDbManager dbManager)
	{
		m_counts = dbManager.getIonLevelsCount(iProject);
	}
	
	public int getLevelsCountForIon(final int iIonNumber)
	{
		int res = -1;
		for(int i = 0; i < m_counts.size(); i++)
		{
			IonLevelsCount count = m_counts.get(i);
			if(iIonNumber == count.getIonNumber())
			{
				res = count.getLevelsCount();
				break;
			}
		}
		return res;
	}
	
	public int getLevelNumber(final int iIonNumber, final int iLevelNumber)
	{
		int iLevelsCount = getLevelsCountForIon(iIonNumber);
		int res = iLevelNumber - iLevelsCount + 1;
		return res;
	}

}
