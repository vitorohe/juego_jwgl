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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
 
public class ChooseLevelState extends MyBasicGameState{

    private int stateId = 0;
   
    Image gear = null;
    
    Sound blockFX = null;
 
    int score = 0;
 
    int deltaCounter = 500;
    int inputDelta = 0;

	private UnicodeFont ufont;

	private StateBasedGame sb;

	private Image background;

	private GameContainer gc;

	private Rectangle ground;
     
    public ChooseLevelState(int stateId)
    {
        this.stateId = stateId;
    }
 
    @Override
    public int getID() {
        return stateId;
    }
 
    @Override
    public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
    	System.out.println("Options menu");
    	
    	background = new Image("testdata/background_game.png");
    	ground = new Rectangle(0, HungryRunnerGame.getHeight()-50, HungryRunnerGame.getWidth(), 50);

    	   	
    	level_list = new ArrayList<Image>(); 
    	
    	level_list.add(new Image("testdata/selected_tutorial_level.png"));
    	
    	if(!HungryRunnerGame.isTutorial_played())    	
    		level_list.add(new Image("testdata/first_level_disabled.png"));
    	else
    		level_list.add(new Image("testdata/first_level.png"));
    	
    	level_list.add(new Image("testdata/second_level_disabled.png"));
    	
//    	menu_list = new ArrayList<String>();
//    	menu_list.add("Atrás");
//
//    	color_menu_list = new ArrayList<Color>();
//    	color_menu_list.add(Color.yellow);
    	
    	actual_menu = 0;
    	
    	setFont();

    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);
        this.gc = gc;
        HungryRunnerGame.setMyBGStoWiimoteListener(this);
        System.out.println("Options Menu entered");
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
    public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
    	background.draw(0,0);
    	g.setColor(Color.green);
    	g.draw(ground);
    	g.fill(ground);
    	
    	int d = 50;
    	
    	for(Image img: level_list){
    		img.draw(d,150,0.4f);
    		d +=50+img.getWidth()*0.4f;
    	}
    	
    	ufont.drawString(HungryRunnerGame.getWidth()/2-110, 30, "Elige el nivel a jugar", Color.blue);
    	
//    	ufont.drawString(HungryRunnerGame.getWidth()/2-110, 5*HungryRunnerGame.getHeight()/6-100, menu_list.get(0), color_menu_list.get(0));
    	
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
		
        else if(input.isKeyPressed(Input.KEY_LEFT))
        	this.menuOptionLeft();
		
        else if(input.isKeyPressed(Input.KEY_RIGHT))
        	this.menuOptionRight();
    	
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
    	case 0:this.sb.enterState(HungryRunnerGame.TUTORIALJUMP);break;
    	case 1:
    		if(HungryRunnerGame.isTutorial_played())
    			this.sb.enterState(HungryRunnerGame.GAMEPLAYSTATE);
    		break;
    	case 2:break;
    	}
    }
    
    @Override
    public void menuOptionBButton(){
    	this.sb.enterState(HungryRunnerGame.MAINMENUSTATE);
    }
    
    @Override
    public void menuOptionLeft() throws SlickException{
    	super.menuOptionLeft();
    }
    
    @Override
    public void menuOptionRight() throws SlickException{
    	super.menuOptionRight();
    }
    
    @Override
	public void moveJump() {
		
	}

}