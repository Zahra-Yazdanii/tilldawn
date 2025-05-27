package com.tilldawn.model.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;

public class TentacleEnemy extends Enemy {
    private TextureRegion currentFrame;

    public TentacleEnemy(Vector2 spawnPosition) {
        super(spawnPosition, 60, 25); // HP: 25
        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            frames[i] = new TextureRegion(new Texture(Gdx.files.internal("images/TentacleIdle/TentacleIdle" + i + ".png")));
        }
        this.animation = new Animation<>(0.1f, frames);
    }

    @Override
    public void update(float delta, Vector2 playerPos) {
        stateTime += delta;
        if (!alive) return;

        Vector2 direction = playerPos.cpy().sub(position).nor();
        position.add(direction.scl(speed * delta));
        currentFrame = animation.getKeyFrame(stateTime, true);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (alive && currentFrame != null)
            batch.draw(currentFrame, position.x, position.y);
    }
}
