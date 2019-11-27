package com.game.story_about_one_hero;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    private Context context = this;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_main);

        Button buttonMoveRight = findViewById(R.id.moveRight);
        buttonMoveRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(FirstLevel.getHero().isAttack()) return false;

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
                if(FirstLevel.getHero().isAttack()) return false;

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    FirstLevel.getHero().startMove(false);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    FirstLevel.getHero().stop();
                }

                return false;
            }
        });

        ImageButton buttonAttack = findViewById(R.id.attack);
        buttonAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirstLevel.getHero().isMove() || FirstLevel.getHero().isAttack()) return;

                FirstLevel.getHero().startAttack(FirstLevel.getHero().isRight());
            }
        });

        Button buttonExit = findViewById(R.id.exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Выход");

                dialog.setMessage("Вы действительно хотите выйти из игры?");

                dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                dialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                dialog.show();
            }
        });

        ImageButton buttonHeal1 = findViewById(R.id.heal1);
        ImageButton buttonHeal2 = findViewById(R.id.heal2);
        View.OnClickListener event = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstLevel.getHero().heal();
            }
        };
        buttonHeal1.setOnClickListener(event);
        buttonHeal2.setOnClickListener(event);

        ImageButton buttonSpecialAttack = findViewById(R.id.specialAttack);
        buttonSpecialAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirstLevel.getHero().isMove() || FirstLevel.getHero().isAttack()) return;

                FirstLevel.getHero().specialAttack(FirstLevel.getHero().isRight());
            }
        });
    }
}
