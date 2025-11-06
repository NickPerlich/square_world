package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldPanel extends JPanel implements KeyListener, PropertyChangeListener {
    private final Map<String, Square> avatars = new ConcurrentHashMap<>();
    private final String username;
    private final BlackBoard blackBoard;
    private final Set<Integer> pressedKeys = new HashSet<>();

    public WorldPanel(String username, BlackBoard blackBoard) {
        this.username = username;
        this.blackBoard = blackBoard;

        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        blackBoard.addPropertyChangeListener(this);

        avatars.put(username, new Square(100, 100, 32, Color.BLUE));
        System.out.println("WorldPanel created, avatars = " + avatars.size());

        SwingUtilities.invokeLater(() -> {
            Square me = avatars.get(username);
            int w = getWidth();
            int h = getHeight();
            int size = me.getSize();
            me.moveTo(w / 2 - size / 2, h / 2 - size / 2);
            repaint();
        });
    }

    /** Called repeatedly by background thread */
    public void updateMovement() {
        try {
            Square me = avatars.get(username);
            if (me == null) return;
            int step = 5;

            if (pressedKeys.contains(KeyEvent.VK_UP)) me.moveBy(0, -step);
            if (pressedKeys.contains(KeyEvent.VK_DOWN)) me.moveBy(0, step);
            if (pressedKeys.contains(KeyEvent.VK_LEFT)) me.moveBy(-step, 0);
            if (pressedKeys.contains(KeyEvent.VK_RIGHT)) me.moveBy(step, 0);

            me.clamp(getWidth(), getHeight());
            repaint();

            if (!pressedKeys.isEmpty()) {
                blackBoard.publishLocalLocation(username, me.getX(), me.getY());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Square s : avatars.values()) {
            s.draw(g);
        }
    }

    @Override public void keyPressed(KeyEvent e) { pressedKeys.add(e.getKeyCode()); }
    @Override public void keyReleased(KeyEvent e) { pressedKeys.remove(e.getKeyCode()); }
    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("remoteLocation".equals(evt.getPropertyName())) {
            BlackBoard.Location loc = (BlackBoard.Location) evt.getNewValue();
            if (!loc.username().equals(username)) {
                avatars.putIfAbsent(loc.username(),
                        new Square(loc.x(), loc.y(), 32, Color.RED));
                avatars.get(loc.username()).moveTo(loc.x(), loc.y());
                repaint();
            }
        }
    }
}
