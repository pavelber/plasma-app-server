package model;

public class CsPoint 
{
	private double m_dX;
	private double m_dY;
	
	public CsPoint()
	{
		
	}
	
	public CsPoint(final double dX, final double dY)
	{
		m_dX = dX;
		m_dY = dY;
	}

	public double getX() 
	{
		return m_dX;
	}

	public void setX(double mDX) 
	{
		m_dX = mDX;
	}

	public double getY() 
	{
		return m_dY;
	}

	public void setY(double mDY) 
	{
		m_dY = mDY;
	}
	
	
	
}
