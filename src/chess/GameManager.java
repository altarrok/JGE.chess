package chess;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;

import java.util.ArrayList;

public class GameManager extends AbstractGame {
    /**
     * GAME CONSTANTS =========================
     */
    private static final int tileSize = 50;
    private static final int tiledWidth = 8;
    private static final int tiledHeight = 8;
    private static final int width = tileSize*tiledWidth;
    private static final int height = tileSize*tiledHeight;
    private static final float scale = 2.3f; // 2.5 is limit
    private static final double wantedFps = 240.0;


    private ArrayList<GameObject> objects = new ArrayList<>();
    // if true => turn of white
    private boolean turn = true;
    private boolean playing = false;
    private Referee ref;

    public GameManager() {
        Board b = new Board();
        ref = new Referee(b);
        objects.add(b);
        b.setup(objects);
    }

    @Override
    public void update(GameContainer gc, float dTime) {
        for (int i = 0; i < objects.size(); i++) {
            // Updating objects
            objects.get(i).update(gc, dTime);
            // Removing objects
            if (objects.get(i).isDead()) {
                objects.remove(i);
                i--;
            }
        }
        if (((Board) getObject("board")).checkmate) {
            objects.add(new CheckMateMessage());
            ((Board) getObject("board")).checkmate = false;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        for (GameObject object : objects) {
            object.render(gc, renderer);
        }
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(width);
        gc.setHeight(height);
        gc.setScale(scale);
        gc.setFPS(wantedFps);
        gc.setDrawFPS(false);
        gc.setTileSize(tileSize);
        gc.start();
    }

    public GameObject getObject(String tag) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getTag().equals(tag)) {
                return objects.get(i);
            }
        }
        return null;
    }

    public boolean isTurn() {
        return turn;
    }

    public Referee getRef() {
        return ref;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
