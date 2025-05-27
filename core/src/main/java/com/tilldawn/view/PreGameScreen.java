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
import com.tilldawn.model.User;
import com.tilldawn.controller.MainMenuController;
import com.tilldawn.model.enums.HeroType;
import com.tilldawn.model.enums.WeaponType;

public class PreGameScreen implements Screen {
    private final Skin skin;
    private final Stage stage;
    private final SelectBox<String> timeSelectBox;
    private final SelectBox<WeaponType>  weaponSelectBox;
    private final SelectBox<HeroType> heroSelectBox;
    private final TextButton startButton, backButton;

    public PreGameScreen(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());

        heroSelectBox = new SelectBox<>(skin);
        weaponSelectBox = new SelectBox<>(skin);
        timeSelectBox = new SelectBox<>(skin);
        startButton = new TextButton("Start Game", skin);
        backButton = new TextButton("Back", skin);
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Image background = new Image(new Texture(Gdx.files.internal("background_dark_blue_black.png")));
        background.setFillParent(true);
        stage.addActor(background);

        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Font/ChevyRay - Lantern.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParam.size = 42;
        BitmapFont titleFont = fontGen.generateFont(fontParam);
        fontGen.dispose();

        Label titleLabel = new Label("Pre-Game Menu", skin, "title");

        Table table = new Table();
        table.setFillParent(true);
        table.top().padTop(70);
        stage.addActor(table);

        table.add(titleLabel).colspan(2).padBottom(40).row();

        // Hero Select
        heroSelectBox.getStyle().fontColor = Color.WHITE;
        heroSelectBox.setItems(HeroType.SHANA, HeroType.DIAMOND, HeroType.SCARLET, HeroType.LILITH, HeroType.DASHER);
        table.add(new Label("Choose Hero:", skin)).left().padBottom(10).row();
        table.add(heroSelectBox).width(600).padBottom(20).row();

        // Weapon Select
        weaponSelectBox.getStyle().fontColor = Color.WHITE;
        weaponSelectBox.setItems(WeaponType.REVOLVER, WeaponType.SHOTGUN, WeaponType.SMG_DUAL);
        table.add(new Label("Choose Weapon:", skin)).left().padBottom(10).row();
        table.add(weaponSelectBox).width(600).padBottom(20).row();

        // Duration Select
        timeSelectBox.getStyle().fontColor = Color.WHITE;
        timeSelectBox.setItems("2", "5", "10", "20"); // in minutes
        table.add(new Label("Choose Game Duration (min):", skin)).left().padBottom(10).row();
        table.add(timeSelectBox).width(600).padBottom(30).row();

        table.add(startButton).width(400).padBottom(10).row();
        table.add(backButton).width(400).padBottom(10).row();

        startButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                saveUserChoices();
                HeroType selectedHero = User.getCurrentUser().getSelectedHero();
                WeaponType selectedWeapon = User.getCurrentUser().getSelectedWeapon();
                Main.getMain().setScreen(new GameScreen(selectedHero,selectedWeapon));
                // بعداً می‌تونی بجای GameScreen، یه کلاس جدید بذاری

            }
        });

        backButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
             //   Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
                User user = User.getCurrentUser();
                Main.getMain().setScreen(new UserDashboardView(user, skin));
            }
        });
    }

    private void saveUserChoices() {
        User user = User.getCurrentUser();
        if (user != null) {
            user.setSelectedHero(heroSelectBox.getSelected());
            user.setSelectedWeapon(weaponSelectBox.getSelected());
            user.setSelectedDuration(Integer.parseInt(timeSelectBox.getSelected()));
        }
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
