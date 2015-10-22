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
            MainApplication.screenType = MainApplication.LastScreenType.TITLE;

        if (MainApplication.screenType == MainApplication.LastScreenType.GAME)
        {
            return gameScreen;
        }
        else if (MainApplication.screenType == MainApplication.LastScreenType.LEVELSELECT)
        {
            return new LevelSelectScreen(this, generator);
        }
        else if (MainApplication.screenType == MainApplication.LastScreenType.TITLE)
        {
            return new TitleScreen(this);
        }
        else if (MainApplication.screenType == MainApplication.LastScreenType.LEVELMAKER)
        {
            return new LevelMakerScreen(this);
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
        else if (screen instanceof TitleScreen)
        {
            MainApplication.screenType = MainApplication.LastScreenType.TITLE;
        }
        else if(screen instanceof LevelMakerScreen) {
            MainApplication.screenType = MainApplication.LastScreenType.LEVELMAKER;
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        MainApplication.board = gameScreen.board;
        MainApplication.generator = gameScreen.generator;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("lastUnlockedLevel", MainApplication.lastUnlockedLevel);
        editor.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        MainApplication.lastUnlockedLevel = settings.getInt("lastUnlockedLevel", 1);
    }
}
