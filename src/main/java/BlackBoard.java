//package src.main.java;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BlackBoard {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    // Local events (from your own movement)
    public void publishLocalLocation(String username, int x, int y, Color color) {
        pcs.firePropertyChange("localLocation", null, new Location(username, x, y, color));
    }

    // Remote events (from MQTT messages)
    public void publishRemoteLocation(String username, int x, int y, Color color) {
        pcs.firePropertyChange("remoteLocation", null, new Location(username, x, y, color));
    }

    public record Location(String username, int x, int y, Color color) {}
}
