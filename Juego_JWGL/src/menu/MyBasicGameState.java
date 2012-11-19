package menu;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MyBasicGameState extends BasicGameState {
	
	public ArrayList<String> menu_list;
	public ArrayList<Image> level_list;
	public ArrayList<Color> color_menu_list;
	public int actual_menu;
	

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

	}

	@Override
	public int getID() {
		return 0;
	}
	
	public void menuOptionDown(){
		
		int actual_menu = this.actual_menu;
		
		if(actual_menu < this.menu_list.size()-1){
			this.color_menu_list.set(actual_menu, Color.black);
			this.color_menu_list.set(actual_menu+1, Color.yellow);
			this.actual_menu++;
		}
	}
	
	public void menuOptionUp(){
		
		int actual_menu = this.actual_menu;
		
		if(actual_menu > 0){
			this.color_menu_list.set(actual_menu, Color.black);
			this.color_menu_list.set(actual_menu-1, Color.yellow);
			this.actual_menu--;
		}		
		
	}
	
	public void menuOptionEnter(){
		
	}
	
	public void menuOptionBButton(){
		
	}

	public void menuOptionLeft() throws SlickException{
		int actual_menu = this.actual_menu;
		
		if(actual_menu > 0){
			Image img1 = this.level_list.get(actual_menu);
			Image img2 = this.level_list.get(actual_menu-1);
			String normal_name = getNormalName(img1.toString());
			String selected_name = getSelectedName(img2.toString());
			this.level_list.set(actual_menu, new Image(normal_name));
			this.level_list.set(actual_menu-1, new Image(selected_name));
			this.actual_menu--;
		}		
		
	}
	
	public void menuOptionRight() throws SlickException{
		int actual_menu = this.actual_menu;
		
		if(actual_menu < this.level_list.size()-1){
			Image img1 = this.level_list.get(actual_menu);
			Image img2 = this.level_list.get(actual_menu+1);
			String normal_name = getNormalName(img1.toString());
			String selected_name = getSelectedName(img2.toString());
			this.level_list.set(actual_menu, new Image(normal_name));
			this.level_list.set(actual_menu+1, new Image(selected_name));
			this.actual_menu++;
		}
	}

	private String getSelectedName(String string) {

		int pos1 = string.indexOf("/")+1;
		int pos2 = string.indexOf("png")+3;		
		return "testdata/selected_"+string.substring(pos1,pos2);
	}

	private String getNormalName(String string) {
		int pos1 = string.indexOf("/selected_")+10;
		int pos2 = string.indexOf("png")+3;		
		return "testdata/"+string.substring(pos1,pos2);
	}

	public void moveJump() {
		
	}
	
	public void stopJump() {
		
	}
	
	public void moveSlide(){
		
	}
	
	public void stopSlide(){
		
	}

}
