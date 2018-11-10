package model;

import model.rank.CalculationWrapper;
import model.rank.ElementWrapper;
import model.rank.IonWrapper;
import model.rank.LevelWrapper;


public class RankChildFactory  
{

  public static int rankCounter_;
   
  public static Rank createChildRank( final String rankClassName, final String sNodeName) 
  {
	 Rank res = null;
	 System.out.println(rankClassName);

     if ( rankClassName.equals( CalculationWrapper.class.getName() ) )
     {
         res = new ElementWrapper(sNodeName);
     }
     else if ( rankClassName.equals( ElementWrapper.class.getName() ) )
     {
         res = new IonWrapper(sNodeName);
     }
     else if ( rankClassName.equals( IonWrapper.class.getName() ) ) 
     {
        res = new LevelWrapper( sNodeName  );
     }
     else 
     {
    	throw new IllegalArgumentException(" Not a valid rank " );
     }
     
     return res;
  }
  
  public static Rank createChildRank( String rankClassName) 
  {

	     if ( rankClassName.equals(ElementWrapper.class.getName()) ) {
	         return new IonWrapper("Ion " + rankCounter_++);
	     }
	     else
	     if ( rankClassName.equals( IonWrapper.class.getName() ) ) {
	        return new LevelWrapper( "# " + rankCounter_++  );
	     }
	     else throw new IllegalArgumentException(" Not a valid rank " );
	   
	     //return null; // for compiler
	  }
}





