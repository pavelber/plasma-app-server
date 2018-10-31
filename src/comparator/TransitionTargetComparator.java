package comparator;



import java.util.Comparator;

import fac2db.transition.Transition;
import fac2db.transition.TransitionDirection;



public class TransitionTargetComparator  implements Comparator<Transition> 
{
	  public TransitionTargetComparator() 
	  {
	  }
	  
	  public int compare(Transition transition1, Transition transition2) 
	  {
		TransitionDirection id1 = ((Transition)transition1).getDirection();
		TransitionDirection id2 = ((Transition)transition2).getDirection();
	    
	    return (id2.getTargetLevelNumber() - id1.getTargetLevelNumber() > 0) ? -1 : 
	    	(id2.getTargetLevelNumber() - id1.getTargetLevelNumber() < 0) ? 1 : 0;
	  }
}
