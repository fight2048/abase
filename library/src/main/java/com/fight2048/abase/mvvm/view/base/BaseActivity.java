package com.fight2048.abase.mvvm.view.base;

import android.app.Dialog;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fight2048.abase.common.ActivityHelper;
import com.fight2048.abase.mvvm.contract.base.BaseContract;
import com.fight2048.abase.mvvm.viewmodel.base.BaseViewModel;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OSUtils;

import cn.itsite.adialog.support.dialog.LoadingDialog;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description: 所有Activity类的基类，负责View层的UI处理
 */
public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements BaseContract.View {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    protected VM mViewModel;
    protected ImmersionBar mImmersionBar;
    protected Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelper.getInstance().addActivity(this);
        initStateBar();
        initViewModel();
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

    private void initViewModel() {
        mViewModel = onCreateViewModel();
        if (mViewModel != null) {
            getLifecycle().addObserver(mViewModel);
            mViewModel.loading.observe(this, o -> {
                onLoading();
            });
            mViewModel.complete.observe(this, o -> {
                onComplete();
            });
            mViewModel.error.observe(this, o -> {
                onError();
            });
        }
    }

    protected VM onCreateViewModel() {
        return onBindViewModel() != null ? new ViewModelProvider(this).get(onBindViewModel()) : null;
    }

    protected Class<VM> onBindViewModel() {
        return null;
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityHelper.getInstance().removeActivity(this);
        if (mImmersionBar != null) {
//            mImmersionBar.destroy();
        }
        getLifecycle().removeObserver(mViewModel);
    }
}
