package com.game.story_about_one_hero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Date;

public class Hero extends Unit implements interfacePlayerHero{
    private int[] currentSprite = {0, 2};//1 - номер по горизонтали, 2 - по вертикали

    private Bitmap[][] sprites = new Bitmap[8][5];

    /*
        номер столбца соответсует действию
        0 столбец - лазание
        1 столбец - ходьба влево
        2 столбец - ходьба вправо
        3 столбец - с 0 по 3 атака влево, с 4 по 7 атака впрвао
        4 столбец - с 0 по 3 прыжок влево, с 4 по 7 прыжок впрвао
    */

    private int relativeX, relativeY;

    private int armor = 90;
    private int defense;
    private int damageFireball;
    private boolean isUseShield;

    Hero(int x, int y, Context context) {
        super(100, 9000,
                x, y,
                12, 100,
                0, 25,
                0, 0,
                null, false, true, 400);

        Bitmap imageUnit = BitmapFactory.decodeResource(context.getResources(), R.drawable.knight);

        setWidthUnit(imageUnit.getWidth() / 8);
        setHeightUnit(imageUnit.getHeight() / 5);
        setImageUnit(imageUnit);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 5; j++){
                sprites[i][j] = Bitmap.createBitmap(imageUnit,
                        i * getWidthUnit(), j * getHeightUnit(),
                        getWidthUnit(), getHeightUnit());
            }
        }

        //обрезаем спрайты

        setWidthUnit(41 * imageUnit.getWidth() / 560);
        setHeightUnit(77 * imageUnit.getHeight() / 450);

        for (int i = 0; i < 8; i++){
            sprites[i][1] = Bitmap.createBitmap(sprites[i][1],
                    17 * imageUnit.getWidth() / 560, 12 * imageUnit.getWidth() / 560,
                    getWidthUnit(), getHeightUnit());
        }

        for (int i = 0; i < 8; i++){
            sprites[i][2] = Bitmap.createBitmap(sprites[i][2],
                    13 * imageUnit.getWidth() / 560, 12 * imageUnit.getHeight() / 450,
                    getWidthUnit(), getHeightUnit());
        }

        for (int i = 0; i < 8; i++){
            sprites[i][3] = Bitmap.createBitmap(sprites[i][3],
                    2 * imageUnit.getWidth() / 560, 13 * imageUnit.getHeight() / 450,
                    68 * imageUnit.getWidth() / 560, getHeightUnit());
        }

        setBaseAttackRange(24 * imageUnit.getWidth() / 560);
    }

    @Override
    void stop() {
        currentSprite[0] = isRight() ?0 :7;
        setMove(false);
    }

    @Override
    void startMove(boolean isRight) {
        setRight(isRight);
        setMove(true);

        currentSprite[0] = isRight() ?0 :7;
        currentSprite[1] = isRight() ?2 :1;

        setPrevTime((new Date()).getTime());
    }

    @Override
    void takeDamage(int attackDamage) {
        int d = armor - attackDamage;
        setHealth(d < 0 ?getHealth() + d :getHealth());
    }

    @Override
    public void move(int minX, int maxX, int minY, int maxY) {
        long newTime = new Date().getTime();

        if((newTime - getPrevTime()) * 8 < getTimeAnimMove()) return;

        setPrevTime(newTime);

        currentSprite[0] += this.isRight() ?1 :-1;
        if(currentSprite[0] < 0) currentSprite[0] = 7;
        if(currentSprite[0] > 7) currentSprite[0] = 0;
        setUnitX(this.isRight() ?getSpeedMove() :-getSpeedMove());

        if(getUnitX() < minX) setUnitX(minX - getUnitX());
        if(getUnitX() > maxX - getWidthUnit()) setUnitX(maxX - getUnitX() - getWidthUnit());

        if(getUnitY() < minY) setUnitY(minY - getUnitY());
        if(getUnitY() > maxY - getHeightUnit()) setUnitY(maxY - getHeightUnit() - getUnitY());
    }

    @Override
    void startAttack(boolean isRight) {
        setAttackDamage(getBaseAttackDamage());
        setAttackRange(getBaseAttackRange());

        setRight(isRight);
        setAttack(true);

        currentSprite[0] = isRight() ?4 :0;
        currentSprite[1] = 3;

        setPrevTime((new Date()).getTime());
    }

    @Override
    void stopAttack() {
        setAttack(false);
        currentSprite[0] = isRight() ?0 :7;
        currentSprite[1] = isRight() ?2 :1;
    }

    @Override
    public void attack() {
        long newTime = new Date().getTime();

        if((newTime - getPrevTime()) * 4 < getTimeAnimAttack()) return;

        setPrevTime(newTime);

        currentSprite[0]++;

        if(isRight() && currentSprite[0] == 8 || !isRight() && currentSprite[0] == 4){
            for (Warrior warrior: FirstLevel.getWarriors()) {
                if(!warrior.isAlive()) continue;

                if(Math.abs(getDistanceBetweenWarrior(warrior)) <= this.getAttackRange()){
                    warrior.takeDamage(this.getAttackDamage());
                }
            }

            stopAttack();
        }
    }

    @Override
    void die() {
        setAlive(false);
    }

    private int getDistanceBetweenWarrior(Warrior warrior){
        int distance = Math.abs(this.getUnitX() - warrior.getUnitX());

        if(this.isRight()){
            distance -= this.getWidthUnit();

            if(warrior.isAttack()){
                if (warrior.isRight()){
                    distance = Math.abs( (this.getUnitX() + this.getWidthUnit()) -
                            (warrior.getUnitX() + warrior.getWidthSwordTip()) );

                    distance = Math.min(distance, Math.abs( (this.getUnitX() + this.getWidthUnit())
                            - (warrior.getUnitX() + warrior.getWidthSwordTip() + warrior.getWidthUnit() / 2) ) );
                }else {
                    distance = (this.getUnitX() + this.getWidthUnit()) -
                            (warrior.getUnitX() + warrior.getAttackRange());
                }
            }
        }else {
            distance -= warrior.getWidthUnit();

            if(warrior.isAttack()){
                distance -= warrior.isRight()
                        ?warrior.getWidthSwordTip()
                        :warrior.getAttackRange() + warrior.getWidthSwordTip();
            }
        }

        return distance;
    }

    public void specialAttack(boolean isRight){
        int spellCost = 100;
        int k = 2;

        if(getMana() < spellCost) return;

        setAttackRange(4 * k * getBaseAttackRange());
        setAttackDamage(k * getBaseAttackDamage());

        setMana(getMana() - spellCost);

        setRight(isRight);
        setAttack(true);

        currentSprite[0] = isRight() ?4 :0;
        currentSprite[1] = 3;

        setPrevTime((new Date()).getTime());
    }

    public void heal(){
        int spellCost = 75;
        int dHP = 25;

        if(getMaxHealth() == getHealth()) return;
        if(getMana() < spellCost) return;

        int newHealth = getHealth() + dHP;
        setHealth(newHealth > getMaxHealth() ?getMaxHealth() :newHealth);

        setMana(getMana() - spellCost);
    }

    public Bitmap getCurrentHeroSprite() {
        return sprites[currentSprite[0]][currentSprite[1]];
    }

    public int getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(int relativeX) {
        this.relativeX = relativeX;
    }

    public int getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(int relativeY) {
        this.relativeY = relativeY;
    }
}
