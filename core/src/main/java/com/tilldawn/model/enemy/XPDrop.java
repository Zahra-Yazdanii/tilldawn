package com.tilldawn.model.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class XPDrop {
    private Vector2 position;
    private Texture texture;
    private Rectangle bounds;
    private boolean collected = false;

    public XPDrop(Vector2 pos) {
        this.position = pos;
        this.texture = new Texture(Gdx.files.internal("images/xp_seed.png")); // عکس ساختگی
        this.bounds = new Rectangle(pos.x, pos.y, 20, 20);
    }

    public void update(Vector2 playerPos) {
        if (playerPos.dst(position) < 30) {
            collected = true;
            // TODO: XP += 3
        }
    }

    public void render(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, position.x, position.y);
        }
    }

    public boolean isCollected() {
        return collected;
    }
}
