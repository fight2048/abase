package com.fight2048.abase.mvvm.view.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fight2048.abase.common.ActivityHelper;
import com.fight2048.abase.mvvm.contract.base.BaseContract;
import com.fight2048.abase.mvvm.viewmodel.base.BaseViewModel;
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
public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements BaseContract.View {
    public static final String TAG = BaseActivity.class.getSimpleName();
    protected VM mViewModel;
    protected Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelper.getInstance().addActivity(this);
        initStateBar();
        initViewModel();
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
        getLifecycle().removeObserver(mViewModel);
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
