package com.tilldawn.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private Music currentMusic;
    private float volume = 0.5f;
    private String currentTrack;

    public void play(String trackName) {
        if (currentTrack != null && currentTrack.equals(trackName)) return;

        stop();

        currentTrack = trackName;
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("music/" + trackName + ".mp3"));
        currentMusic.setLooping(true);
        currentMusic.setVolume(volume);
        currentMusic.play();
    }

    public void stop() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
            currentMusic = null;
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (currentMusic != null) {
            currentMusic.setVolume(volume);
        }
    }

    public String getCurrentTrack() {
        return currentTrack;
    }

    public float getVolume() {
        return volume;
    }
}
