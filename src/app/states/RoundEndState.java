package app.states;

import app.Game;
import app.Styles;
import core.GameCanvas;
import core.gameobjects.ButtonObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.states.GameStateGroup;

import java.util.Iterator;

public class RoundEndState extends AbstractGameState {
    private AbstractGameState nextState;
    private Game game;

    private final ButtonObject startButton = (ButtonObject) new ButtonObject(
            "start next round",
            Styles.buttonFont.deriveFont(60f),
            Styles.buttonBGColor,
            Styles.buttonBorderColor,
            Styles.buttonTextColor,
            true
    ).setPosition(new Vec2(0, 360));

    public RoundEndState(Game game) {
        this.game = game;
    }

    @Override
    public void draw(GameCanvas canvas) {
        startButton.updateAndDraw(canvas);
        game.updateAndDrawScores(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (startButton.isHovered()) {
            game.advanceRound();
            AbstractGameState theNextState = new DealCardsState(game);
            nextState = GameStateGroup.groupStates(
                    new FadeOutScene(this),
                    new FadeInScene(theNextState),
                    theNextState,
                    new TurnStartedState(game)
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
