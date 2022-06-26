package com.fight2048.abase.mvp.view.base;

import android.content.Context;

import androidx.annotation.NonNull;

import com.fight2048.abase.Controller;
import com.fight2048.abase.mvp.contract.base.BaseContract;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description: 所有Controller类的基类，负责View层的UI处理
 */
public abstract class BaseController<P extends BaseContract.Presenter> extends Controller implements BaseContract.View {
    public final String TAG = this.getClass().getSimpleName();
    protected P mPresenter;

    public BaseController(Context context) {
        super(context);
        mPresenter = createPresenter();
    }

    @NonNull
    protected P createPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onClear();
            mPresenter = null;
        }
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void setPresenter(@NonNull P presenter) {
        this.mPresenter = presenter;
    }
}
