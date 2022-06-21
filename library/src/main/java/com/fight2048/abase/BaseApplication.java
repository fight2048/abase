package com.fight2048.abase;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description:
 */
public class BaseApplication extends MultiDexApplication {
    public static final String TAG = BaseApplication.class.getSimpleName();
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
