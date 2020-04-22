package chess.pieces;

import chess.Board;
import chess.EmptyPiece;
import chess.Piece;
import engine.gfx.ImageTile;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Color color, Point initPos) {
        super(color, "BISHOP" , initPos);
        moves = new Point[]{
                new Point(1,1),
                new Point(-1, -1),
                new Point(1, -1),
                new Point(-1, 1),
        };
        this.tile = new ImageTile("/tiles/chess_sprites/" + (color == Color.BLACK ? "black" : "white") + "Bishop.png", 50, 50);
    }

    @Override
    public Point[] availMoves(Board b) {
        // Check each diagonal, add positions that are without a piece on return arr
        // if there is a piece, check if its opposite colored piece, if so add position and return
        // if color is same, return
        ArrayList<Point> temp = new ArrayList<>();
        Piece currP;
        boolean[] doContinue = {true, true, true, true};
        int nextX, nextY;
        for (int i = 1; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                nextX = x + moves[j].x * i;
                nextY = y + moves[j].y * i;
                if (nextX >= 0 && nextX < 8 && nextY >= 0 && nextY < 8 && doContinue[j]) {
                    currP = b.getPiece(nextX, nextY);
                    if (!(currP instanceof EmptyPiece)) {
                        // there is a piece here :c
                        if (currP.getColor() != color) {
                            // enemy piece
                            temp.add(new Point(nextX, nextY));
                        }
                        // Stop searching this way
                        doContinue[j] = false;
                    } else {
                        // no pieces.. add the position
                        temp.add(new Point(nextX, nextY));
                    }
                }
            }
        }
        Point[] tempArr = new Point[temp.size()];
        return temp.toArray(tempArr);
    }
}
