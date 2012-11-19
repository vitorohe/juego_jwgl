package hungry_runner;


import java.io.IOException;
import java.util.ArrayList;

import menu.MyBasicGameState;
import metrics.Chronometer;
import metrics.Fat;
import metrics.Life;
import metrics.Water;

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

import stage.Obstacle;
import stage.RectangleSet;
import stage.Stage;
 
public class GameplayState extends MyBasicGameState{
	
	//window setting
    private static int WIDTH;
	private static int HEIGHT;  
    
    private Rectangle ground;
    private Rectangle runnerSliding;
    private Rectangle runnerRunning;
    private Rectangle runner;
	
	float t = 0, dt= 0.5f;
	private boolean jumping = false;
	private float runnerY;
	private Audio aifEffect;
	private float base;
	private boolean falling = false;
	private float posY;
	private int key_space_pressed = 0;
	private Image background;
	private Image mountain1;
	private Image mountain2;
	private Image mountain3;
	private Image mountain4;
	private float mountain_x1;
	private float mountain_x2;
	private float mountain_x3;
	private float mountain_x4;
	private int stateId=0;
	
	private boolean wiimote_rolling;
	private UnicodeFont ufont;
	private SpriteSheet sprites;
	private Animation runnerAnimation;
	private Image image_runner_slide;
	private Stage stage1;
	private Stage stage2;
	private Stage stage3;
	private ArrayList<Stage> stages;
	private int stage_number;
	private float delta_speed;
	private Life life;
	private int lives;
	private Water water;
	private int delta_metrics;
	private Fat fat;
	private Chronometer chronometer;
	private StateBasedGame sb;
 
    public GameplayState(int stateId)
    {
		HEIGHT = HungryRunnerGame.getHeight();
		WIDTH = HungryRunnerGame.getWidth();
		this.stateId = stateId;
		this.chronometer = new Chronometer();
    }
	

    @Override
    public int getID() {
        return stateId;
    }
	
	@Override
    public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		HEIGHT = HungryRunnerGame.getHeight();
		WIDTH = HungryRunnerGame.getWidth();
					
		delta_metrics = 0;
		
		/* img */
		background = new Image("testdata/background_game.png");
		background = background.getScaledCopy(WIDTH, HEIGHT);
		mountain1 = new Image("testdata/mountain.png");
		mountain2 = new Image("testdata/mountain.png");
		mountain3 = new Image("testdata/mountain.png");
		mountain4 = new Image("testdata/mountain.png");
		//new Rectangle(WIDTH/2-320/2,50,320,150);

		ground = new Rectangle(0, HEIGHT-50, WIDTH, 50);
		runnerRunning = new Rectangle(100, HEIGHT-50, 50, 100);
		runnerSliding = new Rectangle(100, HEIGHT-50, 110, 40);
		
		/* stages */
		stage1 = new Stage(RectangleSet.getStage1());
		stage1.setDrawing(true);
		stage_number = 0;
		stage2 = new Stage(RectangleSet.getStage2());
		stage3 = new Stage(RectangleSet.getStage3());
		
		stages = new ArrayList<Stage>();
		stages.add(stage1);
		stages.add(stage2);
		stages.add(stage3);
		/* stages */
		
		/* metrics */
		lives = HungryRunnerGame.getLives();
		life = new Life(lives,"testdata/PixelHeart.png"); 
		
		if(HungryRunnerGame.getWater() == 0)
			HungryRunnerGame.setWater(100);
		
		water = new Water(HungryRunnerGame.getWater());
		
		if(HungryRunnerGame.getFat() == 100)
			HungryRunnerGame.setFat(0);
		
		fat = new Fat(HungryRunnerGame.getFat());
		/* metrics */
		
		mountain_x1 = 50;
		mountain_x2 = 570;
		mountain_x3 = -350;
		mountain_x4 = 350;
		runner = runnerRunning;
		
		base = HEIGHT-50-runner.getHeight();
		runnerY = HEIGHT-50-runner.getHeight();
		
		runner.setY(runnerY);
		
