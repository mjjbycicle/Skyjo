package core.audio;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A utility class to manage background music.
 */
public class BackgroundMusicManager {
    private @NotNull Sound currSound = Sound.SILENCE;
    private boolean isMuted = true;
    private double volume = 1.0;

    /**
     * Creates a BackgroundMusicManager, muted by
     * default. You must call {@link #unMute()}
     * to unMute the music.
     * <p>
     * Example usage:
     * <pre><code>
     * BackgroundMusicManager m = new BackgroundMusicManager();
     * m.useMusic(new Sound("Music.wav"));
     * m.unMute();
     * </code></pre>
     */
    public BackgroundMusicManager() {}

    /**
     * Stops playing the last Sound used, and starts playing
     * Sound s. If s is null, or if s was the last sound used,
     * this method does nothing.
     * @param s The sound to use as the background music. If
     *          s is null, this method does nothing.
     */
    public void useMusic(@Nullable Sound s) {
        if(s != null && !currSound.equals(s)){
            currSound.stop();
            currSound = s;
            currSound.setVolume(isMuted ? 0 : volume);
            currSound.playLooping();
        }
    }

    /**
     *  Stops the background music entirely. You must call {@link #useMusic(Sound)}
     *  again to start playing music again.
     */
    public void stopMusic() {
        currSound.stop();
        currSound = Sound.SILENCE;
    }

    /**
     * Mutes the background music. The music keeps advancing
     * while the background music is muted, but no sound is
     * played.
     */
    public void mute() {
        isMuted = true;
    }

    /**
     * Unmutes the background music (See {@link #mute()} for
     * more details about what this means).
     */
    public void unMute() {
        isMuted = false;
    }

    /**
     * @return Whether the BackgroundMusicManager is muted.
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * Sets the volume of the background music.
     * @param volume A double in the range [0, 1]
     */
    public void setVolume(double volume) {
        this.volume = volume;
        currSound.setVolume(isMuted ? 0 : volume);
    }
}
