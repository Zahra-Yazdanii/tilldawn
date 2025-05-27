package com.tilldawn.controller;

import com.tilldawn.model.SettingsModel;
import com.tilldawn.manager.MusicManager;

public class SettingsController {
    private final SettingsModel model;
    private final MusicManager musicManager;

    public SettingsController(SettingsModel model, MusicManager musicManager) {
        this.model = model;
        this.musicManager = musicManager;
    }

    public void changeMusic(String track) {
        model.setCurrentMusic(track);
        musicManager.play(track);
    }

    public void setMusicVolume(float volume) {
        model.setMusicVolume(volume);
        musicManager.setVolume(volume);
    }

    public void toggleSfx(boolean enabled) {
        model.setSfxEnabled(enabled);
    }

    public void toggleAutoReload(boolean enabled) {
        model.setAutoReload(enabled);
    }

    public void toggleBWMode(boolean enabled) {
        model.setBwMode(enabled);
    }

    public SettingsModel getModel() {
        return model;
    }
}
