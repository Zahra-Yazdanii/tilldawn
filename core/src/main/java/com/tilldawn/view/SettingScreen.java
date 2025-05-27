package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.User;

public class SettingScreen implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final User user;

    private Music currentMusic;
    private SelectBox<String> musicSelector;
    private Slider volumeSlider;
    private CheckBox musicToggle;
    private CheckBox sfxToggle;
    private CheckBox autoReloadToggle;
    private CheckBox grayscaleToggle;

    public SettingScreen(Skin skin, User user) {
        this.skin = skin;
        this.user = user;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Image bg = new Image(new Texture(Gdx.files.internal("background_dark_blue_black.png"))); // مطمئن شو این عکس وجود داره
        bg.setFillParent(true);
        stage.addActor(bg);
       // stage.clear();

        Table table = new Table();
        table.setFillParent(true);
        table.pad(20);
        stage.addActor(table);

        Label title = new Label("Settings", skin, "title");
        table.add(title).colspan(2).padBottom(40).row();


        table.add(new Label("Select Background Music:", skin)).left();
        musicSelector = new SelectBox<>(skin);
        musicSelector.getStyle().fontColor = Color.WHITE;
        musicSelector.setItems("track1", "track2", "default");
        musicSelector.setSelected("track1");
        table.add(musicSelector).width(300).row();
        Main.getMain().stopIntroMusic();

        table.add(new Label("Music Volume:", skin)).left();
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(0.5f);
        table.add(volumeSlider).width(300).row();


        table.add(new Label("Music On/Off:", skin)).left();
        musicToggle = new CheckBox("", skin);
        musicToggle.setChecked(true);
        table.add(musicToggle).row();

        // --- روشن/خاموش صداهای بازی (SFX) ---
        table.add(new Label("Game SFX On/Off:", skin)).left();
        sfxToggle = new CheckBox("", skin);
        sfxToggle.setChecked(true);
        table.add(sfxToggle).row();

        // --- Auto Reload ---
        table.add(new Label("Auto Reload (Reload-auto) On/Off:", skin)).left();
        autoReloadToggle = new CheckBox("", skin);
        autoReloadToggle.setChecked(true);
        table.add(autoReloadToggle).row();

        // --- سیاه و سفید کردن نمایش ---
        table.add(new Label("Grayscale Display On/Off:", skin)).left();
        grayscaleToggle = new CheckBox("", skin);
        grayscaleToggle.setChecked(false);
        table.add(grayscaleToggle).row();

        // --- دکمه تغییر کنترلر کیبورد ---
        TextButton changeControlsBtn = new TextButton("Change Keyboard Controls", skin);
        table.add(changeControlsBtn).colspan(2).padTop(30).width(700).row();

        // دکمه بازگشت
        TextButton backBtn = new TextButton("Back to Dashboard", skin);
        table.add(backBtn).colspan(2).padTop(30).width(700).row();

        // --- لود و پلی موزیک اولیه ---
        loadAndPlayMusic(musicSelector.getSelected());

        // --- Listenerها ---

        musicSelector.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loadAndPlayMusic(musicSelector.getSelected());
            }
        });

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (currentMusic != null) {
                    currentMusic.setVolume(volumeSlider.getValue());
                }
            }
        });

        musicToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (currentMusic == null) return;
                if (musicToggle.isChecked()) {
                    currentMusic.play();
                } else {
                    currentMusic.pause();
                }
            }
        });

        sfxToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // اینجا می‌تونی فعال/غیرفعال کردن افکت‌های صوتی بازی رو هندل کنی
                // مثلاً user.setSFXEnabled(sfxToggle.isChecked());
            }
        });

        autoReloadToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                User.getCurrentUser().setAutoReloadEnabled(autoReloadToggle.isChecked());
                // اینجا وضعیت auto reload رو ذخیره کن
                // user.setAutoReloadEnabled(autoReloadToggle.isChecked());
            }
        });

        grayscaleToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // اینجا می‌تونی حالت سیاه و سفید رو فعال/غیرفعال کنی
                // مثلاً user.setGrayscaleEnabled(grayscaleToggle.isChecked());
            }
        });

        changeControlsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new KeyboardControlsView(skin, user));
            }
        });

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new UserDashboardView(user, skin));
            }
        });
    }

    private void loadAndPlayMusic(String trackName) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("music/" + trackName + ".mp3"));
        currentMusic.setLooping(true);
        currentMusic.setVolume(volumeSlider.getValue());
        if (musicToggle.isChecked()) {
            currentMusic.play();
        }
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        stage.dispose();
    }
}

