package core.behaviors;

import core.UIBehavior;
import core.math.Vec2;

@SuppressWarnings("unused")
public class PositionAnimationBehavior extends UIBehavior {
    private Vec2 startPosition;
    private Vec2 targetPosition;
    private double elapsedTime;
    private double animationDuration;

    public void moveTo(Vec2 targetPosition, double animationDuration) {
        startPosition = this.gameObject.getPosition();
        this.targetPosition = targetPosition;
        this.animationDuration = animationDuration;
        elapsedTime = 0;
    }

    public void stopAnimation(){
        startPosition = targetPosition = gameObject.getPosition();
    }

    @Override
    public void update() {
        if (elapsedTime >= animationDuration) return;
        elapsedTime += 1;

        Vec2 fullDistance = targetPosition.minus(startPosition);
        Vec2 progress = fullDistance.times(elapsedTime / animationDuration);

        this.gameObject.setPosition(startPosition.plus(progress));
    }
}