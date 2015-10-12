package chess.chessgame;

import android.graphics.Point;

import com.gamedev.framework.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece
{
    public Bishop(int x, int y, Graphics g)
    {
        super(x,y,g);
    }

    public String getName()
    {
        return "Bishop";
    }

    @Override
    public String getImageFileName() {
        return "Bishop.png";
    }

    @Override
    public List<Point> listOfPositions()
    {
        List<Point> points = new ArrayList<Point>();
        for(int i=1; i<8; i++)
        {
            points.add(new Point(getX()+i,getY()+i));
            points.add(new Point(getX()-i,getY()+i));
            points.add(new Point(getX()+i,getY()-i));
            points.add(new Point(getX()-i,getY()-i));
        }

        return points;
    }
}