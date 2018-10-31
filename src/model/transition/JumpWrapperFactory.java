package model.transition;

import model.rank.LevelWrapper;
import response.AiJump;
import response.CiJump;
import response.ExJump;
import response.PiJump;
import response.RtJump;


public class JumpWrapperFactory 
{
//  	public static void main(String args[]) 
//  	{
//  		JumpWrapperFactory factory = new JumpWrapperFactory();
//  		LevelWrapper source = new LevelWrapper("Source");
//  		LevelWrapper target = new LevelWrapper("Target");
//  		Object o = new AiJump(); 
//  		factory.getJumpWrapper(ETransitionProcess.Autoionization,
//  				source, target, o);
//  	}

//  	public JumpWrapper getJumpWrapper(ETransitionProcess process, 
//  			LevelWrapper source, LevelWrapper target, Object o) 
//  	{
//  		JumpWrapper res = null;
//  		switch(process)
//  		{
//  		case Autoionization:
//  			AiJump aiJump = (AiJump)o;
//  			res = new AiJumpWrapper(source, target, aiJump);
//  			break;
//  		case Radiation:
//  			RtJump rtJump = (RtJump)o;
//  			res = new RtJumpWrapper(source, target, rtJump);
//  			break;
//  		case Excitation:
//  			ExJump exJump = (ExJump)o;
//  			res = new ExJumpWrapper(source, target, exJump);
//  			break;
//  		case Ionization:
//  			CiJump ciJump = (CiJump)o;
//  			res = new CiJumpWrapper(source, target, ciJump);
//  			break;
//  		case Photoionization:
//  			PiJump piJump = (PiJump)o;
//  			res = new PiJumpWrapper(source, target, piJump);
//  			break;
//  			default:
//  				break;
//  		}
//  		return res;
//  	}
  	
  	public JumpWrapper getJumpWrapper(ETransitionProcess process, 
  			LevelWrapper source, LevelWrapper target, Object o) 
  	{
  		JumpWrapper res = null;
  		switch(process)
  		{
  		case Autoionization:
  			AiJump aiJump = (AiJump)o;
  			res = new AiJumpWrapper(source, target, aiJump);
  			break;
  		case Radiation:
  			RtJump rtJump = (RtJump)o;
  			res = new RtJumpWrapper(source, target, rtJump);
  			break;
  		case Excitation:
  			ExJump exJump = (ExJump)o;
  			res = new ExJumpWrapper(source, target, exJump);
  			break;
  		case Ionization:
  			CiJump ciJump = (CiJump)o;
  			res = new CiJumpWrapper(source, target, ciJump);
  			break;
  		case Photoionization:
  			PiJump piJump = (PiJump)o;
  			res = new PiJumpWrapper(source, target, piJump);
  			break;
  			default:
  				break;
  		}
  		return res;
  	}

}