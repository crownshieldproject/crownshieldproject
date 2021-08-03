package com.application.News.util.blur;

import android.view.View;
import android.view.animation.AlphaAnimation;

public class Helper {
    public static boolean hasZero(int... args) {
        for (int num : args) {
            if (num == 0) {
                return true;
            }
        }
        return false;
    }

    public static void animate(View v, int duration) {
        AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
        alpha.setDuration(duration);
        v.startAnimation(alpha);
    }
}
