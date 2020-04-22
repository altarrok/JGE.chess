package engine.gfx;

public class ImageTile extends Image {
    private int tileWidth, tileHeight;
    public ImageTile(String path, int tileWidth, int tileHeight) {
        super(path);
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }
}
