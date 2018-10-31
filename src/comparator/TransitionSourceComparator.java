package comparator;



import java.util.Comparator;

import fac2db.transition.Transition;
import fac2db.transition.TransitionDirection;



public class TransitionSourceComparator  implements Comparator<Transition> 
{
	  public TransitionSourceComparator() 
	  {
	  }
	  
	  public int compare(Transition transition1, Transition transition2) 
	  {
		TransitionDirection id1 = ((Transition)transition1).getDirection();
		TransitionDirection id2 = ((Transition)transition2).getDirection();
	    
	    return (id2.getSourceLevelNumber() - id1.getSourceLevelNumber() > 0) ? -1 : 
	    	(id2.getSourceLevelNumber() - id1.getSourceLevelNumber() < 0) ? 1 : 0;
	  }
}
