package stage;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Obstacle {

	public static final int PLATFORM = 0;
	public static final int DANGEROUS = 1;
	public static final int GOOD_FOOD = 2;
	public static final int BAD_FOOD = 3;
	public static final int WATER =  4;
	public static final int GOAL = 5;
	private List<Rectangle> rectangles;
	private float x;
	private int width;
	private int height;
	private String src_image;
	private int type;
	private boolean drawing;

	public Obstacle(List<Rectangle> rectangles, String image, int type) {
		this.rectangles = rectangles;
		this.src_image = image;
		this.x = this.rectangles.get(0).getX();
		this.width = 0;
		this.height = 0;
		this.drawing = true;
		this.setType(type);
	}

	public void draw(Graphics g) {
		if(this.drawing){
			for(Rectangle rect: this.rectangles){
	//			System.out.println("rectx:"+rect.getX());
	//			System.out.println("recty:"+rect.getY());
				Image img = null;
				try {
					img = new Image(this.src_image);
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(this.type != PLATFORM){
					img = img.getScaledCopy((int)rect.getWidth(), (int)rect.getHeight());
					img.draw(rect.getX(), rect.getY());
				}else{
					img = img.getScaledCopy(1.8f);
					g.draw(rect);
					g.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), img, 0, 0);
				}
			}
		}
	}

	public void changeX(float f) {
		for(Rectangle rect: this.rectangles)
			rect.setX(rect.getX()+f);
	}

	public Rectangle crashes(Rectangle runner) {
		
		for(Rectangle rect :  this.rectangles){
			if(runner.intersects(rect)){
				return rect;
			}
		}
		return null;
	}

	public List<Rectangle> getRectangles() {
		return this.rectangles;
		
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void stopDrawing() {
		this.drawing = false;
		
	}

	public boolean isDrawing() {
		return this.drawing;
	}

}
