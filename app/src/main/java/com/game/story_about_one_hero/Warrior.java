package com.game.story_about_one_hero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.Date;

public class Warrior extends Enemy {
    private int[] currentSprite = {0, 0};

    private Bitmap[][] sprites = new Bitmap[4][9];
    /*
        тут строчка это отдельная анимация
        0 - ходьба влево
        1 - ходьба вправо
        2 - атака влево
        3 - атака вправо
    */

    private int widthAttackUnit, heightAttackUnit;
    private int widthMoveUnit, heightMoveUnit;

    private int widthSwordTip;

    Warrior(int x, int y, Context context) {
        super(100, 450, x, y,
                11, 600,
                0, 100,
                0, 0, null, 600);

        Bitmap bitmapSource = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_knight);

        setWidthUnit(26 * bitmapSource.getWidth() / 1536 * 2);
        setHeightUnit(48 * bitmapSource.getHeight() / 2112 * 2);

        setWidthMoveUnit(576 * bitmapSource.getWidth() / 1536 / 9);
        setHeightMoveUnit(48 * bitmapSource.getHeight() / 2112);

        Matrix matrix = new Matrix();

        matrix.setScale(2, 2);

        //ходьба влево
        for(int i = 0; i < 9; i++){
            sprites[0][i] = Bitmap.createBitmap(bitmapSource,
                    i * getWidthMoveUnit(),
                    590  * bitmapSource.getHeight() / 2112 ,
                    getWidthMoveUnit(), getHeightMoveUnit(), matrix, false);
        }

        //ходьба вправо
        for(int i = 0; i < 9; i++){
            sprites[1][i] = Bitmap.createBitmap(bitmapSource, i * getWidthMoveUnit(),
                    718  * bitmapSource.getHeight() / 2112,
                    getWidthMoveUnit(), getHeightMoveUnit(), matrix, false);
        }

        setWidthAttackUnit(1151 * bitmapSource.getWidth() / 1536 / 6);
        setHeightAttackUnit(getHeightUnit());

        //атака влево
        for(int i = 0; i < 6; i++){
            sprites[2][i] = Bitmap.createBitmap(bitmapSource, i * getWidthAttackUnit(),
                    1612  * bitmapSource.getHeight() / 2112,
                    getWidthAttackUnit(), getHeightAttackUnit(), matrix, false);
        }

        //атака вправо
        for(int i = 0; i < 6; i++){
            sprites[3][i] = Bitmap.createBitmap(bitmapSource, i * getWidthAttackUnit(),
                    1996  * bitmapSource.getHeight() / 2112,
                    getWidthAttackUnit(), getHeightAttackUnit(), matrix, false);
        }

        setWidthMoveUnit(27 * bitmapSource.getWidth() / 1536 * 2);
        setHeightMoveUnit(48 * bitmapSource.getHeight() / 2112 * 2);

        setWidthAttackUnit(112 * bitmapSource.getWidth() / 1536 * 2);
        setHeightAttackUnit(48 * bitmapSource.getHeight() / 2112 * 2);

        //обрезаем

        //ходьба влево
        for(int i = 0; i < 9; i++){
            sprites[0][i] = Bitmap.createBitmap(sprites[0][i],20 * bitmapSource.getWidth() / 1536 * 2,
                    0, getWidthMoveUnit(), getHeightMoveUnit());
        }

        //ходьба вправо
        for(int i = 0; i < 9; i++){
            sprites[1][i] = Bitmap.createBitmap(sprites[1][i], 18 * bitmapSource.getWidth() / 1536 * 2,
                    0, getWidthMoveUnit(), getHeightMoveUnit());
        }

        //атака влево
        for(int i = 0; i < 6; i++){
            sprites[2][i] = Bitmap.createBitmap(sprites[2][i], 34 * bitmapSource.getWidth() / 1536,
                    0, getWidthAttackUnit(), getHeightAttackUnit());
        }

        //атака вправо
        for(int i = 0; i < 6; i++){
            sprites[3][i] = Bitmap.createBitmap(sprites[3][i], 138 * bitmapSource.getWidth() / 1536,
                    0, getWidthAttackUnit(), getHeightAttackUnit());
        }

        setAttackRange(66 * bitmapSource.getWidth() / 1536 * 2);

