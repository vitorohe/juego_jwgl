package metrics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Fat extends Metric {
	
	private float percentage;
	private Rectangle border;
	private Rectangle fat;


	public Fat(float percentage){
		this.percentage = percentage;
		this.border = new Rectangle(10,70,120,15); 
		this.fat = new Rectangle(this.border.getX(),
				this.border.getY(),
				this.border.getWidth()*this.percentage/100,
				this.border.getHeight());
	}

	@Override
	public void draw(Graphics g) {
		this.fat.setWidth(this.border.getWidth()*this.percentage/100);
		
		g.setColor(Color.yellow);
		g.draw(border);
		g.draw(fat);
		g.fill(fat);
	}

	public void increasePercentage(){
		this.percentage +=5;
		if(this.percentage > 100)
			this.percentage = 100;
	}
	
	public void decreasePercentage(){
		this.percentage -=5;
		if(this.percentage < 0)
			this.percentage = 0;
	}
	
	public boolean isFull(){
		if(this.percentage==100)
			return true;
		else return false;
	}
	
	public float getPercentage(){
		return this.percentage;
	}
}
