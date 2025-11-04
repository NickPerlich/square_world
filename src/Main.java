package src;

// Main.java
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackBoard blackBoard = new BlackBoard();
            WorldPanel world = new WorldPanel("nick", blackBoard);

            JFrame frame = new JFrame("Multiverse — Step 2");
            frame.setContentPane(world);
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            world.requestFocusInWindow();

            // Test: listen for location changes
            blackBoard.addPropertyChangeListener(evt -> {
                if ("location".equals(evt.getPropertyName())) {
                    System.out.println("Published: " + evt.getNewValue());
                }
            });
        });
    }
}
