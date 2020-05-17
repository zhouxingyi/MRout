package com.fishman.zxy.login;

import com.fishman.zxy.marouter.ARouter;
import com.fishman.zxy.marouter.IRout;

public class ActivityUtils implements IRout {
    @Override
    public void putActivity() {
        ARouter.getInstance().AddActivity("login/login",LoginActivity.class);
    }
}
