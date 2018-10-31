package comparator;


import java.util.Comparator;

import fac2db.transition.Transition;



public class TransitionDirectionComparator  implements Comparator<Transition> 
{
  private Comparator<Transition> m_major;
  private Comparator<Transition> m_minor;

  public TransitionDirectionComparator(Comparator<Transition> major, 
  		Comparator<Transition> minor) 
  {
      m_major = major;
      m_minor = minor;
  }
  
  public int compare(Transition o1, Transition o2) 
  {
      int result1 = m_major.compare(o1, o2);
      
      if (result1 != 0) 
      {
          return result1;
      }
      else 
      {
          return m_minor.compare(o1, o2);
      }
  }
}
