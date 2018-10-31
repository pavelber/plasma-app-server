package comparator;

import java.util.Comparator;

import fac2db.level.ILevelState;


public class LevelEnergyComparator implements Comparator<ILevelState> 
{
	  public LevelEnergyComparator() 
	  {
	  }
	  
	  public int compare(ILevelState level1, ILevelState level2) 
	  {
		double e1 = ((ILevelState)level1).getEnergy();
		double e2 = ((ILevelState)level2).getEnergy();
	    
	    return (e2 - e1 > 0) ? -1 : (e2 - e1 < 0) ? 1 : 0;
	  }
}
