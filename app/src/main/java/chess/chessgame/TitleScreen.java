package chess.chessgame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.gamedev.framework.Game;
import com.gamedev.framework.Graphics;
import com.gamedev.framework.Input;
import com.gamedev.framework.Screen;

import java.util.HashMap;
import java.util.Map;

public class TitleScreen extends Screen
{
    Graphics graphics;
    ButtonType buttonType;
    Typeface levelFont;
    Map<ButtonType, Rect> buttonRectangles;
    Paint paint;
    Input input;
    int buttonTimer;

    private enum ButtonType
    {
        START
    }

    public TitleScreen(Game game) {

        super(game);
        graphics = game.getGraphics();
        buttonRectangles = new HashMap();
        levelFont = Typeface.create("Arial", Typeface.BOLD);
        paint = new Paint();
        input = game.getInput();
        buttonTimer = -1;
        initializeButtons();
    }
    @Override
    public void update(float deltaTime)
    {
        if (buttonTimer > 0)
            buttonTimer--;
        else if (buttonTimer == 0)
        {
            switch (buttonType)
            {
                case START:
                    Board board;
                    LevelGenerator generator;
                    generator = new LevelGenerator();
                    board = generator.nextLevel(game.getGraphics());
                    game.setScreen(new GameScreen(this.game, board, generator));
                    break;
            }
        }
        handleClick();
    }

    private void handleClick()
    {
        if (input.isTouchDown(0))
        {
            int x = input.getTouchX(0);
            int y = input.getTouchY(0);
            for (Map.Entry<ButtonType, Rect> entry : buttonRectangles.entrySet())
            {
                Rect buttonRect = entry.getValue();
                if (buttonRect.contains(x, y))
                {
                    buttonType = entry.getKey();
                    buttonTimer = 4;
                }
            }
        }
    }

   private void initializeButtons()
   {
       buttonRectangles.put(ButtonType.START, new Rect(getOffsetX() + 240, getOffsetY() + 450, getOffsetX() + 560, getOffsetY() + 540));
   }

    @Override
    public void paint(float deltaTime)
    {
        graphics.drawRect(0, 0, game.getWidth() + 1, game.getHeight() + 1, Color.BLACK, Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        if (buttonTimer > 0)
            paint.setColor(Color.RED);
        graphics.drawRect(buttonRectangles.get(ButtonType.START), paint.getColor(), Paint.Style.STROKE);
        paint.setTypeface(levelFont);
        paint.setTextSize(50);
        graphics.drawString("Start Game", getOffsetX() + 400, getOffsetY() + 515, paint);
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
    public boolean backButton()
    {
        return true;
    }
}
