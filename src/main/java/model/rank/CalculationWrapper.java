package model.rank;


import model.CompositeRankNode;
import model.Rank;
import response.Calc;


public class CalculationWrapper extends CompositeRankNode 
{
	 private Calc m_calc;

     public CalculationWrapper( String aName ) 
     {
        super(aName);
     }

     public boolean isLegalParent( Rank parentStr )
     {
        return false;
     }
     
 	public int getElementNumber() 	{ return m_calc.getAtom().getAtomNum(); }
	public int getEndIonNumber() 	{ return m_calc.getEndIonNum(); }
	public int getStartIonNumber() 	{ return m_calc.getStartIonNum(); }
	
	public CalculationWrapper setCalc(Calc calc) 					
	{ 
		m_calc = calc;
		return this;
	}
}