//package com.tilldawn.manager;
//
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.tilldawn.model.Bullet;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class BulletManager {
//    private final List<Bullet> bullets = new ArrayList<>();
//
//    public void addBullet(Bullet bullet) {
//        bullets.add(bullet);
//    }
//
//    public void update(float delta) {
//        Iterator<Bullet> it = bullets.iterator();
//        while (it.hasNext()) {
//            Bullet bullet = it.next();
//            bullet.update(delta);
//            if (!bullet.isAlive()) {
//                it.remove();
//            }
//        }
//    }
//
//    public void render(SpriteBatch batch) {
//        for (Bullet bullet : bullets) {
//            bullet.render(batch);
//        }
//    }
//
//    public List<Bullet> getBullets() {
//        return bullets;
//    }
//
//    public void dispose() {
//        for (Bullet bullet : bullets) {
//            bullet.dispose();
//        }
//    }
//}
