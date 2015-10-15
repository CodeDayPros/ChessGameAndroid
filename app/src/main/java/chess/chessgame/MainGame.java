package chess.chessgame;

import com.gamedev.framework.Screen;
import com.gamedev.framework.implementation.AndroidGame;

public class MainGame extends AndroidGame{
    public static Board board;
    public static LevelGenerator generator;
    @Override
    public Screen getInitScreen()
    {
        if (MainApplication.board != null && MainApplication.generator != null)
        {
            board = MainApplication.board;
            generator = MainApplication.generator;
        }
        else
        {
            generator = new LevelGenerator();
            board = generator.nextLevel(getGraphics());
        }
            return new GameScreen(this, board, generator);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainApplication.board = board;
        MainApplication.generator = generator;
    }
}
