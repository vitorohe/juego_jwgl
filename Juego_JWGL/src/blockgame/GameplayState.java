package blockgame;
import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import blockgame.Piece.Tuple;
 
public class GameplayState extends BasicGameState{
 
    private int stateId = 0;
 
    PieceFactory pieceFactory = null;
 
    Pit pit = null;
 
    blockgame.Piece.Tuple cursorPos;
    Piece currentPiece = null;
    Piece nextPiece = null;
 
    Image gameHUD = null;
    Image transparentGameHUD = null;
 
    TrueTypeFont trueTypeFont = null;
 
    Sound blockFX = null;
 
    int score = 0;
 
    int deltaCounter = 500;
    int inputDelta = 0;
 
    int pitWidth = 0;
    int pitDepth = 0;
 
    static int PIT_X = 52;
    static int PIT_Y = 18;
 
    int blockSize = 0;
 
    ArrayList<Image> blockImages = null;
 
    private enum STATES {
        START_GAME_STATE, NEW_PIECE_STATE, MOVING_PIECE_STATE, LINE_DESTRUCTION_STATE,
        PAUSE_GAME_STATE, HIGHSCORE_STATE, GAME_OVER_STATE
    }
 
    private STATES currentState = null;
 
    public GameplayState(int stateId)
    {
        this.stateId = stateId;
    }
 
    @Override
    public int getID() {
        return stateId;
    }
 
    @Override
    public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
 
        Image allImages = new Image("testdata/gameplaytextures.png");
 
        gameHUD = new Image("testdata/hudblockgame.jpg");
        transparentGameHUD = allImages.getSubImage(29, 0, 287, 573);
 
        blockImages = new ArrayList<Image>();
 
        for(int i = 0; i < 4; i++)
            blockImages.add(allImages.getSubImage(0, i*blockSize, blockSize, blockSize));
 
 
        pieceFactory = new PieceFactory();
        blockSize = 28;
        pitWidth = 10;
        pitDepth = 20;
        pit = new Pit(pitWidth, pitDepth);
 
        blockFX  = new Sound("testdata/cbrown01.wav");
 
