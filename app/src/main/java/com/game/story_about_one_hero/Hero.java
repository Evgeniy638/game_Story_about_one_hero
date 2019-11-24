package com.game.story_about_one_hero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Hero extends Unit {
    private int[] currentSprite = {0, 2};//1 - номер по горизонтали, 2 - по вертикали

    private Bitmap[][] sprites = new Bitmap[8][5];

    private int defense;
    private int damageFireball;
    private boolean isUseShield;

    Hero(int x, int y, Context context) {
        super(100, 9000,
                x, y,
                20, 1000,
                100, 100,
                0, 0,
                null);

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
    }

    @Override
    void takeDamage(int attackDamage) {
        setHealth(isUseShield ?-attackDamage + defense :-attackDamage);
    }

    @Override
    public void move(int minX, int maxX, int minY, int maxY) {
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
    public void attack(Unit unit) {
        unit.takeDamage(this.getAttackDamage());
    }

    public Bitmap getCurrentHeroSprite() {
        return sprites[currentSprite[0]][currentSprite[1]];
    }
}
