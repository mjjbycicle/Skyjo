package app.states;

import app.Game;
import app.Styles;
import core.GameCanvas;
import core.GameObject;
import core.behaviors.TextStyle;
import core.gameobjects.ButtonObject;
import core.gameobjects.ImageObject;
import core.gameobjects.TextObject;
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

    private final TextObject skyjoText = (TextObject) new TextObject(
            "Skyjo",
            Styles.textFont.deriveFont(100f),
            Styles.buttonTextColor,
            TextStyle.TextAlign.ALIGN_CENTER
    ).setPosition(0, -100);

    private final ButtonObject startButton = (ButtonObject) new ButtonObject(
            "Start Game",
            Styles.buttonFont.deriveFont(60f),
            Styles.buttonBGColor,
            Styles.buttonBorderColor,
            Styles.buttonTextColor,
            true
    ).setPosition(new Vec2(0, 360));

    private final ImageObject bg = (ImageObject) new ImageObject("board.jpg").setSize(1920, 1080);

    @Override
    public void draw(GameCanvas canvas) {
        bg.updateAndDraw(canvas);
        startButton.updateAndDraw(canvas);
        skyjoText.updateAndDraw(canvas);
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
