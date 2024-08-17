package core;


import core.math.Vec2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @noinspection UnusedReturnValue, unused
 */
public class GameObject {
    protected final Map<String, UIBehavior> componentMap;
    protected final List<UIBehavior> behaviorList;
    protected final List<GameObject> children;
    protected Vec2 position, size;
    protected GameObject parent;
    private boolean enabled = true;

    public GameObject(UIBehavior... behaviors) {
        this(Vec2.zero, behaviors);
    }

    public GameObject(Vec2 size, UIBehavior... behaviors) {
        this(Vec2.zero, size, behaviors);
    }

    public GameObject(Vec2 position, Vec2 size, UIBehavior... behaviors) {
        this.children = new ArrayList<>();

        this.setPosition(position).setSize(size);
        this.componentMap = new TreeMap<>();
        this.behaviorList = new ArrayList<>();

        addBehaviors(behaviors);
    }

    /**
     * @return position relative to top left corner of screen
     */
    public final Vec2 getAbsolutePosition() {
        if (parent == null) {
            return getPosition();
        } else {
            return parent.getAbsolutePosition().plus(position);
        }
    }

    /**
     * Sets the absolute position of the GameObject
     */
    public final GameObject setAbsolutePosition(Vec2 position) {
        return setPosition(position.minus(parent == null ? Vec2.zero : parent.getAbsolutePosition()));
    }

    /**
     * @return position of top left corner relative to the top left corner of the screen
     */
    public final Vec2 getAbsoluteTopLeft() {
        return getAbsolutePosition().plus(getTopLeftOffset());
    }

    /**
     * @return position of top right corner relative to the top left corner of the screen
     */
    public final Vec2 getAbsoluteTopRight() {
        return getAbsolutePosition().plus(getTopRightOffset());
    }

    /**
     * @return position of bottom left corner relative to the top left corner of the screen
     */
    public final Vec2 getAbsoluteBottomLeft() {
        return getAbsolutePosition().plus(getBottomLeftOffset());
    }

    /**
     * @return position of bottom left corner relative to the top left corner of the screen
     */
    public final Vec2 getAbsoluteBottomRight() {
        return getAbsolutePosition().plus(getBottomRightOffset());
    }

    /**
     * @return position relative to the parent gameObject
     */
    public final Vec2 getPosition() {
        return position;
    }

    /**
     * Set the position relative to the parent gameObject
     */
    public final GameObject setPosition(Vec2 position) {
        this.position = position;
        return this;
    }


    public GameObject setPosition(double x, double y) {
        return setPosition(new Vec2(x, y));
    }

    public final GameObject setX(double x) {
        position = new Vec2(x, position.y);
        return this;
    }

    public final GameObject setY(double y) {
        position = new Vec2(position.x, y);
        return this;
    }

    /**
     * Move the gameObject
     */
    public final GameObject moveBy(Vec2 movement) {
        this.position = this.position.plus(movement);
        return this;
    }

    /**
     * @return position of top left corner relative to the parent gameObject
     */
    public final Vec2 getTopLeft() {
        return position.plus(getTopLeftOffset());
    }

    /**
     * Set the top left corner relative to the parent gameObject
     */
    public final GameObject setTopLeft(Vec2 position) {
        return setPosition(position.minus(getTopLeftOffset()));
    }

    /**
     * @return position of top right corner relative to the parent gameObject
     */
    public final Vec2 getTopRight() {
        return position.plus(getTopRightOffset());
    }

    /**
     * Set the top right corner relative to the parent gameObject
     */
    public final GameObject setTopRight(Vec2 position) {
        return setPosition(position.minus(getTopRightOffset()));
    }

    /**
     * @return position of bottom left corner relative to the parent gameObject
     */
    public final Vec2 getBottomLeft() {
        return position.plus(getBottomLeftOffset());
    }

    /**
     * Set the bottom left corner relative to the parent gameObject
     */
    public final GameObject setBottomLeft(Vec2 position) {
        return setPosition(position.minus(getBottomLeftOffset()));
    }

    /**
     * @return position of bottom left corner relative to the parent gameObject
     */
    public final Vec2 getBottomRight() {
        return position.plus(getBottomRightOffset());
    }

