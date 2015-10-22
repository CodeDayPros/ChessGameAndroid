package chess.chessgame;

import android.graphics.Point;

import com.gamedev.framework.Graphics;
import com.gamedev.framework.Image;

import java.util.List;

public abstract class Piece
{
    private int xPos;
    private int yPos;
    private Image image;

    public Piece(int x, int y, Graphics g)
    {
        xPos = x;
        yPos = y;
        image = g.newImage(getImageFileName(), Graphics.ImageFormat.ARGB4444);
    }

    public int getX()
    {
        return xPos;
    }

    public int getY()
    {
        return yPos;
    }

    public void setPos(int x, int y)
    {
        xPos=x;
        yPos=y;
    }

    public Image getImage()
    {
        return image;
    }

    public abstract String getImageFileName();

    public abstract List<Point> listOfPositions();
}
