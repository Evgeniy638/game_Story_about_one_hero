package com.game.story_about_one_hero;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonMoveRight = findViewById(R.id.moveRight);
        buttonMoveRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    FirstLevel.getHero().startMove(true);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    FirstLevel.getHero().stop();
                }
                return false;
            }
        });

        Button buttonMoveLeft = findViewById(R.id.moveLeft);
        buttonMoveLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    FirstLevel.getHero().startMove(false);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    FirstLevel.getHero().stop();
                }

                return false;
            }
        });
    }
}
