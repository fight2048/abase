package com.fight2048.abase.mvp.view.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fight2048.abase.common.ActivityHelper;
import com.fight2048.abase.mvp.contract.base.BaseContract;
import com.fight2048.adialog.androidx.dialog.LoadingDialog;
import com.gyf.immersionbar.ImmersionBar;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description: 所有Activity类的基类，负责View层的UI处理
 */
public abstract class BaseActivity<P extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.View {
    public static final String TAG = BaseActivity.class.getSimpleName();
    protected P mPresenter;
    protected Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        initStateBar();
        mPresenter = onCreatePresenter();
    }

    private void initActivity() {
        //把每一个Activity加入栈中
        ActivityHelper.getInstance().addActivity(this);
    }

    protected void initStateBar() {
        if (ImmersionBar.hasNavigationBar(this)) {
            ImmersionBar.with(this)
                    //设置这个后，可以保证底部有导航栏的时候，底部空间不被占用
                    .navigationBarEnable(false)
                    .fullScreen(true)
                    .transparentNavigationBar()
                    .statusBarAlpha(0.2F)
                    .keyboardEnable(true)
                    .navigationBarDarkIcon(true)
                    .init();
        }
    }

    @NonNull
    protected P onCreatePresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //把每一个Activity弹出栈。
        ActivityHelper.getInstance().removeActivity(this);

        if (mPresenter != null) {
            mPresenter.onClear();
            mPresenter = null;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    /**
     * 用于被P层调用的通用函数。
     *
     * @param response
     */
    @Override
    public void onLoading(Object... response) {
        showLoading();
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this)
                    .setDimAmount(0);
        }
        loadingDialog.show();
    }

    @Override
    public void onError(Object... error) {
        dismissLoading();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onComplete(Object... response) {
        dismissLoading();
    }
}
