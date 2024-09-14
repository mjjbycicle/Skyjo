package app.states;

import app.Game;
import app.Styles;
import core.GameCanvas;
import core.behaviors.TextStyle;
import core.gameobjects.ButtonObject;
import core.gameobjects.TextObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.states.GameStateGroup;

import java.util.Iterator;
import java.util.List;

public class GameEndedState extends AbstractGameState {
    private AbstractGameState nextState;
    private final Game game;

    private final ButtonObject continueButton = (ButtonObject) new ButtonObject(
            "start new game",
            Styles.buttonFont.deriveFont(60f),
            Styles.buttonBGColor,
            Styles.buttonBorderColor,
            Styles.buttonTextColor,
            true
    ).setPosition(new Vec2(0, 360));

    private final TextObject winnerText;

    public GameEndedState(Game game) {
        this.game = game;
        StringBuilder winText;
        List<Integer> winnerIDs = game.getWinnerIDs();
        if (winnerIDs.size() == 1) {
            winText = new StringBuilder("Player " + (winnerIDs.get(0) + 1) + " wins!");
        } else {
            winText = new StringBuilder("Players ");
            for (int i = 0; i < winnerIDs.size(); i++) {
                if (i == winnerIDs.size() - 1) {
                    winText.append("and ");
                }
                winText.append(winnerIDs.get(i) + 1);
                if (i != winnerIDs.size() - 1) {
                    winText.append(", ");
                }
            }
            winText.append(" won!");
        }
        winnerText = (TextObject) new TextObject(
                winText.toString(),
                Styles.textFont.deriveFont(100f),
                Styles.buttonTextColor,
                TextStyle.TextAlign.ALIGN_CENTER
        ).setPosition(0, 0);
    }

    @Override
    public void draw(GameCanvas canvas) {
        continueButton.updateAndDraw(canvas);
        game.updateAndDrawScores(canvas);
        winnerText.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (continueButton.isHovered()) {
            AbstractGameState theNextState = new TitleState();
            nextState = GameStateGroup.groupStates(
                    new FadeOutScene(this),
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
