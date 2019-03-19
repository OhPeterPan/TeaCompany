package com.example.administrator.chadaodiancompany.application;

import android.app.Application;

import com.example.administrator.chadaodiancompany.util.UtilManager;

public class TeaApplication extends Application {
    private static TeaApplication sTeaApplication;

    public static TeaApplication getInstance() {
        return sTeaApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sTeaApplication = this;
        UtilManager.getInstance().initProject();
    }
}
