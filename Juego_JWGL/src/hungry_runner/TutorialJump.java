package hungry_runner;

import java.io.IOException;
import java.util.List;

import menu.MyBasicGameState;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

 
public class TutorialJump extends MyBasicGameState{
	
	//window setting
    private static int WIDTH;
	private static int HEIGHT;  
    
    private Rectangle ground;
    private Rectangle runnerSliding;
    private Rectangle runnerRunning;
    private Rectangle runner;
	private Rectangle platform;
	
	
	float t = 0, dt= 0.5f;
	private boolean jumping = false;
	private float runnerY;
	private Audio aifEffect;
	private float base;
	private boolean falling = false;
	private float posY;
	private List<Platform> platforms;
	private int plat_number;
	private int key_space_pressed = 0;
	private Image background;
	private Image mountain1;
	private Image mountain2;
	private Image mountain3;
	private Image mountain4;
	private Image tutorial_normal;
	private Image tutorial_jump;
	private float mountain_x1;
	private float mountain_x2;
	private float mountain_x3;
	private float mountain_x4;
	private int stateId=0;
	
	private UnicodeFont ufont;
	private SpriteSheet sprites;
	private Animation instrucciones_tutorial_jump;
	private Animation animation_tutorial_jump;
	private Animation runnerAnimation;
	private Image image_runner_slide;
	
	private int delta_introduccion_tutorial_jump=0;
	private int delta_tutorial_jump=0;
	private int delta_finish_tutorial_jump=0;
	private boolean dibujar_tutorial_jump;
	private boolean dibujar_instrucciones_tutorial_jump;
	private boolean finish_tutorial_jump;
	private boolean reinitiating;
	private boolean plat_x_extended;
	private boolean wiimote_rolling;
	private StateBasedGame sb;
	
    public TutorialJump(int stateId)
    {
		HEIGHT = HungryRunnerGame.getHeight();
		WIDTH = HungryRunnerGame.getWidth();
		this.stateId = stateId;
		reinitiating = false;
    }
	

    @Override
    public int getID() {
        return stateId;
    }
	
	@Override
    public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		HEIGHT = HungryRunnerGame.getHeight();
		WIDTH = HungryRunnerGame.getWidth();
				
		dibujar_tutorial_jump=false;
		dibujar_instrucciones_tutorial_jump=false;
		finish_tutorial_jump=false;
		
		/* img */
		background = new Image("testdata/background_game.png");
		background = background.getScaledCopy(WIDTH, HEIGHT);
		mountain1 = new Image("testdata/mountain.png");
		mountain2 = new Image("testdata/mountain.png");
		mountain3 = new Image("testdata/mountain.png");
		mountain4 = new Image("testdata/mountain.png");		
		tutorial_normal = new Image("testdata/control_normal.png");
		tutorial_jump = new Image("testdata/control_jump.png");
		//new Rectangle(WIDTH/2-320/2,50,320,150);

		ground = new Rectangle(0, HEIGHT-50, WIDTH, 50);
		runnerRunning = new Rectangle(100, HEIGHT-50, 50, 100);
		runnerSliding = new Rectangle(100, HEIGHT-50, 110, 40);
		
		
		/* platforms */
		platforms = new PlatformsSet(5, "bottom").getPlatforms();

		plat_number = 0;
		platform = platforms.get(plat_number).getRect();
				
		/* platforms */
		
		mountain_x1 = 50;
		mountain_x2 = 570;
		mountain_x3 = -350;
		mountain_x4 = 350;
		runner = runnerRunning;
		
		base = HEIGHT-50-runner.getHeight();
		runnerY = HEIGHT-50-runner.getHeight();
		
		runner.setY(runnerY);
		//platform.setY(HEIGHT-50-platform.getHeight());
		
		try {
			aifEffect = AudioLoader.getAudio("AIF", ResourceLoader.getResourceAsStream("testdata/boip.aif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//seting font
		setFont();

        animation_tutorial_jump=new Animation();
        animation_tutorial_jump.addFrame(tutorial_normal, 450);
        animation_tutorial_jump.addFrame(tutorial_jump, 450);
        
        sprites = new SpriteSheet("testdata/textos_tutorial01.png",300,150);
        instrucciones_tutorial_jump = new Animation(sprites,2000);
                
        //sprite for runner running
		sprites = new SpriteSheet("res/running21.png", 100,144);
		runnerAnimation = new Animation(sprites, 100);
		runnerAnimation.setAutoUpdate(true);
		runnerAnimation.setLooping(true);
		runnerAnimation.setCurrentFrame(0);
		  
		//image for runner sliding
		image_runner_slide = new Image("res/runnerSlide.png");
		
		key_space_pressed = 0;
    }
	
    @Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);
        this.init(gc, sb);
        this.sb = sb;
        delta_tutorial_jump = 0;
        delta_introduccion_tutorial_jump = 0;
        HungryRunnerGame.setMyBGStoWiimoteListener(this);
        System.out.println("TutorialJump entered");
    }
	
