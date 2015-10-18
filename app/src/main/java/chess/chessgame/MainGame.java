package chess.chessgame;



import com.gamedev.framework.Screen;
import com.gamedev.framework.implementation.AndroidGame;

public class MainGame extends AndroidGame
{
    TitleScreen titleScreen;
    GameScreen gameScreen;
    Board board;
    LevelGenerator generator;
    @Override
    public Screen getInitScreen() {
            if(!MainApplication.mainGame) {
                titleScreen = new TitleScreen(this);
                return titleScreen;
            }
            else
            {
                if (MainApplication.board != null && MainApplication.generator != null) {
                    board = MainApplication.board;
                    generator = MainApplication.generator;
                } else {
                    generator = new LevelGenerator();
                    board = generator.nextLevel(getGraphics());
                }
                gameScreen = new GameScreen(this, board, generator);
                return gameScreen;
            }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.getCurrentScreen()!=titleScreen) {
            MainApplication.mainGame = true;
            MainApplication.board = board;
            MainApplication.generator = generator;
        }

    }
}
