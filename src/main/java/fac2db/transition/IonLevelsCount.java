package fac2db.transition;

import util.HashCodeUtil;


public class IonLevelsCount 
{
	private int m_iIonNumber;
	private int m_iLevelsCount;
	
	public IonLevelsCount(final int iIonNumber, final int iLevelsCount)
	{
		m_iIonNumber = iIonNumber;
		m_iLevelsCount = iLevelsCount;
	}
	
	public int getLevelsCount()
	{
		return m_iLevelsCount;
	}
	
	public int getIonNumber()
	{
		return m_iIonNumber;
	}
	
	@Override
	public boolean equals(Object that)
	{
		  if ( this == that ) return true;
		  if ( !(that instanceof IonLevelsCount) ) return false;
		  IonLevelsCount thatCounter = (IonLevelsCount)that;
		  return this.m_iIonNumber == thatCounter.getIonNumber();
	}
	
	@Override
	public int hashCode()
	{
	    int result = HashCodeUtil.SEED;
	    result = HashCodeUtil.hash(result, m_iIonNumber);
	    return result;
	}
	
	@Override
	public String toString()
	{
		return "Ion: " + m_iIonNumber + " levels count: " + m_iLevelsCount;
	}

}
