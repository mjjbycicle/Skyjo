package app.states;

import app.Constants;
import app.Game;
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

import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;

public class TurnStateDrawnCard extends AbstractGameState {
    private final Game game;
    private AbstractGameState nextState;
    private final Card drawnCard;

    private final ImageObject bg = (ImageObject) new ImageObject("board.jpg").setSize(1920, 1080);

    private final ButtonObject continueButton = (ButtonObject) new ButtonObject(
            "continue",
            FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(40f),
            new Color(0, 0, 0, 150),
            WHITE,
            GRAY,
            false
    ).setPosition(new Vec2(Constants.CONTINUE_BUTTON_X, Constants.CONTINUE_BUTTON_Y));

    public TurnStateDrawnCard(Game game, Card drawnCard) {
        this.game = game;
        this.drawnCard = drawnCard;
    }

    @Override
    public void draw(GameCanvas canvas) {
        bg.updateAndDraw(canvas);
        game.updateAndDraw(canvas);
        continueButton.updateAndDraw(canvas);
        drawnCard.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (game.matrixClicked()) {
            Vec2 clickedIndex = game.getClickedIndex();
            Card thrown = game.matrixReplaceCard(drawnCard);
            thrown.setFaceDown(false);
            nextState = new DrawnToReplaceStateTransition(game, drawnCard, thrown, clickedIndex);
        } else if (game.discardDeckClicked()) {
            nextState = new DrawnToDiscardedStateTransition(game, drawnCard);
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
