package src;

// BlackBoard.java
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

    public void publishLocation(String username, int x, int y) {
        pcs.firePropertyChange("location", null, new Location(username, x, y));
    }

    // inner record for now
    public record Location(String username, int x, int y) {}
}

