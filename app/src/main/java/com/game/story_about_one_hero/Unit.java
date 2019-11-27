package com.game.story_about_one_hero;

import android.graphics.Bitmap;
abstract public class Unit{
    private int
            health;
    private int mana;
    private int maxHealth;
    private int maxMana;
    private int unitX;
    private int unitY;
    private int speedMove;
    private int timeAnimAttack;

    public void setBaseAttackDamage(int baseAttackDamage) {
        this.baseAttackDamage = baseAttackDamage;
    }

    public void setBaseAttackRange(int baseAttackRange) {
        this.baseAttackRange = baseAttackRange;
    }

    private int baseAttackDamage;
    private int baseAttackRange;
    private int attackRange;
    private int attackDamage;
    private int widthUnit;
    private int heightUnit;
    private int timeAnimMove;

    private long prevTime, newTime;

    private boolean isMove;

    private boolean isAttack;

    private boolean isRight;

    private boolean isAlive = true;

    private boolean isEnemy;
    private boolean isHero;

    private Bitmap imageUnit;

    Unit(int health, int mane,
         int x, int y,
         int speedMove, int timeAnimAttack,
         int attackRange, int attackDamage,
         int width, int height, Bitmap imageUnit,
         boolean isEnemy, boolean isHero,
         int timeAnimMove){
        this.maxHealth = this.health = health;
        this.maxMana = this.mana = mane;
        this.unitX = x;
        this.unitY = y;
        this.speedMove = speedMove;
        this.timeAnimAttack = timeAnimAttack;
        this.baseAttackRange = this.attackRange = attackRange;
        this.baseAttackDamage = this.attackDamage = attackDamage;
        this.widthUnit = width;
        this.heightUnit = height;
        this.imageUnit = imageUnit;
        this.isEnemy = isEnemy;
        this.isHero = isHero;
        this.timeAnimMove = timeAnimMove;
    }

    abstract void stop ();
    abstract void startMove(boolean isRight);
    abstract void takeDamage(int attackDamage);
    abstract void move(int minX, int maxX, int minY, int maxY);
    abstract void startAttack(boolean isRight);
    abstract void stopAttack();
    abstract void attack();
    abstract void die();

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage){
        this.attackDamage = attackDamage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getSpeedMove() {
        return speedMove;
    }

    public void setSpeedMove(int speedMove) {
        this.speedMove = speedMove;
    }

    public int getTimeAnimAttack() {
        return timeAnimAttack;
    }

    public void setTimeAnimAttack(int timeAnimAttack) {
        this.timeAnimAttack = timeAnimAttack;
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

    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setPrevTime(long prevTime) {
        this.prevTime = prevTime;
    }

    public void setNewTime(long newTime) {
        this.newTime = newTime;
    }

    public long getPrevTime() {
        return prevTime;
    }

    public long getNewTime() {
        return newTime;
    }

    public int getTimeAnimMove() {
        return timeAnimMove;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getBaseAttackDamage() {
        return baseAttackDamage;
    }

    public int getBaseAttackRange() {
        return baseAttackRange;
    }
}
