package src;

// WorldPanel.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WorldPanel extends JPanel implements KeyListener {
    private final Square me = new Square(100, 100, 32, new Color(0x3A86FF));
    private final BlackBoard blackBoard;
    private final String username;

    public WorldPanel(String username, BlackBoard blackBoard) {
        this.username = username;
        this.blackBoard = blackBoard;

        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        me.draw(g);
    }

    @Override public void keyPressed(KeyEvent e) {
        int step = 10;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> me.moveBy(0, -step);
            case KeyEvent.VK_DOWN -> me.moveBy(0, step);
            case KeyEvent.VK_LEFT -> me.moveBy(-step, 0);
            case KeyEvent.VK_RIGHT -> me.moveBy(step, 0);
        }
        me.clamp(getWidth(), getHeight());
        repaint();

        // Notify Blackboard of new position
        blackBoard.publishLocation(username, me.getX(), me.getY());
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
