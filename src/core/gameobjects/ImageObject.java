package core.gameobjects;

import core.GameObject;
import core.behaviors.ImageRendererBehavior;
import core.util.ImageLoader;

import java.awt.Image;

public class ImageObject extends GameObject {
	public ImageObject(String file) {
		super(new ImageRendererBehavior(ImageLoader.get(file)));
	}

	public ImageObject(Image image) {
		super(new ImageRendererBehavior(image));
	}

	public Image getImage() {
		return findBehavior(ImageRendererBehavior.class).getImage();
	}
}
