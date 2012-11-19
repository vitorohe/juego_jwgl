package menu;

import java.util.ArrayList;

import hungry_runner.HungryRunnerGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
 
public class ControllerMenuState extends MyBasicGameState{
 
	private int stateId = 0;
   
    Image gear = null;

	private UnicodeFont ufont;

	private GameContainer gc;

	private StateBasedGame sb;

	private Image background;

	private Image controles;

    public ControllerMenuState(int stateId)
    {
        this.stateId = stateId;
    }
 
    @Override
    public int getID() {
        return stateId;
    }
 
    @SuppressWarnings("unchecked")
	@Override
    public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
    	System.out.println("Audio menu");
    	
    	background = new Image("testdata/background_game.png");

    	
    	//--------Menu management---------------------------
    	
//    	HungryRunnerGame.setMyBGStoWiimoteListener(this);
    	
    	menu_list = new ArrayList<String>(); 
    	
    	menu_list.add("Atrás");
    	
    	color_menu_list = new ArrayList<Color>();
    	
    	color_menu_list.add(Color.yellow);
    	
    	actual_menu = 0;

        //--------------------------------------------------
    	
    	
    	gear = new Image("testdata/controllers_icon.png");
    	controles = new Image("testdata/controles.png");
    	
        //--------------------------------------------------
    	         
    	setFont();

    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);
        HungryRunnerGame.setMyBGStoWiimoteListener(this);
        System.out.println("Controller Menu entered");
    }
    
    public void setFont(){
		String fontPath = HungryRunnerGame.getFont();
		try {
			ufont = new UnicodeFont(fontPath , 25, false, false); //Create Instance
			ufont.addAsciiGlyphs();   //Add Glyphs
			ufont.addGlyphs(400, 600); //Add Glyphs
			ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); //Add Effects
			ufont.loadGlyphs();  //Load Glyphs
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
       // Paint HUD
    	background.draw(0,0);
    	gear.draw(50,50,0.5f);
    	controles.draw(HungryRunnerGame.getWidth()/2-(controles.getWidth()*0.8f)/2,150,0.8f);
    	ufont.drawString(HungryRunnerGame.getWidth()/2-27, HungryRunnerGame.getHeight()/2+100, menu_list.get(0), color_menu_list.get(0));
  
    }
 
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {

        Input input = gc.getInput();
        
        this.gc = gc;
        this.sb = sb;
		
        if(input .isKeyPressed(Input.KEY_BACK))
        	this.menuOptionBButton();
		
        else if(input.isKeyPressed(Input.KEY_ENTER))
        	this.menuOptionEnter();
						
        else if(input.isKeyPressed(Input.KEY_DOWN))
        	this.menuOptionDown();
        
        else if(input.isKeyPressed(Input.KEY_UP))
        	this.menuOptionUp();
    	
    }
    
    @Override
    public void menuOptionUp(){
    	super.menuOptionUp();
    }
    
    @Override
    public void menuOptionDown(){
    	super.menuOptionDown();
    }
    
    @Override
    public void menuOptionEnter(){
    	switch(actual_menu){
    	case 0:{
    		this.sb.enterState(HungryRunnerGame.OPTIONSMENUSTATE);break;
    	}
    	}
    }
    
    @Override
    public void menuOptionBButton(){
    	this.sb.enterState(HungryRunnerGame.OPTIONSMENUSTATE);
    }
    
    @Override
    public void menuOptionLeft(){
    	
    }

    
    @Override
    public void menuOptionRight(){
    	
    }

}