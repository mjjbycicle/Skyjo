package app.states;

import app.Constants;
import app.Game;
import core.GameCanvas;
import core.gameobjects.ButtonObject;
import core.gameobjects.ImageObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.states.GameStateGroup;
import core.util.FontLoader;

import java.awt.*;
import java.util.Iterator;

import static java.awt.Color.WHITE;

public class TurnFinishedState extends AbstractGameState {
    private Game game;
    private AbstractGameState nextState;

    private final ImageObject bg = (ImageObject) new ImageObject("board.jpg").setSize(1920, 1080);

    private final ButtonObject continueButton = (ButtonObject) new ButtonObject(
            "continue",
            FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(40f),
            new Color(0, 0, 0, 150),
            WHITE,
            WHITE,
            true
    ).setPosition(new Vec2(Constants.CONTINUE_BUTTON_X, Constants.CONTINUE_BUTTON_Y));

    public TurnFinishedState(Game game) {
        this.game = game;
    }

    @Override
    public void draw(GameCanvas canvas) {
        bg.updateAndDraw(canvas);
        game.updateAndDraw(canvas);
        continueButton.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (continueButton.isHovered()) {
            if (game.isLastRound()) {
                if (game.getActivePlayerIndex() == game.getNumPlayers() - 1) {
                    if (game.isGameFinished()){
                        game.scoreRound();
                        nextState = GameStateGroup.groupStates(
                                new FadeOutScene(this),
                                new GameEndState(game)
                        );
                    } else {
                        game.scoreRound();
                        nextState = GameStateGroup.groupStates(
                                new FadeOutScene(this),
                                new RoundEndState(game)
                        );
                        return;
                    }
                }
            }
            AbstractGameState theNextState = new BetweenTurnsState(game);
            nextState = GameStateGroup.groupStates(
                    new FadeOutScene(this),
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
