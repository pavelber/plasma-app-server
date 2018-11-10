package comparator;


import java.util.Comparator;

import crm.model.state.Level;

public class LevelComparator implements Comparator<Object>
{
  protected boolean m_sortAsc;

  public LevelComparator(boolean sortAsc) 
  {
    m_sortAsc = sortAsc;
  }

  public int compare(Object o1, Object o2) 
  {
    if(!(o1 instanceof Level) || 
    		!(o2 instanceof Level))
      return 0;
    
    Level lev1 = (Level)o1;
    Level lev2 = (Level)o2;
    int 	result = 0;
    double 	d1, d2;
    
    d1 = lev1.getEnergy();
    d2 = lev2.getEnergy();
    result = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);

    if (!m_sortAsc) result = -result;
    
    return result;
  }

  public boolean equals(Object obj) 
  {
    if (obj instanceof LevelComparator) 
    {
    	LevelComparator compObj = (LevelComparator)obj;
      return (compObj.m_sortAsc == m_sortAsc);
    }
    return false;
  }
}
