package command;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bibeault.frontman.Command;
import org.bibeault.frontman.CommandContext;

import util.CrmConstants;
import util.FileConstants;
import db.sql.CrmDbManager;
import crm.CrmStructureBuilder;
import crm.CrmTransitionsBuilder;
import crm.model.state.Calculation;
import crm.model.state.Element;
import crm.model.transition.TransitionProcess;


public class DoCrmTransitionsCommand implements Command
{
    @Override
    public void execute(CommandContext commandContext) throws ServletException, IOException 
    {
        try 
        { 
            HttpServletResponse response = commandContext.getResponse();
            response.setContentType("text/xml");
            PrintWriter p = response.getWriter();
            HttpServletRequest request = commandContext.getRequest();

            String sCalcId = request.getParameter("calcId");
            String sAiStateInitialNumber = request.getParameter("aiStateInitialNumber");
            String sFileName = request.getParameter("fileName");
            int iCalcId = Integer.parseInt(sCalcId);
            int iAiStateInitialNumber = Integer.parseInt(sAiStateInitialNumber);
            makeFile(iCalcId, iAiStateInitialNumber, p, sFileName);
        } 
        catch(Throwable theException) 
        { 
          theException.printStackTrace(); 
        } 
    }
    
    private void makeFile(final int iCalcId, final int iAiStateInitialNumber, 
            final PrintWriter p, final String sFileName)
    {
        try 
        {
        	CrmDbManager factory = new CrmDbManager();
            Calculation calc = initParameters(factory, iCalcId, iAiStateInitialNumber);
            String directoryName = makeDir(calc.getName(), sFileName);
            
            CrmStructureBuilder sBuilder = new CrmStructureBuilder(calc, factory); 
            sBuilder.build();
            
            CrmTransitionsBuilder tBuilder = new CrmTransitionsBuilder(calc, factory);
            TransitionProcess process = setProcess(sFileName);
            tBuilder.setTransitionProcess(process);
            tBuilder.writeTransitionsFile(directoryName);
            
            p.println("<?xml version=\"1.0\"?>\n");
            p.println("<states>");
            p.println("<state>");
            
            long iSize = new File(directoryName + FileConstants.SEPARATOR + sFileName).length();
            
            System.out.println("size " + iSize);
            
            p.println("<size>" + iSize + "</size>");
            p.println("</state>");
            p.println("</states>");
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
    
    private Calculation initParameters(final CrmDbManager factory, 
    		final int iCalcId, final int iAiStateInitialNumber)
    {
        Calculation calc = factory.makeCalculation(iCalcId);
        calc.setAiStateInitialNumber(iAiStateInitialNumber);
        Element element = factory.makeElement(calc.getElementNumber());
        calc.addState(element);
        return calc;
    }
    
    private String makeDir(final String sCalcName, final String sFileName)
    {
       String dirName = FileConstants.HOME_DIR_NAME + FileConstants.SEPARATOR + 
                FileConstants.TOP_DIR_NAME + FileConstants.SEPARATOR + 
                FileConstants.USER_DIR_NAME + FileConstants.SEPARATOR + 
                sCalcName + FileConstants.SEPARATOR + 
                FileConstants.CRM_DIR_NAME;
       
       File dir = new File(dirName);
       boolean exists = (dir).exists();
       if (exists) 
       {
           // File or directory exists
           deleteFile(dir, sFileName);
       } 
       else 
       {
           // File or directory does not exist
           createDir(dirName);
       }
       return dirName;
    }
    
     public boolean deleteFile(File dir, final String sFileName) 
     {
         boolean res = false;
         if (dir.isDirectory()) 
         {
             String[] children = dir.list();
             for (int i=0; i<children.length; i++) 
             {
                 if(children[i].equals(sFileName))
                 {
                     boolean success = new File(dir, children[i]).delete();
                     if (success) 
                     {
                         res = true;
                         break;
                     }
                 }
             }
         }
         return res;
     }
     
     private boolean createDir(final String sDirName)
     {
      // Create a directory; all ancestor directories must exist
         boolean success = (new File(sDirName)).mkdir();
         if (!success) {
             // Directory creation failed
         }
         return success;
     } 
     
    private TransitionProcess setProcess(final String sFileName)
    {
        TransitionProcess process = null;
        
        if(CrmConstants.AI_FILE_NAME.equals(sFileName))
        {
            process = TransitionProcess.AUTOIONIZATION;
        }
        else if(CrmConstants.CI_FILE_NAME.equals(sFileName))
        {
            process = TransitionProcess.COLLISIONAL_IONIZATION;
        }
        else if(CrmConstants.EX_FILE_NAME.equals(sFileName))
        {
            process = TransitionProcess.EXCITATION;
        }
        else if(CrmConstants.I_FILE_NAME.equals(sFileName))
        {
            process = TransitionProcess.IONIZATION;
        }
        else if(CrmConstants.RT_FILE_NAME.equals(sFileName))
        {
            process = TransitionProcess.RADIATION;
        }
        else if(CrmConstants.PI_FILE_NAME.equals(sFileName))
        {
            process = TransitionProcess.PHOTOIONIZATION;
        }
        return process;
    }

}
