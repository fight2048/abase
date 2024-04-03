package com.fight2048.abase.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description:
 */
public abstract class BaseService extends Service {
    protected final List<BroadcastReceiver> receivers = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver();
    }

    @Override
    public void onDestroy() {
        receivers.forEach(r ->
                LocalBroadcastManager.getInstance(this)
                        .unregisterReceiver(r)
        );
        super.onDestroy();
    }

    protected void registerReceiver() {
    }

    protected void registerReceiver(Runnable runnable, String... actions) {
        IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null || TextUtils.isEmpty(intent.getAction())) {
                    return;
                }
                runnable.run();
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver, filter);
        receivers.add(receiver);
    }
}

