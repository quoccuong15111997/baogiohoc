package com.example.tkb;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ManagerSharePreferense.init(this);
    }
}
