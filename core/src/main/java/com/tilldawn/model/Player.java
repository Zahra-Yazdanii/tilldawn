package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.model.enums.HeroType;
import com.tilldawn.model.enums.WeaponType;


public class Player {
    private final HeroType heroType;
    private final Vector2 position;
    private final Vector2 direction = new Vector2(1, 0);
    private float stateTime;
    private final Weapon weapon;

    private static final float SCALE = 3f;

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> walkAnimation;

    private TextureRegion currentFrame;
    private float speed;
    private boolean facingRight = true;

    public Player(HeroType heroType, WeaponType weaponType) {
        this.heroType = heroType;
        this.position = new Vector2(300, 300);
        this.stateTime = 0f;
        this.speed = heroType.getSpeed();
        this.weapon = new Weapon(weaponType);
        loadAnimations();
    }

    private void loadAnimations() {
        String basePath = "Heros/" + heroType.getFolderName();

        idleAnimation = loadAnimation(basePath + "/idle", "Idle_", 6, 0.1f);
        runAnimation = loadAnimation(basePath + "/run", "Run_", 4, 0.1f);

        if (heroType.hasWalkAnim()) {
            walkAnimation = loadAnimation(basePath + "/walk", "Walk_", 8, 0.1f);
        }
    }

    private Animation<TextureRegion> loadAnimation(String folderPath, String filePrefix, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = folderPath + "/" + filePrefix + i + ".png";
            frames[i] = new TextureRegion(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(frameDuration, frames);
    }

    public void update(float delta, Camera camera) {
        stateTime += delta;
        Vector2 moveDelta = new Vector2();
        boolean isRunning = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) moveDelta.y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) moveDelta.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) moveDelta.x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moveDelta.x += 1;

        boolean isMoving = moveDelta.len() > 0;

        if (isMoving) {
            moveDelta.nor().scl(isRunning ? speed * 20f : speed);
            position.add(moveDelta);
            direction.set(moveDelta).nor();
        }

        if (isMoving) {
            if (isRunning) {
                currentFrame = runAnimation.getKeyFrame(stateTime, true);
            } else if (walkAnimation != null) {
                currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = runAnimation.getKeyFrame(stateTime, true);
            }
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }

        if (direction.x < -0.1f) facingRight = false;
        else if (direction.x > 0.1f) facingRight = true;
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);

            float angle = new Vector2(mouse.x - position.x, mouse.y - position.y).angleDeg();
           // weapon.tryShoot(new Vector2(position.x + weaponOffsetX(), position.y + weaponOffsetY()), angle);
        }


        //weapon.update(delta, position);
    }


    public void render(SpriteBatch batch, Camera camera) {
        float drawX = position.x;
        float drawY = position.y;

        float width = currentFrame.getRegionWidth() * SCALE;
        float height = currentFrame.getRegionHeight() * SCALE;

        boolean flipX = !facingRight;
        TextureRegion frame = currentFrame;
        if (frame.isFlipX() != flipX) frame.flip(true, false);

        batch.draw(frame, drawX, drawY, width, height);

        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouse);
        Vector2 dirToMouse = new Vector2(mouse.x - position.x, mouse.y - position.y);
        float angle = dirToMouse.angleDeg();

        boolean flip = dirToMouse.x < 0;
        if (flip) {
            angle = 180 + angle;
        }

        if (weapon != null) {
            weapon.render(batch, position.x, position.y, angle, dirToMouse.x >= 0);
        }
    }



    public void dispose() {
        // TODO: Dispose animations & weapon if needed
    }

    public float getX() { return position.x; }
    public float getY() { return position.y; }

    public Weapon getWeapon() {
        return weapon;
    }
}
