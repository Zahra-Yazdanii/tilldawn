package com.tilldawn.controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;
import com.tilldawn.view.*;

import static com.tilldawn.Main.getMain;


public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getSignUpButton().isPressed()) {
               // Main.getMain().setScreen(new SignUpScreen( GameAssetManager.getGameAssetManager().getSkin()));
                Main.getMain().setScreen(new SignUpScreen(GameAssetManager.getInstance().getSkin()));
            }

            if (view.getLoginButton().isPressed()) {
                // به محض ایجاد صفحه LoginScreen
                // Main.getMain().setScreen(new LoginScreen(...));
                disposeCurrentScreen();
                Main.getMain().setScreen(new LoginScreen(GameAssetManager.getInstance().getSkin()));
            }

            if (view.getGuestButton().isPressed()) {
                // شروع بازی به عنوان مهمان (مثلا رفتن به صفحه پیش‌از‌بازی)
             //  Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                //getMain().setScreen(new PreGameScreen(GameAssetManager.getInstance().getSkin()));
            }

            if (view.getExitButton().isPressed()) {
                Gdx.app.exit();
            }
        }
    }
    private void disposeCurrentScreen() {
        Screen current = Main.getMain().getScreen();
        if (current != null) {
            current.dispose();
        }
    }


}
