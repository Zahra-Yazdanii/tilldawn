package com.tilldawn.model.enums;

public enum WeaponType {
    REVOLVER("Revolver", 6, 1, 1, 20, 1.5f),
    SHOTGUN("Shotgun", 2, 1, 4, 10, 1.2f),
    SMG_DUAL("SMGs Dual", 24, 2, 1, 8, 2.5f);

    private final String name;
    private final int ammo;
    private final float reloadTime;
    private final int projectileCount;
    private final int damage;
    private final float fireRate;

    WeaponType(String name, int ammo, float reloadTime, int projectileCount, int damage, float fireRate) {
        this.name = name;
        this.ammo = ammo;
        this.reloadTime = reloadTime;
        this.projectileCount = projectileCount;
        this.damage = damage;
        this.fireRate = fireRate;
    }

    public String getName() { return name; }
    public int getAmmo() { return ammo; }
    public float getReloadTime() { return reloadTime; }
    public int getProjectileCount() { return projectileCount; }
    public int getDamage() { return damage; }
    public float getFireRate() { return fireRate; }
}
