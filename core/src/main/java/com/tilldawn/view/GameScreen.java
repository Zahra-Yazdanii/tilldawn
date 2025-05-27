package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.tilldawn.model.Player;
import com.tilldawn.model.enums.HeroType;
import com.tilldawn.model.enums.WeaponType;
import com.tilldawn.model.enemy.EnemyManager;

public class GameScreen implements Screen {
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final Player player;
    private final Texture background;
    private final EnemyManager enemyManager = new EnemyManager();
    private float gameTime = 0;

    public GameScreen(HeroType selectedHero, WeaponType selectedWeapon) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1280, 720, camera);
        this.batch = new SpriteBatch();
        this.player = new Player(selectedHero, selectedWeapon);
        this.background = new Texture(Gdx.files.internal("background.png"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, camera.position.x - 640, camera.position.y - 360, 1280, 720);
        player.update(delta, camera);
        player.render(batch, camera);
        batch.end();
    }

    @Override public void resize(int width, int height) { viewport.update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        background.dispose();
        player.dispose();
    }
}
