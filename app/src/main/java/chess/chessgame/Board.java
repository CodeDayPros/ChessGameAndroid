package chess.chessgame;

import android.graphics.Paint;
import android.graphics.Point;

import com.gamedev.framework.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> pieces;
    private int[][] positions;
    private List<Point> finalLocations;

    private Point previousLoc;
    private Point newLoc;
    private int animationTimer;

    private Piece selectedPiece;
    private List<Point> possibleMovementLocations;

    private BoardState state;

    public Board(List<Piece> x, int[][] p, List<Point> l)
    {
        positions = p;
        pieces = x;
        finalLocations = l;
        possibleMovementLocations = new ArrayList<Point>();
        state = BoardState.NONE;
        animationTimer = -1;
    }

    public Board()
    {
        this(new ArrayList<Piece>(), new int[8][8], new ArrayList<Point>());
    }

    public BoardState getState()
    {
        return state;
    }

    public void setState(BoardState s)
    {
        state = s;
    }
    public Piece getPiece(int x, int y)
    {
        for(Piece p: pieces)
        {
            if(p.getX()==x &&p.getY()==y)
                return p;
        }
        return null;
    }

    public Piece getSelectedPiece()
    {
        return selectedPiece;
    }

    public void selectPiece(Piece piece)
    {
        selectedPiece = piece;
        updatePossibleLocations();
    }

    private List<Point> getPossibleLocations(Piece piece)
    {
        List<Point> locations = new ArrayList<Point>();
        for (Point location : piece.listOfPositions())
        {
            int x = location.x;
            int y = location.y;
            if (x >= 0 && x <= 7 && y >= 0 && y <= 7
                    && getPiece(x, y) == null && getValue(x, y) > 0)
            {
                boolean addLocation = true;
                for (Piece otherPiece : pieces)
                {
                    MathVector u = new MathVector((int)(otherPiece.getX() - piece.getX()), (int)(otherPiece.getY() - piece.getY()));
                    MathVector v = new MathVector((int)(x - piece.getX()), (int)(y - piece.getY()));
                    if (Math.abs(u.dotProduct(v)/(u.magnitude()*v.magnitude()) - 1) < 0.0001 && u.magnitude() < v.magnitude())
                    {
                        addLocation = false;
                        break;
                    }
                }
                if (addLocation)
                    locations.add(location);
            }
        }
        return locations;
    }

    private void updatePossibleLocations()
    {
        possibleMovementLocations = getPossibleLocations(selectedPiece);
    }

    private void updateWinLoss()
    {
        if (checkWin())
            state = BoardState.WON;
        else if (checkLoss())
            state = BoardState.LOST;
    }

    private boolean checkWin()
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (getValue(x, y) > 0)
                    return false;
            }
        }
        for (Point location : finalLocations)
        {
            if (getPiece(location.x, location.y) == null)
                return false;
        }
        return true;
    }

    private boolean checkLoss()
    {
        for (Point location : finalLocations)
        {
            int x = location.x;
            int y = location.y;
            if (getPiece(x,y) == null && getValue(x,y) == 0)
                return true;
        }
        for (Piece piece : pieces)
        {
            if (getPossibleLocations(piece).size() > 0)
                return false;
        }
        return true;
    }

    public int getValue(int x, int y)
    {
        return positions[x][y];
    }

    public void setValue(int x, int y, int value)
    {
        positions[x][y]=value;
    }

    public void drawBoard(Graphics g, Paint paint)
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                int valueAtPosition = getValue(x, y);
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

        for (Point location : finalLocations)
        {
            for (int pos = 0; pos <= 100; pos += 10)
            {
                int x = location.x*100;
                int y = location.y*100;
                paint.setARGB(255, 255, 255, 255);
                g.drawLine(x, y+pos, x+pos, y, paint.getColor());
                g.drawLine(x + 100, y + 100 - pos, x + 100 - pos, y + 100, paint.getColor());
            }
        }

        for (Point location : possibleMovementLocations)
        {
            paint.setARGB(255, 0, 255, 0);
            g.drawRect(location.x*100, location.y*100, 100, 100, paint.getColor(), Paint.Style.STROKE);
        }

        if (selectedPiece != null)
        {
            paint.setARGB(255, 255, 255, 0);
            g.drawRect(selectedPiece.getX()*100, selectedPiece.getY()*100, 100, 100, paint.getColor(),
                    Paint.Style.STROKE);
        }

        for (Piece piece : pieces)
        {
            if (animationTimer < 0 || piece != selectedPiece)
                g.drawScaledImage(piece.getImage(), piece.getX()*100, piece.getY()*100, 100, 100);
        }

        if (animationTimer >= 0)
        {
            g.drawScaledImage(selectedPiece.getImage(),
                    (int) ((newLoc.x + (previousLoc.x - newLoc.x) * (animationTimer / 10.)) * 100 + 0.5),
                    (int) ((newLoc.y + (previousLoc.y - newLoc.y) * (animationTimer / 10.)) * 100 + 0.5),
                    100, 100);
            animationTimer--;
        }
    }

    public void clickOnBoard(int x, int y)
    {
        if (state == BoardState.NONE && animationTimer == -1)
        {
            int gridX = x/100;
            int gridY = y/100;
            if (gridX < 0 || gridX > 7 || gridY < 0 || gridY > 7)
                return;
            Piece piece = getPiece(gridX, gridY);
            if (piece != null)
                selectPiece(piece);
            else
            {
                for (Point location : possibleMovementLocations)
                {
                    if (gridX == location.x && gridY == location.y)
                    {
                        previousLoc = new Point(selectedPiece.getX(), selectedPiece.getY());
                        newLoc = new Point(gridX, gridY);
                        animationTimer = 10;
                        selectedPiece.setPos(gridX, gridY);
                        setValue(gridX, gridY, getValue(gridX, gridY) - 1);
                        updatePossibleLocations();
                        updateWinLoss();
                        return;
                    }
                }
            }
        }
    }
}
