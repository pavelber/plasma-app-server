package model.rank;

import model.CompositeRankNode;
import model.Rank;
import response.Atom;


public class ElementWrapper extends CompositeRankNode  
{
	private Atom 	m_atom;

    public ElementWrapper( String aName ) 
    {
        super(aName);
    }

    public boolean isLegalParent( Rank parentOrg ) 
    {
        boolean result = ( parentOrg instanceof CalculationWrapper )?  true : false;
            return result;
    }
    
	public int getNumber() 
	{
		return m_atom.getAtomNum();
	}
	public String getSymbol() 
	{
		return m_atom.getAtomSymbol();
	}
	public void setAtom(Atom atom) 
	{
		m_atom = atom;
	}
}
