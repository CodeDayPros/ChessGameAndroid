package chess.chessgame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.gamedev.framework.Audio;
import com.gamedev.framework.Game;
import com.gamedev.framework.Graphics;
import com.gamedev.framework.Input;
import com.gamedev.framework.Screen;
import com.gamedev.framework.Sound;

import java.util.ArrayList;

public class LevelSelectScreen extends Screen
{
    Paint paint;
    Graphics graphics;
    Input input;
    Audio audio;
    Typeface levelFont;
    int buttonTimer;
    int levelNum;
    ArrayList<Rect> levelRectangles;
    LevelGenerator generator;

    public LevelSelectScreen(Game game, LevelGenerator gen)
    {
        super(game);
        graphics=game.getGraphics();
        paint = new Paint();
        input = game.getInput();
        audio = game.getAudio();
        levelFont = Typeface.create("Arial", Typeface.BOLD);
        buttonTimer = -1;
        levelNum = -1;
        levelRectangles = new ArrayList<>();
        generator = gen;
        initializeButtons();
    }

    @Override
    public void update(float deltaTime) {
        if (buttonTimer > 0)
            buttonTimer--;
        if (buttonTimer == 3)
        {
            PlaySounds.buttonSound(game.getAudio());
        }
        else if (buttonTimer == 0)
        {
            Board board = generator.getBoardForLevel(game.getGraphics(), levelNum + 1);
            game.setScreen(new GameScreen(game, board, generator));
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
            for (int index = 0;
                 index < MainApplication.lastUnlockedLevel && index < levelRectangles.size();
                 index++)
            {
                Rect rect = levelRectangles.get(index);
                if (rect.contains(x, y))
                {
                    levelNum = levelRectangles.indexOf(rect);
                    buttonTimer = 4;
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime)
    {
        graphics.drawRect(0, 0, game.getWidth() + 1, game.getHeight() + 1, Color.BLACK, Paint.Style.FILL); //clear window
        drawLevelButtons();
    }

    private void initializeButtons()
    {
        int totalRows = game.isPortrait() ? 7 : 5;
        int totalColumns = game.isPortrait() ? 5 : 7;
        for (int row = 0; row < totalRows; row++)
        {
            for (int col = 0; col < totalColumns; col++)
            {
                int levelNum = row * totalColumns + col;
                if (levelNum >= generator.getNumLevels())
                    return;

                levelRectangles.add(new Rect(
                        col * 160 + 10,
                        row * 160 + 10,
                        col * 160 + 150,
                        row * 160 + 150));
            }
        }
    }

    private void drawLevelButtons()
    {
        paint.setTypeface(levelFont);
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        for (int index = 0; index < levelRectangles.size(); index++)
        {
            Rect rect = levelRectangles.get(index);
            paint.setColor(Color.WHITE);
            if (index >= MainApplication.lastUnlockedLevel)
                paint.setColor(Color.GRAY);
            else if (index == levelNum && buttonTimer >= 0)
                paint.setColor(Color.RED);
            graphics.drawString(String.valueOf(index + 1), rect.centerX(), rect.centerY() + 20, paint);
            graphics.drawRect(rect, paint.getColor(), Paint.Style.STROKE);
        }
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
        game.setScreen(new TitleScreen(game));
        return false;
    }
}
