package db.factory;


import response.*;

import java.sql.SQLException;
import java.util.List;

import db.sql.AtomicFactory;


public class TransitionsBuilder 
{
	private static Calc m_calc;
	private static AtomicFactory m_factory;
	
	public TransitionsBuilder(final Calc c, final AtomicFactory f)  
	throws Exception
	{
		m_calc = c;
		m_factory = f;
	}
	
	public void createTransitionsForLevel(final int iIonCharge, 
			final int iLevelId, boolean withPoints) 
	throws SQLException
	{
		Atom element = m_calc.getAtom();
		List<Ion> ions = element.getIon();
		int iIonCount = ions.size();
		System.out.println(iIonCount);
    	for(int i = 0; i <= iIonCount; i++) 
    	{
    	 Ion ion = ions.get(i);
    	 System.out.println("working with ion " + ion.getCharge());

    	 if(i == iIonCount - 1) return;
    	 Ion nextIon = ions.get(i + 1);
    	 
    	 if(ion.getCharge() == iIonCharge)
    	 {
        	 List<Level> levels = ion.getFac().getLevel();
        	 System.out.println("included " + levels.size() + " levels");

        	 for(int j = 0; j < levels.size(); j++)
        	 {
        		 Level level = levels.get(j);
        		 
        		 if(level.getLevId() == iLevelId)
        		 {
     				createAiTransitionsForLevel(nextIon, level);
    				createExTransitionsForLevel(ion, level, withPoints);
    				createCiTransitionsForLevel(nextIon, level, withPoints);
    				createPiTransitionsForLevel(nextIon, level, withPoints);
    				createRtTransitionsForLevel(ion, level);
    				break;
        		 }
        		 else
        		 {
        			 continue;
        		 }
        	 }
    	 }
    	 else
    	 {
    		 continue;
    	 }
    		 
     	}
	}

	
	public void createTransitions(EProcess process, boolean withPoints) 
	throws SQLException
	{
		Atom element = m_calc.getAtom();
		List<Ion> ions = element.getIon();
		int iIonCount = ions.size();
    	for(int i = 0; i <= iIonCount; i++) 
//    	for(int i = 0; i <= 1; i++)
    	{
    	 Ion ion = ions.get(i);
    	 System.out.println("working with ion " + ion.getCharge());

    	 if(i == iIonCount - 1) return;
    	 Ion nextIon = ions.get(i + 1);
    		 
    	 List<Level> levels = ion.getFac().getLevel();
    	 System.out.println("included " + levels.size() + " levels");

    	 for(int j = 0; j < levels.size(); j++)
    	 {
    		 Level level = levels.get(j);
    			 
    			switch(process)
    			{
    			case AUTOIONIZATION:
    				createAiTransitionsForLevel(nextIon, level);
    				break;
    			case EXCITATION:
    				createExTransitionsForLevel(ion, level, withPoints);
    				break;
    			case COLLISIONAL_IONIZATION:
    				createCiTransitionsForLevel(nextIon, level, withPoints);
    				break;
    			case PHOTOIONIZATION:
    				createPiTransitionsForLevel(nextIon, level, withPoints);
    				break;
    			case RADIATION:
    				createRtTransitionsForLevel(ion, level);
    				break;
    				default:
    					break;
    			}
    	 }
     	}
	}
	
	public void createIonizationTransitions(boolean withPoints) 
	throws SQLException
	{
		Atom element = m_calc.getAtom();
		List<Ion> ions = element.getIon();
		int iIonCount = ions.size();
    	for(int i = 0; i <= iIonCount; i++) 
    	{
    	 Ion ion = ions.get(i);
    	 System.out.println("working with ion " + ion.getCharge());

    	 if(i == iIonCount - 1) return;
    	 Ion nextIon = ions.get(i + 1);
    		 
    	 List<Level> levels = ion.getFac().getLevel();
    	 System.out.println("included " + levels.size() + " levels");

    	 for(int j = 0; j < levels.size(); j++)
    	 {
    		 Level level = levels.get(j);
    		 createCiTransitionsForLevel(nextIon, level, withPoints);
    	 }
     	}
	}
	
	public void createPhotoionizationTransitions(boolean withPoints) 
	throws SQLException
	{
		Atom element = m_calc.getAtom();
		List<Ion> ions = element.getIon();
		int iIonCount = ions.size();
    	for(int i = 0; i <= iIonCount; i++) 
    	{
    	 Ion ion = ions.get(i);
    	 System.out.println("working with ion " + ion.getCharge());

    	 if(i == iIonCount - 1) return;
    	 Ion nextIon = ions.get(i + 1);
    		 
    	 List<Level> levels = ion.getFac().getLevel();
    	 System.out.println("included " + levels.size() + " levels");

    	 for(int j = 0; j < levels.size(); j++)
    	 {
    		 Level level = levels.get(j);
    		 createPiTransitionsForLevel(nextIon, level, withPoints);
    	 }
     	}
	}


	
	public void createExcitationTransitions(boolean withPoints) 
	throws SQLException
	{
		Atom element = m_calc.getAtom();
		List<Ion> ions = element.getIon();
		int iIonCount = ions.size();
    	for(int i = 0; i <= iIonCount; i++) 
    	{
    	 Ion ion = ions.get(i);
    	 System.out.println("working with ion " + ion.getCharge());
    	 if(i == iIonCount - 1) return;
    		 
    	 List<Level> levels = ion.getFac().getLevel();
    	 System.out.println("included " + levels.size() + " levels");

    	 for(int j = 0; j < levels.size(); j++)
    	 {
    		 Level level = levels.get(j);
    		 createExTransitionsForLevel(ion, level, withPoints);
    	 }
     	}
	}
	
