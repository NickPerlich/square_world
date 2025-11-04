package src;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BlackBoard {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    // Called when you move locally
    public void publishLocalLocation(String username, int x, int y) {
        pcs.firePropertyChange("localLocation", null, new Location(username, x, y));
    }

    // Called when MQTT receives someone else’s movement
    public void publishRemoteLocation(String username, int x, int y) {
        pcs.firePropertyChange("remoteLocation", null, new Location(username, x, y));
    }

    public record Location(String username, int x, int y) {}
}
