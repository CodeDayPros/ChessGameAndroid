package chess.chessgame;

import android.graphics.Point;

import com.gamedev.framework.Graphics;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece
{
    public King(int x, int y, Graphics g)
    {
        super(x, y, g);
    }

    @Override
    public String getImageFileName()
    {
        return "King.png";
    }

    @Override
    public List<Point> listOfPositions()
    {
        List<Point> points = new ArrayList<>();
        points.add(new Point(getX() + 1, getY() + 1));
        points.add(new Point(getX() + 1, getY() - 1));
        points.add(new Point(getX() - 1, getY() + 1));
        points.add(new Point(getX() - 1, getY() - 1));
        points.add(new Point(getX(), getY() - 1));
        points.add(new Point(getX(), getY() + 1));
        points.add(new Point(getX() - 1, getY()));
        points.add(new Point(getX() + 1, getY()));
        return points;
    }
}