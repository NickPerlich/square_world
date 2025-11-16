//package src.main.java;

import org.eclipse.paho.client.mqttv3.*;
import java.awt.Color;

public class MQTTSubscriber {
    private final BlackBoard bb;
    private final String topic;
    private final String brokerUrl;
    private MqttClient client;

    public MQTTSubscriber(BlackBoard bb, String brokerUrl, String topic) {
        this.bb = bb;
        this.topic = topic;
        this.brokerUrl = brokerUrl;

        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId());
            client.connect();
            client.subscribe(topic, (t, msg) -> {
                String payload = new String(msg.getPayload());
                String[] parts = payload.split(",");
                if (parts.length == 6) {
                    String id = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    int r = Integer.parseInt(parts[3]);
                    int g = Integer.parseInt(parts[4]);
                    int b = Integer.parseInt(parts[5]);
                    bb.publishRemoteLocation(id, x, y, new Color(r, g, b)); 
                }
            });
            System.out.println("Subscribed to topic: " + topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            System.out.println("Subscriber disconnected.");
        } catch (Exception ignored) {}
    }
}
