package chess.chessgame;

public class MathVector
{
    private int x;
    private int y;

    public MathVector(int x1, int y1)
    {
        x = x1;
        y = y1;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public double magnitude()
    {
        return Math.sqrt((double)x * x + y * y);
    }

    public int dotProduct(MathVector other)
    {
        return x * other.getX() + y * other.getY();
    }
}
