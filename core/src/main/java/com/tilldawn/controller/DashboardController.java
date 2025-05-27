package com.tilldawn.controller;

import com.badlogic.gdx.Game;
import com.tilldawn.model.User;
import com.tilldawn.view.*;

public class DashboardController {
    private final Game app;
    private final User user;
    private UserDashboardView view;

    public DashboardController(Game app, User user) {
        this.app = app;
        this.user = user;
    }

    public void setView(UserDashboardView view) {
        this.view = view;
    }

    public void openSettings() {
       // app.setScreen(new SettingsView(user)); // فرض بر اینه که SettingsView وجود داره
    }

    public void openProfile() {
       // app.setScreen(new ProfileView(user)); // فرض بر اینه که ProfileView پیاده‌سازی شده
    }

    public void openPreGameMenu() {
       // app.setScreen(new PreGameMenuView(new PreGameMenuController(app, user), user.getSkin()));
    }

    public void openScoreboard() {
       // app.setScreen(new ScoreboardView(user)); // فرض بر اینه که ScoreboardView ساخته شده
    }

    public void openHints() {
       // app.setScreen(new HintView(user)); // منوی Talent/Hints
    }

    public void continueLastGame() {
        if (user.getLastFinishedGame() != null) {
          //  app.setScreen(new GameView(new GameController(app, user, user.getLastFinishedGame()), user.getSkin()));
        }
    }

    public void logout() {
        //app.setScreen(new LoginScreen(user.getSkin()));
    }
}
