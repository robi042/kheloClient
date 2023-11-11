package code.fortomorrow.kheloNowAdmin;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;


public class AnalyticsApplication extends Application {
    private static GoogleAnalytics sAnalytics;
    private static Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (mTracker == null) {
            mTracker = sAnalytics.newTracker(R.xml.global_tracer);
        }

        return mTracker;
    }
}
