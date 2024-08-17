package core.engine;

import core.GameCanvas;
import core.math.Vec2;
import core.math.Vec2i;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A basic class representing a game window.
 */
public class GameWindow extends JFrame {
    protected GameCanvas canvas;

    /**
     * @param title Title of the window
     */
    public GameWindow(String title) {
        this(title, new Vec2(1280, 720));
    }

    /**
     * @param title Title of the window
     * @param minWindowSize Minimum size of the window.
     */
    public GameWindow(String title, Vec2 minWindowSize) {
        this(title, minWindowSize, new Vec2(1920, 1080));
    }

    /**
     * @param title Title of the window
     * @param minWindowSize Minimum size of the window.
     * @param referenceWindowResolution The default scale of the drawing window
     *      When the window size is equal to referenceWindowResolution, length
     *      is measured in pixels. Otherwise, length is automatically scaled down
     *      to fit the window size. This parameter lets you avoid coding different
     *      logic for different screen sizes.
     */
    public GameWindow(String title, Vec2 minWindowSize, Vec2 referenceWindowResolution) {
        // Init window
        super();
        setTitle("Kingdom Builder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Get window border
        pack();
        Vec2 border_size = new Vec2(getSize()).minus(new Vec2(getContentPane().getSize()));

        // Init canvas
        canvas = new GameCanvas(new Vec2i(referenceWindowResolution));
        add(canvas);

        // Set size
        setMinimumSize(new Vec2i(minWindowSize.plus(border_size)).toDimension());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set visible
        setVisible(true);

        // Add key listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F11) {
                    dispose();
                    setUndecorated(!isUndecorated());
                    setVisible(true);
                }
            }
        });
    }

    public GameCanvas getCanvas() {
        return canvas;
    }
}