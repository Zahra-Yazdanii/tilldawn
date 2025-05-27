package com.tilldawn.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.model.SettingsModel;
import com.tilldawn.manager.MusicManager;
import com.tilldawn.model.User;


public class MainController {
    private final Game game;
    private final Skin skin;
    private final MusicManager musicManager;
    private final SettingsModel settingsModel;
    private final User user;

    public MainController(Game game, Skin skin, User user) {
        this.game = game;
        this.skin = skin;
        this.user = user;
        this.musicManager = new MusicManager();
        this.settingsModel = new SettingsModel();
    }

    public void showDashboard() {
        //game.setScreen(new UserDashboardView(user, skin, this));
    }

    public void showSettings() {
     //   game.setScreen(new SettingsScreen(skin));
    }
}
