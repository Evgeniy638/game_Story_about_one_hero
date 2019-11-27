package com.game.story_about_one_hero;

import android.graphics.Bitmap;

public interface interfacePlayerHero {
    Bitmap getCurrentHeroSprite();
    int getRelativeX();
    void setRelativeX(int relativeX);
    int getRelativeY();
    void setRelativeY(int relativeY);
}
