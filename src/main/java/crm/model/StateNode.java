package crm.model;


import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.*;

import crm.model.transition.Transition;
import crm.model.transition.TransitionProcess;


public abstract class StateNode  implements State, MutableTreeNode 
{
    private String 	m_sLocation;
    private String 	m_sName;
    private State   m_parent;
    private Object 	m_userObject;
    private boolean m_bIsSelected = false;

    /**
    * Each state must have a name
    */
    public StateNode( String aName ) 
    {
        setName( aName);
    }

    public String getLocation() 
    {
        return m_sLocation;
    }

    public void setLocation( String anLocation ) 
    {
        m_sLocation = anLocation;
    }

    public String getName() 
    {
        return m_sName;
    }

    public void setName( String aName ) 
    {
        m_sName = aName;
    }


    public void setUserObject( java.lang.Object object ) 
    {
        m_userObject = object;
    }

    public Object getUserObject() 
    {
        return m_userObject;
    }
    
    public boolean isSelected()
    {
    	return m_bIsSelected;
    }
    
    public void setSelected(final boolean bIsSelected)
    {
    	m_bIsSelected = bIsSelected;
    }

    public abstract Enumeration<State> getSubStates();
    public abstract List<Transition> getIncomingTransitionsFor(final TransitionProcess processType);
    public abstract List<Transition> getOutcomingTransitionsFor(final TransitionProcess processType);

    public void setParentState(State parent)
    {
        if ( parent != null)
        {
            if (!isLegalParent( parent )) 
            {
               throw new IllegalArgumentException("Parent not allowed");
            }
       	    m_parent = parent;
        }
    }

    // check if the parent is allowed
     protected abstract boolean isLegalParent(State parent);

     public void insertState( State child, int index ) 
     {
         throw new RuntimeException("Not a composite state");
     }

     public  void removeState( State child ) 
     {
         throw new RuntimeException("Not a composite state");
     }

     public State getStateAt( int index ) 
     {
         throw new RuntimeException("Not a composite state");
     }

     public State getParentState()
     {
         return (State) getParent();
     }


    // mutable tree nodes methods
    public TreeNode getChildAt(int childIndex) 
    {
         throw new RuntimeException("Illegal Method Call");
    }

    public int getChildCount() 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public TreeNode getParent() 
    {
         return (TreeNode)m_parent;
    }

    public int getIndex(TreeNode node) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public boolean getAllowsChildren() 
    {
        return false;
    }

    public boolean isLeaf() 
    {
        return true;
    }

    public Enumeration<State> children() 
    {
        return getSubStates();
    }

    public void  insert( MutableTreeNode child, int index ) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public void remove( int index ) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public void insert( State child ) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public void addState( State child ) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public void remove( MutableTreeNode node ) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public void removeFromParent() 
    {
        setParent(null);
    }

    public void   setParent( MutableTreeNode newParent ) 
    {
        setParentState((State)newParent);
    }

    public String toString() 
    {
        return getName();
    }
}



