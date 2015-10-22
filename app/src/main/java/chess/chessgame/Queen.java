package chess.chessgame;

import android.graphics.Point;

import com.gamedev.framework.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece
{
    public Queen(int x, int y, Graphics g)
    {
        super(x, y, g);
    }

    @Override
    public String getImageFileName()
    {
        return "Queen.png";
    }

    @Override
    public List<Point> listOfPositions()
    {
        List<Point> points = new ArrayList<>();
        for(int i = 1; i < 8; i++)
        {
            points.add(new Point(getX() + i,getY() + i));
            points.add(new Point(getX() - i,getY() + i));
            points.add(new Point(getX() + i,getY() - i));
            points.add(new Point(getX() - i,getY() - i));
            points.add(new Point(getX() + i,getY()));
            points.add(new Point(getX() - i,getY()));
            points.add(new Point(getX(),getY() + i));
            points.add(new Point(getX(),getY() - i));
        }
        return points;
    }
}