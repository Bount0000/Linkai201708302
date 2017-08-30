package com.bwei.linkai201708302;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lenovo on 2017/8/30.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
