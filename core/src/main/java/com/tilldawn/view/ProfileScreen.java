package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.MainMenuController;
import com.tilldawn.controller.ProfileController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ProfileScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final TextField usernameField, passwordField;
    private final Label messageLabel;
    private final Image avatarPreview;

    private final List<String> avatars = Arrays.asList(
        "images/T/T_Abby_Portrait.png",
        "images/T/T_Luna_Portrait.png",
        "images/T/T_Raven_Portrait.png",
        "images/T/T_Spark_Portrait.png"
    );

    private String selectedAvatarPath = avatars.get(0);

    public ProfileScreen() {
        this.skin = GameAssetManager.getInstance().getSkin();
        this.stage = new Stage(new ScreenViewport());
        this.usernameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.messageLabel = new Label("", skin);
        this.avatarPreview = new Image(new Texture(Gdx.files.internal(selectedAvatarPath)));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Image background = new Image(new Texture(Gdx.files.internal("background_dark_blue_black.png")));
        background.setFillParent(true);
        stage.addActor(background);
        User user = User.getCurrentUser();

        Table root = new Table();
        root.setFillParent(true);
        root.pad(20);
        root.center();
        stage.addActor(root);

        Label title = new Label("Profile Settings", skin, "title");
        root.add(title).colspan(2).padBottom(30).row();

        // Username
        usernameField.setMessageText("New Username");
        TextButton usernameBtn = new TextButton("Change Username", skin);
        usernameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newUsername = usernameField.getText().trim();
                if (newUsername.isEmpty()) {
                    messageLabel.setText("Username cannot be empty.");
                } else if (ProfileController.updateUsername(newUsername)) {
                    messageLabel.setText("Username changed successfully!");
                } else {
                    messageLabel.setText("Username is already taken.");
                }
            }
        });
        root.add(usernameField).width(500).height(100).padBottom(10).padRight(10);
        root.add(usernameBtn).width(500).height(100).padBottom(10).row();

        // Password
        passwordField.setMessageText("New Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        TextButton passwordBtn = new TextButton("Change Password", skin);
        passwordBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newPass = passwordField.getText();
                if (ProfileController.updatePassword(newPass)) {
                    messageLabel.setText("Password updated!");
                } else {
                    messageLabel.setText("Weak password!");
                }
            }
        });
        root.add(passwordField).width(500).height(100).padBottom(10).padRight(10);
        root.add(passwordBtn).width(500).height(100).padBottom(10).row();

        // Avatar Preview
        Label avatarLabel = new Label("Current Avatar", skin);
        root.add(avatarLabel).colspan(2).padTop(20).row();
        root.add(avatarPreview).size(100).colspan(2).padBottom(15).row();

        // Avatar selection buttons
        Table avatarTable = new Table();
        for (String path : avatars) {
            Texture texture = new Texture(Gdx.files.internal(path));
            ImageButton imgBtn = new ImageButton(new Image(texture).getDrawable());
            imgBtn.getImage().setScaling(Scaling.fit);
            imgBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selectedAvatarPath = path;
                    avatarPreview.setDrawable(new Image(new Texture(Gdx.files.internal(path))).getDrawable());
                    ProfileController.updateAvatar(path);
                    messageLabel.setText("Avatar updated.");
                }
            });
            avatarTable.add(imgBtn).size(80).pad(5);
        }
        root.add(avatarTable).colspan(2).padBottom(10).row();

        // Upload custom avatar from file system
        TextButton uploadBtn = new TextButton("Upload Custom Avatar", skin);
        uploadBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SwingUtilities.invokeLater(() -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Choose an image...");
                    int result = chooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = chooser.getSelectedFile();
                        Gdx.app.postRunnable(() -> {
                            try {
                                FileHandle copied = Gdx.files.local("avatars/" + file.getName());
                                copied.writeBytes(new FileHandle(file.getAbsolutePath()).readBytes(), false);
                                selectedAvatarPath = copied.path();
                                avatarPreview.setDrawable(new Image(new Texture(copied)).getDrawable());
                                ProfileController.updateAvatar(copied.path());
                                messageLabel.setText("Custom avatar uploaded.");
                            } catch (Exception e) {
                                messageLabel.setText("Failed to load avatar.");
                            }
                        });
                    }
                });
            }
        });
        root.add(uploadBtn).colspan(2).width(700).height(100).padBottom(20).row();

        // Delete + Back
        TextButton deleteBtn = new TextButton("Delete Account", skin);
        deleteBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ProfileController.deleteAccount();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        TextButton backBtn = new TextButton("Go Back", skin);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new UserDashboardView(user, skin));
            }
        });

        root.add(deleteBtn).width(400).height(100).padRight(20);
        root.add(backBtn).width(400).height(100).row();

        root.add(messageLabel).colspan(2).padTop(30);
    }

    @Override public void render(float delta) { stage.act(delta); stage.draw(); }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
