package stage;

import java.util.List;

import org.newdawn.slick.geom.Rectangle;

public class Stage {

	private List<Obstacle> obstacles;
	private boolean drawing;

	public Stage(List<Obstacle> obstacles) {
		this.setObstacles(obstacles); 
		this.drawing = false;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(List<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}
	
	public boolean isFinishing(){
		List<Rectangle> rects = this.obstacles.get(this.obstacles.size()-1).getRectangles();
		Rectangle rect = rects.get(rects.size()-1);
		return rect.getX() + rect.getWidth() < 600;
	}
	
	public boolean hasFinished(){
		List<Rectangle> rects = this.obstacles.get(this.obstacles.size()-1).getRectangles();
		Rectangle rect = rects.get(rects.size()-1);
		return rect.getX() + rect.getWidth() < 0;
	}
	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

}
