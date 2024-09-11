package app.states;

import app.Constants;
import app.Game;
import app.Styles;
import app.objects.Player;
import core.GameCanvas;
import core.behaviors.TextStyle;
import core.gameobjects.ButtonObject;
import core.gameobjects.TextObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.util.FontLoader;

import java.awt.*;
import java.util.Iterator;

import static java.awt.Color.WHITE;

public class GameEndState extends AbstractGameState {
    private final Game game;
    private final Player winner;
    private AbstractGameState nextState;

    private final TextObject winnerText;

    public GameEndState(Game game) {
        this.game = game;
        this.winner = game.getWinner();
        winnerText = (TextObject) new TextObject(
                "Player " + winner.getID() + "wins!",
                Styles.textFont.deriveFont(60f),
                Styles.buttonTextColor,
                TextStyle.TextAlign.ALIGN_CENTER
        ).setPosition(0, -100);
    }

    private final ButtonObject continueButton = (ButtonObject) new ButtonObject(
            "continue",
            FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(40f),
            new Color(0, 0, 0, 150),
            WHITE,
            WHITE,
            true
    ).setPosition(new Vec2(Constants.CONTINUE_BUTTON_X, Constants.CONTINUE_BUTTON_Y));

    @Override
    public void draw(GameCanvas canvas) {
        game.updateAndDrawScores(canvas);
        winnerText.updateAndDraw(canvas);
        continueButton.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (continueButton.isHovered()) {
            nextState = new TitleState();
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
