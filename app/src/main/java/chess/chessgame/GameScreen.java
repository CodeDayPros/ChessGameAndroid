package chess.chessgame;

import android.graphics.Paint;

import com.gamedev.framework.Game;
import com.gamedev.framework.Graphics;
import com.gamedev.framework.Input;
import com.gamedev.framework.Screen;

public class GameScreen extends Screen
{
    Paint paint;
    Board board;
    LevelGenerator generator;
    Graphics graphics;
    Input input;

    public GameScreen(Game game, Board b, LevelGenerator gen)
    {
        super(game);
        graphics=game.getGraphics();
        paint = new Paint();
        generator = gen;
        board = b;
        input = game.getInput();
    }

    @Override
    public void update(float deltaTime) {
        if (input.isTouchDown(0))
        {
            int x = input.getTouchX(0);
            int y = input.getTouchY(0);
            board.clickOnBoard(x, y, getOffsetX(), getOffsetY());
        }
    }

    @Override
    public void paint(float deltaTime)
    {
        board.drawBoard(graphics, paint, getOffsetX(), getOffsetY());
    }

    private int getOffsetX()
    {
        return game.isPortrait() ? 0 : (game.getWidth() - 800) / 2;
    }

    private int getOffsetY()
    {
        return game.isPortrait() ? (game.getHeight() - 800) / 2 : 0;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }
}
