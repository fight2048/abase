package com.fight2048.abase.mvvm.contract.base;

import androidx.lifecycle.DefaultLifecycleObserver;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description: 所有契约接口的基类接口，定义了各层对象的生命周期。
 */
public interface BaseContract {
    interface View {

        /**
         * 用于加载loading操作
         *
         * @param response
         */
        default void onLoading(Object... response) {

        }

        /**
         * VM层如果请求发生错误会发信号过来，默认调用该方法
         *
         * @param error
         */
        default void onError(Object... error) {

        }

        /**
         * VM层请求完成后会会发信号过来，默认调用该方法
         *
         * @param response
         */
        default void onComplete(Object... response) {

        }
    }

    interface ViewModel extends DefaultLifecycleObserver {

        /**
         * 用于被View调用，初始化ViewModel请求等。
         */
        default void onInitialize(Object... request) {

        }

        /**
         * View可见时会调用该方法。
         */
        default void onVisible(Object... request) {

        }

        /**
         * View不可见时会调用该方法。
         */
        default void onInvisible(Object... request) {

        }
    }

    interface Model {

        /**
         * Model的生命周期开始。
         */
        default void onInitialize(Object... request) {

        }

        /**
         * Model的生命周期结束，释放资源。
         */
        default void onClear() {

        }
    }
}
