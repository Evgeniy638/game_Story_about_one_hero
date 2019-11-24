package com.game.story_about_one_hero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class ImagesSheet{
    private Bitmap tree, groundWithGrass;

    private int
            widthTree = 111 * 3,
            heightTree = 128 * 3;

    private int
            heightGroundBox = 15 * 3,
            widthGroundBox = 46 * 3;

    ImagesSheet(Context context, int canvasWidth, int canvasHeight){
        Bitmap bitmapSource = BitmapFactory.decodeResource(context.getResources(), R.drawable.sheet);

        Matrix matrixGWS = new Matrix();
        matrixGWS.setScale(2, 2);
        groundWithGrass = Bitmap.createBitmap(bitmapSource, 113*3 ,0,
                widthGroundBox, heightGroundBox, matrixGWS, false);
        widthGroundBox *= 2; //так как в дальнейшем картинка будет в 2 раза больше
        heightGroundBox *= 2;

        Matrix matrixT = new Matrix();
        float k = 0.75f * canvasHeight / heightTree;
        matrixT.setScale(k, k);
        tree = Bitmap.createBitmap(bitmapSource, 0, 0, widthTree, heightTree, matrixT, false);
        //так как в дальнейшем картинка будет в k раза больше
        widthTree *= k;
        heightTree *= k;
    }

    public Bitmap getTree() {
        return tree;
    }

    public Bitmap getGroundWithGrass() {
        return groundWithGrass;
    }

    public void getGroundWithoutGrass() {

    }

    public int getWidthTree() {
        return widthTree;
    }

    public int getHeightTree() {
        return heightTree;
    }

    public int getHeightGroundBox() {
        return heightGroundBox;
    }

    public int getWidthGroundBox() {
        return widthGroundBox;
    }
}
