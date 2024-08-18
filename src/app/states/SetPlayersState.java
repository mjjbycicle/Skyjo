package app.states;

import core.GameCanvas;
import core.GameObject;
import core.behaviors.TextStyle;
import core.gameobjects.ButtonObject;
import core.gameobjects.TextObject;
import core.gameobjects.VerticalLayoutObject;
import core.input.MouseEvent;
import core.math.Vec2;
import core.states.AbstractGameState;
import core.states.GameStateGroup;
import core.util.FontLoader;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.awt.Color.*;

public class SetPlayersState extends AbstractGameState {
    private AbstractGameState nextState;

    private List<String> players;

    public SetPlayersState(List<String> players) {
        this.players = players;

        for (String name : players) {
            playerSidebar.addChild(new TextObject(
                    name,
                    FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(40f),
                    WHITE,
                    0
            ).resizeToFit(0));
        }
        playerSidebar.setPosition(new Vec2(0, 0));

        addPlayerButton = (ButtonObject) new ButtonObject(
                "add player",
                FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(60f),
                BLACK,
                WHITE,
                (players.size() != 8)? WHITE : GRAY,
                players.size() != 8
        ).setPosition(new Vec2(0, 360));

        continueButton = (ButtonObject) new ButtonObject(
                "continue",
                FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(60f),
                BLACK,
                WHITE,
                (players.size() >= 2)? WHITE : GRAY,
                players.size() >= 2
        ).setPosition(new Vec2(0, 260));
    }

    public SetPlayersState() {
        this(new ArrayList<>());
    }

    private final ButtonObject addPlayerButton;
    private final ButtonObject continueButton;
    private final GameObject playerSidebar = new VerticalLayoutObject();

    @Override
    public void draw(GameCanvas canvas) {
        addPlayerButton.updateAndDraw(canvas);
        continueButton.updateAndDraw(canvas);
        playerSidebar.updateAndDraw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        if (continueButton.isHovered()) {
            if (players.size() >= 2) {
                System.out.println("continue");
                var theNextState = new TitleState();
                nextState = GameStateGroup.groupStates(
                        new FadeOutScene(this), new FadeInScene(theNextState),
                        theNextState
                );
            }
        }
        else if (addPlayerButton.isHovered()) {
            players.add("Player " + (players.size() + 1));
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
