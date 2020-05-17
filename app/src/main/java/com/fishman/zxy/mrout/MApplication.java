package com.fishman.zxy.mrout;

import android.app.Application;

import com.fishman.zxy.marouter.ARouter;

public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
