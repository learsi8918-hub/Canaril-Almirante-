package com.almirante.canaril;

import android.app.Activity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.webkit.JavascriptInterface;

public class NotificationBridge {
    private final Activity activity;
    public NotificationBridge(Activity activity) { this.activity = activity; }

    @JavascriptInterface
    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= 33 &&
            activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 200);
        }
    }

    @JavascriptInterface
    public void schedule(String title, String isoDateTime, String body) {
        NotificationScheduler.schedule(activity, title, isoDateTime, body);
    }
}
