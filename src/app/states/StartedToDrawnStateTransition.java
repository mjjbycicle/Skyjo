package app.states;

import app.Constants;
import app.Game;
import app.Styles;
import app.objects.Card;
import core.GameCanvas;
import core.gameobjects.ButtonObject;
import core.gameobjects.ImageObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.util.FontLoader;

import java.awt.*;
import java.util.Iterator;

import static java.awt.Color.*;

public class StartedToDrawnStateTransition extends AbstractGameState {
    private Game game;
    private AbstractGameState nextState;
    private Card drawnCard;

    private final ImageObject bg = (ImageObject) new ImageObject("board.jpg").setSize(1920, 1080);

    private final ButtonObject continueButton = (ButtonObject) new ButtonObject(
            "continue",
            Styles.buttonFont,
            new Color(0, 0, 0, 150),
            WHITE,
            GRAY,
            false
    ).setPosition(new Vec2(Constants.CONTINUE_BUTTON_X, Constants.CONTINUE_BUTTON_Y));

    public StartedToDrawnStateTransition(Game g, Card drawnCard) {
        this.game = g;
        this.drawnCard = drawnCard;
        drawnCard.moveTo(Constants.DRAWN_CARD_X, Constants.DRAWN_CARD_Y);
    }

    @Override
    public void draw(GameCanvas canvas) {
        bg.updateAndDraw(canvas);
        game.updateAndDraw(canvas);
        continueButton.updateAndDraw(canvas);
        drawnCard.updateAndDraw(canvas);
    }

    @Override
    public boolean isFinished() {
        if (drawnCard.finishedMoving()) {
            nextState = new TurnStateDrawnCard(game, drawnCard);
        }
        return nextState != null;
    }

    @Override
    public Iterator<? extends AbstractGameState> getStatesAfter() {
        return makeIterator(nextState);
    }

}
