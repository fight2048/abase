package com.fight2048.abase.common;

import android.view.View;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description:
 */
public abstract class OnQuickClickListener implements View.OnClickListener {
    public static final int MIN_DELAY_TIME = 618;
    private long lastTime = 0;

    @Override
    public void onClick(View view) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > MIN_DELAY_TIME) {
            lastTime = currentTime;
            onQuickClick(view);
        }
    }

    public abstract void onQuickClick(View view);
}