package hungry_runner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import menu.AudioMenuState;
import menu.ChooseLevelState;
import menu.ControllerMenuState;
import menu.GameOverState;
import menu.MainMenuState;
import menu.MyBasicGameState;
import menu.OptionsState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


import wiigee.MyWiimoteListener;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
 
/**
 *
 * @author Hungry Runner Developer Team
 *
 */
public class HungryRunnerGame extends StateBasedGame {
 
	private static int height;
	private static int width;
	public static AppGameContainer app = null;
	private static MyWiimoteListener wiimoteListener = null;
	private static boolean fullscreen;
	private static float volume;
	private static boolean music;
	private static int lives;
	private static boolean tutorial_played;
	private static float water;
	private static float fat;
	private static String font = "res/MAIAN.TTF";
	
	private static Properties prop;
		
	public static final int MAINMENUSTATE          = 0;
    public static final int RESOURCELOADERSTATE    = 1;
    public static final int GAMEPLAYSTATE          = 2;
    public static final int OPTIONSMENUSTATE       = 3;
	public static final int AUDIOMENUSTATE         = 4;
	public static final int CONTROLLERMENUSTATE    = 5;
	public static final int TUTORIALJUMP           = 6;
	public static final int TUTORIALSLIDE          = 7;
	public static final int GAMEOVERSTATE          = 8;
	public static final int CHOOSELEVELSTATE       = 9;
	private static Properties metrics;
		
	
 
    public HungryRunnerGame()
    {
        super("Hungry Runner");
 
        setProperties();
        
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.addState(new TutorialJump(TUTORIALJUMP));
        this.addState(new OptionsState(OPTIONSMENUSTATE));
        this.addState(new AudioMenuState(AUDIOMENUSTATE));
        this.addState(new ControllerMenuState(CONTROLLERMENUSTATE));
        this.addState(new TutorialSlide(TUTORIALSLIDE));
        this.addState(new GameOverState(GAMEOVERSTATE));
        this.addState(new ChooseLevelState(CHOOSELEVELSTATE));
        this.enterState(MAINMENUSTATE);
    }
 
    private void setProperties() {
       	prop = new Properties();
       	metrics = new Properties();
   	 
    	try {
            //load a properties file
    		prop.load(new FileInputStream("config.properties"));
 
            //get the property value
            height = Integer.parseInt(prop.getProperty("height"));
    		width = Integer.parseInt(prop.getProperty("width"));
    		fullscreen = Boolean.parseBoolean(prop.getProperty("fullscreen"));
    		volume = Float.parseFloat(prop.getProperty("volume"));
    		music = Boolean.parseBoolean(prop.getProperty("music"));
    		tutorial_played = Boolean.parseBoolean(prop.getProperty("tutorial_played"));
    		
    		metrics.load(new FileInputStream("metrics.properties"));
    		setWater(100);
    		setLives(5);
    		setFat(0);
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		
	}

	public static void main(String[] args) throws SlickException
    {
    	wiimoteListener = new MyWiimoteListener(); 
    	 	
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		
		if(wiimotes.length != 0){
			Wiimote wiimote = wiimotes[0];
			wiimote.activateIRTRacking();
			wiimote.activateMotionSensing();
			wiimote.activateContinuous();
			wiimote.addWiiMoteEventListeners(wiimoteListener);
		}
    	
        app = new AppGameContainer(new HungryRunnerGame());
      
		app.setDisplayMode(getWidth(), getHeight(), fullscreen);
		app.setTargetFrameRate(300);
		app.setFullscreen(fullscreen);
		app.setShowFPS(false);
		app.start();
    }
 
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
 
        this.getState(MAINMENUSTATE).init(gameContainer, this);
        this.getState(GAMEPLAYSTATE).init(gameContainer, this);
        this.getState(OPTIONSMENUSTATE).init(gameContainer, this);
 
    }
    
    public static void setMyBGStoWiimoteListener(MyBasicGameState myBGS){
    	wiimoteListener.setMyBGS(myBGS);
    }

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int _height) {
		height = _height;
		prop.setProperty("height", _height+"");
		saveProperties();
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int _width) {
		width = _width;
		prop.setProperty("width", _width+"");
		saveProperties();
	}
	
    public static boolean isFullscreen() {
		return fullscreen;
	}

	public static void setFullscreen(boolean _fullscreen) {
		fullscreen = _fullscreen;
		prop.setProperty("fullscreen", _fullscreen+"");
		saveProperties();
		
		try {
			app.setFullscreen(_fullscreen);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static float getVolume() {
		return volume;
	}

	public static void setVolume(float _volume) {
		volume = _volume;
		prop.setProperty("volume", _volume+"");
		saveProperties();
	}

	public static boolean isMusic() {
		return music;
	}

	public static void setMusic(boolean _music) {
		music = _music;
		prop.setProperty("music", _music+"");
		saveProperties();
	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int _lives) {
		lives = _lives;
		prop.setProperty("lives", _lives+"");
		saveMetrics();
	}

	public static boolean isTutorial_played() {
		return tutorial_played;
	}

	public static void setTutorial_played(boolean _tutorial_played) {
		tutorial_played = _tutorial_played;
		prop.setProperty("tutorial_played", _tutorial_played+"");
		saveProperties();
	}

	public static float getWater() {
		return water;
	}

	public static void setWater(float _water) {
		water = _water;
		metrics.setProperty("water", _water+"");
		saveMetrics();
	}

	public static float getFat() {
		return fat;
	}

	public static void setFat(float _fat) {
		fat = _fat;
		metrics.setProperty("fat", _fat+"");
		saveMetrics();
	}

	private static void saveProperties() {
		try {
			prop.store(new FileOutputStream("config.properties"), null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static void saveMetrics() {
		try {
			metrics.store(new FileOutputStream("metrics.properties"), null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static String getFont() {
		return font;
	}   
 
}