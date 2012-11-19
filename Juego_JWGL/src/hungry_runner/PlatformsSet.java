package hungry_runner;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class PlatformsSet {

	
	private List<Platform> platforms = null;
	
	public PlatformsSet(int n, String type){
		createPlatforms(n, type);
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public void createPlatforms(int n, String type) {
		platforms = new ArrayList<Platform>();
		Rectangle rect = null;
		float x;
		float y;
		float width;
		float height;
		
		if(type.equals("bottom")){
			for(int i = 0; i < n; i++){
				rect = new Rectangle(800, 550-30*(i+1), 200, 30*(i+1));
				platforms.add(new Platform(rect, "bottom"));
			}
		}else if(type.equals("top")){
			for(int i = 0; i < n; i++){
//				rect = new Rectangle(HungryRunnerGame.getWidth()-300, HungryRunnerGame.getHeight()-150-((1+i)*20), 250, 350);
				rect = new Rectangle(HungryRunnerGame.getWidth()-300, HungryRunnerGame.getHeight()-380, 250, 250);
				platforms.add(new Platform(rect, "top"));
			}
		}
	}
}
