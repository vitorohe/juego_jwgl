package metrics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Water extends Metric {
	
	private float percentage;
	private Rectangle water;
	private Rectangle border;
	
	public Water(float percentage){
		this.percentage = percentage;
		this.border = new Rectangle(10,50,120,15); 
		this.water = new Rectangle(this.border.getX(),
				this.border.getY(),
				this.border.getWidth()*this.percentage/100,
				this.border.getHeight());
	}

	@Override
	public void draw(Graphics g) {
		this.water.setWidth(this.border.getWidth()*this.percentage/100);
		
		g.setColor(Color.blue);
		g.draw(border);
		g.draw(water);
		g.fill(water);
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

	public void full() {
		this.percentage = 100;
	}
	
	public boolean isEmpty(){
		if (this.percentage==0)
			return true;
		else return false;
	}

	public float getPercentage(){
		return this.percentage;
	}
}
