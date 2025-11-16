//package src.main.java;

import java.awt.Color;
import java.awt.Graphics;

public class Square {
    private int x, y, size;
    private final Color color;

    public Square(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void moveTo(int nx, int ny) {
        x = nx;
        y = ny;
    }

    public void clamp(int width, int height) {
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + size > width) x = width - size;
        if (y + size > height) y = height - size;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

    public Color getColor() { return color; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
}
