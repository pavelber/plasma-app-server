package fit2db.scanner;


import java.util.EnumMap;
import java.util.Map;

import fit2db.property.EPiFitProperty;
import fit2db.record.PiFitRecord;


public class PiFit 
{
    private static final String FIT_TO_STRING = "PiFit[" +
		"ion=%d, src=%d, trg=%d, method=%d, A=%s, B=%s, C=%s, D=%s]";

	private Map<EPiFitProperty, Double> m_fitProperties =
		new EnumMap<EPiFitProperty, 
			Double>(EPiFitProperty.class);
	
	
	public PiFit()
	{
	}
	
	public double get(final EPiFitProperty propertyKey) 
	{
		return m_fitProperties.get(propertyKey);
	}
	
	public void set(final EPiFitProperty propertyKey, 
			final double dValue) 
	{
		m_fitProperties.put(propertyKey, dValue);
	}
	
	public void set(final double[] values) 
	{
		final EPiFitProperty[] piFitprops = EPiFitProperty.values();
		for (int i = 0; i < piFitprops.length; i++)
		{
			m_fitProperties.put(piFitprops[i], values[i]);
		}
	}

	public final PiFitRecord toRecord(final long lTransitionId)
	{
		double[] values = new double[7];
		values[0] = lTransitionId;
		values[1] = (int)get(EPiFitProperty.METHOD);
		values[2] = get(EPiFitProperty.A);
		values[3] = get(EPiFitProperty.B);
		values[4] = get(EPiFitProperty.C);
		values[5] = get(EPiFitProperty.D);
		//values[6] = "YES"; // fit is valid
		
		PiFitRecord dbFit = new PiFitRecord();
		dbFit.set(values);
		return dbFit;
	}

	@Override
	public final String toString()
	{
		return String.format(FIT_TO_STRING, 
				(int)get(EPiFitProperty.ION_NUMBER),
				(int)get(EPiFitProperty.SOURCE_LEV_NUMBER),
				(int)get(EPiFitProperty.TARGET_LEV_NUMBER),
				(int)get(EPiFitProperty.METHOD),
				get(EPiFitProperty.A), 
				get(EPiFitProperty.B), 
				get(EPiFitProperty.C), 
				get(EPiFitProperty.D));
	}
}
