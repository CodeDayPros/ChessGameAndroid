package chess.chessgame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.Button;

import com.gamedev.framework.Game;
import com.gamedev.framework.Graphics;
import com.gamedev.framework.Input;
import com.gamedev.framework.Screen;

import java.util.HashMap;
import java.util.Map;

public class GameScreen extends Screen
{
    Paint paint;
    Board board;
    LevelGenerator generator;
    Graphics graphics;
    Input input;
    Typeface levelFont;
    int buttonTimer;
    ButtonType buttonType;
    Map<ButtonType, Rect> buttonRectangles;

    private enum ButtonType
    {
        RETRY
    }

    public GameScreen(Game game, Board b, LevelGenerator gen)
    {
        super(game);
        graphics=game.getGraphics();
        paint = new Paint();
        generator = gen;
        board = b;
        input = game.getInput();
        levelFont = Typeface.create("Arial", Typeface.BOLD);
        buttonTimer = -1;
        buttonRectangles = new HashMap();
        initializeButtons();
    }

    @Override
    public void update(float deltaTime) {
        if (buttonTimer > 0)
            buttonTimer--;
        else if (buttonTimer == 0)
        {
            switch(buttonType)
            {
                case RETRY:
                    board = generator.restartLevel(graphics);
            }
            buttonTimer = -1;
        }
        handleClick();
    }

    private void handleClick()
    {
        if (input.isTouchDown(0))
        {
            int x = input.getTouchX(0);
            int y = input.getTouchY(0);
            for ( Map.Entry<ButtonType, Rect> entry : buttonRectangles.entrySet() ) {
                Rect buttonRect = entry.getValue();
                if (buttonRect.contains(x, y))
                {
                    buttonType = entry.getKey();
                    buttonTimer = 4;
                }
            }
            board.clickOnBoard(x, y, getOffsetX(), getOffsetY());
        }
    }

    @Override
    public void paint(float deltaTime)
    {
        board.drawBoard(graphics, paint, getOffsetX(), getOffsetY());
        drawInterface();
    }

    private void initializeButtons()
    {
        buttonRectangles.put(ButtonType.RETRY, new Rect(game.getWidth() - 290, game.getHeight() - 90, game.getWidth(), game.getHeight()));
    }

    private void drawInterface()
    {
        paint.setTypeface(levelFont);
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        graphics.drawString("Level " + generator.getCurrentLevel(), 20, game.getHeight() - 30, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        if (buttonType == ButtonType.RETRY && buttonTimer >= 0)
            paint.setColor(Color.RED);
        graphics.drawString("Retry Level", game.getWidth() - 20, game.getHeight() - 30, paint);
        graphics.drawRect(buttonRectangles.get(ButtonType.RETRY), paint.getColor(), Paint.Style.STROKE);
    }

    private int getOffsetX()
    {
        return game.isPortrait() ? 0 : (game.getWidth() - 800) / 2;
    }

    private int getOffsetY()
    {
        return game.isPortrait() ? (game.getHeight() - 800) / 2 : 0;
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
