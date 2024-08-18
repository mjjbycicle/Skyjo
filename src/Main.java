import app.App;
import core.engine.GameEngine;

public class Main {
    public static void main(String[] args) {
        GameEngine.enableHardwareAcceleration();
        new App().run();
    }
}