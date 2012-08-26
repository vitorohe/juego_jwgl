import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
 
public class TextureExample {
 
	/** The texture that will hold the image details */
	private Texture texture;
 
 
	/**
	 * Start the example
	 */
	public void start() {
		initGL(800,600);
		
 
		while (true) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			init();
			render();
 
			Display.update();
			Display.sync(100);
 
			if (Display.isCloseRequested()) {
				Display.destroy();
				System.exit(0);
			}
		}
	}
 
	/**
	 * Initialise the GL display
	 * 
	 * @param width The width of the display
	 * @param height The height of the display
	 */
	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
 
		GL11.glEnable(GL11.GL_TEXTURE_2D);               
 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
 
        	// enable alpha blending
        	GL11.glEnable(GL11.GL_BLEND);
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
 
        	GL11.glViewport(0,0,width,height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
 
	/**
	 * Initialize resources
	 */
	public void init() {
 
		try {
			// load texture from PNG file
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/image.png"));
 
			System.out.println("Texture loaded: "+texture);
			System.out.println(">> Image width: "+texture.getImageWidth());
			System.out.println(">> Image height: "+texture.getImageHeight());
			System.out.println(">> Texture width: "+texture.getTextureWidth());
			System.out.println(">> Texture height: "+texture.getTextureHeight());
			System.out.println(">> Texture ID: "+texture.getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * draw a quad with the image on it
	 */
	public void render() {
		
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		Color.white.bind();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
 
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(100,100);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(100+texture.getTextureWidth(),100);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(100+texture.getTextureWidth(),100+texture.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(100,100+texture.getTextureHeight());
		GL11.glEnd();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		
		glColor3f(1.0f, 0.5f, 1.0f);
		
		glBegin(GL_QUADS);
			glVertex2f(500, 100);
			glVertex2f(500, 100+50);
			glVertex2f(500-50, 100+50);
			glVertex2f(500-50, 100);
		glEnd();
	}
 
	/**
	 * Main Class
	 */
	public static void main(String[] argv) {
		TextureExample textureExample = new TextureExample();
		textureExample.start();
	}
}