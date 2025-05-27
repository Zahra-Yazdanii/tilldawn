package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.MainMenuController;

public class MainMenuView implements Screen {

    private Stage stage;
    private final TextButton signUpButton;
    private final TextButton loginButton;
    private final TextButton guestButton;
    private final TextButton exitButton;
    public Table table;
    private final MainMenuController controller;
    private Texture backgroundTexture;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.signUpButton = new TextButton("Sign Up", skin);
        this.loginButton = new TextButton("Login", skin);
        this.guestButton = new TextButton("Guest", skin);
        this.exitButton = new TextButton("Exit", skin);
        this.table = new Table();

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Background
        stage.getBatch().begin();
        stage.getBatch().end();
        backgroundTexture = new Texture(Gdx.files.internal("background_dark_blue_black.png"));
        table.setFillParent(true);
        table.center();

        // Add logo instead of title text
        Image logo = new Image(new Texture(Gdx.files.internal("T_20Logo.png")));
        table.add(logo).padBottom(20).row();

        // Buttons
        float buttonWidth = 300;
        float buttonSpacing = 20;
        table.row();
        table.add(signUpButton).width(buttonWidth).padBottom(buttonSpacing).row();
        table.add(loginButton).width(buttonWidth).padBottom(buttonSpacing).row();
        table.add(guestButton).width(buttonWidth).padBottom(buttonSpacing).row();
        //table.add(playButton).width(buttonWidth).padBottom(buttonSpacing).row();
        table.add(exitButton).width(buttonWidth).padBottom(buttonSpacing).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleMainMenuButtons();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // public TextButton getPlayButton() { return playButton; }
    public TextButton getSignUpButton() {
        return signUpButton;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getGuestButton() {
        return guestButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }
}
