package fit2db.scanner;

import java.util.EnumMap;
import java.util.Map;

import fit2db.property.ECiFitProperty;
import fit2db.record.CiFitRecord;


public class CiFit 
{
    private static final String FIT_TO_STRING = "CiFit[" +
		"ion=%d, src=%d, next_ion=%d, trg=%d, method=%d, A=%s, B=%s, C=%s, D=%s]";

	private Map<ECiFitProperty, Double> m_fitProperties =
		new EnumMap<ECiFitProperty, 
			Double>(ECiFitProperty.class);
	
	public CiFit()
	{
	}
	
	public double get(final ECiFitProperty propertyKey) 
	{
		return m_fitProperties.get(propertyKey);
	}
	
	public void set(final ECiFitProperty propertyKey, 
			final double dValue) 
	{
		m_fitProperties.put(propertyKey, dValue);
	}
	
	public void set(final double[] values) 
	{
		final ECiFitProperty[] ciFitprops = ECiFitProperty.values();
		for (int i = 0; i < ciFitprops.length; i++)
		{
			m_fitProperties.put(ciFitprops[i], values[i]);
		}
	}

	public final CiFitRecord toRecord(final long lTransitionId)
	{
		double[] values = new double[7];
		values[0] = lTransitionId;
		values[1] = (int)2;
		values[2] = (-1) * get(ECiFitProperty.NEGATIVE_A);
		values[3] = get(ECiFitProperty.B);
		values[4] = get(ECiFitProperty.C);
		values[5] = get(ECiFitProperty.D);
		
		CiFitRecord dbFit = new CiFitRecord();
		dbFit.set(values);
		return dbFit;
	}

	@Override
	public final String toString()
	{
		return String.format(FIT_TO_STRING, 
				(int)get(ECiFitProperty.ION_NUMBER),
				(int)get(ECiFitProperty.SOURCE_LEV_NUMBER),
				(int)get(ECiFitProperty.NEXT_ION_NUMBER),
				(int)get(ECiFitProperty.TARGET_LEV_NUMBER),
				(int)2,
				(-1) * get(ECiFitProperty.NEGATIVE_A), 
				get(ECiFitProperty.B), 
				get(ECiFitProperty.C), 
				get(ECiFitProperty.D));
	}
}
