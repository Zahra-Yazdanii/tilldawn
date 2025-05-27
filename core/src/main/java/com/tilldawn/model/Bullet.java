//package com.tilldawn.model;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Vector2;
//
//public class Bullet {
//    private static final float SPEED = 700f;
//    private static final float LIFETIME = 2f;
//
//    private final Texture texture;
//    private final Vector2 position;
//    private final Vector2 velocity;
//    private final int damage;
//    private float lifeTimer;
//    private boolean alive = true;
//
//    public Bullet(Vector2 start, float angle, int damage) {
//        this.texture = new Texture("bullet.png");
//        this.position = start.cpy();
//        this.damage = damage;
//        this.velocity = new Vector2(1, 0).setAngleDeg(angle).scl(SPEED);
//        this.lifeTimer = LIFETIME;
//    }
//
//    public void update(float delta) {
//        position.mulAdd(velocity, delta);
//        lifeTimer -= delta;
//        if (lifeTimer <= 0) alive = false;
//    }
//
//    public void render(SpriteBatch batch) {
//        batch.draw(texture, position.x, position.y);
//    }
//
//    public boolean isAlive() { return alive; }
//    public void kill() { alive = false; }
//
//    public Vector2 getPosition() { return position; }
//    public int getDamage() { return damage; }
//
//    public void dispose() { texture.dispose(); }
//}
