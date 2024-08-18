package app.states;

import app.Game;
import core.GameCanvas;
import core.GameObject;
import core.gameobjects.ButtonObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.util.FontLoader;

import java.awt.*;
import java.util.Iterator;

import static java.awt.Color.*;

public class TitleState extends AbstractGameState {
    private AbstractGameState nextState;

    private final ButtonObject startButton = (ButtonObject) new ButtonObject(
            "Start Game",
            FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(60f),
            BLACK,
            WHITE,
            WHITE,
            true
    ).setPosition(new Vec2(0, 360));

    @Override
    public void draw(GameCanvas canvas) {
        startButton.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (startButton.isHovered()) {
            nextState = new SetPlayersState();
        }
    }

    @Override
    public boolean isFinished() {
        return nextState != null;
    }

    @Override
    public Iterator<? extends AbstractGameState> getStatesAfter() {
        return makeIterator(nextState);
    }
}
