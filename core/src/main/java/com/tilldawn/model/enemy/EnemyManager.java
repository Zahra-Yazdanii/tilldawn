package com.tilldawn.model.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.*;

public class EnemyManager {
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<XPDrop> xpDrops = new ArrayList<>();
    private float spawnTimer = 0;
    private int enemyCount = 0;

    public void update(float delta, Vector2 playerPos, float elapsedTime) {
        spawnTimer += delta;

        int toSpawn = (int)(elapsedTime / 30f); // طبق شرط: بعد از i ثانیه، i‌اُمین دشمن باید هر ۳ ثانیه اسپان بشه
        if (enemyCount < toSpawn && spawnTimer >= 3f) {
            enemies.add(new TentacleEnemy(playerPos.cpy().add(300, 0)));
            enemyCount++;
            spawnTimer = 0;
        }

        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            enemy.update(delta, playerPos);

            if (!enemy.isAlive()) {
                xpDrops.add(new XPDrop(enemy.getPosition().cpy()));
                it.remove();
            }
        }

        for (XPDrop drop : xpDrops) drop.update(playerPos);
        xpDrops.removeIf(XPDrop::isCollected); // remove collected
    }

    public void render(SpriteBatch batch) {
        for (Enemy enemy : enemies) enemy.render(batch);
        for (XPDrop drop : xpDrops) drop.render(batch);
    }
}
