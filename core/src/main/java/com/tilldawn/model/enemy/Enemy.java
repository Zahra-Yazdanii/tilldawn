package com.tilldawn.model.enemy;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
    protected Vector2 position;
    protected float speed;
    protected int health;
    protected Animation<TextureRegion> animation;
    protected float stateTime = 0;
    protected boolean alive = true;

    public Enemy(Vector2 spawnPosition, float speed, int health) {
        this.position = spawnPosition;
        this.speed = speed;
        this.health = health;
    }

    public abstract void update(float delta, Vector2 playerPos);
    public abstract void render(SpriteBatch batch);

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public Vector2 getPosition() {
        return position;
    }
}