	@Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
    	background.draw(0,0);
    	mountain1.draw(mountain_x1,HEIGHT-50-mountain1.getHeight());
    	mountain2.draw(mountain_x2,HEIGHT-50-mountain2.getHeight());
    	mountain3.draw(mountain_x3,HEIGHT-50-mountain3.getHeight()*1.2f, 1.2f);
    	mountain4.draw(mountain_x4,HEIGHT-50-mountain4.getHeight()*0.8f, 0.8f);
    	
    	if(delta_tutorial_jump>6500 && !reinitiating){
    		if(plat_number == 0 && !plat_x_extended){
    			plat_x_extended = true;
    			platform.setX(HungryRunnerGame.getWidth()+400);
    			System.out.println("platform n�: "+plat_number+", data: "+platforms.get(plat_number).toString() + "-- x: "+platform.getX());
    		}
    		g.draw(platform);
        	g.fillRect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight(), new Image("res/brick.png"), 0, 0);

    	}else if(reinitiating){
    		if(plat_number == 0 && !plat_x_extended){
    			plat_x_extended = true;
    			platform.setX(HungryRunnerGame.getWidth());
    		}
    		g.draw(platform);
        	g.fillRect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight(), new Image("res/brick.png"), 0, 0);
    	}
    	
    	
    	g.setColor(Color.green);    	
    	g.draw(ground);
    	g.fill(ground);
    	g.setColor(Color.yellow);

    	   	
    	//title
    	
    	int x = HungryRunnerGame.getWidth()/2 - ufont.getWidth("TUTORIAL 1: JUMPING")/2;
    	ufont.drawString(x, 0, "TUTORIAL 1: JUMPING", Color.blue);
    	
    	if(dibujar_instrucciones_tutorial_jump)
    		instrucciones_tutorial_jump.draw(WIDTH/2-300/2,50);
    	
    	if(dibujar_tutorial_jump)
    		animation_tutorial_jump.draw(40,40);
    	
    	/*if(finish_tutorial_jump){
    		g.drawImage(image_finish_tutorial_jump,WIDTH/2-300/2,50);
    		
    	}*/
    	
    	if(key_space_pressed == 0)
    		runnerAnimation.draw(this.runner.getX(), this.runner.getY()-36);
    	else{
//    		System.out.println("w: "+runner.getWidth() + " h: "+runner.getHeight()); 
    		image_runner_slide.draw(this.runner.getX(), this.runner.getY(), this.runner.getWidth()+10, this.runner.getHeight()+5);
    	}    	
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
        
    	Input input = gc.getInput();
        input.enableKeyRepeat();
        
        delta_introduccion_tutorial_jump+=delta;
        delta_tutorial_jump+=delta;
        
//        System.out.println(finish_tutorial_jump +""+ delta_finish_tutorial_jump);
        
        if(finish_tutorial_jump && delta_finish_tutorial_jump>500){
			sb.enterState(HungryRunnerGame.TUTORIALSLIDE, new FadeOutTransition(), new FadeInTransition());
			return;
		}
        
        if(delta_introduccion_tutorial_jump>200)
        	dibujar_instrucciones_tutorial_jump=true;
        	
        
        if(delta_introduccion_tutorial_jump>8000)
        	dibujar_instrucciones_tutorial_jump=false;
        
       
        //para mostrar animacion movimiento del control
        if (delta_tutorial_jump>5500 && !reinitiating){
        	dibujar_tutorial_jump = true;
        }
        else if(delta_tutorial_jump>500 && reinitiating){
        	dibujar_tutorial_jump = true;
        }
        
        if (delta_tutorial_jump>9500 && !reinitiating){
        	dibujar_tutorial_jump = false;
        }
        else if(delta_tutorial_jump>4500 && reinitiating){
        	dibujar_tutorial_jump = false;
        }
        
        if(input.isKeyPressed(Input.KEY_ESCAPE))
        	menuOptionBButton();
        
        else if (input.isKeyPressed(Input.KEY_UP))
			moveJump();
		
        else if (!jumping && (input.isKeyDown(Input.KEY_SPACE) || wiimote_rolling)){
			
			if(key_space_pressed == 0){
				key_space_pressed = 1;
				runner = runnerSliding;
				runner.setX(runnerRunning.getX());
				runner.setY(runnerRunning.getY()+runnerRunning.getHeight()-runnerSliding.getHeight());
//				System.out.println("space down first");
			}
			else{
				key_space_pressed += 1;
//				System.out.println("space down next");
			}

        }else if (!jumping && (!input.isKeyPressed(Input.KEY_SPACE) || !wiimote_rolling)){//&& sliding == 0){
//			System.out.println("space not pressed");
			key_space_pressed  = 0;
			if(runner.equals(runnerSliding))
				runner = runnerRunning;
		}
        if(jumping){
        	if(!falling)
        		posY = getY(t, 1, 15);
        	else{
        		base = HEIGHT-ground.getHeight()-runner.getHeight();
        		posY = getY(t, HEIGHT-ground.getHeight()-runner.getHeight()-runner.getY(), 0);
        	}
        	runnerY = base - posY;
			t += dt;
			runner.setY(runnerY);
			if(runner.intersects(ground)){
				resetJump();
				base = HEIGHT-50-runner.getHeight();
				runner.setY(base);
				falling = false;
			}
        }
        
        //intersecting the platform
        
        if(delta_introduccion_tutorial_jump>6500 && runner.intersects(platform)){
        	if((runner.getX() + runner.getWidth()) > platform.getX()){
        		float k = (runner.getY() + runner.getHeight()) - platform.getY();
        		if(k >= 0 && k < 10){
        			resetJump();
        			base = platform.getY() - runner.getHeight();
        			runner.setY(base);
        		}else{
        			delta_tutorial_jump = 0;
        			key_space_pressed  = 0;
        			plat_number = 0;
        			reinitiating  = true;
        			plat_x_extended = false; 
        			this.init(gc, sb);
        		}
        	}
        }
        
        if(!runner.intersects(ground) && !runner.intersects(platform) && !jumping){
        	jumping = true;
        	falling = true;
//        	System.out.println("volando");
        }
        
        //platform reseting position
