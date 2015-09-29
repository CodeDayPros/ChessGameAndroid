package chess.chessgame;

import com.gamedev.framework.Screen;
import com.gamedev.framework.implementation.AndroidGame;

public class MainGame extends AndroidGame{
    @Override
    public Screen getInitScreen()
    {
        return new GameScreen(this);
    }
}
