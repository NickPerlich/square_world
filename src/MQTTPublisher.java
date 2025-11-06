package src;

import org.eclipse.paho.client.mqttv3.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MQTTPublisher implements PropertyChangeListener {
    private final String broker = "tcp://broker.hivemq.com:1883";
    private final String username;
    private MqttClient client;

    public MQTTPublisher(String username) {
        this.username = username;
        try {
            client = new MqttClient(broker, MqttClient.generateClientId());
            client.connect();
            System.out.println("Connected to MQTT broker.");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("localLocation".equals(evt.getPropertyName())) {
            BlackBoard.Location loc = (BlackBoard.Location) evt.getNewValue();
            String topic = "csc509/multiverse/" + username + "/";
            String payload = String.format("%s,%d,%d", loc.username(), loc.x(), loc.y());
            try {
                client.publish(topic, payload.getBytes(), 0, false);
                System.out.println("Published: " + payload);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                System.out.println("Publisher disconnected from MQTT.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
