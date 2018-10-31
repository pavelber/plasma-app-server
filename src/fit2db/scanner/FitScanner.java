package fit2db.scanner;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Pattern;

import db.sql.FitDbManager;

import fit2db.property.ETransitionProcess;


public final class FitScanner 
{
	public static final Pattern SEPARATOR_PATTERN = Pattern.compile("\\s+");
	private FitFactory 	m_factory;
	
	public FitScanner()
	{
	}

    public void scan(final File file, final int iProjectId, 
    		final FitDbManager dbManager) throws IOException
    {
    	System.out.println(file.getCanonicalPath());

        if(	file.getName().equals("excit.inp") ||
        		file.getName().equals("EXCIT.INP"))
        {
        	processExcitationFile(file, iProjectId, dbManager);
        }
        else if(file.getName().equals("bcfp.inp") ||
        		file.getName().equals("BCFP.INP"))
        {
        	processIonizationFile(file, iProjectId, dbManager);
        }
        else 
        if(file.getName().equals("rrec.inp") ||
        		file.getName().equals("RREC.INP"))
        {
        	processPhotoionizationFile(file, iProjectId, dbManager);
        }
        else
        {
        	return;
        }
    }
    
    private void processExcitationFile(final File file, final int iProjectId, 
    		final FitDbManager dbManager) throws IOException
    {
        final LineNumberReader reader =
            new LineNumberReader(new FileReader(file));
    
        ETransitionProcess process = ETransitionProcess.Excitation;
    	m_factory = new FitFactory(process);
        for (String sLine = reader.readLine();
        sLine != null; sLine = reader.readLine())
        {
        	double[] values = createExFitValues(sLine);
        	if(values != null)
        	{
        		m_factory.addFit(values);
        	}
        }
        dbManager.loadExFits(iProjectId, m_factory.getExFits());
        reader.close();
    }
    
    private void processIonizationFile(final File file, final int iProjectId, 
    		final FitDbManager dbManager) throws IOException
    {
        final LineNumberReader reader =
            new LineNumberReader(new FileReader(file));
    
        ETransitionProcess process = ETransitionProcess.Ionization;
    	m_factory = new FitFactory(process);
        for (String sLine = reader.readLine();
        sLine != null; sLine = reader.readLine())
        {
        	double[] values = createCiFitValues(sLine);
        	if(values != null)
        	{
        		m_factory.addFit(values);
        	}
        }
        dbManager.loadCiFits(iProjectId, m_factory.getCiFits());
        reader.close();
    }

    private void processPhotoionizationFile(final File file, final int iProjectId, 
    		final FitDbManager dbManager) throws IOException
    {
        final LineNumberReader reader =
            new LineNumberReader(new FileReader(file));
    
        ETransitionProcess process = ETransitionProcess.Photoionization;
    	m_factory = new FitFactory(process);
        for (String sLine = reader.readLine();
        sLine != null; sLine = reader.readLine())
        {
        	double[] values = createPiFitValues(sLine);
        	if(values != null)
        	{
        		m_factory.addFit(values);
        	}
        }
        dbManager.loadPiFits(iProjectId, m_factory.getPiFits());
        reader.close();
    }
 
	private double[] createCiFitValues(final String sLine)
	{
		double[] values = null;
		
        if(sLine != null)
        {
        	String[] fields = sLine.trim().split("\\s+");
        	values = new double[fields.length];
        	for(int i = 0; i < fields.length; i++)
        	{
        		values[i] = Double.parseDouble(fields[i]);
        	}
        }
        return values;
	}

	private double[] createExFitValues(final String sLine)
	{
		double[] values = null;
		
        if(sLine != null && !sLine.trim().equals(""))
        {
        	String[] fields = sLine.trim().split("\\s+");
        	values = new double[fields.length];
        	for(int i = 0; i < fields.length; i++)
        	{
        		if(fields.length > 3)
        		{
        			if(!"-5".equals(fields[3]))
        			{
                		values[i] = Double.parseDouble(fields[i]);
        			}
        		}
        		//System.out.println(i + "    " + fields[i]);
        	}
        }

		
/*        if(sLine != null)
        {
        	String[] fields = sLine.trim().split("\\s+");
        	
        	if(!"-5".equals(fields[3]))
        	{
            	values = new double[fields.length];
            	for(int i = 0; i < fields.length; i++)
            	{
            		values[i] = Double.parseDouble(fields[i]);
            	}
        	}
        }*/
        return values;
	}
	
	private double[] createPiFitValues(final String sLine)
	{
		double[] values = null;
		
        if(sLine != null)
        {
        	String[] fields = sLine.trim().split("\\s+");
        	
        	if(!"0".equals(fields[3]))
        	{
            	values = new double[fields.length];
            	for(int i = 0; i < fields.length; i++)
            	{
            		values[i] = Double.parseDouble(fields[i]);
            	}
        	}
        }
        return values;
	}
}
