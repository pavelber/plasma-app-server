package model;


import model.transition.ETransitionProcess;
import model.transition.JumpWrapper;

import java.util.Enumeration;
import java.util.List;


public interface Rank  
{
    String getName();
    Enumeration<Rank> getSubRanks();
    Rank getRankAt( final int index );
    Rank getParentRank();
    boolean isSelected();
    
    List<JumpWrapper> getIncomingTransitionsFor(final ETransitionProcess processType);
    List<JumpWrapper> getOutcomingTransitionsFor(final ETransitionProcess processType);

    void setName( final String aName );
    void setParentRank(final Rank parent);
    void addRank( final Rank child );
    void insertRank( final Rank child, final int index );
    void removeRank( final Rank child );
    void setSelected(boolean bIsSelected);
}


  
  




