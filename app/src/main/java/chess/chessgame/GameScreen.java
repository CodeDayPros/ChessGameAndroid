package chess.chessgame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.Button;

import com.gamedev.framework.Audio;
import com.gamedev.framework.Game;
import com.gamedev.framework.Graphics;
import com.gamedev.framework.Input;
import com.gamedev.framework.Screen;
import com.gamedev.framework.Sound;

import java.util.HashMap;
import java.util.Map;

public class GameScreen extends Screen
{
    Paint paint;
    Board board;
    LevelGenerator generator;
    Graphics graphics;
    Input input;
    Audio audio;
    Typeface levelFont;
    int buttonTimer;
    ButtonType buttonType;
    Map<ButtonType, Rect> buttonRectangles;

    private enum ButtonType
    {
        RETRY, NEXT, LOSE, LEVELSELECT
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
        audio= game.getAudio();
        buttonRectangles = new HashMap();
        initializeButtons();
    }

    @Override
    public void update(float deltaTime) {
        if(buttonTimer == 3)
            PlaySounds.buttonSound(game.getAudio());
        if (buttonTimer > 0)
            buttonTimer--;
        else if (buttonTimer == 0)
        {
            switch(buttonType)
            {
                case LOSE:
                case RETRY:
                    board = generator.restartLevel(graphics);
                    break;
                case NEXT:
                    board = generator.nextLevel(graphics);
                    break;
                case LEVELSELECT:
                    game.setScreen(new LevelSelectScreen(game, generator));
                    break;
            }
            buttonTimer = -1;
        }
        handleClick();
        updateLastUnlockedLevel();
    }

    private void handleClick()
    {
        if (input.isTouchDown(0))
        {
            int x = input.getTouchX(0);
            int y = input.getTouchY(0);
            for ( Map.Entry<ButtonType, Rect> entry : buttonRectangles.entrySet() )
            {
                Rect buttonRect = entry.getValue();
                if (buttonRect.contains(x, y))
                {
                    ButtonType type = entry.getKey();
                    if (type == ButtonType.RETRY
                    || (type == ButtonType.NEXT && board.getState() == BoardState.WON)
                    || (type == ButtonType.LOSE && board.getState() == BoardState.LOST)
                    || type == ButtonType.LEVELSELECT)
                    {
                        buttonType = type;
                        buttonTimer = 4;
                    }
                }
            }
            board.clickOnBoard(x, y, getOffsetX(), getOffsetY(), game.getAudio());
        }
    }

    private void updateLastUnlockedLevel()
    {
        if (board.getState() == BoardState.WON)
        {
            int nextLevel = generator.getCurrentLevel() + 1;
            if (nextLevel > MainApplication.lastUnlockedLevel)
                MainApplication.lastUnlockedLevel = nextLevel;
        }
    }
    @Override
    public void paint(float deltaTime)
    {
        graphics.drawRect(0, 0, game.getWidth() + 1, game.getHeight() + 1, Color.BLACK, Paint.Style.FILL); //clear window
        board.drawBoard(graphics, paint, getOffsetX(), getOffsetY());
        drawInterface();
    }

    private void initializeButtons()
    {
        buttonRectangles.put(ButtonType.RETRY, new Rect(game.getWidth() - 290, game.getHeight() - 90, game.getWidth(), game.getHeight()));
        Rect loseWinRect = new Rect(getOffsetX() + 250, getOffsetY() + 450, getOffsetX() + 550, getOffsetY() + 540);
        buttonRectangles.put(ButtonType.NEXT, loseWinRect);
        buttonRectangles.put(ButtonType.LOSE, loseWinRect);
        buttonRectangles.put(ButtonType.LEVELSELECT, new Rect(game.getWidth() - 290, game.getHeight() - 190, game.getWidth(), game.getHeight() - 100));
    }

    private void drawInterface()
    {
        paint.setTypeface(levelFont);
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        graphics.drawString("Level " + generator.getCurrentLevel(), 20, game.getHeight() - 30, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        drawButton(ButtonType.RETRY, "Retry Level", Color.WHITE);
        drawButton(ButtonType.LEVELSELECT, "Select Level", Color.WHITE);

        if (board.getState() != BoardState.NONE)
        {
            paint.setARGB(200, 255, 255, 255);
            graphics.drawRect(getOffsetX() + 200, getOffsetY() + 250, 400, 300, paint.getColor(), Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            ButtonType type;
            String buttonName;
            String text;
            if (board.getState() == BoardState.WON)
            {
                type = ButtonType.NEXT;
                buttonName = "Next Level";
                text = "You Win!";
            }
            else
            {
                type = ButtonType.LOSE;
                buttonName = "Retry Level";
                text = "You Lose!";
            }
            graphics.drawString(text, getOffsetX() + 400, getOffsetY() + 370, paint);
            drawButton(type, buttonName, Color.BLACK);
        }
    }

    private void drawButton(ButtonType type, String buttonName, int defaultColor)
    {
        paint.setColor(defaultColor);
        if (buttonType == type && buttonTimer >= 0)
            paint.setColor(Color.RED);
        Rect rect = buttonRectangles.get(type);
        graphics.drawString(buttonName, rect.centerX(), rect.centerY() + 20, paint);
        graphics.drawRect(buttonRectangles.get(type), paint.getColor(), Paint.Style.STROKE);
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
