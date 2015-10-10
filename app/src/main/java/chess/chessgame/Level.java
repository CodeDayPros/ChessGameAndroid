package chess.chessgame;

import android.graphics.Point;

import com.gamedev.framework.Graphics;

import java.util.List;

public interface Level
{
    void generate(List<Piece> pieces, List<Point> finalLocations, int[][] positions, Graphics g);
}
