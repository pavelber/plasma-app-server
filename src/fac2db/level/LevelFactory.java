package fac2db.level;


import java.util.Map;


final class LevelFactory
{
    private static void fillLevelState(
            Map<ELevelProperty, String> properties, final LevelState level,
            final int iPrId, final int iElNum, final int iIonNum)
    {
    	level.setBaseIndex(Integer.parseInt(properties.get(ELevelProperty.BASE_INDEX)));
    	level.setComplexName(properties.get(ELevelProperty.COMPLEX_NAME));
    	level.setEnergy(Double.parseDouble(properties.get(ELevelProperty.ENERGY)));
    	
    	String sNrConf = properties.get(ELevelProperty.NON_RELATIVISTIC_NAME);
    	level.setNonRelativsticName(sNrConf);
    	
      	String[] tokens = sNrConf.split("\\s+");
      	if(tokens.length == 1)
      	{
      		String sConf = convert(level, iElNum, iIonNum);
      	    level.setConfiguration(sConf); 
      	}
      	else
      	{
      	    level.setConfiguration(sNrConf);
      	}

    	level.setNumber(Integer.parseInt(properties.get(ELevelProperty.NUMBER)));
    	level.setParity(Integer.parseInt(properties.get(ELevelProperty.PARITY)));
    	level.setRelativsticName(properties.get(ELevelProperty.RELATIVISTIC_NAME));
    	level.setStatisticalWeight(Integer.parseInt(properties.get(ELevelProperty.STATISTICAL_WEIGHT)));////////////////////////
    	level.setVnl(Integer.parseInt(properties.get(ELevelProperty.VNL)));
    	level.setProjectNumber(iPrId);
    	level.setElementNumber(iElNum);
    	level.setIonNumber(iIonNum);
   }
    
    final LevelState createLevel(
            final Map<ELevelProperty, String> properties, 
            final int iPrId, final int iElNum, final int iIonNum)
    {
        final LevelState result = new LevelState();
        fillLevelState(properties, result,
        		iPrId, iElNum, iIonNum);
        return result;
    }
    
