package blockgame;
import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 
/**
 *
 * @author Spiegel
 *
 */
public class SlickBlocksGame extends StateBasedGame {
 
    public static final int MAINMENUSTATE          = 0;
    public static final int RESOURCELOADERSTATE    = 1;
    public static final int GAMEPLAYSTATE          = 2;
 
    public SlickBlocksGame()
    {
        super("SlickBlocks");
 
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.enterState(MAINMENUSTATE);
    }
 
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new SlickBlocksGame());
 
         app.setDisplayMode(800, 600, false);
         app.start();
    }
 
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
 
        this.getState(MAINMENUSTATE).init(gameContainer, this);
        this.getState(GAMEPLAYSTATE).init(gameContainer, this);
 
        //tentar criar um novo state
 
    }
 
}