package engine;

import engine.gfx.Font;
import engine.gfx.Image;
import engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class Renderer {
    private final int BLACK = 0xff000000;
    private final int WHITE = 0xffffffff;
    private final int GREEN = 0xff00ff00;
    private int ambientColor = BLACK;
    private int pixelWidth, pixelHeight;
    private int[] pixels;
    private Font font = Font.BIG;

    public Renderer(GameContainer gc) {
        pixelWidth = gc.getWidth();
        pixelHeight = gc.getHeight();
        pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = ambientColor;
        }
    }

    public void drawImage(Image image, int offX, int offY) {
        if (offX < -image.getWidth() || offY < -image.getHeight() || offX >= pixelWidth || offY >= pixelHeight) return;
        int tmpX = 0;
        int tmpY = 0;
        int tmpWidth = image.getWidth();
        int tmpHeight = image.getHeight();

        if ((tmpWidth + offX) >= pixelWidth) tmpWidth = pixelWidth - offX;
        if ((tmpHeight + offY) >= pixelHeight) tmpHeight = pixelHeight - offY;
        if (offX < 0) tmpX -= offX;
        if (offY < 0) tmpY -= offY;


        for (int y = tmpY; y < tmpHeight; y++) {
            for (int x = tmpX; x < tmpWidth; x++) {
                setPixel(x + offX, y+ offY, image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }

    public void setPixel(int x, int y, int value) {
        if ((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) || value == 0xffff00ff) return;

        pixels[x + y * pixelWidth] = value;
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        if (offX < -image.getTileWidth() || offY < -image.getTileHeight() || offX >= pixelWidth || offY >= pixelHeight) return;
        int tmpX = 0;
        int tmpY = 0;
        int tmpWidth = image.getTileWidth();
        int tmpHeight = image.getTileHeight();

        if ((tmpWidth + offX) >= pixelWidth) tmpWidth = pixelWidth - offX;
        if ((tmpHeight + offY) >= pixelHeight) tmpHeight = pixelHeight - offY;
        if (offX < 0) tmpX -= offX;
        if (offY < 0) tmpY -= offY;


        for (int y = tmpY; y < tmpHeight; y++) {
            for (int x = tmpX; x < tmpWidth; x++) {
                setPixel(x + offX,
                        y + offY,
                        image.getPixels()[(x + tileX * image.getTileWidth()) +
                                (y + tileY * image.getTileHeight()) * image.getWidth()]);
            }
        }
    }

    public void drawText(String text, int offX, int offY, int color) {
        int offset = 0;
        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i);

            for (int y = 0; y < font.getFontImage().getHeight(); y++) {
                for (int x = 0; x < font.getWidths()[unicode]; x++) {
                    if (font.getFontImage().getPixels()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == WHITE) {
                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }

            offset += font.getWidths()[unicode];
        }
    }

    public void drawRect(int offX, int offY, int width, int height, int color) {
        for (int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }
        for (int x = 0; x <= width; x++) {
            setPixel(offX + x, offY, color);
            setPixel(offX + x, offY + height, color);
        }
    }

    public void fillRect(int offX, int offY, int width, int height, int color) {
        if (offX < -width || offY < -height || offX >= pixelWidth || offY >= pixelHeight) return;
        int tmpX = 0;
        int tmpY = 0;
        int tmpWidth = width;
        int tmpHeight = height;

        if ((tmpWidth + offX) >= pixelWidth) tmpWidth = pixelWidth - offX;
        if ((tmpHeight + offY) >= pixelHeight) tmpHeight = pixelHeight - offY;
        if (offX < 0) tmpX -= offX;
        if (offY < 0) tmpY -= offY;

        for (int y = tmpY; y <= tmpHeight; y++) {
            for (int x = tmpX; x <= tmpWidth; x++) {
                setPixel(x + offX, y + offY, color);
            }
        }
    }

    public int getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(int ambientColor) {
        this.ambientColor = ambientColor;
    }
}
