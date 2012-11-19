package menu;

import hungry_runner.HungryRunnerGame;
import hungry_runner.Resolution;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
 
public class VideoMenuState extends MyBasicGameState{
 
    private int stateId = 0;
    private int resolution_numberid; 
    Image gear = null;
     
    int score = 0;
 
    int deltaCounter = 500;
    int inputDelta = 0;

    List<Resolution> resolutions = new ArrayList<Resolution>();
    
	private UnicodeFont ufont;
	private GameContainer gc;
	private StateBasedGame sb;
 
    public VideoMenuState(int stateId)
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
    	System.out.println("Video menu");
    	
    	
    	//--------Menu management---------------------------
    	
//    	HungryRunnerGame.setMyBGStoWiimoteListener(this);
    	
    	menu_list = new ArrayList<String>(); 
    	
    	
    	if(HungryRunnerGame.isFullscreen())
    		menu_list.add("Pantalla completa: Sí");
    	else
    		menu_list.add("Pantalla completa: No");
    	menu_list.add("Resolución: "+HungryRunnerGame.getWidth()+"x"+HungryRunnerGame.getHeight());
    	menu_list.add("Atrás");
    	
    	color_menu_list = new ArrayList<Color>();
    	
    	color_menu_list.add(Color.blue);
    	color_menu_list.add(Color.yellow);
    	color_menu_list.add(Color.yellow);

    	actual_menu = 0;

        //--------------------------------------------------
    	
    	gear = new Image("testdata/resolution.png");
    	
        //--------------------------------------------------
    	         
		String fontPath = "res/Origin-Bold.ttf";
		try {
			ufont = new UnicodeFont(fontPath , 20, false, false); //Create Instance
			ufont.addAsciiGlyphs();   //Add Glyphs
			ufont.addGlyphs(400, 600); //Add Glyphs
			ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); //Add Effects
			ufont.loadGlyphs();  //Load Glyphs
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		resolutions.add(new Resolution(800,600));
		resolutions.add(new Resolution(1024,768));
		resolutions.add(new Resolution(1280,600));
		resolutions.add(new Resolution(1280,720));
		resolutions.add(new Resolution(1280,768));
		resolutions.add(new Resolution(1360,768));
		resolutions.add(new Resolution(1366,768));
	
    	switch (HungryRunnerGame.getWidth()){
    	case 800:resolution_numberid=0;break;
    	case 1024:resolution_numberid=1;break;
    	case 1280:
    		switch(HungryRunnerGame.getHeight()){
    		case 600:resolution_numberid=2;break;
    		case 720:resolution_numberid=3;break;
    		case 768:resolution_numberid=4;break;
    		}
    		break;
    	case 1360:resolution_numberid=6;break;
    	case 1366:resolution_numberid=7;break;
    	}
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);
        HungryRunnerGame.setMyBGStoWiimoteListener(this);
        System.out.println("Video Menu entered");
    }
 
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
       // Paint HUD
       gear.draw(50,50);
       ufont.drawString(HungryRunnerGame.getWidth()/2-130, HungryRunnerGame.getHeight()/2-100, menu_list.get(0), color_menu_list.get(0));
       ufont.drawString(HungryRunnerGame.getWidth()/2-120, HungryRunnerGame.getHeight()/2-50, menu_list.get(1), color_menu_list.get(1));
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
        
		else if(input .isKeyPressed(Input.KEY_RIGHT))
			this.menuOptionRight();
		
		else if(input .isKeyPressed(Input.KEY_LEFT))
			this.menuOptionLeft();
		
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
    		HungryRunnerGame.setFullscreen(!HungryRunnerGame.isFullscreen());
	    	if(HungryRunnerGame.isFullscreen()){
	    		menu_list.set(0,"Pantalla completa: Sí");
	    	}
	    	else{
	    		menu_list.set(0,"Pantalla completa: No");
	    	}
			break;
    	}
    	case 1:{
    		System.out.println("enter 1");
    		break;
    	}
    	case 2:System.out.println("enter 2");sb.enterState(HungryRunnerGame.OPTIONSMENUSTATE);break;
    	}
    }
    
    @Override
    public void menuOptionBButton(){
    	this.sb.enterState(HungryRunnerGame.OPTIONSMENUSTATE);
    }
    
    @Override
    public void menuOptionLeft(){
		if(actual_menu == 1 && (resolution_numberid > 0)){
			resolution_numberid -= 1;
			setAndDisplayResolution();
		}
    }
    
    @Override
    public void menuOptionRight(){
    	if(actual_menu == 1 && (resolution_numberid < resolutions.size()-1)){
			resolution_numberid += 1;
			setAndDisplayResolution();
		}
    }

	private void setAndDisplayResolution() {
		HungryRunnerGame.setWidth(resolutions.get(resolution_numberid).getWidth());
		HungryRunnerGame.setHeight(resolutions.get(resolution_numberid).getHeight());
		try {
			HungryRunnerGame.app.setDisplayMode(HungryRunnerGame.getWidth(), HungryRunnerGame.getHeight(), HungryRunnerGame.isFullscreen());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		menu_list.set(1, "Resolución: "+HungryRunnerGame.getWidth() +"x"+	HungryRunnerGame.getHeight());
	}
    
}