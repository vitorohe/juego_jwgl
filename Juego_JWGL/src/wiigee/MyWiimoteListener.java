package wiigee;


import org.newdawn.slick.SlickException;

import menu.MyBasicGameState;

import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;


public class MyWiimoteListener implements WiimoteListener {
	
	float prev_roll=0;
	float act_roll=0;
	private MyBasicGameState myBGS;
	private int sliding = 0;
	private short act_z;
	private float act_pitch;


	public void setMyBGS(MyBasicGameState myBGS) {
		this.myBGS = myBGS;
	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		
		if(arg0.isButtonUpJustPressed()){
			this.myBGS.menuOptionUp();
		}
		
		else if(arg0.isButtonDownJustPressed()){
			this.myBGS.menuOptionDown();
		}
		
		else if(arg0.isButtonAJustPressed()){
				this.myBGS.menuOptionEnter();
		}
		
		else if(arg0.isButtonBJustPressed()){
			if(!this.myBGS.toString().contains("MainMenu"))
				this.myBGS.menuOptionBButton();
		}
		
		else if(arg0.isButtonLeftJustPressed()){
			try {
				this.myBGS.menuOptionLeft();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		else if(arg0.isButtonRightJustPressed()){
			try {
				this.myBGS.menuOptionRight();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClassicControllerInsertedEvent(
			ClassicControllerInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClassicControllerRemovedEvent(
			ClassicControllerRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExpansionEvent(ExpansionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIrEvent(IREvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		
		act_roll=arg0.getOrientation().getARoll();
		act_z = arg0.getRawAcceleration().getZ();
		act_pitch = arg0.getOrientation().getAPitch();
				
//		if(act_roll>120)
//		System.out.println("act_roll:"+act_roll);
//		System.out.println("act_z:"+act_z);
//		System.out.println("act_pitch:"+act_pitch);
		
		if(act_z>180 && this.myBGS != null){
			this.myBGS.moveJump();
//			System.out.println("JUMP!");
		}
		if(act_roll<-20 && act_pitch < -10  && act_z <165 && this.myBGS != null){
			if(sliding < 0)
				sliding = 0;
			this.myBGS.moveSlide();
//			System.out.println("DESLIZ!");
		}
		if(act_roll>-10 && this.myBGS != null){
			if(sliding > 0){
				sliding = 0;
				this.myBGS.stopSlide();
			}
			
			this.myBGS.stopSlide();
//			System.out.println("No DESLIZ!");
		}
		if(act_roll<5 && act_roll>-5){
//			System.out.println("ESTOY QUIETO!");
		}
	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusEvent(StatusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}