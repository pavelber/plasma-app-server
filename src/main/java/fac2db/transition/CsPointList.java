package fac2db.transition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsPointList 
{
	private List<CrossSectionPoint> m_points;
	private long m_lSource;
	private long m_lTarget;
	
	public CsPointList()
	{
		m_points = new ArrayList<CrossSectionPoint>();
	}
	
	
	public int getSize()
	{
		return m_points.size();
	}
	
	public Iterator<CrossSectionPoint> getPoints() 			
	{ 
		return m_points.iterator();
	}

	public void addPoint(final CrossSectionPoint point) 		
	{ 
		m_points.add(point);		
	}

	public long getSource() 
	{
		return m_lSource;
	}

	public void setSourceLevelPk(long source) 
	{
		m_lSource = source;
	}

	public long getTarget() 
	{
		return m_lTarget;
	}

	public void setTargetLevelPk(long target) 
	{
		m_lTarget = target;
	}
	
}
