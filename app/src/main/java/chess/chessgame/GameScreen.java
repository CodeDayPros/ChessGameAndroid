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

    public GameScreen(Game game)
    {
        super(game);
        graphics=game.getGraphics();
        paint = new Paint();
        generator = new LevelGenerator();
        board = generator.nextLevel(graphics);
        input = game.getInput();
    }

    @Override
    public void update(float deltaTime) {
        if (input.isTouchDown(0))
        {
            int x = input.getTouchX(0);
            int y = input.getTouchY(0);
            board.clickOnBoard(x, y);
        }
    }

    @Override
    public void paint(float deltaTime)
    {
        board.drawBoard(graphics, paint);
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
