package com.xingsu.italker.push;

import com.igexin.sdk.PushManager;
import com.xingsu.italker.common.common.app.Application;
import com.xingsu.italker.factory.Factory;

/**
 * Created by 王泽华 on 2017/9/24.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //调用Factory进行初始化
        Factory.setup();
        //推送进行初始化
        PushManager.getInstance().initialize(this);
    }
}
