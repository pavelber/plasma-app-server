package comparator;


import java.util.Comparator;

import fac2db.level.ILevelState;


public class LevelIonNumberComparator implements Comparator<ILevelState> 
{
	  public LevelIonNumberComparator() 
	  {
	  }
	  
	  public int compare(ILevelState level1, ILevelState level2) 
	  {
		int e1 = ((ILevelState)level1).getIonNumber();
		int e2 = ((ILevelState)level2).getIonNumber();
	    
	    return (e2 - e1 > 0) ? -1 : (e2 - e1 < 0) ? 1 : 0;
	  }
}