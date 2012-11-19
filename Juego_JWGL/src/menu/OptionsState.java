package menu;

import hungry_runner.HungryRunnerGame;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
 
public class OptionsState extends MyBasicGameState{

    private int stateId = 0;
   
    Image gear = null;
    
    Sound blockFX = null;
 
    int score = 0;
 
    int deltaCounter = 500;
    int inputDelta = 0;

	private UnicodeFont ufont;

	private StateBasedGame sb;

	private Image background;
     
    public OptionsState(int stateId)
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
    	System.out.println("Options menu");
    	
    	background = new Image("testdata/background_game.png");

    	
    	//--------Menu management---------------------------
    	
//    	HungryRunnerGame.setMyBGStoWiimoteListener(this);
    	
    	menu_list = new ArrayList<String>(); 
    	
    	menu_list.add("Audio");
    	menu_list.add("Controles");
    	menu_list.add("Atrás");
    	
    	color_menu_list = new ArrayList<Color>();
    	
    	color_menu_list.add(Color.yellow);
    	color_menu_list.add(Color.black);
    	color_menu_list.add(Color.black);
    	
    	actual_menu = 0;
    	
        //--------------------------------------------------
    	
    	
    	gear = new Image("testdata/gear.png");
    	
        //--------------------------------------------------
    	         
    	setFont();

    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);
        HungryRunnerGame.setMyBGStoWiimoteListener(this);
        System.out.println("Options Menu entered");
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
    	gear.draw(50,50);
    	ufont.drawString(HungryRunnerGame.getWidth()/2-30, HungryRunnerGame.getHeight()/2-100, menu_list.get(0), color_menu_list.get(0));
    	ufont.drawString(HungryRunnerGame.getWidth()/2-50, HungryRunnerGame.getHeight()/2-50, menu_list.get(1), color_menu_list.get(1));
    	ufont.drawString(HungryRunnerGame.getWidth()/2-27, HungryRunnerGame.getHeight()/2, menu_list.get(2), color_menu_list.get(2));
  
    }
 
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {

        Input input = gc.getInput();
        
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
    	case 0:this.sb.enterState(HungryRunnerGame.AUDIOMENUSTATE);break;
    	case 1:this.sb.enterState(HungryRunnerGame.CONTROLLERMENUSTATE);break;
    	case 2:this.sb.enterState(HungryRunnerGame.MAINMENUSTATE);break;
    	}
    }
    
    @Override
    public void menuOptionBButton(){
    	this.sb.enterState(HungryRunnerGame.MAINMENUSTATE);
    }
    
    @Override
    public void menuOptionLeft(){
    	
    }
    
    @Override
    public void menuOptionRight(){
    	
    }
    
    @Override
	public void moveJump() {
		// TODO Auto-generated method stub
		
	}

}