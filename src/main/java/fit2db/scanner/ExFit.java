package fit2db.scanner;

import java.util.EnumMap;
import java.util.Map;

import fit2db.property.EExFitProperty;
import fit2db.record.ExFitRecord;
import fit2db.record.ExTransitionRecord;


public class ExFit 
{
    private static final String FIT_TO_STRING = "ExFit[" +
		"ion=%d, src=%d, trg=%d, method=%d, A=%s, B=%s, C=%s, D=%s, E=%s]";

	private Map<EExFitProperty, Double> m_fitProperties =
		new EnumMap<EExFitProperty, 
			Double>(EExFitProperty.class);
	
	public ExFit()
	{
	}
	
	public double get(final EExFitProperty propertyKey) 
	{
		return m_fitProperties.get(propertyKey);
	}

	public void set(final EExFitProperty propertyKey, 
			final double dValue) 
	{
		m_fitProperties.put(propertyKey, dValue);
	}

	public void set(final double[] values) 
	{
		final EExFitProperty[] exFitprops = EExFitProperty.values();
		for (int i = 0; i < exFitprops.length; i++)
		{
			m_fitProperties.put(exFitprops[i], values[i]);
		}
	}
	
	public final ExTransitionRecord toTrRecord(final long lTransitionId)
	{
		double[] values = new double[2];
		values[0] = lTransitionId;
		values[1] = get(EExFitProperty.OSC_STRENGTH);
		
		ExTransitionRecord dbTr = new ExTransitionRecord();
		dbTr.set(values);
		return dbTr;
	}
	
	public final ExFitRecord toRecord(final long lTransitionId)
	{
		double[] values = new double[8];
		values[0] = lTransitionId;
		values[1] = (int)get(EExFitProperty.METHOD);
		values[2] = get(EExFitProperty.A);
		values[3] = get(EExFitProperty.B);
		values[4] = get(EExFitProperty.C);
		values[5] = get(EExFitProperty.D);
		values[6] = get(EExFitProperty.E);
		
		ExFitRecord dbFit = new ExFitRecord();
		dbFit.set(values);
		return dbFit;
	}

	@Override
	public final String toString()
	{
		return String.format(FIT_TO_STRING, 
				(int)get(EExFitProperty.ION_NUMBER),
				(int)get(EExFitProperty.SOURCE_LEV_NUMBER),
				(int)get(EExFitProperty.TARGET_LEV_NUMBER),
				(int)get(EExFitProperty.METHOD),
				get(EExFitProperty.A), 
				get(EExFitProperty.B), 
				get(EExFitProperty.C), 
				get(EExFitProperty.D),
				get(EExFitProperty.E));
	}
}
