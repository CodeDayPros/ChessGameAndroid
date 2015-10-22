package chess.chessgame;

import android.app.Application;

public class MainApplication extends Application {
    private static Board board;
    private static LevelGenerator generator;
    private static LastScreenType screenType;
    private static int lastUnlockedLevel;

    public static Board getBoard()
    {
        return board;
    }

    public static void setBoard(Board board)
    {
        MainApplication.board = board;
    }

    public static LevelGenerator getGenerator()
    {
        return generator;
    }

    public static void setGenerator(LevelGenerator generator)
    {
        MainApplication.generator = generator;
    }

    public static LastScreenType getScreenType()
    {
        return screenType;
    }

    public static void setScreenType(LastScreenType screenType)
    {
        MainApplication.screenType = screenType;
    }

    public static int getLastUnlockedLevel()
    {
        return lastUnlockedLevel;
    }

    public static void setLastUnlockedLevel(int lastUnlockedLevel)
    {
        MainApplication.lastUnlockedLevel = lastUnlockedLevel;
    }

    public enum LastScreenType
    {
        GAME, TITLE, LEVELSELECT
    }
}
