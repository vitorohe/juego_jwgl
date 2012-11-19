package menu;

import hungry_runner.HungryRunnerGame;

import java.util.ArrayList;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;


 
public class MainMenuState extends MyBasicGameState{
 
	/* Menu fields */
	
	private GameContainer gc;
	private StateBasedGame sb;
	UnicodeFont ufont;
	private static Music wavEffect;
    Image background = null;
    Image startGameOption = null;
    Image exitOption = null;
 
    int stateID = 0;
 
    float startGameScale = 1;
    float exitScale = 1;
	private Image logo;
	private Rectangle ground;
 

	
 
    public MainMenuState(int stateID )
    {
        this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
    	
    	
    	logo = new Image("res/hungryrunner.png");
    	background = new Image("testdata/background_game.png");
    	ground = new Rectangle(0, HungryRunnerGame.getHeight()-50, HungryRunnerGame.getWidth(), 50);
    	
    	//--------Menu management---------------------------
    	
    	menu_list = new ArrayList<String>(); 
    	color_menu_list = new ArrayList<Color>();
    	
    	menu_list.add("Jugar");
     	menu_list.add("Opciones");
    	menu_list.add("Salir");
 		color_menu_list.add(Color.yellow);     	
    	color_menu_list.add(Color.black);
    	color_menu_list.add(Color.black);
    	
    	actual_menu = 0;
    	
        //--------------------------------------------------
 
    	wavEffect = new Music("testdata/intro.wav");
    	
    	if(HungryRunnerGame.isMusic())
    		play();        
        wavEffect.setVolume(HungryRunnerGame.getVolume());
        //--------------------------------------------------


        setFont();

    }
 
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);
        HungryRunnerGame.setMyBGStoWiimoteListener(this);
        System.out.println("Main Menu entered");
        this.init(gc, sb);
    }
    
    @SuppressWarnings("unchecked")
	public void setFont(){
		String fontPath = HungryRunnerGame.getFont();
		try {
			ufont = new UnicodeFont(fontPath , 25, false, false); //Create Instance
			ufont.addAsciiGlyphs();   //Add Glyphs
			ufont.addGlyphs(400, 600); //Add Glyphs
			ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); //Add Effects
			ufont.loadGlyphs();  //Load Glyphs
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }
 
    @Override
    public void render(GameContainer gac, StateBasedGame arg1, Graphics gr) throws SlickException {   	
    	background.draw(0,0);
    	gr.setColor(Color.green);
    	gr.draw(ground);
    	gr.fill(ground);
    	logo.draw(HungryRunnerGame.getWidth()/2-logo.getWidth()/2,60);
    	
    	ufont.drawString(HungryRunnerGame.getWidth()/2-37, 5*HungryRunnerGame.getHeight()/6-100, menu_list.get(0), color_menu_list.get(0));
    	ufont.drawString(HungryRunnerGame.getWidth()/2-60, 5*HungryRunnerGame.getHeight()/6-50, menu_list.get(1), color_menu_list.get(1));
        ufont.drawString(HungryRunnerGame.getWidth()/2-30, 5*HungryRunnerGame.getHeight()/6, menu_list.get(2), color_menu_list.get(2));	
    	        
    }

    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
        Input input = gc.getInput();
        this.gc = gc;
        this.sb = sb;
        
        if(input.isKeyPressed(Input.KEY_ESCAPE))
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
	    	case 0: this.sb.enterState(HungryRunnerGame.CHOOSELEVELSTATE,new EmptyTransition(),  new HorizontalSplitTransition());break;
	    	case 1: this.sb.enterState(HungryRunnerGame.OPTIONSMENUSTATE); break;
	    	case 2: this.gc.exit();
	   	}
    }
    
    @Override
    public void menuOptionBButton(){
    	this.gc.exit();
    }
    
    @Override
    public void menuOptionLeft(){
    	
    }
    
    @Override
    public void menuOptionRight(){
    	
    }

	public static float getVolume() {
		return wavEffect.getVolume();
	}

	public static void setVolume(float f) {
		wavEffect.setVolume(f);
		HungryRunnerGame.setVolume(f);		
	}

	public static boolean isPlaying() {
		return wavEffect.playing();
	}

	public static void stop() {
		wavEffect.stop();
	}

	public static void play() {
		wavEffect.loop(1.0f, getVolume());
	}
}