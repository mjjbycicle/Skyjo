package core.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Basic class representing a playable sound. Sounds can be played at the same time.
 */
public class Sound {
	private static class SoundSource {
		public final byte[] data;
		public final DataLine.Info info;

		private SoundSource(AudioInputStream audioInputStream) throws IOException {
			this.data = audioInputStream.readAllBytes();
			this.info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
		}

		private Clip createClip() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(data)));
			return clip;
		}
	}

	private final static Map<String, SoundSource> sources = new HashMap<>();

	private static Clip getClip(String filename) {
		SoundSource source;
		if(sources.containsKey(filename)) source = sources.get(filename);
		else {
			try (InputStream _inputStream = Sound.class.getResourceAsStream("/" + filename)) {
				if (_inputStream != null) {
					var inputStream = new BufferedInputStream(_inputStream);
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
					sources.put(filename, source = new SoundSource(audioInputStream));
				} else {
					throw new IOException("Null input stream");
				}
			}
			catch (Exception e){
				throw new RuntimeException(e);
			}
		}

		try {
			return source.createClip();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final Clip clip;
	private final FloatControl gainControl;

	/**
	 * Loads a sound, given the file name. <br>
	 * WARNING: This is an expensive operation.
	 */
	public Sound(String fileName) {
		this(getClip(fileName));
    }

	private Sound(Clip clip) {
		this.clip = clip;
		if (clip != null) gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		else gainControl = null;
	}

	public boolean equals(Sound sound) {
		return sound != null && clip == sound.clip;
	}

	/**
	 * A sound representing silence. Used internally by
	 * the game engine, for the most part.
	 */
	public static final Sound SILENCE = new Sound((Clip) null);

	/**
	 * Play the sound and loop it until {@link Sound#stop()}
	 * is called
	 */
	public void playLooping() {
		if (clip == null) return;
		clip.stop();
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Stops playing the sound
	 */
	public void stop() {
		if (clip == null) return;
		clip.stop();
		clip.setFramePosition(0);
	}

	/**
	 * Plays the sound once.
	 */
	public void play() {
		if (clip == null) return;
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}

	/**
	 * @return Whether the sound is currently playing
	 */
	public boolean isPlaying() {
		return clip != null && clip.isRunning();
	}

	/**
	 * @return The volume of the sound
	 */
	public double getVolume() {
		return gainControl == null ? 0 : Math.pow(10, gainControl.getValue() / 20);
	}

	/**
	 * Sets the volume of the sound. Volume should be a number between 0 and 1
	 */
	public Sound setVolume(double volume) {
		if (gainControl != null) {
			float vol = 20f * (float) Math.log10(Math.max(Math.min(volume, 1), 0));
			gainControl.setValue(Math.max(vol, -80f));
		}
		return this;
	}
}