//package src.main.java;

import org.eclipse.paho.client.mqttv3.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MQTTPublisher implements PropertyChangeListener {
    private final String username;
    private final String topic;
    private final String brokerUrl;
    private MqttClient client;

    public MQTTPublisher(String username, String brokerUrl, String topic) {
        this.username = username;
        this.brokerUrl = brokerUrl;
        this.topic = topic;

        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId());
            client.connect();
            System.out.println("Connected to broker: " + brokerUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("localLocation".equals(evt.getPropertyName())) {
            BlackBoard.Location loc = (BlackBoard.Location) evt.getNewValue();
            publish(loc);
        }
    }

    public void publish(BlackBoard.Location loc) {
        try {
            int r = loc.color().getRed();
            int g = loc.color().getGreen();
            int b = loc.color().getBlue();
            String payload = String.format("%s,%d,%d,%d,%d,%d", loc.username(), loc.x(), loc.y(), r, g, b);
            client.publish(topic, new MqttMessage(payload.getBytes()));
            System.out.println("Published: " + payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            System.out.println("Publisher disconnected.");
        } catch (Exception ignored) {}
    }
}