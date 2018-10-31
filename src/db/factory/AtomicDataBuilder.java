package db.factory;



import response.*;

import java.sql.Connection;
import java.sql.SQLException;

import db.sql.AtomicFactory;


public class AtomicDataBuilder 
{
	private final AtomicFactory factory;
	
	public AtomicDataBuilder(final Connection connection)
	throws ClassNotFoundException, SQLException
	{
		factory = new AtomicFactory(connection);
	}
	
	public void buildStructure(final Calc calc)
	{
		try 
		{
			StructureBuilder sBuilder = new StructureBuilder(calc, factory); 
        	System.out.println("Building structure ...");

			sBuilder.build();
			
//			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
//			EProcess[] processes = EProcess.values();
//			for(int i = 0; i < processes.length; i++)
//			{
//	        	System.out.println("Building transitions for " + processes[i].name() + " ... ");
//
//				tBuilder.createTransitions(processes[i]);
//			}
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void buildTransitionsForLevel(final Calc calc, final int iIonCharge, 
			final int iLevelId, boolean withPoints)
	{
		try 
		{
			buildStructure(calc);
			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
        	System.out.println("Building transitions for ion " + iIonCharge + " level ID " + iLevelId + " ... ");
			tBuilder.createTransitionsForLevel(iIonCharge, iLevelId, withPoints);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void buildTransitions(final Calc calc, boolean withPoints)
	{
		try 
		{
			buildStructure(calc);
			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
			 for (EProcess process : EProcess.values()) 
			 {
			     System.out.println(process);
			     tBuilder.createTransitions(process, withPoints);
			     
/*			     if(process == EProcess.AUTOIONIZATION || process == EProcess.RADIATION)
			     {
			    	 tBuilder.createTransitions(process, false);
			     }
			     else
			     {
			    	 tBuilder.createTransitions(process, true);
			     }*/
			 }
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void buildExcitationTransitionsForIon(final Calc calc, final int iIonCharge, boolean withPoints)
	{
		try 
		{
			buildStructure(calc);
			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
        	System.out.println("Building transitions for ion " + iIonCharge + " ... ");
			tBuilder.createExcitationTransitionsForIon(iIonCharge, withPoints);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void buildIonizationTransitionsForIon(final Calc calc, final int iIonCharge, boolean withPoints)
	{
		try 
		{
			buildStructure(calc);
			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
        	System.out.println("Building transitions for ion " + iIonCharge + " ... ");
			tBuilder.createIonizationTransitionsForIon(iIonCharge, withPoints);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void buildPhotoionizationTransitionsForIon(final Calc calc, final int iIonCharge, boolean withPoints)
	{
		try 
		{
			buildStructure(calc);
			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
        	System.out.println("Building transitions for ion " + iIonCharge + " ... ");
			tBuilder.createPhotoionizationTransitionsForIon(iIonCharge, withPoints);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}


	
	public void buildExcitationTransitions(final Calc calc, boolean withPoints)
	{
		try 
		{
			buildStructure(calc);
			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
		    System.out.println(EProcess.EXCITATION);
		    tBuilder.createExcitationTransitions(withPoints);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void buildIonizationTransitions(final Calc calc, boolean withPoints)
	{
		try 
		{
			buildStructure(calc);
			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
		    System.out.println(EProcess.COLLISIONAL_IONIZATION);
		    tBuilder.createIonizationTransitions(withPoints);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void buildPhotoionizationTransitions(final Calc calc, boolean withPoints)
	{
		try 
		{
			buildStructure(calc);
			TransitionsBuilder tBuilder = new TransitionsBuilder(calc, factory);
		    System.out.println(EProcess.PHOTOIONIZATION);
		    tBuilder.createPhotoionizationTransitions(withPoints);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
