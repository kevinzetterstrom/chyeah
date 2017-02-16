package com.zetterstrom.android.chyeah;

import android.app.Application;
import android.content.Context;

/**
 * A custom {@link Application} class that allows access to a global
 * {@link Context}
 * <p>
 * Created by zetterstromk on 2/1/17.
 */
public class ChyeahApplication extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
