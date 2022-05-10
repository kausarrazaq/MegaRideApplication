package com.example.megarideapplication.utilis;

import android.app.Application;

public class ApplicationMegaRide extends Application {
    ShareMemory shareMemory;

    @Override
    public void onCreate() {
        super.onCreate();

        ShareMemory.init(this);
        shareMemory = ShareMemory.getmInstence();
    }
}
