package converter;

import fac2db.level.ILevelState;
import fac2db.record.ELevelFields;
import fac2db.record.LevelRecord;

public class FacConfigurationConverter 
{

	  public static String convert(final ILevelState level)
	  {
		  String sRes = null; 
		  LevelRecord record = level.toRecord();
		  String sComplex = record.get(ELevelFields.complex);
		  String sNrelConf = record.get(ELevelFields.nrel_config);
		  int iElement = Integer.parseInt(record.get(ELevelFields.el_number));
		  int iIon = Integer.parseInt(record.get(ELevelFields.ion_number));
		  int iElectrons = iElement - iIon + 1;

		  if(((iElectrons == 19) && 
				  (sComplex.equals("1*2 2*8 3*9")) && 
				  (sNrelConf.endsWith("1"))))
		  {
			  sRes = "3p6 " + sNrelConf;
		  }		  
		  
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

		  if((iElectrons == 9) && 
				  (sComplex.equals("1*1 2*8")) && 
				  (sNrelConf.endsWith("1")))
		  {
			  sRes = sNrelConf + " 2s2";
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
		  
		  if((iElectrons == 6) && 
				  (sComplex.equals("1*2 2*2 3*2")) && 
				  (sNrelConf.endsWith("2")))
		  {
			  sRes = "2s2 " + sNrelConf;
		  }
		  
			if((iElectrons == 6) && 
				  (sComplex.equals("2*6")) && 
				  (sNrelConf.endsWith("4")))
		  {
			  sRes = "2s2 " + sNrelConf;
		  }

			if((iElectrons == 6) && 
				  (sComplex.equals("2*6")) && 
				  (sNrelConf.endsWith("6")))
		  {
			  sRes = "2s0 " + sNrelConf;
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
						sComplex.equals("1*2 2*2 5*1") ||
						sComplex.equals("1*2 2*2 6*1")) && 
				  (sNrelConf.endsWith("1")))
		  {
			  sRes = "2s2 " + sNrelConf;
		  }
		  
		  if(iElectrons == 5 && 
			 sComplex.equals("2*5") && 
				  sNrelConf.endsWith("3"))
		  {
			  sRes = "1s2 " + sNrelConf;
		  }
			  
		  if(iElectrons == 5 && 
			 sComplex.equals("2*5") && 
				  sNrelConf.endsWith("5"))
		  {
			  sRes = "1s0 " + sNrelConf;
		  }
		  

		  if(iElectrons == 4 && 
				  (sComplex.equals("1*2 3*2") ||
				  sComplex.equals("1*2 2*2")))
		  {
			  sRes = "1s2 " + sNrelConf;
		  }
		  
   		  if(iElectrons == 4 && 
   			  sComplex.equals("2*4") &&  
   				sNrelConf.endsWith("2") )
		  {
			  sRes = "2s2 " + sNrelConf;
		  }
  		  if(iElectrons == 4 && 
			  sComplex.equals("2*4") &&  
				sNrelConf.endsWith("4") )
		  {
			  sRes = "2s0 " + sNrelConf;
		  }
  		  
		  if(iElectrons == 3 && 
				  (sComplex.equals("1*2 2*1") || 
					sComplex.equals("1*2 3*1") || 
					sComplex.equals("1*2 4*1")  || 
					sComplex.equals("1*2 5*1")  || 
					sComplex.equals("1*2 6*1") ) && 
						sNrelConf.endsWith("1"))
		  {
			  sRes = "1s2 " + sNrelConf;
		  }
		  
		  if(iElectrons == 3 && 
				  sComplex.equals("1*1 2*2"))
		  {
			  sRes = sNrelConf + " 2s2";
		  }
		  if(iElectrons == 3 && 
				  sComplex.equals("1*1 3*2"))
		  {
			  sRes = sNrelConf + " 3s2";
		  }
		  if(iElectrons == 3 && 
				  sComplex.equals("1*1 4*2"))
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
		  
          if(iElectrons == 3 && 
			  (sComplex.equals("2*2 3*1") ||  
				sComplex.equals("2*2 4*1")  || 
				sComplex.equals("2*2 5*1") || 
				sComplex.equals("2*2 6*1")) && 
					sNrelConf.endsWith("1"))
		  {
			  sRes = "2s2 " + sNrelConf ;
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
		  
		  if(iElectrons == 1 && 
				  (sComplex.equals("2*1") ||
				  sComplex.equals("3*1") || 
				  sComplex.equals("4*1")  || 
				  sComplex.equals("5*1") ||
				  sComplex.equals("6*1")))
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
