package model;


import model.transition.ETransitionProcess;
import model.transition.JumpWrapper;

import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.*;


public abstract class RankNode  implements Rank, MutableTreeNode 
{
    private String 	m_sLocation;
    private String 	m_sName;
    private Rank   m_parent;
    private Object 	m_userObject;
    private boolean m_bIsSelected = false;

    /**
    * Each rank must have a name
    */
    public RankNode( String aName ) 
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

    public abstract Enumeration<Rank> getSubRanks();
    public abstract List<JumpWrapper> getIncomingTransitionsFor(final ETransitionProcess processType);
    public abstract List<JumpWrapper> getOutcomingTransitionsFor(final ETransitionProcess processType);

    public void setParentRank(Rank parent)
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
     protected abstract boolean isLegalParent(Rank parent);

     public void insertRank( Rank child, int index ) 
     {
         throw new RuntimeException("Not a composite rank");
     }

     public  void removeRank( Rank child ) 
     {
         throw new RuntimeException("Not a composite rank");
     }

     public Rank getRankAt( int index ) 
     {
         throw new RuntimeException("Not a composite rank");
     }

     public Rank getParentRank()
     {
         return (Rank) getParent();
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

    public Enumeration<Rank> children() 
    {
        return getSubRanks();
    }

    public void  insert( MutableTreeNode child, int index ) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public void remove( int index ) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public void insert( Rank child ) 
    {
        throw new RuntimeException("Illegal Method Call");
    }

    public void addRank( Rank child ) 
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
        setParentRank((Rank)newParent);
    }

    public String toString() 
    {
        return getName();
    }
}



