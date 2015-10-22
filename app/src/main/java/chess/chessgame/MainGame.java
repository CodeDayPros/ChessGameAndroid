package chess.chessgame;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.gamedev.framework.Screen;
import com.gamedev.framework.implementation.AndroidGame;

public class MainGame extends AndroidGame
{
    GameScreen gameScreen;
    public static final String PREFS_NAME = "ChessGame";

    @Override
    public Screen getInitScreen()
    {
        Board board;
        LevelGenerator generator;
        if (MainApplication.getBoard() != null && MainApplication.getGenerator() != null)
        {
            board = MainApplication.getBoard();
            generator = MainApplication.getGenerator();
        }
        else
        {
            generator = new LevelGenerator();
            board = generator.nextLevel(getGraphics());
        }
        gameScreen = new GameScreen(this, board, generator);

        if (MainApplication.getScreenType() == null)
            MainApplication.setScreenType(MainApplication.LastScreenType.TITLE);

        if (MainApplication.getScreenType() == MainApplication.LastScreenType.GAME)
        {
            return gameScreen;
        }
        else if (MainApplication.getScreenType() == MainApplication.LastScreenType.LEVELSELECT)
        {
            return new LevelSelectScreen(this, generator);
        }
        else if (MainApplication.getScreenType() == MainApplication.LastScreenType.TITLE)
        {
            return new TitleScreen(this);
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
            MainApplication.setScreenType(MainApplication.LastScreenType.GAME);
        }
        else if (screen instanceof LevelSelectScreen)
        {
            MainApplication.setScreenType(MainApplication.LastScreenType.LEVELSELECT);
        }
        else if (screen instanceof TitleScreen)
        {
            MainApplication.setScreenType(MainApplication.LastScreenType.TITLE);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        MainApplication.setBoard(gameScreen.getBoard());
        MainApplication.setGenerator(gameScreen.getGenerator());
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("lastUnlockedLevel", MainApplication.getLastUnlockedLevel());
        editor.apply();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        MainApplication.setLastUnlockedLevel(settings.getInt("lastUnlockedLevel", 1));
    }
}
