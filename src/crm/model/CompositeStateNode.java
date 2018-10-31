package crm.model;


import javax.swing.tree.*;

import crm.model.state.Level;
import crm.model.transition.Transition;
import crm.model.transition.TransitionProcess;

import java.util.*;


public abstract class CompositeStateNode extends StateNode 
{
    private List<State> m_children = new ArrayList<State>();

    public CompositeStateNode (String aName) 
    {
        super(aName);
    }
    
    public void sort(Comparator<Level> comparator)
    {
    	List<Level> levels = new ArrayList<Level>();
    	for(int i = 0; i < m_children.size(); i++)
    	{
    		State child = m_children.get(i);
    		final Enumeration e = child.getSubStates();
    		while(e.hasMoreElements())
    		{
    			Level level = (Level)e.nextElement();
    			levels.add(level);
     		}
    	}
        Collections.sort(levels, comparator);
    }


    public void insertState( State child,int index ) 
    {
        child.setParentState( this );
        m_children.add(index, child);
    }

    public void removeState( State child ) 
    {
        child.setParentState( null );
        m_children.remove(child);
    }

    public void addState( State child ) 
    {
        insertState(child, getChildCount());
    }

    public Enumeration<State> getSubStates() 
    {
    	return Collections.enumeration(m_children);
    }

    public List<Transition> getIncomingTransitionsFor(final TransitionProcess processType) 
    {
	    Enumeration enumeration = getSubStates();
	    List<Transition> totalTransitions = new ArrayList<Transition>();
	    while ( enumeration.hasMoreElements() ) 
	    {
	         State currentChild = (State) enumeration.nextElement();
	         List<Transition> transitions = currentChild.getIncomingTransitionsFor(processType);
	         totalTransitions.addAll(transitions);
	    }
	    return totalTransitions;
    }
    
    public List<Transition> getOutcomingTransitionsFor(final TransitionProcess processType) 
    {
	    Enumeration enumeration = getSubStates();
	    List<Transition> totalTransitions = new ArrayList<Transition>();
	    while ( enumeration.hasMoreElements() ) 
	    {
	         State currentChild = (State) enumeration.nextElement();
	         List<Transition> transitions = currentChild.getOutcomingTransitionsFor(processType);
	         totalTransitions.addAll(transitions);
	    }
	    return totalTransitions;
    }

    public int getIndex(TreeNode node) 
    {
        return m_children.indexOf(node);
    }

    public State getStateAt( int index ) 
    {
           return (State) m_children.get(index);
    }

    /**
    * Mutable Tree Nodes methods
    */
    public void  insert( MutableTreeNode child, int index ) 
    {
        insertState((State)child,index);
    }

    public void remove( int index ) 
    {
        State org = getStateAt(index);
        removeState(org);
    }

    public int getChildCount() 
    {
        return m_children.size();
    }

    public TreeNode getChildAt(int childIndex) 
    {
        return (TreeNode) m_children.get(childIndex);
    }

    public void remove( MutableTreeNode node ) 
    {
        removeState((State)node);
    }

    public void  removeFromParent() 
    {
        setParent(null);
    }

    public boolean isLeaf() 
    {
        return false;
    }
}
