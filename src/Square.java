package src;

import java.awt.Color;
import java.awt.Graphics;

public class Square {
    private int x, y, size;
    private Color color;

    public Square(int x, int y, int size, Color color) {
        this.x = x; 
        this.y = y; 
        this.size = size; 
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

    public void moveBy(int dx, int dy) { x += dx; y += dy; }
    public void clamp(int w, int h) {
        x = Math.max(0, Math.min(x, w - size));
        y = Math.max(0, Math.min(y, h - size));
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
}
