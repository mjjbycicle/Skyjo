package core;

/**
 * An abstract UI Component class.
 *
 * @noinspection EmptyMethod
 */
public abstract class UIBehavior {
    protected GameObject gameObject;
    private boolean disabled = false;

    /**
     * Update the behavior. Used for non-graphics UI-related processing
     */
    public void update(){}

    /**
     * Draws the behavior
     *
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public void draw(GameCanvas canvas){}

    /**
     * Should not be called by client code <br>
     * Sets the parent game object of this component
     */
    final void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    /**
     * @return whether the Behavior is disabled
     */
    public final boolean isEnabled() {
        return !disabled;
    }

    /**
     * Enable the Behavior
     */
    public final void enable() {
        disabled = false;
    }

    /**
     * Disable the Behavior
     */
    public final void disable() {
        disabled = true;
    }

    /**
     * @return The GameObject that the behavior is attached to
     */
    public GameObject getGameObject() {
        return gameObject;
    }
}