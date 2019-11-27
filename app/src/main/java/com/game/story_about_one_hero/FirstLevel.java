package com.game.story_about_one_hero;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class FirstLevel extends View implements interfaceLevel {
    private int maxX = 100000, maxY = 1200,
        minX = 0, minY = 0;

    private int groundY = 0, groundX = 0;

    Paint paint = new Paint();
    Context context;
    private Canvas canvas;

    private static Hero hero;
    private static Warrior[] warriors = new Warrior[100];
    private ImagesSheet sheet;

    boolean isStarted;
    boolean isSecondRedrawing;

    public FirstLevel(Context context) {
        super(context);
        init(context);
    }

    public FirstLevel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FirstLevel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(isSecondRedrawing){
            hero.setUnitY(getHeight() / 2 - hero.getHeightUnit() / 2);
            groundY = hero.getUnitY() + hero.getHeightUnit();

            for(int i = 0; i < warriors.length; i++){
                warriors[i].setUnitY(groundY - warriors[i].getHeightUnit());
            }

            isSecondRedrawing = false;
        }

        if(!isStarted){
            isStarted = true;
            isSecondRedrawing = true;

            hero = new Hero(0,0, context);
            sheet = new ImagesSheet(context, getWidth(), getHeight());

            for(int i = 0; i < warriors.length; i++){
                warriors[i] = new Warrior(2 * getWidth() + i * 100, 0, context);
            }

            this.canvas = canvas;
        }

        if(hero.isMove()){
            hero.move(minX, maxX, minY, maxY);
        }

        if(hero.isAttack()){
            hero.attack();
        }

        drawBackground();
        drawGround();

        drawHealthAndManaBar();

        drawHero();

        boolean isWin = true;
        for (Warrior warrior : warriors) {
            if(!warrior.isAlive()) continue;

            isWin = false;

            warrior.attackHero(getWidth());

            if(warrior.isMove()){
                warrior.move(minX, maxX, minY, maxY);
            }

            if(warrior.isAttack()){
                warrior.attack();
            }

            drawEnemy(warrior);
        }

        if(isWin) win();

        if(hero.getHealth() <= 0){
            dieHero();
        }

        drawCounterEnemy();

        invalidate();
    }

    public void drawEnemy(Enemy enemy){
        int distanceX = enemy.getUnitX() - hero.getUnitX();
        int relativeX = hero.getRelativeX() + distanceX;

        int distanceY = enemy.getUnitY() - hero.getUnitY();
        int relativeY = hero.getRelativeY() - distanceY;

        canvas.drawBitmap(enemy.getCurrentEnemySprite(), relativeX, enemy.getUnitY(), null);
    }

    public void drawGround(){
        int dx = sheet.getWidthGroundBox();
        int oi = groundX;

        if(hero.getUnitX() > canvas.getWidth() / 2 - hero.getWidthUnit()&&
                hero.getUnitX() <= maxX - canvas.getWidth() / 2 - hero.getWidthUnit()){
            oi += -(hero.getUnitX() - canvas.getWidth() / 2 + hero.getWidthUnit()) % dx;
        }else if(hero.getUnitX() >= maxX - canvas.getWidth()){
            oi += -(maxX - canvas.getWidth()) % dx;
        }else {
            oi += 0;
        }

        for (int i = oi; i < canvas.getWidth(); i+=dx){
            canvas.drawBitmap(sheet.getGroundWithGrass(), i, groundY, null);
        }

        //нижний слой земли
        paint.setColor(Color.rgb(39, 32, 52));
        canvas.drawRect(groundX, groundY + sheet.getHeightGroundBox(),
                canvas.getWidth(), canvas.getHeight(), paint);
    }

    public void drawBackground(){
        paint.setColor(Color.rgb(162, 190, 235));
        canvas.drawRect(0 , 0, getWidth(), getHeight(), paint);

        //деревья на заднем фоне
        int dx = sheet.getWidthTree() + 10;
        int oi;

        if(hero.getUnitX() > canvas.getWidth() / 2 - hero.getWidthUnit()&&
                hero.getUnitX() <= maxX - canvas.getWidth() / 2 - hero.getWidthUnit()){
            oi = -(hero.getUnitX() - canvas.getWidth() / 2 + hero.getWidthUnit()) % dx;
        }else if(hero.getUnitX() >= maxX - canvas.getWidth()){
            oi = -(maxX - canvas.getWidth()) % dx;
        }else {
            oi = 0;
        }

        for (int i = oi; i < canvas.getWidth(); i+=dx){
            canvas.drawBitmap(sheet.getTree(), i, 0, null);
        }
    }

    public void drawHero(){

        int relativeX ;

        if(hero.getUnitX() >= canvas.getWidth() / 2 - hero.getWidthUnit()&&
                hero.getUnitX() <= maxX - canvas.getWidth() / 2 - hero.getWidthUnit()){
            relativeX = canvas.getWidth() / 2 - hero.getWidthUnit();
        }else if(hero.getUnitX() >= maxX - canvas.getWidth() / 2 - hero.getWidthUnit()){
            relativeX = canvas.getWidth() - maxX + hero.getUnitX();
        }else {
            relativeX = hero.getUnitX();
        }

        int relativeY;

        if(hero.getUnitY() >= canvas.getHeight() / 2){
            relativeY = canvas.getHeight() / 2;
        }else {
            relativeY = hero.getUnitY();
        }

        hero.setRelativeX(relativeX);
        hero.setRelativeY(relativeY);

        canvas.drawBitmap(hero.getCurrentHeroSprite(), relativeX, relativeY, null);
    }

    public void drawHealthAndManaBar(){
        int
                widthBar = 700 * canvas.getWidth() / 1920,
                heightBar = 60 * canvas.getHeight() / 1080,
                manaY = canvas.getHeight() - 50 * canvas.getHeight() / 1080 - heightBar,
                manaX = canvas.getWidth() / 2 - widthBar / 2,
                hpX = manaX,
                hpY = manaY - 20 * canvas.getHeight() / 1080 - heightBar,
                currentHP = hero.getHealth() >= 0 ?hero.getHealth() :0,
                currentMana = hero.getMana() >= 0 ?hero.getMana() :0;

        paint.setColor(Color.BLACK);

        canvas.drawRect(manaX, manaY, manaX + widthBar, manaY + heightBar, paint);
        canvas.drawRect(hpX, hpY, hpX + widthBar, hpY + heightBar, paint);

        paint.setColor(Color.BLUE);
        canvas.drawRect(manaX, manaY,
                manaX + widthBar * currentMana / hero.getMaxMana(), manaY + heightBar, paint);

        paint.setColor(Color.rgb(3, 150, 3));
        canvas.drawRect(hpX, hpY,
                hpX + widthBar * currentHP / hero.getMaxHealth(), hpY + heightBar, paint);

        //рисуем текст
        paint.setColor(Color.WHITE);
        Rect mTextBoundRect = new Rect();

        //рисуем сколько хп
        String textHP = currentHP + "/" + hero.getMaxHealth();

        float widthText = paint.measureText(textHP);

        paint.getTextBounds(textHP, 0 , textHP.length(), mTextBoundRect);
        paint.setTextSize(20);

        float heightText = mTextBoundRect.height();

        canvas.drawText(textHP,
                (float) canvas.getWidth() / 2f - widthText / 2f,
                hpY + (float)heightBar / 2f + heightText / 2f, paint);

        //рисуем сколько маны
        String textMana = currentMana + "/" + hero.getMaxMana();

        widthText = paint.measureText(textMana);

        paint.getTextBounds(textMana, 0 , textMana.length(), mTextBoundRect);
        heightText = mTextBoundRect.height();

        canvas.drawText(textMana,
                (float)canvas.getWidth() / 2f - widthText / 2f,
                manaY + (float)heightBar / 2f + heightText / 2f, paint);
    }

    public void dieHero(){
        hero.die();

        Rect mTextBoundRect = new Rect();

        paint.setTextSize(68);
        paint.setColor(Color.rgb(166, 19, 8));

        String text = "ВЫ МЕРТВЫ";

        float widthText = paint.measureText(text);

        paint.getTextBounds(text, 0 , text.length(), mTextBoundRect);
        float heightText = mTextBoundRect.height();

        canvas.drawText(text,
                (float)canvas.getWidth() / 2f - widthText / 2f,
                (float)canvas.getHeight() / 2f - heightText / 2f,paint);

        isStarted = false;
    }

    public void win(){
        hero.die();

        Rect mTextBoundRect = new Rect();

        paint.setTextSize(68);
        paint.setColor(Color.rgb(25, 191, 0));

        String text = "ВЫ ПОБЕДИЛИ";

        float widthText = paint.measureText(text);

        paint.getTextBounds(text, 0 , text.length(), mTextBoundRect);
        float heightText = mTextBoundRect.height();

        canvas.drawText(text,
                (float)canvas.getWidth() / 2f - widthText / 2f,
                (float)canvas.getHeight() / 2f - heightText / 2f, paint);

        isStarted = false;
    }

    public void drawCounterEnemy(){
        int count = 0;

        for(Warrior warrior: warriors){
            if (warrior.isAlive()) count++;
        }

        Rect mTextBoundRect = new Rect();
        Paint paint = new Paint();

        String text = "врагов осталось: " + count;

        paint.setTextSize(40);
        paint.setColor(Color.rgb(87, 3, 10));

        paint.getTextBounds(text, 0 , text.length(), mTextBoundRect);
        float heightText = mTextBoundRect.height();

        canvas.drawText(text,
                (float)10 * canvas.getWidth() / 1920f,
                (float)10 * canvas.getHeight() / 1080f + heightText, paint);
    }

    static public Hero getHero(){
        return hero;
    }
    static public Warrior[] getWarriors() {
        return warriors;
    }
}
