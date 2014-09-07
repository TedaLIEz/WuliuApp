package com.aicre.wuliuapp.app;

import android.app.Application;

/**
 * Created by wei on 14-7-5.
 */
public class Myapp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        RequestManager.init(this);
    }
}
