package crm.model.transition;

import util.EqualsUtil;
import util.HashCodeUtil;


public class TransitionDirection 
{
	private int m_iSourceIonNumber;
	private int m_iSourceLevelNumber;
	private int m_iTargetIonNumber;
	private int m_iTargetLevelNumber;

	public TransitionDirection(final int iSourceIon, final int iSourceLevel,
			final int iTargetIon, final int iTargetLevel)
	{
		m_iSourceIonNumber = iSourceIon;
		m_iSourceLevelNumber = iSourceLevel;
		m_iTargetIonNumber = iTargetIon;
		m_iTargetLevelNumber = iTargetLevel;
	}
	
    public boolean equals(Object that)
    {
    	  if ( this == that ) return true;
    	  if ( !(that instanceof TransitionDirection) ) return false;
    	  TransitionDirection thatLevel = (TransitionDirection)that;
    	  return
	  	    EqualsUtil.areEqual(this.m_iSourceIonNumber, thatLevel.m_iSourceIonNumber) &&
		    EqualsUtil.areEqual(this.m_iTargetIonNumber, thatLevel.m_iTargetIonNumber) &&
		    EqualsUtil.areEqual(this.m_iSourceLevelNumber, thatLevel.m_iSourceLevelNumber) &&
		    EqualsUtil.areEqual(this.m_iTargetLevelNumber, thatLevel.m_iTargetLevelNumber);
    }
    
    public int hashCode()
    {
    	  int result = HashCodeUtil.SEED;
    	  //collect the contributions of various fields
    	  result = HashCodeUtil.hash(result, m_iSourceIonNumber);
    	  result = HashCodeUtil.hash(result, m_iTargetIonNumber);
    	  result = HashCodeUtil.hash(result, m_iSourceLevelNumber);
    	  result = HashCodeUtil.hash(result, m_iTargetLevelNumber);
    	  return result;
    }


	public int getSourceIonNumber() {
		return m_iSourceIonNumber;
	}

	public void setSourceIonNumber(int sourceIonNumber) {
		m_iSourceIonNumber = sourceIonNumber;
	}

	public int getSourceLevelNumber() {
		return m_iSourceLevelNumber;
	}

	public void setSourceLevelNumber(int sourceLevelNumber) {
		m_iSourceLevelNumber = sourceLevelNumber;
	}

	public int getTargetIonNumber() {
		return m_iTargetIonNumber;
	}

	public void setTargetIonNumber(int targetIonNumber) {
		m_iTargetIonNumber = targetIonNumber;
	}

	public int getTargetLevelNumber() {
		return m_iTargetLevelNumber;
	}

	public void setTargetLevelNumber(int targetLevelNumber) {
		m_iTargetLevelNumber = targetLevelNumber;
	}
    
    public String toString()
    {
	    return "Direction: : " + m_iSourceIonNumber + 
	    		" " + m_iSourceLevelNumber + " " + 
	    		m_iTargetIonNumber + " " + 
	    		m_iTargetLevelNumber;

    }

}
