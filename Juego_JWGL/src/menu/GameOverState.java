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
 
public class GameOverState extends MyBasicGameState{

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

	private Image logo;

	private Rectangle ground;
     
    public GameOverState(int stateId)
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
    	
    	logo = new Image("testdata/gameover.png");
    	background = new Image("testdata/background_game.png");
    	ground = new Rectangle(0, HungryRunnerGame.getHeight()-50, HungryRunnerGame.getWidth(), 50);

    	   	
    	menu_list = new ArrayList<String>(); 
    	
    	menu_list.add("Ir a Menú Principal");
    	menu_list.add("Salir");
    	
    	color_menu_list = new ArrayList<Color>();
    	
    	color_menu_list.add(Color.yellow);
    	color_menu_list.add(Color.black);
    	
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
    	logo.draw(HungryRunnerGame.getWidth()/2-logo.getWidth()/2,60);
    	ufont.drawString(HungryRunnerGame.getWidth()/2-110, 5*HungryRunnerGame.getHeight()/6-100, menu_list.get(0), color_menu_list.get(0));
    	ufont.drawString(HungryRunnerGame.getWidth()/2-30, 5*HungryRunnerGame.getHeight()/6-50, menu_list.get(1), color_menu_list.get(1));  
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
    	case 0:this.sb.enterState(HungryRunnerGame.MAINMENUSTATE);break;
    	case 1:this.gc.exit();
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
		
	}

}