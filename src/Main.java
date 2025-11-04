package src;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String username = JOptionPane.showInputDialog("Enter your username:");
            BlackBoard bb = new BlackBoard();

            MQTTPublisher publisher = new MQTTPublisher(username);
            MQTTSubscriber subscriber = new MQTTSubscriber(bb);
            bb.addPropertyChangeListener(publisher);

            WorldPanel world = new WorldPanel(username, bb);
            JFrame frame = new JFrame("Multiverse — Step 5");
            frame.setContentPane(world);
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            // Disconnect on close
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    publisher.disconnect();
                    subscriber.disconnect();
                }
            });

            frame.setVisible(true);
            world.requestFocusInWindow();
            world.repaint();
        });
    }
}

