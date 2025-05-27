package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.User;

import java.util.HashMap;
import java.util.Map;

public class KeyboardControlsView implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final User user;

    // Map for actions and keys
    private Map<String, Integer> controls = new HashMap<>();
    private Map<String, TextButton> buttons = new HashMap<>();

    private String waitingForKey = null;

    public KeyboardControlsView(Skin skin, User user) {
        this.skin = skin;
        this.user = user;
        stage = new Stage(new ScreenViewport());

        // تنظیمات پیشفرض (WASD)
        controls.put("Move Up", Keys.W);
        controls.put("Move Down", Keys.S);
        controls.put("Move Left", Keys.A);
        controls.put("Move Right", Keys.D);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Image bg = new Image(new Texture(Gdx.files.internal("background_dark_blue_black.png"))); // مطمئن شو این عکس وجود داره
        bg.setFillParent(true);
        stage.addActor(bg);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(20);
        stage.addActor(table);

        Label title = new Label("Keyboard Controls", skin, "title");
        table.add(title).colspan(2).padBottom(40).row();

        for (String action : controls.keySet()) {
            table.add(new Label(action + ":", skin)).left().pad(10);

            TextButton btn = new TextButton(Keys.toString(controls.get(action)), skin);
            buttons.put(action, btn);
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    waitingForKey = action;
                    btn.setText("Press a key...");
                }
            });

            table.add(btn).width(200).pad(10).row();
        }

        TextButton backBtn = new TextButton("Back to Settings", skin);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new SettingScreen(skin, user));
            }
        });

        table.add(backBtn).colspan(2).padTop(30).width(500).row();
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        if (waitingForKey != null && Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
            int pressedKey = getPressedKey();
            if (pressedKey != -1) {
                controls.put(waitingForKey, pressedKey);
                buttons.get(waitingForKey).setText(Keys.toString(pressedKey));
                waitingForKey = null;
            }
        }
    }

    private int getPressedKey() {
        for (int i = 0; i <= 255; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
