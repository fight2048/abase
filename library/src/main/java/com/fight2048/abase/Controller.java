package com.fight2048.abase;

import android.content.Context;
import android.view.View;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description:
 */
public abstract class Controller {
    protected View mView;
    protected Context mContext;

    public Controller(Context context) {
        this.mContext = context;
        this.mView = initView();
        initData();
    }

    /**
     * 初始化View
     *
     * @return 返回根布局的View
     */
    public abstract View initView();

    /**
     * 初始化数据的方法，孩子如果有数据初始化，就复写
     */
    public void initData() {
    }

    public View getView() {
        return mView;
    }

    public Context getContext() {
        return mContext;
    }

    public void onDestroy() {
        mContext = null;
        mView = null;
    }
}
