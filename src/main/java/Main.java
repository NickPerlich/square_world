//package src.main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String username = JOptionPane.showInputDialog("Enter your username:");
            if (username == null || username.isEmpty()) username = "user" + System.currentTimeMillis();

            // Get color 
            String colorInput = JOptionPane.showInputDialog("Enter your color (name or R,G,B):", "0,0,255");
            Color color = parseColor(colorInput);

            // Get broker 
            String brokerUrl = JOptionPane.showInputDialog("Enter MQTT broker URL:", "tcp://broker.hivemq.com:1883");
            if (brokerUrl == null || brokerUrl.isEmpty()) brokerUrl = "tcp://broker.hivemq.com:1883";

            // Get topic 
            String topic = JOptionPane.showInputDialog("Enter topic:", "calpoly/csc509/brokerverse");
            if (topic == null || topic.isEmpty()) topic = "calpoly/csc509/brokerverse";

            // Create shared Blackboard
            BlackBoard bb = new BlackBoard();

            // Pass topic and broker to MQTT classes
            MQTTPublisher publisher = new MQTTPublisher(username, brokerUrl, topic);
            MQTTSubscriber subscriber = new MQTTSubscriber(bb, brokerUrl, topic);
            bb.addPropertyChangeListener(publisher);

            // Create game window
            WorldPanel world = new WorldPanel(username, bb, color);
            JFrame frame = new JFrame("Multiverse");
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

            // Start update loop thread
            Thread loop = new Thread(world);
            new java.lang.Thread(loop).start();
        });
    }

    private static Color parseColor(String input) {
        try {
            if (input.contains(",")) {
                String[] parts = input.split(",");
                int r = Integer.parseInt(parts[0].trim());
                int g = Integer.parseInt(parts[1].trim());
                int b = Integer.parseInt(parts[2].trim());
                return new Color(r, g, b);
            } else {
                // Try predefined color name
                return (Color) Color.class.getField(input.toLowerCase()).get(null);
            }
        } catch (Exception e) {
            return Color.BLUE; // fallback
        }
    }
}
