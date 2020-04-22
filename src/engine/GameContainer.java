package engine;

public class GameContainer implements Runnable {

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;

    public Renderer getRenderer() {
        return renderer;
    }

    private AbstractGame game;

    private int width = 320 , height = 240;
    private int tileSize = 16;
    private float scale = 2f;
    private String title = "AltayoEngine v0.1";

    private boolean drawFPS = true;
    private int fpsOffX = 0;
    private int fpsOffY = 0;
    private int fpsColor = 0xff00ffff;


    private boolean running = false;
    private boolean render = false;
    private double updateLimit = 1.0/60.0;
    private final double NANO_TO_SEC = 1000000000.0;

    public GameContainer(AbstractGame game) {
        this.game = game;
    }

    public void start() {
        window = new Window(this);
        renderer = new Renderer(this);
        thread = new Thread(this);
        input = new Input(this);
        thread.run();
    }

    public void stop() {

    }

    @Override
    public void run() {
        running = true;

        double firsTime = 0;
        double lastTime = System.nanoTime() / NANO_TO_SEC;
        double diffTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while (running) {
            render = false;
            firsTime = System.nanoTime() / NANO_TO_SEC;
            diffTime = firsTime - lastTime;
            lastTime = firsTime;

            unprocessedTime += diffTime;
            frameTime += diffTime;

            // Updating
            while (unprocessedTime >= updateLimit) {
                unprocessedTime -= updateLimit;
                render = true;
                game.update(this, (float) updateLimit);
                input.update();
                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            // Rendering
            if (render) {
                renderer.clear();
                game.render(this, renderer);
                if (drawFPS) {
                    renderer.drawText("FPS: " + fps, fpsOffX, fpsOffY, fpsColor);
                    renderer.drawRect(-10, fpsOffY - 9, width + 10, 50, fpsColor);
                }
                window.update();
                frames++;
            } else {
                try {
                    thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        dispose();
    }

    public void dispose() {

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }

    public void setFPS(double updateLimit) {
        this.updateLimit = 1.0/ updateLimit;
    }

    public void setDrawFPS(boolean drawFPS) {
        this.drawFPS = drawFPS;
    }

    public void setFpsOffX(int fpsOffX) {
        this.fpsOffX = fpsOffX;
    }

    public void setFpsOffY(int fpsOffY) {
        this.fpsOffY = fpsOffY;
    }

    public void setFpsColor(int fpsColor) {
        this.fpsColor = fpsColor;
    }

    public int getTileSize() {
        return tileSize;
    }

    public AbstractGame getGame() {
        return game;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void sleep(long minis) {
        try {
            thread.sleep(minis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
