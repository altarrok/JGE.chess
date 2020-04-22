package engine;

public abstract class AbstractGame {
    public abstract void update(GameContainer gc, float dTime);
    public abstract void render(GameContainer gc, Renderer renderer);
}
