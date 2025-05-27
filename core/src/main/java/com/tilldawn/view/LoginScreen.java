package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.LoginController;
import com.tilldawn.controller.MainMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

public class LoginScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final TextField usernameField, passwordField, answerField, newPasswordField;
    private final Label errorLabel, questionLabel;
    private final TextButton loginButton, forgotButton, resetButton, backButton;

    public LoginScreen(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        answerField = new TextField("", skin);
        newPasswordField = new TextField("", skin);

        errorLabel = new Label("", skin);
        questionLabel = new Label("", skin);

        loginButton = new TextButton("Login", skin);
        forgotButton = new TextButton("Forgot Password?", skin);
        resetButton = new TextButton("Reset Password", skin);
        backButton = new TextButton("Go Back", skin);
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Image background = new Image(new Texture(Gdx.files.internal("background_dark_blue_black.png")));
        background.setFillParent(true);
        stage.addActor(background);

        FreeTypeFontGenerator titleGen = new FreeTypeFontGenerator(Gdx.files.internal("Font/ChevyRay - Lantern.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter titleParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParam.size = 48;
        BitmapFont titleFont = titleGen.generateFont(titleParam);
        titleGen.dispose();

        Label titleLabel = new Label("Login Menu", skin, "title");
        Table table = new Table();
        table.setFillParent(true);
        table.top().padTop(70);
        stage.addActor(table);

        table.add(titleLabel).colspan(2).padBottom(40).row();

        usernameField.setMessageText("Enter Username");
        table.add(usernameField).width(600).colspan(2).padBottom(10).row();

        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Enter Password");
        table.add(passwordField).width(600).colspan(2).padBottom(10).row();

        table.add(errorLabel).colspan(2).pad(10).row();

        Table btnRow = new Table();
        btnRow.add(loginButton).width(235).padRight(20);
        btnRow.add(backButton).width(235);
        table.add(btnRow).colspan(2).padBottom(10).row();


        table.add(forgotButton).colspan(2).padBottom(10).row();

        answerField.setMessageText("Answer security question");
        newPasswordField.setMessageText("Enter new password");
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');

        table.add(questionLabel).colspan(2).padTop(20).row();
        table.add(answerField).width(600).colspan(2).padBottom(10).row();
        table.add(newPasswordField).width(600).colspan(2).padBottom(10).row();
        table.add(resetButton).colspan(2).padTop(5).row();

        questionLabel.setVisible(false);
        answerField.setVisible(false);
        newPasswordField.setVisible(false);
        resetButton.setVisible(false);

        loginButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                if (LoginController.validateLogin(usernameField.getText(), passwordField.getText())) {
                    User user = LoginController.getUserByUsername(usernameField.getText());
                    User.setCurrentUser(user);
                    errorLabel.setText("Login successful!");
                    Main.getMain().setScreen(new UserDashboardView(user,GameAssetManager.getInstance().getSkin()));
                } else {
                    errorLabel.setText("Invalid credentials.");
                }
            }
        });

        forgotButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                User user = LoginController.getUserByUsername(usernameField.getText());
                if (user != null) {
                    questionLabel.setText(user.getQuestion());
                    questionLabel.setVisible(true);
                    answerField.setVisible(true);
                    newPasswordField.setVisible(true);
                    resetButton.setVisible(true);
                    errorLabel.setText("");
                } else {
                    errorLabel.setText("Username not found.");
                }
            }
        });

        resetButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                boolean success = LoginController.resetPassword(
                    usernameField.getText(), answerField.getText(), newPasswordField.getText()
                );
                errorLabel.setText(success ? "Password reset!" : "Incorrect answer.");
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
    }


    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
