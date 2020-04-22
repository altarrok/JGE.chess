package chess;

import chess.pieces.*;
import engine.GameContainer;
import engine.Input;
import engine.Renderer;

import java.awt.*;
import java.util.ArrayList;

public class Board extends GameObject {
    private Piece[] boardArr;
    public boolean checkmate;

    public Board() {
        this.tag = "board";
        boardArr = new Piece[64];
        // Filling boardArr with empty pieces
        for (int i = 0; i < boardArr.length; i++) {
            boardArr[i] = EmptyPiece.getInstance();
        }
    }

    /**
     * Adds given piece to the board at given position.
     */
    public void addPiece(Piece p) {
        boardArr[p.getX() + p.getY() * 8] = p;
    }

    public void addEmptyPiece(int x, int y) {
        boardArr[x + y * 8] = EmptyPiece.getInstance();
    }

    /**
     * Moves given piece to the given position if piece is in boardArr
     * Finds the piece from boardArr and changes its position
     * If necessary kills the piece on the destination
     */
    public void play(Piece p, int x, int y) {
        for (int i = 0; i < boardArr.length; i++) {
            if (boardArr[i].equals(p)) {
                // boardArr[i] is p
                if (!(boardArr[x + y * 8] instanceof EmptyPiece)) {
                    // there is a piece at destination => kill it
                    boardArr[x + y * 8].kill();
                }
                boardArr[x + y * 8] = boardArr[i];
                boardArr[i] = EmptyPiece.getInstance();
                return;
            }
        }
    }

    /**
     * Moves the piece at given location to given destination
     * @param currX current x coord of the piece that will be moved
     * @param currY current y coord of the piece that will be moved
     * @param destX x coord of destination
     * @param destY y coord of destination
     */
    public void play(int currX, int currY, int destX, int destY) {
        boardArr[currX + currY * 8].move(this, new Point(destX, destY));
    }

    public void play(int i, int destX, int destY) {
        boardArr[i].move(this, new Point(destX, destY));
    }

    public void setup(ArrayList<GameObject> objects) {
        // WHITE PIECES
        add2Both(objects, new Rook(Piece.Color.WHITE, new Point(7,7)));
        add2Both(objects, new Rook(Piece.Color.WHITE, new Point(0,7)));
        add2Both(objects, new Knight(Piece.Color.WHITE, new Point(1, 7)));
        add2Both(objects, new Knight(Piece.Color.WHITE, new Point(6, 7)));
        add2Both(objects, new Bishop(Piece.Color.WHITE, new Point(2, 7)));
        add2Both(objects, new Bishop(Piece.Color.WHITE, new Point(5, 7)));
        add2Both(objects, new Queen(Piece.Color.WHITE, new Point(3,7)));
        add2Both(objects, new King(Piece.Color.WHITE, new Point(4,7)));
        for (int i = 0; i < 8; i++) {
            add2Both(objects, new Pawn(Piece.Color.WHITE, new Point(i, 6)));
        }

        // BLACK PIECES
        add2Both(objects, new Rook(Piece.Color.BLACK, new Point(7,0)));
        add2Both(objects, new Rook(Piece.Color.BLACK, new Point(0,0)));
        add2Both(objects, new Knight(Piece.Color.BLACK, new Point(1, 0)));
        add2Both(objects, new Knight(Piece.Color.BLACK, new Point(6, 0)));
        add2Both(objects, new Bishop(Piece.Color.BLACK, new Point(2, 0)));
        add2Both(objects, new Bishop(Piece.Color.BLACK, new Point(5, 0)));
        add2Both(objects, new Queen(Piece.Color.BLACK, new Point(3,0)));
        add2Both(objects, new King(Piece.Color.BLACK, new Point(4,0)));
        for (int i = 0; i < 8; i++) {
            add2Both(objects, new Pawn(Piece.Color.BLACK, new Point(i, 1)));
        }
    }

    private void add2Both(ArrayList<GameObject> objects, Piece p) {
        objects.add(p);
        addPiece(p);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        // Find selected piece
        // if clicked position is inside where selected piece can move => move it
        Input i = gc.getInput();
        if (i.isButtonDown(1)) {
            int selectedIndex = -1;
            for (int it = 0; it < boardArr.length; it++) {
                if (boardArr[it].isSelected()) {
                    selectedIndex = it;
                    break;
                }
            }
            if (selectedIndex != -1) {
                GameManager gm = ((GameManager) gc.getGame());
                Piece p = boardArr[selectedIndex];
                int mX = i.getMouseX();
                int mY = i.getMouseY();
                Point[] points = p.availMoves(this);
                for (Point point : points) {
                    if (mX > point.x * 50 && mX < point.x * 50 + 50 && mY > point.y * 50 && mY < point.y * 50 + 50) {
                        try {
                            if (gm.getRef().play(this, selectedIndex, point.x, point.y)) {
                                play(selectedIndex, point.x, point.y);
                                gm.setTurn(p.color != Piece.Color.WHITE);
                            }
                        } catch (CheckMateException e) {
                            play(selectedIndex, point.x, point.y);
                            gm.setTurn(p.color != Piece.Color.WHITE);
                            checkmate = true;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        for (int row = 0; row < 8; row++) {
            for (int i = 0; i < 8; i++) {
                if ((row + i) % 2 == 0) {
                    r.fillRect(i * 50, row * 50, 49, 49, 0xFFe8ebef);
                } else {
                    r.fillRect(i * 50, row * 50, 49, 49, 0xFF7d8796);
                }
            }
        }
    }

    public Piece getPiece(int x, int y) {
        return boardArr[x + y * 8];
    }

    public Piece getPiece(int i) {
        return boardArr[i];
    }

    public King getKing(Piece.Color color) {
        for (Piece piece : boardArr) {
            if (piece instanceof King && piece.color == color) {
                return (King) piece;
            }
        }
        return null;
    }
}
