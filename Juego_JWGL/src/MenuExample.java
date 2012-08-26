import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class MenuExample {

	public enum State {
		INTRO, GAME, MAIN_MENU;
	}

	private static State state = State.INTRO;
	
	private void start() {

        try {
		    Display.setDisplayMode(new DisplayMode(800,600));
		    Display.create();
		} catch (LWJGLException e) {
		    e.printStackTrace();
		    System.exit(0);
		}
	 
		// init OpenGL here
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 600, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
	 
		while (!Display.isCloseRequested()) {
	 
		    
			while (Keyboard.next()) {
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
					if(state == State.INTRO)
						state = State.MAIN_MENU;
					else if(state == State.MAIN_MENU)
						state = State.GAME;
					else if(state == State.GAME)
						state = State.INTRO;
				}
			}
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			render();
	 
		    Display.update();
		}
	 
		Display.destroy();
	}

	
	public void render() {
		switch (state) {
		case INTRO:
			GL11.glColor3f(1.0f, 0.0f, 0.0f);
			GL11.glRectf(0, 0, 800, 600);
			break;
		case GAME:
			GL11.glColor3f(0.0f, 1.0f, 0.0f);
			GL11.glRectf(0, 0, 800, 600);
			break;
		case MAIN_MENU:
			GL11.glColor3f(0.0f, 0.0f, 1.0f);
			GL11.glRectf(0, 0, 800, 600);
			break;
		}
	}
	
	public void checkInput() {
		
	}
	
	public static void main(String[] args) {

		MenuExample dse = new MenuExample();
		dse.start();
		
	}
	
}
