package com.tilldawn.model;

public class SettingsModel {
    private String currentMusic = "track1";
    private float musicVolume = 0.5f;
    private boolean sfxEnabled = true;
    private boolean autoReload = true;
    private boolean bwMode = false;

    public String getCurrentMusic() {
        return currentMusic;
    }

    public void setCurrentMusic(String currentMusic) {
        this.currentMusic = currentMusic;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public boolean isSfxEnabled() {
        return sfxEnabled;
    }

    public void setSfxEnabled(boolean sfxEnabled) {
        this.sfxEnabled = sfxEnabled;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public boolean isBwMode() {
        return bwMode;
    }

    public void setBwMode(boolean bwMode) {
        this.bwMode = bwMode;
    }
}
