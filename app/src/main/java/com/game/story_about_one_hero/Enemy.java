package com.game.story_about_one_hero;

import android.graphics.Bitmap;

public abstract class Enemy extends Unit {
    Enemy(int health, int mane, int x, int y,
          int speedMove, int speedAttack, int attackRange, int attackDamage,
          int width, int height, Bitmap imageUnit, int timeAnimMove) {
        super(health, mane, x, y, speedMove, speedAttack,
                attackRange, attackDamage, width, height, imageUnit, true, false, timeAnimMove);
    }

    abstract public void attackHero(int maxDistance);
    abstract public Bitmap getCurrentEnemySprite();
}
