package com.fight2048.abase.mvp.view.base;

import android.app.Dialog;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fight2048.abase.common.ActivityHelper;
import com.fight2048.abase.mvp.contract.base.BaseContract;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OSUtils;

import cn.itsite.adialog.dialog.LoadingDialog;

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
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    protected P mPresenter;
    protected ImmersionBar mImmersionBar;
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
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true)
                .navigationBarWithKitkatEnable(false)
                .init();
        //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景色未被隐藏的问题
        if (OSUtils.isEMUI3_1()) {
            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    (NAVIGATIONBAR_IS_MIN), true, new ContentObserver(new Handler()) {
                @Override
                public void onChange(boolean selfChange) {
                    int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                            NAVIGATIONBAR_IS_MIN, 0);
                    if (navigationBarIsMin == 1) {
                        //导航键隐藏了
                        mImmersionBar.transparentNavigationBar().init();
                    } else {
                        //导航键显示了
                        mImmersionBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                                .fullScreen(false)
                                .init();
                    }
                }
            });
            //第二种,禁止对导航栏的设置
            //mImmersionBar.navigationBarEnable(false).init();
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

        if (mImmersionBar != null) {
//            mImmersionBar.destroy();
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
