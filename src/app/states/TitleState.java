package app.states;

import app.Styles;
import core.GameCanvas;
import core.behaviors.TextStyle;
import core.gameobjects.ButtonObject;
import core.gameobjects.ImageObject;
import core.gameobjects.TextObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.states.GameStateGroup;

import java.util.ArrayList;
import java.util.Iterator;

public class TitleState extends AbstractGameState {
    private AbstractGameState nextState;

    private final ButtonObject startButton = (ButtonObject) new ButtonObject(
            "Start Game",
            Styles.buttonFont.deriveFont(60f),
            Styles.buttonBGColor,
            Styles.buttonBorderColor,
            Styles.buttonTextColor,
            true
    ).setPosition(new Vec2(0, 360));

    private final TextObject titleText = (TextObject) new TextObject(
            "Skyjo",
            Styles.textFont.deriveFont(120f),
            Styles.buttonTextColor,
            TextStyle.TextAlign.ALIGN_CENTER
    ).setPosition(0, -100);

    private final ImageObject bg = (ImageObject) new ImageObject("board.jpg").setSize(1920, 1080);

    @Override
    public void draw(GameCanvas canvas) {
        bg.updateAndDraw(canvas);
        startButton.updateAndDraw(canvas);
        titleText.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (startButton.isHovered()) {
            ArrayList<String> players = new ArrayList<>();
            players.add("Player 1");
            players.add("Player 2");
            AbstractGameState theNextState = new SetPlayersState(players);
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
