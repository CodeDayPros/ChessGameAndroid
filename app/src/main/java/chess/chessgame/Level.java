package chess.chessgame;

import android.graphics.Point;

import java.util.List;

public interface Level
{
    void generate(List<Piece> pieces, List<Point> finalLocations, int[][] positions);
}
