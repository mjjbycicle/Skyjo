package core.engine;

public class GameEngine {
    /**
     * Enables hardware acceleration for rendering and images
     * Usually, this can improve performance by up to 5x, at
     * least in my experience
     * <p>
     * Make sure to call this method before everything else to
     * ensure it affects the rest of your code. THIS IS VERY
     * IMPORTANT.
     */
    public static void enableHardwareAcceleration() {
        // Enable hardware acceleration using opengl (faster than default settings)
        System.setProperty("sun.java2d.opengl", "True");
        // Enable image caching and acceleration immediately when loaded
        System.setProperty("sun.java2d.accthreshold", "0");
    }
}
