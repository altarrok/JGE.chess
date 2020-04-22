package chess.pieces;

import chess.Board;
import chess.EmptyPiece;
import chess.Piece;
import engine.gfx.ImageTile;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(Color color, Point initPos) {
        super(color, "PAWN", initPos);
        moves = new Point[]{
               new Point(color == Color.WHITE ? 1 : -1, 0),
        };
        this.tile = new ImageTile("/tiles/chess_sprites/" + (color == Color.BLACK ? "black" : "white") + "Pawn.png", 50, 50);
    }

    @Override
    public Point[] availMoves(Board b) {
        ArrayList<Point> temp = new ArrayList<>();
        Piece nextP;
        if (color == Color.WHITE) {
            if (y == 6 &&
                    b.getPiece(x,y - 2) instanceof EmptyPiece &&
                    b.getPiece(x, y - 1) instanceof EmptyPiece) {
                temp.add(new Point(x, y - 2));
            }
            if ((y - 1 >= 0) && b.getPiece(x, y - 1) instanceof EmptyPiece) {
                temp.add(new Point(x, y - 1));
            }
            if (x - 1 >= 0 && y - 1 >= 0) {
                nextP = b.getPiece(x - 1, y - 1);
                if (!(nextP instanceof EmptyPiece) && nextP.getColor() != color) {
                    temp.add(new Point(x - 1, y - 1));
                }
            }
            if (x + 1 < 8 && y - 1 >= 0) {
                nextP = b.getPiece(x + 1, y - 1);
                if (!(nextP instanceof EmptyPiece) && nextP.getColor() != color) {
                    temp.add(new Point(x + 1, y - 1));
                }
            }
        } else {
            if (y == 1 &&
                    b.getPiece(x,y + 2) instanceof EmptyPiece &&
                    b.getPiece(x, y + 1) instanceof EmptyPiece) {
                temp.add(new Point(x, y + 2));
            }
            if ((y + 1 < 8) && b.getPiece(x, y + 1) instanceof EmptyPiece) {
                temp.add(new Point(x, y + 1));
            }
            if (x + 1 < 8 && y + 1 < 8) {
                nextP = b.getPiece(x + 1, y + 1);
                if (!(nextP instanceof EmptyPiece) && nextP.getColor() != color) {
                    temp.add(new Point(x + 1, y + 1));
                }
            }
            if (x - 1 >= 0 && y + 1 < 8) {
                nextP = b.getPiece(x - 1, y + 1);
                if (!(nextP instanceof EmptyPiece) && nextP.getColor() != color) {
                    temp.add(new Point(x - 1, y + 1));
                }
            }
        }
        Point[] tempArr = new Point[temp.size()];
        return temp.toArray(tempArr);
    }
}
