package engine;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private GameContainer gc;

    private final int NUM_KEYS = 256;
    private boolean[] keysOld = new boolean[NUM_KEYS];
    private boolean[] keys = new boolean[NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private boolean[] mouseButtonsOld = new boolean[NUM_BUTTONS];
    private boolean[] mouseButtons = new boolean[NUM_BUTTONS];

    private int mouseX, mouseY;
    //scroll : + => down; 0 => still; - => up;
    private int scroll;

    public Input(GameContainer gc) {
        this.gc = gc;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
        gc.getWindow().getCanvas().addMouseWheelListener(this);
    }

    public void update() {
        scroll = 0;

        for (int i = 0; i < NUM_KEYS; i++) {
            keysOld[i] = keys[i];
        }

        for (int i = 0; i < NUM_BUTTONS; i++) {
            mouseButtonsOld[i] = mouseButtons[i];
        }
    }

    public boolean isKey(int keyCode) {
        return keys[keyCode];
    }

    public boolean isKeyUp(int keyCode) {

        return !keys[keyCode] && keysOld[keyCode];
    }

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysOld[keyCode];
    }

    public boolean isButton(int buttonCode) {
        return mouseButtons[buttonCode];
    }

    public boolean isButtonUp(int buttonCode) {

        return !mouseButtons[buttonCode] && mouseButtons[buttonCode];
    }

    public boolean isButtonDown(int buttonCode) {
        return mouseButtons[buttonCode] && !mouseButtonsOld[buttonCode];
    }

    /**
     * KEYBOARD =================================
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    /**
     * MOUSE ===================================
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButtons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseButtons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX() / gc.getScale());
        mouseY = (int) (e.getY() / gc.getScale());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }
}
