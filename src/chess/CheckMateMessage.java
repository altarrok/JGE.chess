package chess;

import engine.GameContainer;
import engine.Renderer;

public class CheckMateMessage extends GameObject {
    double timer = 0;

    @Override
    public void update(GameContainer gc, float dt) {
        if (timer > 3) {
            this.dead = true;
        }
        timer += dt;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.fillRect(gc.getWidth() / 2 - 85, gc.getHeight() / 2 - 35, 169, 69, 0xFF0000CC);
        r.drawText("CHECK MATE", gc.getWidth() / 2 - 77, gc.getHeight() / 2 - 11, 0xFFFFFFFF);
    }
}
