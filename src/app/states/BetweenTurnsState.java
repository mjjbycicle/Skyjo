package app.states;

import app.Constants;
import app.Game;
import core.GameCanvas;
import core.behaviors.TextStyle;
import core.gameobjects.ButtonObject;
import core.gameobjects.ImageObject;
import core.gameobjects.TextObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.states.GameStateGroup;
import core.util.FontLoader;

import java.awt.*;
import java.util.Iterator;

import static java.awt.Color.WHITE;

public class BetweenTurnsState extends AbstractGameState {
    private Game game;
    private AbstractGameState nextState;

    private final ButtonObject continueButton = (ButtonObject) new ButtonObject(
            "start turn",
            FontLoader.load("font/TitilliumWeb-SemiBold.ttf").deriveFont(40f),
            new Color(40, 160, 160, 50),
            new Color(18, 150, 255),
            WHITE,
            true
    ).setPosition(new Vec2(Constants.START_TURN_BUTTON_X, Constants.START_TURN_BUTTON_Y));

    private final TextObject startTurnText;

    public BetweenTurnsState(Game game) {
        this.game = game;
        startTurnText = (TextObject) new TextObject(
                "Player " + (game.getActivePlayerIndex() + 1),
                FontLoader.load("font/TitilliumWeb-ExtraLight.ttf").deriveFont(60f),
                WHITE,
                TextStyle.TextAlign.ALIGN_CENTER
        ).setPosition(new Vec2(Constants.START_TURN_TEXT_X, Constants.START_TURN_TEXT_Y));
    }

    @Override
    public void draw(GameCanvas canvas) {
        continueButton.updateAndDraw(canvas);
        startTurnText.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (continueButton.isHovered()) {
            AbstractGameState theNextState = new TurnStartedState(game);
            nextState = GameStateGroup.groupStates(
                    new FadeInScene(theNextState),
                    theNextState
            );
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
