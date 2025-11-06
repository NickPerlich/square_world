package src;

/**
 * Custom update thread (Runnable) that calls WorldPanel.updateMovement()
 * periodically in the background.
 */
public class Thread implements Runnable {
    private final WorldPanel world;
    private volatile boolean running = true;

    public Thread(WorldPanel world) {
        this.world = world;
    }

    @Override
    public void run() {
        while (running) {
            try {
                world.updateMovement();
                java.lang.Thread.sleep(50); // ~20 FPS
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
