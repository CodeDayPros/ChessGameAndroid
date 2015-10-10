package chess.chessgame;

import android.graphics.Point;

import com.gamedev.framework.Image;

import java.util.List;

public abstract class Piece
{
    private int xPos;
    private int yPos;

    public Piece(int x, int y)
    {
        xPos=x;
        yPos=y;
    }
    public abstract String getName();

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
    public abstract Image getImage();
    public abstract List<Point> listOfPositions();


}
