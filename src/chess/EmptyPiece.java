package chess;

import engine.GameContainer;
import engine.Renderer;

import java.awt.*;

public class EmptyPiece extends Piece {
    private static EmptyPiece piece = null;

    private EmptyPiece() {
        super(Color.WHITE, "empty_piece");
    }

    public static EmptyPiece getInstance() {
        if (piece == null)
            piece = new EmptyPiece();
        return piece;
    }

    @Override
    public Point[] availMoves(Board b) {
        return new Point[0];
    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer r) {

    }
}
