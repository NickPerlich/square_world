package src;

import org.eclipse.paho.client.mqttv3.*;

public class MQTTSubscriber {
    private final BlackBoard blackBoard;
    private final String broker = "tcp://broker.hivemq.com:1883";
    private MqttClient client;

    public MQTTSubscriber(BlackBoard blackBoard) {
        this.blackBoard = blackBoard;
        try {
            client = new MqttClient(broker, MqttClient.generateClientId());
            client.connect();
            client.subscribe("csc509/multiverse/#", (topic, msg) -> {
                String payload = new String(msg.getPayload());
                String[] parts = payload.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    blackBoard.publishRemoteLocation(username, x, y);
                }
            });
            System.out.println("Subscribed to csc509/multiverse/#");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                System.out.println("Subscriber disconnected from MQTT.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}



