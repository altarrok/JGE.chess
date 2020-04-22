package chess.pieces;

import chess.Board;
import chess.EmptyPiece;
import chess.Piece;
import engine.gfx.ImageTile;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(Color color, Point initPos) {
        super(color, "KNIGHT", initPos);
        moves = new Point[]{
                new Point(1, 2),
                new Point(-2, 1),
                new Point(-1, 2),
                new Point(2, -1),
                new Point(1, -2),
                new Point(2, 1),
                new Point(-1, -2),
                new Point(-2, -1),
        };
        this.tile = new ImageTile("/tiles/chess_sprites/" + (color == Color.BLACK ? "black" : "white") + "Knight.png", 50, 50);

    }

    @Override
    public Point[] availMoves(Board b) {
        ArrayList<Point> temp = new ArrayList<>();
        Piece currP;
        int nextX, nextY;
        for (Point move : moves) {
            nextX = x + move.x;
            nextY = y + move.y;
            if (nextX >= 0 && nextX < 8 && nextY >= 0 && nextY < 8) {
                currP = b.getPiece(nextX, nextY);
                if (currP instanceof EmptyPiece || currP.getColor() != color) {
                    // no pieces.. or enemy piece add the position
                    temp.add(new Point(nextX, nextY));
                }
            }
        }
        Point[] tempArr = new Point[temp.size()];
        return temp.toArray(tempArr);

    }
}
