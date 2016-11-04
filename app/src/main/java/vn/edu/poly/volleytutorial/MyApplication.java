package vn.edu.poly.volleytutorial;

import android.app.Application;
import android.content.Context;

/**
 * Created by nix on 11/2/16.
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
