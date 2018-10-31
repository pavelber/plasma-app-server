package crm.model;


import java.util.Enumeration;
import java.util.List;

import crm.model.transition.Transition;
import crm.model.transition.TransitionProcess;


public interface State  
{
    String getName();
    Enumeration<State> getSubStates();
    State getStateAt( final int index );
    State getParentState();
    boolean isSelected();
    
    List<Transition> getIncomingTransitionsFor(final TransitionProcess processType);
    List<Transition> getOutcomingTransitionsFor(final TransitionProcess processType);

    void setName( final String aName );
    void setParentState(final State parent);
    void addState( final State child );
    void insertState( final State child, final int index );
    void removeState( final State child );
    void setSelected(boolean bIsSelected);
}


  
  




