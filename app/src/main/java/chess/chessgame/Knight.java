package chess.chessgame;

import android.graphics.Point;

import com.gamedev.framework.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece
{
    public Knight(int x, int y, Graphics g)
    {
        super(x, y, g);
    }

    @Override
    public String getImageFileName()
    {
        return "Knight.png";
    }

    @Override
    public List<Point> listOfPositions()
    {
        List<Point> points = new ArrayList<>();
        points.add(new Point(getX() + 2, getY() + 1));
        points.add(new Point(getX() + 2, getY() - 1));
        points.add(new Point(getX() - 2, getY() + 1));
        points.add(new Point(getX() - 2, getY() - 1));
        points.add(new Point(getX() + 1, getY() - 2));
        points.add(new Point(getX() + 1, getY() + 2));
        points.add(new Point(getX() - 1, getY() - 2));
        points.add(new Point(getX() - 1, getY() + 2));

        return points;
    }
}