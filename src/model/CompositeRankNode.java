package model;


import model.rank.LevelWrapper;
import model.transition.ETransitionProcess;
import model.transition.JumpWrapper;

import javax.swing.tree.*;

import java.util.*;


public abstract class CompositeRankNode extends RankNode 
{
    private List<Rank> m_children = new ArrayList<Rank>();

    public CompositeRankNode (String aName) 
    {
        super(aName);
    }
    
    public void sort(Comparator<LevelWrapper> comparator)
    {
    	List<LevelWrapper> levels = new ArrayList<LevelWrapper>();
    	for(int i = 0; i < m_children.size(); i++)
    	{
    		Rank child = m_children.get(i);
    		final Enumeration e = child.getSubRanks();
    		while(e.hasMoreElements())
    		{
    			LevelWrapper level = (LevelWrapper)e.nextElement();
    			levels.add(level);
     		}
    	}
        Collections.sort(levels, comparator);
    }


    public void insertRank( Rank child,int index ) 
    {
        child.setParentRank( this );
        m_children.add(index, child);
    }

    public void removeRank( Rank child ) 
    {
        child.setParentRank( null );
        m_children.remove(child);
    }

    public void addRank( Rank child ) 
    {
        insertRank(child, getChildCount());
    }

    public Enumeration<Rank> getSubRanks() 
    {
    	return Collections.enumeration(m_children);
    }

    public List<JumpWrapper> getIncomingTransitionsFor(final ETransitionProcess processType) 
    {
	    Enumeration enumeration = getSubRanks();
	    List<JumpWrapper> totalTransitions = new ArrayList<JumpWrapper>();
	    while ( enumeration.hasMoreElements() ) 
	    {
	         Rank currentChild = (Rank) enumeration.nextElement();
	         List<JumpWrapper> transitions = currentChild.getIncomingTransitionsFor(processType);
	         totalTransitions.addAll(transitions);
	    }
	    return totalTransitions;
    }
    
    public List<JumpWrapper> getOutcomingTransitionsFor(final ETransitionProcess processType) 
    {
	    Enumeration enumeration = getSubRanks();
	    List<JumpWrapper> totalTransitions = new ArrayList<JumpWrapper>();
	    while ( enumeration.hasMoreElements() ) 
	    {
	         Rank currentChild = (Rank) enumeration.nextElement();
	         List<JumpWrapper> transitions = currentChild.getOutcomingTransitionsFor(processType);
	         totalTransitions.addAll(transitions);
	    }
	    return totalTransitions;
    }

    public int getIndex(TreeNode node) 
    {
        return m_children.indexOf(node);
    }

    public Rank getRankAt( int index ) 
    {
           return (Rank) m_children.get(index);
    }

    /**
    * Mutable Tree Nodes methods
    */
    public void  insert( MutableTreeNode child, int index ) 
    {
        insertRank((Rank)child,index);
    }

    public void remove( int index ) 
    {
        Rank org = getRankAt(index);
        removeRank(org);
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
        removeRank((Rank)node);
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
