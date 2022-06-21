package com.fight2048.abase.mvvm.model.base;

import com.fight2048.abase.mvvm.contract.base.BaseContract;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description: 所有Model类的基类，负责模型层的内容，包括数据获取和处理以及部分业务逻辑代码。
 */
public abstract class BaseRepository implements BaseContract.Model {
    public final String TAG = BaseRepository.class.getSimpleName();
}
