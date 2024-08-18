package app;

import app.states.TitleState;
import core.StateMachine;
import core.engine.GameWindow;
import core.states.AbstractGameState;

import java.util.Iterator;

public class App extends GameWindow {

    public App() {
        super("Skyjo");
    }


    public void run() {
        StateMachine.run(new AbstractGameState() {
            @Override
            public Iterator<? extends AbstractGameState> getStatesBefore() {
                return makeLoopIterator(TitleState::new, () -> true);
            }
        }, canvas);
    }
}
