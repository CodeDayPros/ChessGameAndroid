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
import java.util.List;
import java.util.Map;

public class LevelMakerScreen extends Screen {

    Graphics graphics;
    ButtonType buttonType;
    Typeface levelFont;
    Map<ButtonType, Rect> buttonRectangles;
    List<Piece> pieces;
    Paint paint;
    Input input;
    int buttonTimer;


    private enum ButtonType
    {
        KNIGHT, QUEEN, ROOK, BISHOP, KING, POSITION, FINALPOSITION
    }

    public LevelMakerScreen(Game game) {
        super(game);
        graphics = game.getGraphics();
        buttonRectangles = new HashMap();
        levelFont = Typeface.create("Arial", Typeface.BOLD);
        paint = new Paint();
        input = game.getInput();
        buttonTimer = -1;
        initializeButtons();
    }

    public void initializeButtons() // idea is that each button will change what thing will be added to board when board is touched.
    {                               // also consider a drag and drop type of feature?
        buttonRectangles.put(ButtonType.KNIGHT , new Rect(game.getWidth() - 290, game.getHeight() - 90, game.getWidth(), game.getHeight()));
        buttonRectangles.put(ButtonType.QUEEN , new Rect(0, game.getHeight() - 90, 290, game.getHeight()));
        buttonRectangles.put(ButtonType.KING , new Rect(game.getWidth() - 290, game.getHeight() - 290, game.getWidth(), game.getHeight()-200));
        buttonRectangles.put(ButtonType.ROOK , new Rect(0, game.getHeight() - 190, 290, game.getHeight()-100));
        buttonRectangles.put(ButtonType.BISHOP, new Rect(game.getWidth() - 290, game.getHeight() - 190, game.getWidth(), game.getHeight() - 100));
    }
    @Override
    public void update(float deltaTime) {
        if(buttonTimer==3)
            PlaySounds.buttonSound(game);
        if(buttonTimer>0)
            buttonTimer--;
        else if(buttonTimer==0)
        {
            switch (buttonType)
            {
                case KNIGHT:

                    break;
                case QUEEN:

                    break;
                case KING:

                    break;
                case BISHOP:

                    break;
                case ROOK:

                    break;
            }
            buttonTimer = -1;
        }
        handleClick();
    }

    @Override
    public void paint(float deltaTime) {
        graphics.drawRect(0, 0, game.getWidth() + 1, game.getHeight() + 1, Color.BLACK, Paint.Style.FILL); //clear window
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {

                    if ((x + y) % 2 == 0)
                        paint.setARGB(255, 150, 150, 150);
                    else
                        paint.setARGB(255, 120, 120, 120);
                graphics.drawRect(x * 100 + getOffsetX(), y * 100 + getOffsetY(), 100, 100, paint.getColor(), Paint.Style.FILL);
                paint.setARGB(255, 0, 0, 0);
                graphics.drawRect(x * 100 + getOffsetX(), y * 100 + getOffsetY(), 100, 100, paint.getColor(), Paint.Style.STROKE);
            }
        }
        paint.setTypeface(levelFont);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        drawButton(ButtonType.KNIGHT, "Knight");
        drawButton(ButtonType.QUEEN, "Queen");
        drawButton(ButtonType.BISHOP, "Bishop");
        drawButton(ButtonType.ROOK, "Rook");
        drawButton(ButtonType.KING, "King");
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

    private void drawButton(ButtonType type, String buttonName)
    {
        paint.setColor(Color.WHITE);
        if (buttonTimer > 0 && buttonType == type)
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
    public boolean backButton() {
        game.setScreen(new TitleScreen(game));
        return false;
    }
}
