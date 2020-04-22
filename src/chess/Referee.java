package chess;

import chess.pieces.King;

import java.awt.*;

/**
 * Given a board and a move, determines if the move is legal/illegal.
 * Also determines if there is a check, or a mate (checkmate or stalemate).
 */
public class Referee {
    private Board board;

    public Referee(Board b) {
        this.board = b;
    }

    /**
     * Check for:
     *      Checks
     *      Mates
     */

    public boolean play(Board b, int index, int destX, int destY) throws CheckMateException{
        if (isCheckMate(b, index, destX, destY)) {
            throw new CheckMateException();
        }
        return checkForCheck(b, index, destX, destY);
    }

    public boolean checkForCheck(Board b, int index, int destX, int destY) {
        // Check
        Piece.Color playingColor = b.getPiece(index).color;
        King currKing = b.getKing(playingColor);
        Piece pOnDest = b.getPiece(destX, destY);
        Piece pCurr = b.getPiece(index);
        int oldX = pCurr.x;
        int oldY = pCurr.y;
        // Playing the move
        b.play(index, destX, destY);
        boolean playable = !isInCheck(currKing, b);
        pOnDest.setDead(false);
        if (pOnDest instanceof EmptyPiece) {
            b.addEmptyPiece(destX, destY);
        } else {
            b.addPiece(pOnDest);
        }
        pCurr.x = oldX;
        pCurr.y = oldY;
        b.addPiece(pCurr);
        return playable;
    }

    private boolean isInCheck(King king, Board b) {
        Piece currP;
        for (int i = 0; i < 64; i++) {
            currP = b.getPiece(i);
            if (currP instanceof EmptyPiece) continue;
            if (currP.color == king.color) continue;
            Point[] moves = currP.availMoves(b);
            for (Point move : moves) {
                if (move.x == king.x && move.y == king.y) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCheckMate(Board b, int index, int destX, int destY) {
        Piece currP;
        // Check
        Piece.Color playingColor = b.getPiece(index).color;
        King currKing = b.getKing(playingColor);
        Piece pOnDest = b.getPiece(destX, destY);
        Piece pCurr = b.getPiece(index);
        int oldX = pCurr.x;
        int oldY = pCurr.y;
        // Playing the move
        b.play(index, destX, destY);
        for (int i = 0; i < 64; i++) {
            currP = b.getPiece(i);
            if (currP instanceof EmptyPiece) continue;
            if (currP.color == playingColor) continue;
            Point[] moves = currP.availMoves(b);
            for (Point move : moves) {
                if (checkForCheck(b, i, move.x, move.y)) {
                    pOnDest.setDead(false);
                    if (pOnDest instanceof EmptyPiece) {
                        b.addEmptyPiece(destX, destY);
                    } else {
                        b.addPiece(pOnDest);
                    }
                    pCurr.x = oldX;
                    pCurr.y = oldY;
                    b.addPiece(pCurr);
                    return false;
                }
            }
        }
        pOnDest.setDead(false);
        if (pOnDest instanceof EmptyPiece) {
            b.addEmptyPiece(destX, destY);
        } else {
            b.addPiece(pOnDest);
        }
        pCurr.x = oldX;
        pCurr.y = oldY;
        b.addPiece(pCurr);
        return true;
    }
}