        Font font = new Font("Verdana", Font.BOLD, 40);
        trueTypeFont = new TrueTypeFont(font, true);
    }
 
    @Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);
 
        pit.makeCleanPit();
        currentState = STATES.START_GAME_STATE;
 
        score = 0;
    }
 
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
       // Paint HUD
       gameHUD.draw(0,0);
 
       // draw pit
       for(int lineIdx = 0; lineIdx < pit.getNumberOfLines(); lineIdx++)
       {
           for(int columnIdx = 0; columnIdx < pit.getNumberOfColumns(); columnIdx++)
           {
               int blockType = pit.getBlockAt(columnIdx, lineIdx);
 
               if(blockType != -1)
               {
                   blockImages.get(blockType).draw(columnIdx*blockSize+PIT_X, PIT_Y +
                                  (blockSize* ( pit.getNumberOfLines() - lineIdx - 1)));
               }
           }
       }
 
       // draw currentPiece
       if(currentPiece != null)
       {
           for(int i = 0; i < 4; i++)
           {
               Tuple blockPos = currentPiece.getPosOfBlock(i);
               blockImages.get(i).draw( PIT_X + (blockPos.x + cursorPos.x) * blockSize,
                                        PIT_Y + ( pit.getNumberOfLines() - 1 -( blockPos.y + cursorPos.y)) * blockSize );
           }
       }
 
       // draw nextPiece
       if(nextPiece != null)
       {
           for(int i = 0; i < 4; i++)
           {
               Tuple blockPos = nextPiece.getPosOfBlock(i);
               blockImages.get(i).draw( PIT_X + 336 + (blockPos.x) * blockSize, PIT_Y + 56 + ( ( blockPos.y )) * blockSize );
           }
       }
 
       trueTypeFont.drawString(600, 80, String.valueOf(score), Color.orange );
 
       transparentGameHUD.draw(48, 15);
 
    }
 
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
        switch(currentState)
        {
            case START_GAME_STATE:
                currentState = STATES.NEW_PIECE_STATE;
                deltaCounter = 500;
                break;
            case NEW_PIECE_STATE:
                generateNewPiece();
 
                break;
            case MOVING_PIECE_STATE:
                updatePiece( gc, sb, delta);
                break;
            case LINE_DESTRUCTION_STATE:
                checkForFullLines(gc, sb, delta);
                currentState = STATES.NEW_PIECE_STATE;
                break;
            case HIGHSCORE_STATE:
                break;
            case PAUSE_GAME_STATE:
                break;
            case GAME_OVER_STATE:
 
                Highscores.getInstance().addScore(score);
 
                sb.enterState(SlickBlocksGame.MAINMENUSTATE);
                break;
        }
    }
 
    public void checkForFullLines( GameContainer gc, StateBasedGame sb, int delta )
    {
        int linesDestroyed = 0;
 
        for(int lineIdx = 0; lineIdx < pit.getNumberOfLines(); )
        {
            if( pit.isLineFull(lineIdx) )
            {   
                pit.eraseLine(lineIdx);
                linesDestroyed++;
            }else
                lineIdx++;
        }
 
        switch(linesDestroyed)
        {
            case 0 : score += 10;
            break;
            case 1 : score += 100;
            break;
            case 2 : score += 300;
            break;
            case 3 : score += 600;
            break;
            case 4 : score += 1000;
            break;
 
        }
    }
 
    private void updatePiece( GameContainer gc, StateBasedGame sb, int delta )
    {
        Tuple newCursorPos = new Tuple( cursorPos.x, cursorPos.y);
 
        //verificar se está na altura de cair
        deltaCounter -= delta;
        inputDelta -= delta;
        if( deltaCounter < 0)
        {
            newCursorPos.y -= 1;
             if(!pit.isPieceInsertableIn(currentPiece, newCursorPos))
             {
                 pit.insertPieceAt(currentPiece, (int)cursorPos.x, (int)cursorPos.y);
                 blockFX.play();
                 currentState = STATES.LINE_DESTRUCTION_STATE;
                 return;
             }
 
            deltaCounter= 500;
        }
        //verificar o input
        Input input = gc.getInput();
        if(inputDelta < 0)
        {
            if(input.isKeyDown(Input.KEY_LEFT))
            {
                newCursorPos.x -= 1;
 
                if(!pit.isPieceInsertableIn(currentPiece, newCursorPos))
                    newCursorPos.x += 1;
                else
                    inputDelta = 100;
            }
            if(input.isKeyDown(Input.KEY_RIGHT))
            {
                newCursorPos.x += 1;
                if(!pit.isPieceInsertableIn(currentPiece, newCursorPos))
                    newCursorPos.x -= 1;
                else
                    inputDelta = 100;
            }
            if(input.isKeyDown(Input.KEY_UP))
            {
                currentPiece.rotateRight();
                if(!pit.isPieceInsertableIn(currentPiece, newCursorPos))
                    currentPiece.rotateLeft();
                else
                    inputDelta = 150;
            }
            if(input.isKeyDown(Input.KEY_DOWN))
            {
                newCursorPos.y -= 1;
                if(!pit.isPieceInsertableIn(currentPiece, newCursorPos)){
                    newCursorPos.y += 1;
                }
                else
                    inputDelta = 10;
            }
        }
 
        cursorPos = new Tuple(newCursorPos.x, newCursorPos.y);
    }
 
    private void generateNewPiece()
    {
        if(currentPiece == null)
            nextPiece = pieceFactory.generateRandomPiece();
 
        currentPiece = nextPiece;
 
        cursorPos = new Tuple( 5, 19);
 
        if(pit.isPieceInsertableIn(currentPiece, cursorPos ))
        {
            nextPiece = pieceFactory.generateRandomPiece();
            currentState = STATES.MOVING_PIECE_STATE;
        }else{
            currentState = STATES.GAME_OVER_STATE;
        }
 
    }
 
}