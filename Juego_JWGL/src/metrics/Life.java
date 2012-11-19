package metrics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Life extends Metric {

	private int lives;
	private String srcImg;

	public Life(int lives, String srcImg){
		this.lives = lives;
		this.srcImg = srcImg;
	}

	@Override
	public void draw(Graphics g) {
		for(int i = 0; i < this.lives; i++){
			Image img = null;
			try {
				img = new Image(this.srcImg);
				img.draw(40*i+10,10,30,30);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			 
		}
	}

}