//        System.out.println("delta intro: "+delta_introduccion_tutorial_jump);
        if(delta_introduccion_tutorial_jump>6900){
        	
        	platform.setX(platform.getX() - 1.8f);
        	if(platform.getX() < -platform.getWidth()){
        		plat_number +=1;
        		
        		if(plat_number == platforms.size()){
        			plat_number--;
        			delta_finish_tutorial_jump+=delta;
        			finish_tutorial_jump=true;
        		}
        		else{
	        		platform = platforms.get(plat_number).getRect();
	        		platform.setX(WIDTH);
	        		
	        		if(platforms.get(plat_number).getType().equals("bottom")){
	//        			System.out.println(platform.getY());
	        			platform.setY(HEIGHT-50-platform.getHeight());
	        		}
	//        		System.out.println("x:"+platform.getX()+","+"x:"+platform.getY());
	        	
	//        		System.out.println("platform n�: "+plat_number+", data: "+platforms.get(plat_number).toString());
	        	
        		}
        	}
        }
        
        mountain_x1 -= 0.09f;
        mountain_x2 -= 0.09f;
        mountain_x3 -= 0.09f;
        mountain_x4 -= 0.09f;
        
        if(mountain_x1 < -mountain1.getWidth())
        	mountain_x1 = WIDTH+80;
        if(mountain_x2 < -mountain2.getWidth())
        	mountain_x2 = WIDTH+80;
        if(mountain_x3 < -mountain3.getWidth()*1.2f)
        	mountain_x3 = WIDTH;
        if(mountain_x4 < -mountain4.getWidth()*0.8f)
        	mountain_x4 = WIDTH+90*1.8f;
        
//        System.out.println(mountain_x1 +" "+ -mountain1.getWidth());
	}
  
    public float getY(float t, float height, float velocity){
    	return (0.5f*(-0.4f)*t*t + velocity*t + height);
	}
    
    public float falling(float height, float t){
    	return (0.5f*(-0.2f)*t*t + height);
    }
    
    public void resetJump(){
    	jumping = false;
    	t = 0;
    }
    
    @SuppressWarnings("unchecked")
	public void setFont(){
		String fontPath = HungryRunnerGame.getFont();
		try {
			ufont = new UnicodeFont(fontPath , 20, false, false); //Create Instance
			ufont.addAsciiGlyphs();   //Add Glyphs
			ufont.addGlyphs(400, 600); //Add Glyphs
			ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); //Add Effects
			ufont.loadGlyphs();  //Load Glyphs
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void menuOptionUp(){
    	
    }
    
    @Override
    public void menuOptionDown(){

    }
    
    @Override
    public void menuOptionEnter(){

    }
    
    @Override
    public void menuOptionBButton(){
    	this.sb.enterState(HungryRunnerGame.CHOOSELEVELSTATE);
    }
    
    @Override
    public void menuOptionLeft(){

    }
    
    @Override
    public void menuOptionRight(){

    }

	@Override
	public void moveJump() {
		this.jumping = true;
		if(!aifEffect.isPlaying())
			aifEffect.playAsSoundEffect(1.0f, 1.0f, false);
		
	}
    
    @Override
    public void moveSlide(){
    	wiimote_rolling = true;
    }
    
    @Override
    public void stopSlide(){			
    	wiimote_rolling = false;
    }
        
}
