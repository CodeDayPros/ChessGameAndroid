package chess.chessgame;

import android.app.Application;

public class MainApplication extends Application {
    public static Board board;
    public static LevelGenerator generator;
    public static LastScreenType screenType;

    public enum LastScreenType
    {
        GAME, TITLE, LEVELSELECT
    }
}
