package sui_examples;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.*;
import mdes.slick.sui.*;
import mdes.slick.sui.event.*;

public class Lesson1 extends BasicGame {
    
    public static void main(String[] args) throws Exception {
        AppGameContainer app = new AppGameContainer(new Lesson1());
        app.setDisplayMode(800,600,false);
        app.start();
    }
    
    /** The top-level Sui display. */
    private Display display;
    
    public Lesson1() {
        super("Lesson1");
    }
    
    public void init(GameContainer container) throws SlickException {
        //set up a nice blue background
        Color background = new Color(71,102,124);
        container.getGraphics().setBackground(background);        

        //we create a Sui display from our Slick container
        display = new Display(container);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        //update the display and its components
        display.update(container, delta);
        
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        //render the display and its components
        display.render(container, g);
    }
}