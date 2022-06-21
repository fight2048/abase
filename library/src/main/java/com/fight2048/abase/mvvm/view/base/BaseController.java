package com.fight2048.abase.mvvm.view.base;

import android.content.Context;

import androidx.annotation.NonNull;

import com.fight2048.abase.Controller;
import com.fight2048.abase.mvvm.contract.base.BaseContract;
import com.fight2048.abase.mvvm.viewmodel.base.BaseViewModel;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description: 所有Controller类的基类，负责View层的UI处理
 */
public abstract class BaseController<VM extends BaseViewModel> extends Controller implements BaseContract.View {
    public final String TAG = this.getClass().getSimpleName();
    protected VM mViewModel;

    public BaseController(Context context) {
        super(context);
        mViewModel = createViewModel();
    }

    @NonNull
    protected VM createViewModel() {
        return null;
    }

    public VM getViewModel() {
        return mViewModel;
    }

    public void setViewModel(@NonNull VM viewModel) {
        this.mViewModel = viewModel;
    }

}
