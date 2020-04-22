package chess;

import engine.GameContainer;
import engine.Input;
import engine.Renderer;
import engine.gfx.ImageTile;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public abstract class Piece extends GameObject{
    protected enum Color {
        BLACK,
        WHITE,
    }

    // Positions
    protected int x;
    protected int y;

    protected Color color;
    protected ImageTile tile;
    protected boolean selected = false;
    /**
     * Moves of the pieces are represented with Point objects.
     * For ex. Point(2, 2) expresses that a piece can move to (x + 2 * n, y + 2 * n)
     */
    protected Point[] moves;

    public Piece(Color color, String tag) {
        this.tag = color + "_" + tag + "_" + UUID.randomUUID().toString();
        this.color = color;
        this.x = -1;
        this.y = -1;
    }

    public Piece(Color color, String tag, Point initPos) {
        this(color, tag);
        this.x = initPos.x;
        this.y = initPos.y;
    }

    /**
     * Moves the piece to destination in given board
     */
    public boolean move(Board b, Point dest) {
        if (Arrays.asList(availMoves(b)).contains(dest)) {
            this.x = dest.x;
            this.y = dest.y;
            b.play(this, x, y);
            return true;
        }
        return false;
    }

    public void kill() {
        this.dead = true;
    }

    @Override
    public void update(GameContainer gc, float dt) {
        Input i = gc.getInput();
        if (i.isButtonDown(1)) {
            if (((GameManager) gc.getGame()).isTurn() ^ (color == Color.BLACK)) {
                int mX = i.getMouseX();
                int mY = i.getMouseY();
                if (mX > x * 50 && mX < x * 50 + 50 && mY > y * 50 && mY < y * 50 + 50) {
                    selected = true;
                    return;
                }
            }
            selected = false;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImageTile(tile, this.x * 50, this.y * 50, 0, 0);
        if (selected) {
            trippleRect(x, y, 49, 49, 0xff00bd00, r);
            Point[] pArr = availMoves((Board) ((GameManager) gc.getGame()).getObject("board"));
            for (Point point : pArr) {
                trippleRect(point.x, point.y, 49, 49, 0xffbb4600, r);
            }
        }
    }

    private void trippleRect(int offX, int offY, int w, int h, int color, Renderer r) {
        r.drawRect(offX * 50, offY * 50, w, h, color);
        r.drawRect(offX * 50 + 1, offY * 50 + 1, w - 2, h - 2, color);
        r.drawRect(offX * 50 + 2, offY * 50 + 2, w - 4, h - 4, color);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return x == piece.x &&
                y == piece.y &&
                color == piece.color &&
                tag.equals(piece.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color);
    }

    /**
     * Finds available moves of this.
     * @return array of destinations of available moves. this piece can move to any point in returned array
     */
    abstract public Point[] availMoves(Board b);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point[] getMoves() {
        return moves;
    }

    public void setMoves(Point[] moves) {
        this.moves = moves;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
