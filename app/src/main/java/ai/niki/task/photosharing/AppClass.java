package ai.niki.task.photosharing;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import static ai.niki.task.photosharing.utils.LogUtils.makeLogTag;

/**
 * Created by sHIVAM on 2/24/2018.
 */

public class AppClass extends Application {

    private final static String TAG = makeLogTag(AppClass.class);

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
