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
 
public class AudioMenuState extends MyBasicGameState{
 
	private int stateId = 0;
   
    Image gear = null;

	private UnicodeFont ufont;

	private GameContainer gc;

	private StateBasedGame sb;

	private Image background;

    public AudioMenuState(int stateId)
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
    	
    	if(HungryRunnerGame.isMusic())
    		menu_list.add("Música: Sí");
    	else
    		menu_list.add("Música: No");
    	menu_list.add("Volumen: "+(int)(MainMenuState.getVolume()*10)+"/10");
    	menu_list.add("Atrás");
    	
    	color_menu_list = new ArrayList<Color>();
    	
    	color_menu_list.add(Color.yellow);
    	color_menu_list.add(Color.black);
    	color_menu_list.add(Color.black);
    	
    	actual_menu = 0;

        //--------------------------------------------------
    	
    	
    	gear = new Image("testdata/audio.png");
    	
        //--------------------------------------------------

    	setFont();
    	
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);
        HungryRunnerGame.setMyBGStoWiimoteListener(this);
        System.out.println("Audio Menu entered");
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
    	ufont.drawString(HungryRunnerGame.getWidth()/2-60, HungryRunnerGame.getHeight()/2-100, menu_list.get(0), color_menu_list.get(0));
    	ufont.drawString(HungryRunnerGame.getWidth()/2-90, HungryRunnerGame.getHeight()/2-50, menu_list.get(1), color_menu_list.get(1));
    	ufont.drawString(HungryRunnerGame.getWidth()/2-30, HungryRunnerGame.getHeight()/2, menu_list.get(2), color_menu_list.get(2));
  
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
        	
		else if(input .isKeyPressed(Input.KEY_LEFT))
			this.menuOptionLeft();
			
		else if(input .isKeyPressed(Input.KEY_RIGHT))
			this.menuOptionRight();
						
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
    		if(MainMenuState.isPlaying()){
				MainMenuState.stop();
				menu_list.set(0, "Música: No");
				HungryRunnerGame.setMusic(false);
    		}
			else{	
				MainMenuState.play();
				MainMenuState.getVolume();
				menu_list.set(0, "Música: Sí");
				HungryRunnerGame.setMusic(true);
			}
    		
    		break;
    	}
    	case 1:{
    		break;
    	}
    	case 2:this.sb.enterState(HungryRunnerGame.OPTIONSMENUSTATE);break;
    	}
    }
    
    @Override
    public void menuOptionBButton(){
    	this.sb.enterState(HungryRunnerGame.OPTIONSMENUSTATE);
    }
    
    @Override
    public void menuOptionLeft(){
    	
    	float plusVolume = -0.1f;
    	
		if(actual_menu == 1){
			System.out.println("Volume pre left key: "+MainMenuState.getVolume());
			if(MainMenuState.getVolume() <= 0f)
				MainMenuState.setVolume(0f);
			else{
				setNewVolume(plusVolume);
			}
		}
    	
    }

    
    @Override
    public void menuOptionRight(){
    	float plusVolume = 0.1f;
    	
		if(actual_menu == 1){
			System.out.println("Volume pre right key: "+MainMenuState.getVolume());
			if(MainMenuState.getVolume() == 1.0f)
				MainMenuState.setVolume(1.0f);
			else{
				setNewVolume(plusVolume);
			}
		}
    }
    
	private void setNewVolume(float plusVolume) {
		MainMenuState.setVolume((float)Math.round((MainMenuState.getVolume()+plusVolume)*10)/10);
		menu_list.set(1, "Volumen: "+ Math.round(MainMenuState.getVolume()*10)+"/10");
	}

}