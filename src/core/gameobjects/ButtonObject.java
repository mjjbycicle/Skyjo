package core.gameobjects;

import core.GameObject;
import core.behaviors.*;
import core.input.Input;

import java.awt.*;

public class ButtonObject extends GameObject {
	private final boolean underline;
	public ButtonObject(String text, Font font, Color background, Color border, Color textColor, boolean underline) {
		super(
				new RoundedRectRendererBehavior(20, border, background),
				new TextRendererBehavior(text, new TextStyle(font, textColor, TextStyle.TextAlign.ALIGN_CENTER)),
				new ButtonBehavior()
		);
		this.underline = underline;
		findBehavior(TextRendererBehavior.class).resizeToFit(10);
	}

	public boolean isHovered() {
		return findBehavior(ButtonBehavior.class).contains(Input.getMousePosition());
	}

	@Override
    public GameObject update() {
		if (underline) this.findBehavior(TextRendererBehavior.class).setUnderlined(isHovered());

		return super.update();
    }
}
