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

public class InstructionsScreen extends Screen
{
    private Graphics graphics;
    private Typeface levelFont;
    private Rect buttonRectangle;
    private Paint paint;
    private Input input;
    private int buttonTimer;
    private LevelGenerator generator;
    private static boolean[] hasInstructions = new boolean[]{true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};;

    public InstructionsScreen(Game game, LevelGenerator gen)
    {
        super(game);
        graphics = game.getGraphics();
        levelFont = Typeface.create("Arial", Typeface.BOLD);
        paint = new Paint();
        input = game.getInput();
        buttonTimer = -1;
        buttonRectangle = new Rect(new Rect(getOffsetX() + 240, game.getHeight() - 90, getOffsetX() + 560, game.getHeight()));
        generator = gen;
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
            Board board = generator.nextLevel(game.getGraphics());
            game.setScreen(new GameScreen(this.game, board, generator));
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
            if (buttonRectangle.contains(x, y))
            {
                buttonTimer = 4;
            }
        }
    }

    @Override
    public void paint(float deltaTime)
    {
        drawBackground();
        paint.setTypeface(levelFont);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        drawInstructions(generator.getCurrentLevel());
        drawButton("Start Level");
    }

    private void drawButton(String buttonName)
    {
        paint.setColor(Color.WHITE);
        if (buttonTimer > 0)
            paint.setColor(Color.RED);
        graphics.drawString(buttonName, buttonRectangle.centerX(), buttonRectangle.centerY() + 20, paint);
        graphics.drawRect(buttonRectangle, paint.getColor(), Paint.Style.STROKE);
    }

    private void drawInstructions(int levelNum)
    {
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
        game.setScreen(new TitleScreen(game));
        return false;
    }

    public static boolean levelHasInstructions(int level)
    {
        return hasInstructions[level - 1];
    }
}
