package db.factory;


import response.*;

import java.sql.SQLException;
import java.util.List;

import db.sql.AtomicFactory;


public class StructureBuilder 
{
	private static final long serialVersionUID = 1L;
	
	static AtomicFactory 	m_factory;
	private static Calc 	m_calc;
	
	public StructureBuilder(final Calc c, final AtomicFactory f)  
	throws Exception
	{
		m_calc = c;
		m_factory = f;
	}
	
	public void build() throws SQLException
	{
		 Atom atom = m_calc.getAtom();
    	 List<Ion> ions = atom.getIon();
    	 
    	 for(int i = m_calc.getStartIonNum(); i <= m_calc.getEndIonNum(); i++)
    	 {
    		Ion ion = new Ion();
    		Fac fac = new Fac();
    		ion.setFac(fac);
    		ion.setCharge((short) i);
    		m_factory.populateIon(ion, atom.getAtomNum(), i);
    		
    		List<Level> levels = ion.getFac().getLevel();
    		m_factory.populateLevels(m_calc, ion, levels);
    		
    		ions.add(ion);
    	 }
	}
}
