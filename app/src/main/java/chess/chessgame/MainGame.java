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

        if (MainApplication.screenType == null)
            MainApplication.screenType = MainApplication.LastScreenType.GAME; //change to title later

        if (MainApplication.screenType == MainApplication.LastScreenType.GAME)
        {
            return gameScreen;
        }
        else if (MainApplication.screenType == MainApplication.LastScreenType.LEVELSELECT)
        {
            return new LevelSelectScreen(this, generator);
        }
        return null;
    }

    @Override
    public void setScreen(Screen screen)
    {
        super.setScreen(screen);

        if (screen instanceof GameScreen)
        {
            gameScreen = (GameScreen)screen;
            MainApplication.screenType = MainApplication.LastScreenType.GAME;
        }
        else if (screen instanceof LevelSelectScreen)
        {
            MainApplication.screenType = MainApplication.LastScreenType.LEVELSELECT;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainApplication.board = gameScreen.board;
        MainApplication.generator = gameScreen.generator;
    }
}
