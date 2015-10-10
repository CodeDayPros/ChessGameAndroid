package chess.chessgame;

import android.graphics.Paint;

import com.gamedev.framework.Game;
import com.gamedev.framework.Graphics;
import com.gamedev.framework.Screen;

public class GameScreen extends Screen
{
    Paint paint;
    Board board;
    LevelGenerator generator;

    public GameScreen(Game game)
    {
        super(game);

        paint = new Paint();
        generator = new LevelGenerator();
        board = generator.nextLevel();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void paint(float deltaTime)
    {
        Graphics g = game.getGraphics();

        board.drawBoard(g, paint);
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