    private static String convert(final ILevelState level, final int iElNum, final int iIonNum)
    {
  	  String sRes = null; 
  	  String sComplex = level.getComplexName();
  	  String sNrelConf = level.getNonRelativsticName();

  	  int iElectrons = iElNum - iIonNum + 1;
  	  
  	  if(((iElectrons == 18) && 
  			  (sComplex.equals("1*2 2*8 3*8")) && 
  			  (sNrelConf.endsWith("6"))))
  	  {
  		  sRes = "3s2 " + sNrelConf;
  	  }
  	  if(((iElectrons == 17) && 
  			  (sComplex.equals("1*1 2*8 3*8")) && 
  			  (sNrelConf.endsWith("1"))))
  	  {
  		  sRes = sNrelConf + " 2s2";
  	  }
  	  if(((iElectrons == 17) && 
  			  (sComplex.equals("1*2 2*7 3*8")) && 
  			  (sNrelConf.endsWith("1"))))
  	  {
  		  sRes = sNrelConf + " 3s2";
  	  }
  	  if(((iElectrons == 17) && 
  			  (sComplex.equals("1*2 2*7 3*8")) && 
  			  (sNrelConf.endsWith("5"))))
  	  {
  		  sRes = sNrelConf + " 3s2";
  	  }
  	  if(((iElectrons == 17) && 
  			  (sComplex.equals("1*2 2*8 3*7")) && 
  			  (sNrelConf.endsWith("5"))))
  	  {
  		  sRes = "3s2 " + sNrelConf;
  	  }
  	  if(((iElectrons == 17) && 
  			  (sComplex.equals("1*2 2*8 3*7")) && 
  			  (sNrelConf.endsWith("1"))))
  	  {
  		  sRes = sNrelConf + " 3p6";
  	  }
  	  if(((iElectrons == 16) && 
  			  (sComplex.equals("2*8 3*8")) && 
  			  (sNrelConf.endsWith("6"))))
  	  {
  		  sRes = "1s0 " + sNrelConf;
  	  }
  	  if(((iElectrons == 16) && 
  			  (sComplex.equals("1*2 2*6 3*8")) && 
  			  (sNrelConf.endsWith("6"))))
  	  {
  		  sRes = "2s0 " + sNrelConf;
  	  }
  	  if(((iElectrons == 16) && 
  			  (sComplex.equals("1*2 2*6 3*8")) && 
  			  (sNrelConf.endsWith("4"))))
  	  {
  		  sRes = sNrelConf + " 3s2";
  	  }
  	  if(((iElectrons == 16) && 
  			  (sComplex.equals("1*2 2*8 3*6")) && 
  			  (sNrelConf.endsWith("6"))))
  	  {
  		  sRes = "3s0 " + sNrelConf;
  	  }
  	  if(((iElectrons == 16) && 
  			  (sComplex.equals("1*2 2*8 3*6")) && 
  			  (sNrelConf.endsWith("4"))))
  	  {
  		  sRes = "3s2 " + sNrelConf;
  	  }
  	  if(((iElectrons == 15) && 
  			  (sComplex.equals("1*2 2*8 3*5"))))
  	  {
  		  if(sNrelConf.endsWith("3"))
  		  {
  			  sRes = "3s2 " + sNrelConf;
  		  }
  		  else if(sNrelConf.endsWith("5"))
  		  {
  			  sRes = "3s0 " + sNrelConf;
  		  }
  	  }
  	  if(((iElectrons == 15) && 
  			  (sComplex.equals("1*2 2*6 3*7")) && 
  			  (sNrelConf.endsWith("5"))))
  	  {
  		  sRes = "2s0 " + sNrelConf;
  	  }
  	  if(((iElectrons == 15) && 
  			  (sComplex.equals("2*8 3*7")) && 
  			  (sNrelConf.endsWith("5"))))
  	  {
  		  sRes = "1s0 " + sNrelConf;
  	  }
  	  if(((iElectrons == 14) && 
  			  (sComplex.equals("1*2 2*8 3*4")) && 
  			  (sNrelConf.endsWith("2"))))
  	  {
  		  sRes = "3s2 " + sNrelConf;
  	  }
  	  if(((iElectrons == 14) && 
  			  (sComplex.equals("1*2 2*8 3*4")) && 
  			  (sNrelConf.endsWith("4"))))
  	  {
  		  sRes = "3s0 " + sNrelConf;
  	  }
  	  if(((iElectrons == 14) && 
  			  (sComplex.equals("1*2 2*6 3*6")) && 
  			  (sNrelConf.endsWith("4"))))
  	  {
  		  sRes = "2s0 " + sNrelConf;
  	  }
  	  if(((iElectrons == 14) && 
  			  (sComplex.equals("2*8 3*6")) && 
  			  (sNrelConf.endsWith("4"))))
  	  {
  		  sRes = "1s0 " + sNrelConf;
  	  }
  	  if(((iElectrons == 13) && 
  			  (sComplex.equals("1*2 2*8 3*3")) && 
  			  (sNrelConf.endsWith("1"))) ||
  			  ((iElectrons == 13) && 
  			  (sComplex.equals("1*2 2*8 3*2 4*1")) && 
  			  (sNrelConf.endsWith("1"))))
  	  {
  		  sRes = "3s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 12) && 
  			  (sComplex.equals("1*2 2*8 3*2")) && 
  			  (sNrelConf.endsWith("2")))
  	  {
  		  sRes = "2p6 " + sNrelConf;
  	  }
  	  if((iElectrons == 11) && 
  			  (sComplex.equals("1*2 2*8 3*1") || 
  					  sComplex.equals("1*2 2*8 4*1")) && 
  				(sNrelConf.endsWith("1")))
  	  {
  		  sRes = "2p6 " + sNrelConf;
  	  }
  	  if((iElectrons == 11) && 
  			  (sComplex.equals("1*2 2*7 3*2")) && 
  			  (sNrelConf.endsWith("5")))
  	  {
  		  sRes = sNrelConf + " 3s2";
  	  }
  	  if((iElectrons == 10) && 
  			  (sComplex.equals("1*2 2*8")) && 
  			  (sNrelConf.endsWith("6")))
  	  {
  		  sRes = "2s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 9) && 
  			  (sComplex.equals("1*2 2*7")) && 
  			  (sNrelConf.endsWith("5")))
  	  {
  		  sRes = "2s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 9) && 
  			  (sComplex.equals("1*2 2*7")) && 
  			  (sNrelConf.endsWith("1")))
  	  {
  		  sRes = sNrelConf + " 2p6";
  	  }
  	  if((iElectrons == 8) && 
  			  (sComplex.equals("1*2 2*6")) && 
  			  (sNrelConf.endsWith("4")))
  	  {
  		  sRes = "2s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 8) && 
  			  (sComplex.equals("1*2 2*6")) && 
  			  (sNrelConf.endsWith("6")))
  	  {
  		  sRes = "1s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 7) && 
  			  (sComplex.equals("1*2 2*5")) && 
  			  (sNrelConf.endsWith("3")))
  	  {
  		  sRes = "2s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 7) && 
  			  (sComplex.equals("1*2 2*5")) && 
  			  (sNrelConf.endsWith("5")))
  	  {
  		  sRes = "1s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 6) && 
  			  (sComplex.equals("1*2 2*4")) && 
  			  (sNrelConf.endsWith("2")))
  	  {
  		  sRes = "2s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 6) && 
  			  (sComplex.equals("1*2 2*4")) && 
  			  (sNrelConf.endsWith("4")))
  	  {
  		  sRes = "1s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 5) && 
  			  (sComplex.equals("1*2 2*3")) && 
  			  (sNrelConf.endsWith("1")))
  	  {
  		  sRes = "2s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 5) && 
  			  (sComplex.equals("1*2 2*3")) && 
  			  (sNrelConf.endsWith("3")))
  	  {
  		  sRes = "1s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 5) && 
  			  (sComplex.equals("1*2 2*2 3*1") ||
  					sComplex.equals("1*2 2*2 4*1") ||
  					sComplex.equals("1*2 2*2 5*1")) && 
  			  (sNrelConf.endsWith("1")))
  	  {
  		  sRes = "2s2 " + sNrelConf;
  	  }
  	  if(iElectrons == 4 && (sComplex.equals("1*2 3*2") ||
  			  sComplex.equals("1*2 2*2")))
  	  {
  		  sRes = "1s2 " + sNrelConf;
  	  }
  	  if((iElectrons == 3) && 
  			  (sComplex.equals("1*2 4*1")) && 
  			  (sNrelConf.endsWith("1")))
  	  {
  		  sRes = "1s2 " + sNrelConf;
  	  }
  	  if(iElectrons == 3 && (sComplex.equals("1*2 2*1") ||
  			  sComplex.equals("1*2 3*1")))
  	  {
  		  sRes = "1s2 " + sNrelConf;
  	  }
  	  if(iElectrons == 3 && sComplex.equals("1*1 2*2"))
  	  {
  		  sRes = sNrelConf + " 2s2";
  	  }
  	  if(iElectrons == 3 && sComplex.equals("1*1 3*2"))
  	  {
  		  sRes = sNrelConf + " 3s2";
  	  }
  	  if(iElectrons == 3 && sComplex.equals("1*1 4*2"))
  	  {
  		  sRes = sNrelConf + " 4s2";
  	  }
  	  if((iElectrons == 3) && 
  			  (sComplex.equals("2*3")) && 
  			  (sNrelConf.endsWith("1")))
  	  {
  		sRes = "2s2 " + sNrelConf;
  		System.out.println(sRes);
  	  }
  	  if((iElectrons == 3) && 
  			  (sComplex.equals("2*3")) && 
  			  (sNrelConf.endsWith("3")))
  	  {
  		sRes = "2s0 " + sNrelConf;
  		System.out.println(sRes);
  	  }
  	  if(iElectrons == 2 && sComplex.equals("1*2"))
  	  {
  		  sRes = sNrelConf + " 2s0";
  	  }
  	  if(iElectrons == 2 && (sComplex.equals("2*2") ||
  			  sComplex.equals("3*2")))
  	  {
  		  sRes = "1s0 " + sNrelConf;
  	  }
  	  if(iElectrons == 1 && sComplex.equals("1*1"))
  	  {
  		  sRes = sNrelConf + " 2s0";
  	  }
  	  if(iElectrons == 1 && (sComplex.equals("2*1") ||
  			  sComplex.equals("3*1") || sComplex.equals("4*1")))
  	  {
  		  sRes = "1s0 " + sNrelConf;
  	  }
  	  if(iElectrons == 0)
  	  {
  		  sRes = "1s0";
  	  }
  	  return sRes;
    }
}