    /**
     * Set the bottom right corner relative to the parent gameObject
     */
    public final GameObject setBottomRight(Vec2 position) {
        return setPosition(position.minus(getBottomRightOffset()));
    }

    /**
     * Get offset of top left corner
     */
    public final Vec2 getTopLeftOffset() {
        return size.times(-.5, -.5);
    }

    /**
     * Get offset of top right corner
     */
    public final Vec2 getTopRightOffset() {
        return size.times(.5, -.5);
    }

    /**
     * Get offset of bottom left corner
     */
    public final Vec2 getBottomLeftOffset() {
        return size.times(-.5, .5);
    }

    /**
     * Get offset of bottom right corner
     */
    public final Vec2 getBottomRightOffset() {
        return size.times(.5, .5);
    }

    /**
     * Adds the given {@link Vec2} to the position
     */
    public final GameObject move(Vec2 delta) {
        return setPosition(position.plus(delta));
    }

    /**
     * @return the size of the GameObject in pixels
     */
    public final Vec2 size() {
        return size;
    }

    public GameObject setWidth(double w) {
        return setSize(w, size.y);
    }

    public GameObject setHeight(double h) {
        return setSize(size.x, h);
    }

    /**
     * Set the size of the GameObject
     */
    public final GameObject setSize(Vec2 size) {
        this.size = size;
        return this;
    }

    /**
     * Set the size of the GameObject
     */
    public final GameObject setSize(double width, double height) {
        return setSize(new Vec2(width, height));
    }

    public void updateAndDraw(GameCanvas canvas) {
        update();
        draw(canvas);
    }

    /**
     * Updates the GameObject by its behaviors. Child objects are updated before the parent object.
     *
     * @return
     */
    public GameObject update() {
        if(!enabled) return this;

        for (GameObject child : children)
            child.update();

        for (UIBehavior behavior : behaviorList)
            if (behavior.isEnabled())
                behavior.update();

        return this;
    }

    /**
     * Draws the GameObject. Parent objects are drawn before the child objects
     *
     * @param canvas The {@link GameCanvas} on which to draw the GameObject
     */
    public void draw(GameCanvas canvas) {
        if(!enabled) return;

        for (UIBehavior behavior : behaviorList)
            if (behavior.isEnabled())
                behavior.draw(canvas);

        for (GameObject child : children)
            child.draw(canvas);
    }

    public final GameObject addBehavior(UIBehavior behavior) {
        behaviorList.add(behavior);
        componentMap.put(behavior.getClass().getName(), behavior);
        behavior.setGameObject(this);
        return this;
    }

    public final GameObject addBehaviorFront(UIBehavior behavior) {
        behaviorList.add(0, behavior);
        componentMap.put(behavior.getClass().getName(), behavior);
        behavior.setGameObject(this);
        return this;
    }

    public final GameObject addBehaviors(UIBehavior... behaviors) {
        for (UIBehavior behavior : behaviors) {
            addBehavior(behavior);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <Type extends UIBehavior> Type findBehavior(Class<Type> type) {
        return (Type) componentMap.get(type.getName());
    }

    public void removeFromParent() {
        setParent(null);
    }

    public GameObject addChild(GameObject object) {
        object.setParent(this);
        children.add(object);
        return this;
    }

    public final GameObject addChild(Entity entity) {
        return addChild(entity.getGameObject());
    }

    public final GameObject addChildren(GameObject... children) {
        Arrays.stream(children).forEach(this::addChild);
        return this;
    }

    public final GameObject addChildren(Entity... children) {
        for (Entity child : children) {
            addChild(child);
        }
        return this;
    }

    public final List<GameObject> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "\n" + getClass().getName() + "{\n  Position:"
                + getPosition()
                + "\n  Components : ["
                + behaviorList.stream().map(i -> i.getClass().getName()).collect(Collectors.joining(", "))
                + "]\n  Children: [" + (
                children.size() == 0 ? "" :
                        children.stream().map(
                                i -> "    " + i.toString().replaceAll("\n", "\n    ")
                        ).collect(Collectors.joining("")) + "\n  "
                )
                + "]\n}";
    }

    public final GameObject getParent() {
        return parent;
    }

    private void setParent(GameObject object) {
        if (parent != null) parent.children.remove(this);
        parent = object;
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
    }
    public void disable() {
        this.enabled = false;
    }
}