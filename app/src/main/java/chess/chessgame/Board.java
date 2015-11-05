package chess.chessgame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.gamedev.framework.Audio;
import com.gamedev.framework.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Board
{
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
        possibleMovementLocations = new ArrayList<>();
        state = BoardState.NONE;
        animationTimer = -1;
    }

    public BoardState getState()
    {
        return state;
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

    public void selectPiece(Piece piece)
    {
        selectedPiece = piece;
        updatePossibleLocations();
    }

    private List<Point> getPossibleLocations(Piece piece)
    {
        List<Point> locations = new ArrayList<>();
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
                    MathVector u = new MathVector(otherPiece.getX() - piece.getX(), otherPiece.getY() - piece.getY());
                    MathVector v = new MathVector(x - piece.getX(), y - piece.getY());
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

    private void updateWinLoss(Audio audio)
    {
        if (checkWin())
        {
            state = BoardState.WON;
            PlaySounds.winSound(audio);
        }
        else if (checkLoss())
        {
            state = BoardState.LOST;
            PlaySounds.loseSound(audio);
        }
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

    public void drawBoard(Graphics g, Paint paint, int offsetX, int offsetY)
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                int valueAtPosition = getValue(x, y);
                int xCoord = x * 100 + offsetX;
                int yCoord = y * 100 + offsetY;
                if (valueAtPosition == 0)
                {
                    if ((x + y) % 2 == 0)
                        paint.setARGB(255, 150, 150, 150);
                    else
                        paint.setARGB(255, 120, 120, 120);
                }
                else if (valueAtPosition == 1)
                    g.setGradient(xCoord, yCoord, xCoord + 100, yCoord + 100, Color.rgb(255, 0, 0), Color.rgb(160, 20, 20));
                else if (valueAtPosition == 2)
                    g.setGradient(xCoord, yCoord, xCoord + 100, yCoord + 100, Color.rgb(0, 0, 255), Color.rgb(20, 20, 160));

                g.drawRect(xCoord, yCoord, 100, 100, valueAtPosition == 0 ? paint.getColor() : 0, Paint.Style.FILL);
                paint.setARGB(255, 0, 0, 0);
                g.drawRect(xCoord, yCoord, 100, 100, paint.getColor(), Paint.Style.STROKE);
            }
        }

        for (Point location : finalLocations)
        {
            for (int pos = 0; pos <= 100; pos += 10)
            {
                int x = location.x*100;
                int y = location.y*100;
                paint.setARGB(255, 255, 255, 255);
                g.drawLine(x + offsetX, y+pos + offsetY, x+pos + offsetX, y + offsetY, paint.getColor());
                g.drawLine(x + 100 + offsetX, y + 100 - pos + offsetY, x + 100 - pos + offsetX, y + 100 + offsetY, paint.getColor());
            }
        }

        for (Point location : possibleMovementLocations)
        {
            paint.setARGB(255, 0, 255, 0);
            g.drawRect(location.x * 100 + offsetX, location.y * 100 + offsetY, 100, 100, paint.getColor(), Paint.Style.STROKE);
            g.drawRect(location.x * 100 + 1 + offsetX, location.y * 100 + 1 + offsetY, 98, 98, paint.getColor(), Paint.Style.STROKE);
        }

        if (selectedPiece != null)
        {
            paint.setARGB(255, 255, 255, 0);
            g.drawRect(selectedPiece.getX() * 100 + offsetX, selectedPiece.getY() * 100 + offsetY, 100, 100, paint.getColor(),
                    Paint.Style.STROKE);
            g.drawRect(selectedPiece.getX() * 100 + 1 + offsetX, selectedPiece.getY() * 100 + 1 + offsetY, 98, 98, paint.getColor(),
                    Paint.Style.STROKE);
        }

        for (Piece piece : pieces)
        {
            if (animationTimer < 0 || piece != selectedPiece)
                g.drawScaledImage(piece.getImage(), piece.getX()*100 + offsetX, piece.getY()*100 + offsetY, 100, 100);
        }

        if (animationTimer >= 0)
        {
            g.drawScaledImage(selectedPiece.getImage(),
                    (int) ((newLoc.x + (previousLoc.x - newLoc.x) * (animationTimer / 10.)) * 100 + offsetX + 0.5),
                    (int) ((newLoc.y + (previousLoc.y - newLoc.y) * (animationTimer / 10.)) * 100 + offsetY + 0.5),
                    100, 100);
            animationTimer--;
        }
    }

    public void clickOnBoard(int x, int y, int offsetX, int offsetY, Audio audio)
    {
        if (state == BoardState.NONE && animationTimer == -1)
        {
            int gridX = (x - offsetX) / 100;
            int gridY = (y - offsetY) / 100;
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
                        PlaySounds.moveSound(audio);
                        updateWinLoss(audio);
                        return;
                    }
                }
            }
        }
    }
}
