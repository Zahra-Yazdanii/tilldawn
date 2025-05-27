package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.model.enums.WeaponType;

public class Weapon {
    private final WeaponType type;
    private final Texture texture;
    private final float originX;
    private final float originY;

    public Weapon(WeaponType type) {
        this.type = type;

        String path = "images/" + type.name() + "Still/" + type.name() + "Still.png";
        this.texture = new Texture(Gdx.files.internal(path));

        this.originX = texture.getWidth() / 2f;
        this.originY = texture.getHeight() / 2f;
    }

    public void render(SpriteBatch batch, float playerX, float playerY, float angle, boolean facingRight) {
        float offsetX = facingRight ? 25 : -35;
        float offsetY = 30;

        batch.draw(
            texture,
            playerX + offsetX, playerY + offsetY,
            originX, originY,
            texture.getWidth(), texture.getHeight(),
            2.5f, 2.5f,
            angle,
            0, 0,
            texture.getWidth(), texture.getHeight(),
            !facingRight, false
        );
    }



    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void dispose() {
        texture.dispose();
    }
}