	public void createExcitationTransitionsForIon(final int iIonCharge, boolean withPoints) /////////////////////
	throws SQLException
	{
		Atom element = m_calc.getAtom();
		List<Ion> ions = element.getIon();
		int iIonCount = ions.size();
    	for(int i = 0; i <= iIonCount; i++) 
    	{
    	 Ion ion = ions.get(i);
    	 System.out.println("working with ion " + ion.getCharge());
    	 if(i == iIonCount - 1) return;
    		 
    	 if(ion.getCharge() == iIonCharge)
    	 {
        	 List<Level> levels = ion.getFac().getLevel();
        	 System.out.println("included " + levels.size() + " levels");

        	 for(int j = 0; j < levels.size(); j++)
        	 {
        		 Level level = levels.get(j);
 				 createExTransitionsForLevel(ion, level, withPoints);
        	 }
    	 }
    	 else
    	 {
    		 continue;
    	 }
    		 
     	}
	}

	public void createIonizationTransitionsForIon(final int iIonCharge, boolean withPoints) /////////////////////
	throws SQLException
	{
		Atom element = m_calc.getAtom();
		List<Ion> ions = element.getIon();
		int iIonCount = ions.size();
    	for(int i = 0; i <= iIonCount; i++) 
    	{
    	 Ion ion = ions.get(i);
    	 System.out.println("working with ion " + ion.getCharge());
    	 if(i == iIonCount - 1) return;
    		 
    	 if(ion.getCharge() == iIonCharge)
    	 {
        	 List<Level> levels = ion.getFac().getLevel();
        	 System.out.println("included " + levels.size() + " levels");

        	 for(int j = 0; j < levels.size(); j++)
        	 {
        		 Level level = levels.get(j);
 				 createCiTransitionsForLevel(ion, level, withPoints);
        	 }
    	 }
    	 else
    	 {
    		 continue;
    	 }
    		 
     	}
	}
	
	public void createPhotoionizationTransitionsForIon(final int iIonCharge, boolean withPoints) /////////////////////
	throws SQLException
	{
		Atom element = m_calc.getAtom();
		List<Ion> ions = element.getIon();
		int iIonCount = ions.size();
    	for(int i = 0; i <= iIonCount; i++) 
    	{
    	 Ion ion = ions.get(i);
    	 System.out.println("working with ion " + ion.getCharge());
    	 if(i == iIonCount - 1) return;
    		 
    	 if(ion.getCharge() == iIonCharge)
    	 {
        	 List<Level> levels = ion.getFac().getLevel();
        	 System.out.println("included " + levels.size() + " levels");

        	 for(int j = 0; j < levels.size(); j++)
        	 {
        		 Level level = levels.get(j);
 				 createPiTransitionsForLevel(ion, level, withPoints);
        	 }
    	 }
    	 else
    	 {
    		 continue;
    	 }
    		 
     	}
	}

	
	private void createExTransitionsForLevel(final Ion ion, 
			final Level level, boolean withPoints)
	{
 	 try 
 	 {
		List<ExJump> transitions = 
			 m_factory.getExTransitionList(ion, level, withPoints);
		 
		 List<ExJump> jumps = level.getExJump();
		 for(int k = 0; k < transitions.size(); k++)
		 {
			 ExJump transition = transitions.get(k);
			 jumps.add(transition);
		 }
	 } 
 	 catch (SQLException e) 
 	 {
		e.printStackTrace();
	 }
	}
	
	private void createPiTransitionsForLevel(final Ion nextIon, 
			final Level level, boolean withPoints)
	{
 	 try 
 	 {
		List<PiJump> transitions = 
			 m_factory.getPiTransitionList(nextIon, level, withPoints);
		 
		 List<PiJump> jumps = level.getPiJump();
		 for(int k = 0; k < transitions.size(); k++)
		 {
			 PiJump transition = transitions.get(k);
			 jumps.add(transition);
		 }
	 } 
 	 catch (SQLException e) 
 	 {
		e.printStackTrace();
	 }
	}
	
	private void createCiTransitionsForLevel(final Ion nextIon, 
			final Level level, boolean withPoints)
	{
 	 try 
 	 {
		List<CiJump> transitions = 
			 m_factory.getCiTransitionList(nextIon, level, withPoints);
		 
		 List<CiJump> jumps = level.getCiJump();
		 for(int k = 0; k < transitions.size(); k++)
		 {
			 CiJump transition = transitions.get(k);
			 jumps.add(transition);
		 }
	 } 
 	 catch (SQLException e) 
 	 {
		e.printStackTrace();
	 }
	}
	
	private void createRtTransitionsForLevel(final Ion ion, final Level level)
	{
 	 try 
 	 {
		List<RtJump> transitions = 
			 m_factory.getRtTransitionList(ion, level);
		 
		 List<RtJump> jumps = level.getRtJump();
		 for(int k = 0; k < transitions.size(); k++)
		 {
			 RtJump transition = transitions.get(k);
			 jumps.add(transition);
		 }
	 } 
 	 catch (SQLException e) 
 	 {
		e.printStackTrace();
	 }
	}
	
	private void createAiTransitionsForLevel(final Ion nextIon, final Level level)
	{
 	 try 
 	 {
		List<AiJump> transitions = 
			 m_factory.getAiTransitionList(nextIon, level);
		 
		 List<AiJump> jumps = level.getAiJump();
		 for(int k = 0; k < transitions.size(); k++)
		 {
			 AiJump transition = transitions.get(k);
			 jumps.add(transition);
		 }
	 } 
 	 catch (SQLException e) 
 	 {
		e.printStackTrace();
	 }
	}
}
