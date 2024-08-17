package core.states;

import core.GameCanvas;
import core.audio.Sound;
import core.input.MouseEvent;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A "scene" in an app.
 *
 * @noinspection EmptyMethod, unused
 */
public abstract class AbstractGameState {
    /**
     * Makes an iterator through the scenes provided
     */
    public static Iterator<AbstractGameState> makeIterator(AbstractGameState... states) {
        return Arrays.stream(states).iterator();
    }

    /**
     * Make a while-loop-like scene iterator
     *
     * @param supplier  A function that returns a GameState, is called repeatedly in the loop
     * @param condition Returns true as long as the loop should execute
     */
    public static Iterator<AbstractGameState> makeLoopIterator(Supplier<AbstractGameState> supplier, Supplier<Boolean> condition) {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return condition.get();
            }

            @Override
            public AbstractGameState next() {
                return supplier.get();
            }
        };
    }

    /**
     * Converts a {@link List} into an iterator over GameStates
     *
     * @param lst The input list to iterate through
     * @param map How to convert the each element of the input list into a GameState
     */
    public static <T> Iterator<AbstractGameState> makeLoopIterator(List<T> lst, Function<T, AbstractGameState> map) {
        return lst.stream().map(map).iterator();
    }

    @SafeVarargs
    public static Iterator<AbstractGameState> joinIterators(Iterator<? extends AbstractGameState>... its) {
        return new Iterator<>() {
            private final Iterator<Iterator<? extends AbstractGameState>> ii = List.of(its).iterator();
            private Iterator<? extends AbstractGameState> current = null;

            @Override
            public boolean hasNext() {
                while (current == null || !current.hasNext()) {
                    if (!ii.hasNext()) return false;
                    current = ii.next();
                }
                return true;
            }

            @Override
            public AbstractGameState next() {
                return current.next();
            }
        };
    }

    public static AbstractGameState groupStates(AbstractGameState... scenes) {
        return new AbstractGameState() {
            @Override
            public Iterator<? extends AbstractGameState> getStatesBefore() {
                return makeIterator(scenes);
            }
        };
    }

    /**
     * @return An {@code Iterator} of {@code Actions} that should be run before this actions executes, may return an
     * indefinite number of Actions. This is queried immediately after the {@link AbstractGameState#onSchedule()} method is called
     */
    public Iterator<? extends AbstractGameState> getStatesBefore() {
        return null;
    }

    /**
     * @return An {@code Iterator} of {@code Action}s that should be run after this actions executes, may
     * return an indefinite number of Actions. This is queried after the {@link AbstractGameState#onExecutionEnd} method is called
     */
    public Iterator<? extends AbstractGameState> getStatesAfter() {
        return null;
    }

    /**
     * Called whenever the scene is active and the mouse is clicked. Guaranteed to be called before update is called
     *
     * @param me The mouse event
     */
    public void onMouseClick(MouseEvent me) {
    }

    /**
     * Called whenever the scene is active and a key is pressed. Guaranteed to be called before update is called
     *
     * @param ke The key event
     */
    public void onKeyPress(KeyEvent ke) {
    }

    /**
     * Called whenever the scene is active and the mouse wheel is moved. Should handle scrolling within the scene.
     * @param distance the distance the wheel is moved
     */
    public void onScroll(int distance) {
    }

    /**
     * Called every frame after input event handlers are called and before {@link AbstractGameState#draw} is called.
     * This method should handle any logic that does not belong in event handlers or the {@link AbstractGameState#draw}
     * method
     */
    public void onUpdate() {
    }

    /**
     * Called at the beginning of each frame after {@link AbstractGameState#isFinished} is queried. This method should handle all
     * drawing logic
     *
     * @param canvas The {@link GameCanvas} on which to draw things
     */
    public void draw(GameCanvas canvas) {
    }

    /**
     * Called as soon as the Action is scheduled. Any initialization up not done in the constructor should happen here.
     */
    public void onSchedule() {
    }

    /**
     * Called when all the pre-actions are finished executing.
     */
    public void onExecutionStart() {
    }

    /**
     * Called as soon as the Action finishes executing
     */
    public void onExecutionEnd() {
    }

    /**
     * @return Whether the Action has finished executing
     */
    public boolean isFinished() {
        return true;
    }

    public Sound getBGM () { return null; }
}