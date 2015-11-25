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
    private Graphics graphics;
    private ButtonType buttonType;
    private Typeface levelFont;
    private Map<ButtonType, Rect> buttonRectangles;
    private Paint paint;
    private Input input;
    private int buttonTimer;

    private enum ButtonType
    {
        NEWGAME, LEVELSELECT, CONTINUE
    }

    public TitleScreen(Game game)
    {
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
        if(buttonTimer == 3)
            PlaySounds.buttonSound(game.getAudio());
        if (buttonTimer > 0)
            buttonTimer--;
        else if (buttonTimer == 0)
        {
            LevelGenerator generator = new LevelGenerator();
            Board board;
            switch (buttonType)
            {
                case NEWGAME:
                    MainGame.setLevel(game, generator, generator.getCurrentLevel() + 1);
                    break;
                case LEVELSELECT:
                    game.setScreen(new LevelSelectScreen(this.game, generator));
                    break;
                case CONTINUE:
                    MainGame.setLevel(game, generator, MainApplication.getLastUnlockedLevel());
                    break;
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
       buttonRectangles.put(ButtonType.NEWGAME, new Rect(getOffsetX() + 240, getOffsetY() + 450, getOffsetX() + 560, getOffsetY() + 540));
       buttonRectangles.put(ButtonType.CONTINUE, new Rect(getOffsetX() + 240, getOffsetY() + 550, getOffsetX() + 560, getOffsetY() + 640));
       buttonRectangles.put(ButtonType.LEVELSELECT, new Rect(getOffsetX() + 240, getOffsetY() + 650, getOffsetX() + 560, getOffsetY() + 740));
   }

    @Override
    public void paint(float deltaTime)
    {
        drawBackground();
        paint.setTypeface(levelFont);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        drawButton(ButtonType.NEWGAME, "New Game");
        drawButton(ButtonType.CONTINUE, "Continue");
        drawButton(ButtonType.LEVELSELECT, "Select Level");
    }

    private void drawButton(ButtonType type, String buttonName)
    {
        paint.setColor(Color.WHITE);
        if (buttonTimer > 0 && buttonType == type)
            paint.setColor(Color.RED);
        Rect rect = buttonRectangles.get(type);
        graphics.drawString(buttonName, rect.centerX(), rect.centerY() + 20, paint);
        graphics.drawRect(rect, paint.getColor(), Paint.Style.STROKE);
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public boolean backButton()
    {
        return true;
    }
}
