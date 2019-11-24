package com.game.story_about_one_hero;

import android.graphics.Bitmap;
abstract public class Unit{
    private int
            health, mane,
            unitX, unitY,
            speedMove, speedAttack,
            attackRange, attackDamage,
            widthUnit, heightUnit;

    private boolean isMove;

    private boolean isRight;

    private Bitmap imageUnit;

    Unit(int health, int mane,
         int x, int y,
         int speedMove, int speedAttack,
         int attackRange, int attackDamage,
         int width, int height, Bitmap imageUnit){
        this.health = health;
        this.mane = mane;
        this.unitX = x;
        this.unitY = y;
        this.speedMove = speedMove;
        this.speedAttack = speedAttack;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        this.widthUnit = width;
        this.heightUnit = height;
        this.imageUnit = imageUnit;
    }

    abstract void stop ();
    abstract void startMove(boolean isRight);
    abstract void takeDamage(int attackDamage);
    abstract void move(int minX, int maxX, int minY, int maxY);
    abstract void attack(Unit unit);

    public int getHealth() {
        return health;
    }

    public int getMane() {
        return mane;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMane(int mane) {
        this.mane = mane;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getSpeedMove() {
        return speedMove;
    }

    public void setSpeedMove(int speedMove) {
        this.speedMove = speedMove;
    }

    public int getSpeedAttack() {
        return speedAttack;
    }

    public void setSpeedAttack(int speedAttack) {
        this.speedAttack = speedAttack;
    }

    public Bitmap getImageUnit() {
        return imageUnit;
    }

    public void setImageUnit(Bitmap imageUnit) {
        this.imageUnit = imageUnit;
    }

    public int getWidthUnit() {
        return widthUnit;
    }

    public void setWidthUnit(int widthUnit) {
        this.widthUnit = widthUnit;
    }

    public int getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(int heightUnit) {
        this.heightUnit = heightUnit;
    }

    public int getUnitX() {
        return unitX;
    }

    public void setUnitX(int unitX) {
        this.unitX += unitX;
    }

    public int getUnitY() {
        return unitY;
    }

    public void setUnitY(int unitY) {
        this.unitY += unitY;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        this.isMove = move;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
