package com.tilldawn;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.controller.MainMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.MainMenuView;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private Mp3.Music introMusic;
    public static Game getInstance() {
        return main;
    }

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        introMusic = (Mp3.Music) Gdx.audio.newMusic(Gdx.files.internal("music/default.mp3"));
        introMusic.setLooping(true);
        introMusic.setVolume(0.1f);
        introMusic.play();
       // GameAssetManager.getInstance().initAssets();
        getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void stopIntroMusic() {
        if (introMusic != null && introMusic.isPlaying()) {
            introMusic.stop();
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }


}
