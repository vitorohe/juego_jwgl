
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


import static org.lwjgl.opengl.GL11.*;


public class QuadExample {

	
	float x = 800,h=50;
	double t=0, dt= 0.5;
	private boolean jumping = false;
	private float y;
	private boolean crashing = false;
	private Texture texture;
	private Audio aifEffect;
	
	public void start(){
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// for texture
		// enable alpha blending
		GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	
		GL11.glViewport(0,0,800,600);
		
		// init OpenGL 
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,800,0,600,1,-1);
		glMatrixMode(GL_MODELVIEW);

		try {
			aifEffect = AudioLoader.getAudio("AIF", ResourceLoader.getResourceAsStream("testdata/boip.aif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(!Display.isCloseRequested()){
			// CLear the screen and depth buffer			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
					
			// set the color of the quad (R,G,B,A)
			glColor3f(0.0f, 1.0f, 0.0f);
			
			// ground
			glBegin(GL_QUADS);
				glVertex2f(0, 0);
				glVertex2f(0, 50);
				glVertex2f(800, 50);
				glVertex2f(800, 0);
			glEnd();
			
			glColor3f(0.0f, 1.0f, 1.0f);
			// obstacle
			glBegin(GL_QUADS);
				glVertex2f(x, 50);
				glVertex2f(x, 200);
				glVertex2f(x-100, 200);
				glVertex2f(x-100, 50);
			glEnd();
			

			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				jumping  = true;
				aifEffect.playAsSoundEffect(1.0f, 1.0f, false);
			}
					
			if(!jumping){
				y = 50;
			}
			else{
				if(!inTerrain(x-100,y)){
					y = getY(t);
					t += dt;
					if(y<50){
						jumping = false;
						y=50;
						t=0;
					}
				}else{
					y = 200; 
				}
					
			}
				
			// person					
						
			renderTexture();
//			glColor3f(1.0f, 0.5f, 1.0f);
//			
//			glBegin(GL_QUADS);
//				glVertex2f(300, y);
//				glVertex2f(300, y+50);
//				glVertex2f(300-50, y+50);
//				glVertex2f(300-50, y);
//			glEnd();
			
			
			crashing = isCrashing(x-100,y);
			
			if(!crashing )
				x -= 3;
			else{
				x = 850;
			    crashing = false; 
			}
			
						
			if(x<0)
				x = 850;
			if(h<50)
				h = 50;
			if(h>550)
				h = 550;
			
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		AL.destroy();
		
	}
	
	private void renderTexture() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/image.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Color.white.bind();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
 
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(300,y);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(300-texture.getTextureWidth(),y);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(300-texture.getTextureWidth(),y+texture.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(300,y+texture.getTextureHeight());
		GL11.glEnd();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
	}	
	
	private boolean inTerrain(float x2, float y1) {

		float x1 = 300;
		float y2 = 200; 
		
		return (x1-x2<150) && (x1-x2>2) && (y1-y2<=0);
	}

	private boolean isCrashing(float x2, float y1) {
		
		float x1 = 300;
		float y2 = 200;  
		
		return (Math.abs(x1-x2)<=2) && (y2-y1>0);
	}

	public float getY(double t){
		return (float) (0.5*(-2)*t*t + 30*t + 50);
	}
	
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public static void main(String[] args){
		QuadExample qe = new QuadExample();
		qe.start();
	}
	
}

