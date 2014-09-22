
package slidenerd.vivz.nosqlite;

import com.activeandroid.ActiveAndroid;

import android.app.Application;

public class MyApplication extends Application {
    public static final String TAG = "VIVZ";

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        // Notice this initialization code here
        ActiveAndroid.initialize(this);
    }
}
