package chess.chessgame;

import com.gamedev.framework.Screen;
import com.gamedev.framework.implementation.AndroidGame;

public class MainGame extends AndroidGame
{
    GameScreen gameScreen;

    @Override
    public Screen getInitScreen()
    {
        Board board;
        LevelGenerator generator;
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
        gameScreen = new GameScreen(this, board, generator);
        return gameScreen;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainApplication.board = gameScreen.board;
        MainApplication.generator = gameScreen.generator;
    }
}
