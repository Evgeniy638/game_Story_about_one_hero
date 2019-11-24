package com.game.story_about_one_hero;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FirstLevel extends View {
    private int maxX = 10000, maxY = 1200,
        minX = 0, minY = 0;

    private int groundY = 0, groundX = 0;

    Paint paint = new Paint();
    Context context;
    private Canvas canvas;

    private static Hero hero;
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
            isSecondRedrawing = false;
        }

        if(!isStarted){
            isStarted = true;
            isSecondRedrawing = true;

            hero = new Hero(0,0, context);
            sheet = new ImagesSheet(context, getWidth(), getHeight());
            this.canvas = canvas;
        }

        if(hero.isMove()){
            hero.move(minX, maxX, minY, maxY);
        }

        drawBackground();
        drawGround();
        drawHero();

        invalidate();
    }

    private void drawGround(){
        int dx = sheet.getWidthGroundBox();
        int oi = groundX;

        if(hero.getUnitX() > canvas.getWidth() / 2 - hero.getWidthUnit()&&
                hero.getUnitX() <= maxX - canvas.getWidth() / 2 - hero.getWidthUnit()){
            oi += -(hero.getUnitX() - canvas.getWidth() / 2 + hero.getWidthUnit()) % dx;
        }else if(hero.getUnitX() >= maxX - canvas.getWidth()){
            oi += maxX - canvas.getWidth() -
                    ((maxX - canvas.getWidth()) % dx);
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

    private void drawBackground(){
        paint.setColor(Color.rgb(162, 190, 235));
        canvas.drawRect(0 , 0, getWidth(), getHeight(), paint);

        //деревья на заднем фоне
        int dx = sheet.getWidthTree() + 10;
        int oi;

        if(hero.getUnitX() > canvas.getWidth() / 2 - hero.getWidthUnit()&&
                hero.getUnitX() <= maxX - canvas.getWidth() / 2 - hero.getWidthUnit()){
            oi = -(hero.getUnitX() - canvas.getWidth() / 2 + hero.getWidthUnit()) % dx;
        }else if(hero.getUnitX() >= maxX - canvas.getWidth()){
            oi = maxX - canvas.getWidth() -
                    ((maxX - canvas.getWidth()) % dx);
        }else {
            oi = 0;
        }

        for (int i = oi; i < canvas.getWidth(); i+=dx){
            canvas.drawBitmap(sheet.getTree(), i, 0, null);
        }
    }

    private void drawHero(){

        int relativeX ;

        if(hero.getUnitX() >= canvas.getWidth() / 2 - hero.getWidthUnit()&&
                hero.getUnitX() <= maxX - canvas.getWidth() / 2 + hero.getWidthUnit()){
            relativeX = canvas.getWidth() / 2 - hero.getWidthUnit();
        }else if(hero.getUnitX() >= maxX - canvas.getWidth() / 2 - hero.getWidthUnit()){
            relativeX = maxX - hero.getUnitX();
        }else {
            relativeX = hero.getUnitX();
        }

        int relativeY;

        if(hero.getUnitY() >= canvas.getHeight() / 2){
            relativeY = canvas.getHeight() / 2;
        }else {
            relativeY = hero.getUnitY();
        }

        canvas.drawBitmap(hero.getCurrentHeroSprite(), relativeX, relativeY, null);
    }

    static public Hero getHero(){
        return hero;
    }
}
