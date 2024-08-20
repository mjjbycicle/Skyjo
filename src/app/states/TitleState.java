package app.states;

import app.Game;
import core.GameCanvas;
import core.GameObject;
import core.gameobjects.ButtonObject;
import core.gameobjects.ImageObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.util.FontLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static java.awt.Color.*;

public class TitleState extends AbstractGameState {
    private AbstractGameState nextState;

    private final ButtonObject startButton = (ButtonObject) new ButtonObject(
            "Start Game",
            FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(60f),
            new Color(0, 0, 0, 150),
            WHITE,
            WHITE,
            true
    ).setPosition(new Vec2(0, 360));

    private final ImageObject bg = (ImageObject) new ImageObject("board.jpg").setSize(1920, 1080);

    @Override
    public void draw(GameCanvas canvas) {
        bg.updateAndDraw(canvas);
        startButton.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (startButton.isHovered()) {
            ArrayList<String> players = new ArrayList<>();
            players.add("Player 1");
            players.add("Player 2");
            nextState = new SetPlayersState(players);
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
