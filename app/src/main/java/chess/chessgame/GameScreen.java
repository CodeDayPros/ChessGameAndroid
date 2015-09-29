package chess.chessgame;

import android.graphics.Color;
import android.graphics.Paint;

import com.gamedev.framework.Game;
import com.gamedev.framework.Screen;
import com.gamedev.framework.Graphics;

public class GameScreen extends Screen
{
    Paint paint;

    public GameScreen(Game game)
    {
        super(game);

        paint = new Paint();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void paint(float deltaTime)
    {
        Graphics g = game.getGraphics();

        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                int valueAtPosition = /*getValue(x, y)*/0;
                if (valueAtPosition == 0)
                {
                    if ((x + y) % 2 == 0)
                        paint.setARGB(255, 150, 150, 150);
                    else
                        paint.setARGB(255, 120, 120, 120);
                }
                else if (valueAtPosition == 1)
                    paint.setARGB(255, 255, 0, 0);
                else if (valueAtPosition == 2)
                    paint.setARGB(255, 225, 0, 225);


                g.drawRect(x * 100, y * 100, 100, 100, paint.getColor(), Paint.Style.FILL);
                paint.setARGB(255, 0, 0, 0);
                g.drawRect(x * 100, y * 100, 100, 100, paint.getColor(), Paint.Style.STROKE);
            }
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
    public void backButton() {
        pause();
    }
}
