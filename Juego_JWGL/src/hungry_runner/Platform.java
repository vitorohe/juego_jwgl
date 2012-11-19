package hungry_runner;

import org.newdawn.slick.geom.Rectangle;

public class Platform {

	private Rectangle rect;
	private String type;
	
	public Platform(Rectangle rect, String type){
		this.setRect(rect);
		this.setType(type);
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString(){
		return "Rect: " + getRect().toString() + "/ type: " + getType();
		
	}
	
}
