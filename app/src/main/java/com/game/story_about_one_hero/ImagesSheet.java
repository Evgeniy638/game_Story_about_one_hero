package com.game.story_about_one_hero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class ImagesSheet{
    private Bitmap tree, groundWithGrass;

    private int widthTree, heightTree;

    private int heightGroundBox, widthGroundBox;

    ImagesSheet(Context context, int canvasWidth, int canvasHeight){
        Bitmap bitmapSource = BitmapFactory.decodeResource(context.getResources(), R.drawable.sheet);

        widthGroundBox = 46 * bitmapSource.getWidth() / 272;
        heightGroundBox = 15 * bitmapSource.getHeight() / 128;
        widthTree = 111 * bitmapSource.getWidth() / 272 ;
        heightTree = bitmapSource.getHeight();

        Matrix matrixGWS = new Matrix();
        matrixGWS.setScale(6, 6);
        groundWithGrass = Bitmap.createBitmap(bitmapSource, 113 * bitmapSource.getWidth() / 272,0,
                widthGroundBox, heightGroundBox, matrixGWS, false);
        widthGroundBox *= 6; //так как в дальнейшем картинка будет в 2 раза больше
        heightGroundBox *= 6;

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