        widthSwordTip = getWidthAttackUnit() - getAttackRange() - getWidthMoveUnit();
    }

    @Override
    public void attackHero(int maxDistance) {
        int distance = getDistanceBetwenHero();

        if(!isAttack() && Math.abs(distance) <= this.getAttackRange()){

            this.stop();
            this.startAttack(distance > 0);

        }else if(!isAttack() && Math.abs(distance) <= maxDistance && (!isMove() ||
                (isRight() != (distance > 0)))){

            this.startMove(distance > 0);

        }
    }

    @Override
    void stop() {
        setMove(false);
        currentSprite[0] = isRight() ?1 :0;
        currentSprite[1] = 0;
    }

    @Override
    void startMove(boolean isRight) {
        setRight(isRight);
        setMove(true);

        currentSprite[0] = isRight() ?1 :0;
        currentSprite[1] = 0;

        setPrevTime((new Date()).getTime());
    }

    @Override
    void takeDamage(int attackDamage) {
        this.setHealth(this.getHealth() - attackDamage);

        if(getHealth() <= 0) die();
    }

    @Override
    void move(int minX, int maxX, int minY, int maxY) {
        long newTime = new Date().getTime();

        if((newTime - getPrevTime()) * 9 < getTimeAnimMove()) return;

        setPrevTime(newTime);

        currentSprite[1] = (currentSprite[1] + 1) % 9;

        setUnitX(this.isRight() ?getSpeedMove() :-getSpeedMove());

        if(getUnitX() < minX) setUnitX(minX - getUnitX());
        if(getUnitX() > maxX - getWidthUnit()) setUnitX(maxX - getUnitX() - getWidthUnit());

        if(getUnitY() < minY) setUnitY(minY - getUnitY());
        if(getUnitY() > maxY - getHeightUnit()) setUnitY(maxY - getHeightUnit() - getUnitY());
    }

    @Override
    void startAttack(boolean isRight) {
        setRight(isRight);
        setAttack(true);

        setUnitX(isRight ?-widthSwordTip :-getAttackRange());

        currentSprite[0] = isRight() ?3 :2;
        currentSprite[1] = 0;

        setPrevTime((new Date()).getTime());
    }

    @Override
    void stopAttack() {
        setAttack(false);
        currentSprite[1] = 0;
        currentSprite[0] = isRight() ?1 :0;
        setUnitX(isRight() ?widthSwordTip :this.getAttackRange());
    }

    @Override
    void attack() {
        long newTime = new Date().getTime();

        if((newTime - getPrevTime()) * 6 < getTimeAnimAttack()) return;

        setPrevTime(newTime);

        currentSprite[1]++;

        int distance;

        if(isRight()){
            distance = getDistanceBetwenHero() - widthSwordTip;
        }else {
            distance = (FirstLevel.getHero().getUnitX() + FirstLevel.getHero().getWidthUnit() / 2)
                    - (this.getUnitX() + this.getAttackRange())  ;
        }


        if(currentSprite[1] == 6 && Math.abs(distance) <= this.getAttackRange()){

            FirstLevel.getHero().takeDamage(this.getAttackDamage());
        }

        if (currentSprite[1] == 6 ){
            stopAttack();
        }
    }

    @Override
    void die() {
        setAlive(false);
    }

    private int getDistanceBetwenHero(){
        int distance = FirstLevel.getHero().getUnitX() - this.getUnitX();

        if (distance > 0) distance -= this.getWidthUnit() - FirstLevel.getHero().getWidthUnit() / 2;
        if (distance < 0) distance += FirstLevel.getHero().getWidthUnit() / 2;


        return distance;
    }

    public int getWidthSwordTip(){
        return widthSwordTip;
    }

    public Bitmap getCurrentEnemySprite() {
        return sprites[currentSprite[0]][currentSprite[1]];
    }

    public int getWidthAttackUnit() {
        return widthAttackUnit;
    }

    public void setWidthAttackUnit(int widthAttackUnit) {
        this.widthAttackUnit = widthAttackUnit;
    }

    public int getHeightAttackUnit() {
        return heightAttackUnit;
    }

    public void setHeightAttackUnit(int heightAttackUnit) {
        this.heightAttackUnit = heightAttackUnit;
    }

    public int getWidthMoveUnit() {
        return widthMoveUnit;
    }

    public void setWidthMoveUnit(int widthMoveUnit) {
        this.widthMoveUnit = widthMoveUnit;
    }

    public int getHeightMoveUnit() {
        return heightMoveUnit;
    }

    public void setHeightMoveUnit(int heightMoveUnit) {
        this.heightMoveUnit = heightMoveUnit;
    }
}
