package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.MainController;
import com.tilldawn.controller.MainMenuController;
import com.tilldawn.controller.ProfileController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

import static com.tilldawn.Main.getMain;

public class UserDashboardView implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final User user;
    //private final DashboardController controller;

    public UserDashboardView(User user, Skin skin) {
        this.user = user;
        this.skin = skin;

        this.stage = new Stage(new ScreenViewport());
      //  controller.setView(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


        Image bg = new Image(new Texture(Gdx.files.internal("background_dark_blue_black.png")));
        bg.setFillParent(true);
        stage.addActor(bg);

        Table table = new Table();
        table.setFillParent(true);
        table.top().padTop(40);
        stage.addActor(table);


        Label titleLabel = new Label("Welcome, " + user.getUsername() + "!", skin, "title");
        table.add(titleLabel).colspan(2).padBottom(30).row();


        Table userInfo = new Table();
        Texture avatarTex = new Texture(Gdx.files.internal(user.getAvatar()));
        Image avatarImage = new Image(avatarTex);
        userInfo.add(avatarImage).size(100).padRight(20);

        Table labels = new Table();
        labels.add(new Label("Username: " + user.getUsername(), skin)).left().row();
        labels.add(new Label("Total Score: " + user.getTotalScore(), skin)).left().row();
        userInfo.add(labels).left();

        table.add(userInfo).colspan(2).padBottom(30).row();


        String[][] menuItems = {
            {"Settings", "Profile"},
            {"Pre-Game", "Scoreboard"},
            {"Hints / Talents", "Resume Adventure"}
        };

        for (String[] pair : menuItems) {
            for (String label : pair) {
                TextButton btn = new TextButton(label, skin);
                table.add(btn).width(500).pad(10);

                btn.addListener(new ChangeListener() {
                    @Override public void changed(ChangeEvent event, Actor actor) {
                        // اینجا کنترلر رو وصل کن
                        // مثلاً: if (label.equals("Settings")) controller.openSettings();
                        switch (label){
                            case "Settings":
                                getMain().setScreen(new SettingScreen(GameAssetManager.getInstance().getSkin(),user));
                                break;
                            case "Profile":
                                User currentUser = User.getCurrentUser(); // یا هرجایی که کاربر ذخیره شده
                                ProfileController controller = new ProfileController();
                                Main.getMain().setScreen(new ProfileScreen());
                                break;
                                case "Pre-Game":
                                    getMain().setScreen(new PreGameScreen(GameAssetManager.getInstance().getSkin()));

                        }
                    }
                });
            }
            table.row();
        }

        // ✅ فقط دکمه خروج پایین وسط
        TextButton logoutBtn = new TextButton("Logout", skin);
        table.add(logoutBtn).colspan(2).padTop(30).width(500);

        logoutBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
    }


    @Override public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
