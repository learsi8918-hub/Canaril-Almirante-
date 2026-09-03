package com.almirante.canaril;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationScheduler {
    public static void schedule(Context context, String title, String isoDateTime, String body) {
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

            Date date = sdf.parse(isoDateTime);

            if (date == null || date.getTime() <= System.currentTimeMillis()) {
                return;
            }

            Intent intent = new Intent(context, ReminderReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("body", body);

            int requestCode =
                    Math.abs((title + isoDateTime + body).hashCode());

            PendingIntent pi = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT |
                            PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager am =
                    (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= 31 &&
                    !am.canScheduleExactAlarms()) {

                am.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        date.getTime(),
                        pi
                );

            } else if (Build.VERSION.SDK_INT >= 23) {

                am.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        date.getTime(),
                        pi
                );

            } else {

                am.setExact(
                        AlarmManager.RTC_WAKEUP,
                        date.getTime(),
                        pi
                );
            }

        } catch (Exception ignored) {
        }
    }
}
