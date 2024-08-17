package core;

import core.audio.Sound;
import core.input.Input;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;

import javax.swing.*;
import java.awt.event.*;
import java.util.Queue;
import java.util.*;

public class StateMachine {
    protected final Stack<Iterator<? extends AbstractGameState>> scheduleStack;
    protected final Stack<AbstractGameState> sceneStack;
    protected final Queue<MouseEvent> mouseEvents = new ArrayDeque<>();
    protected int wheelMovement = 0;
    protected final Queue<KeyEvent> keyEvents = new ArrayDeque<>();
    protected final GameCanvas canvas;
    protected final double delay;
    protected AbstractGameState lastExecutedGameState;

    protected StateMachine(AbstractGameState a, double fps, GameCanvas canvas) {
        sceneStack = new Stack<>();
        scheduleStack = new Stack<>();

        this.delay = 1000 / fps;

        this.canvas = canvas;

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                MouseEvent.MouseButton button = MouseEvent.MouseButton.NONE;

                if (SwingUtilities.isLeftMouseButton(e)) {
                    button = MouseEvent.MouseButton.LEFT;
                    Input.__mouseDownL = true;
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    button = MouseEvent.MouseButton.RIGHT;
                    Input.__mouseDownR = true;
                }
                Vec2 pos = new Vec2(e.getPoint().x, e.getPoint().y).minus(canvas.get_size().times(.5)).times(1 / canvas.getScale());
                mouseEvents.add(new MouseEvent(pos, button));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) Input.__mouseDownL = false;
                if (SwingUtilities.isRightMouseButton(e)) Input.__mouseDownR = false;
            }
        });

        SwingUtilities.getWindowAncestor(canvas).addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                Input.__keysDown.add(e.getKeyChar());
                keyEvents.add(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Input.__keysDown.remove(e.getKeyChar());
            }
        });

        SwingUtilities.getWindowAncestor(canvas).addMouseWheelListener(e -> wheelMovement += e.getWheelRotation());

        scheduleAction(new AbstractGameState() {
            @Override
            public Iterator<AbstractGameState> getStatesBefore() {
                return Collections.singletonList(a).iterator();
            }
        });

        run();
    }

    public static void run(AbstractGameState a, GameCanvas component) {
        run(a, component, 60);
    }

    public static void run(AbstractGameState a, GameCanvas component, double fps) {
        new StateMachine(a, fps, component);
    }

    protected void run() {
        new Thread(() -> {
            while (true) {
                if (sceneStack.isEmpty()) break;

                AbstractGameState currentGameState = sceneStack.peek();
                checkExecutionStart(currentGameState);

                if (currentGameState.isFinished()) {
                    // Finish this action
                    currentGameState.onExecutionEnd();
                    // Remove action from stack
                    sceneStack.pop();

                    Iterator<? extends AbstractGameState> scenesAfter = currentGameState.getStatesAfter();
                    if (scenesAfter != null && scenesAfter.hasNext()) {
                        scheduleAction(new AbstractGameState() {
                            @Override
                            public Iterator<? extends AbstractGameState> getStatesBefore() {
                                return scenesAfter;
                            }
                        });
                    } else {
                        // No more actions to execute, quit the event loop
                        if (scheduleStack.isEmpty()) break;
                            // Get the next action
                        else scheduleNextAction();
                    }
                } else {
                    executeAction(currentGameState);

                    try {
                        //noinspection BusyWait
                        Thread.sleep((long) delay);
                    } catch (Exception e) {/*Nothing*/}
                }
            }
        }).start();
    }

    protected void scheduleAction(AbstractGameState s) {
        sceneStack.push(s);

        s.onSchedule();

        Iterator<? extends AbstractGameState> scenesBefore = s.getStatesBefore();
        if (scenesBefore != null && scenesBefore.hasNext()) {
            scheduleStack.push(scenesBefore);
            scheduleAction(scenesBefore.next());
        }
    }

    protected void scheduleNextAction() {
        if (scheduleStack.isEmpty() || sceneStack.isEmpty())
            throw new Error("Pop off schedule stack when empty");   //hopefully never happens

        Iterator<? extends AbstractGameState> schedule = scheduleStack.peek();

        if (schedule != null && schedule.hasNext()) {
            AbstractGameState nextGameState = schedule.next();
            scheduleAction(nextGameState);
        } else {
            scheduleStack.pop();
        }
    }

    private Sound currSound = Sound.SILENCE;

    protected void checkExecutionStart(AbstractGameState scene) {
        if (scene != lastExecutedGameState) {
            scene.onExecutionStart();
            lastExecutedGameState = scene;
        }

        if(scene.getBGM() != null && !currSound.equals(scene.getBGM())){
            currSound.stop();
            currSound = scene.getBGM();
            currSound.playLooping();
        }
    }

    protected void executeAction(AbstractGameState scene) {
        Input.__setMousePosition(canvas.getMousePos());

        synchronized (canvas.synchronizer){
            scene.onUpdate();

            while (!mouseEvents.isEmpty())
                scene.onMouseClick(mouseEvents.remove());

            while (!keyEvents.isEmpty())
                scene.onKeyPress(keyEvents.remove());

            if(wheelMovement != 0)
                scene.onScroll(wheelMovement);
            wheelMovement = 0;
        }

        canvas.repaint(scene);
    }
}