		try {
			aifEffect = AudioLoader.getAudio("AIF", ResourceLoader.getResourceAsStream("testdata/boip.aif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//seting font
		setFont();

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
        this.sb = sb;
        delta_metrics = 0;
        HungryRunnerGame.setFat(0);
        HungryRunnerGame.setWater(100);
        HungryRunnerGame.setLives(5);
        HungryRunnerGame.setMyBGStoWiimoteListener(this);
        this.init(gc, sb);
        System.out.println("Gameplay entered");

    }
	
	@Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		background.draw(0,0);
    	mountain1.draw(mountain_x1,HEIGHT-50-mountain1.getHeight());
    	mountain2.draw(mountain_x2,HEIGHT-50-mountain2.getHeight());
    	mountain3.draw(mountain_x3,HEIGHT-50-mountain3.getHeight()*1.2f, 1.2f);
    	mountain4.draw(mountain_x4,HEIGHT-50-mountain4.getHeight()*0.8f, 0.8f);
    	
    	/* metrics */
    	life.draw(g);
    	water.draw(g);
    	fat.draw(g);
    	/* metrics */
    	
    	/* stages */
    	g.setColor(Color.darkGray);
    	for(Stage stage: stages){
    		
    		//draw not finished stages
    		if(stage.isDrawing() && !stage.hasFinished()){
    			for(Obstacle obs: stage.getObstacles()){
    				obs.draw(g);
    			}
    		}
    		// change actual stage number and not continue drawing finished stage
    		if(stage.isDrawing() && stage.hasFinished()){
    			stage.setDrawing(false);
    			stage_number++;
    		}
    	}
    	
    	// draw next stage if actual one is finishing
    	if(stage_number < stages.size()){
    		if(stages.get(stage_number).isFinishing()){
    			if(stage_number < stages.size()-1){
    				stages.get(stage_number+1).setDrawing(true);
    			}
    		}
    	}
    	/* stages */
    	
    	g.setColor(Color.green);    	
    	g.draw(ground);
    	g.fill(ground);
    	   	
    	if(key_space_pressed == 0)
    		runnerAnimation.draw(this.runner.getX(), this.runner.getY()-36);
    	else{
    		image_runner_slide.draw(this.runner.getX(), this.runner.getY(), this.runner.getWidth()+10, this.runner.getHeight()+5);
    	}    	
    	
    	//drawing time
    	ufont.drawString(400, 0, chronometer.getTime(), Color.blue);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
        Input input = gc.getInput();
        input.enableKeyRepeat();
        
        delta_metrics += delta;
        
        if(delta_metrics%1000 == 0)
        	water.decreasePercentage();
        
        if(input.isKeyPressed(Input.KEY_ESCAPE))
        	menuOptionBButton();
        
        else if (input.isKeyPressed(Input.KEY_UP)) {
        	moveJump();
		}else if (!jumping && (input.isKeyDown(Input.KEY_SPACE) || wiimote_rolling)){
			
			if(key_space_pressed == 0){
				key_space_pressed = 1;
				runner = runnerSliding;
				runner.setX(runnerRunning.getX());
				runner.setY(runnerRunning.getY()+runnerRunning.getHeight()-runnerSliding.getHeight());
			}
			else{
				key_space_pressed += 1;
			}

        }else if (!jumping && (!input.isKeyPressed(Input.KEY_SPACE) || !wiimote_rolling)){//&& sliding == 0){
			key_space_pressed  = 0;
			if(runner.equals(runnerSliding))
				runner = runnerRunning;
		}
        if(jumping){
        	delta_speed = 1.5f;
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
        }else{
        	delta_speed = 1.2f;
        }
        
        // crashing with obstacle
        int intersecting = 0;
        for(Stage stage: stages){
        	if(stage.isDrawing()){
		        for(Obstacle obs: stage.getObstacles()){
		        	
		        	Rectangle rect = obs.crashes(runner);
		        	//intersects rectangle
		        	if(rect !=null){
		        		intersecting++;
		        		if((runner.getX() + runner.getWidth()) > rect.getX()){
		        			float k = (runner.getY() + runner.getHeight()) - rect.getY();
		        			
		        			
		        			// runner has intersected with water
		        			if(obs.getType() == Obstacle.WATER){
		        				if(obs.isDrawing()){
		        					delta_metrics = 0;
		        					obs.stopDrawing();
		        					fat.decreasePercentage();
		        					water.full();
		        				}
		        			}
		        			
		        			// runner has intersected with good food
		        			else if(obs.getType() == Obstacle.GOOD_FOOD){
		        				if(obs.isDrawing()){
		        					delta_metrics = 0;
		        					obs.stopDrawing();
		        					fat.decreasePercentage();
		        					water.increasePercentage();
		        				}
		        			}
		        			
						// complete stage
		        			else if(obs.getType() == Obstacle.GOAL){
		        				this.sb.enterState(HungryRunnerGame.MAINMENUSTATE);
		        			}

		        			// runner has intersected with bad food
		        			else if(obs.getType() == Obstacle.BAD_FOOD){
		        				if(obs.isDrawing()){
		        					delta_metrics = 0;
		        					obs.stopDrawing();
		        					fat.increasePercentage();
		        					water.decreasePercentage();
		        				}
		        			}
		        			
		        			// runner has intersected with a platform, over it
		        			else if((k >= 0 && k < 10) && obs.getType() == Obstacle.PLATFORM){
		            			resetJump();
		            			base = rect.getY() - runner.getHeight();
		            			runner.setY(base);
		            		}
		            		// runner has crashed with a platform or a dangerous one (cat/dog/cosa puntuda)
		            		else{
		            			HungryRunnerGame.setLives(lives-1);
		            		}
		        			checkLives(gc, sb);
		        			HungryRunnerGame.setFat(fat.getPercentage());
        					HungryRunnerGame.setWater(water.getPercentage());
		        		}
		        	}
		        	
		        	obs.changeX(-delta_speed);
		        }
        	}
        }    
        // falling
        if(!runner.intersects(ground) && intersecting==0 && !jumping){
        	jumping = true;
        	falling = true;
        }
        

        // move mountains
        
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
        
	}
  
    public float getY(float t, float height, float velocity){
    	return (0.5f*(-0.5f)*t*t + velocity*t + height);
	}
    
    public float falling(float height, float t){
    	return (0.5f*(-0.2f)*t*t + height);
    }
    
    public void resetJump(){
    	jumping = false;
    	t = 0;
    }

    public void checkLives(GameContainer gc,StateBasedGame sb) throws SlickException{
    	if(HungryRunnerGame.getLives() == 0)
			sb.enterState(HungryRunnerGame.GAMEOVERSTATE, new FadeOutTransition(), new FadeInTransition());
		else if(fat.isFull()||water.isEmpty() || lives > HungryRunnerGame.getLives()){
			key_space_pressed  = 0;
			delta_metrics = 0;
			this.init(gc, sb);
		}
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
