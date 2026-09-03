package com.almirante.canaril;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL = "canaril_lembretes";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm =
                (NotificationManager) context.getSystemService(
                        Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL,
                            "Lembretes do Canaril",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            nm.createNotificationChannel(channel);
        }

        Intent abrirApp =
                new Intent(context, MainActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        abrirApp,
                        PendingIntent.FLAG_UPDATE_CURRENT |
                                PendingIntent.FLAG_IMMUTABLE
                );

        android.app.Notification.Builder builder;

        if (Build.VERSION.SDK_INT >= 26) {
            builder =
                    new android.app.Notification.Builder(
                            context,
                            CHANNEL
                    );
        } else {
            builder =
                    new android.app.Notification.Builder(context);
        }

        builder.setSmallIcon(
                        android.R.drawable.ic_popup_reminder
                )
                .setContentTitle(
                        intent.getStringExtra("title")
                )
                .setContentText(
                        intent.getStringExtra("body")
                )
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        nm.notify(
                (int) (System.currentTimeMillis() & 0x7fffffff),
                builder.build()
        );
    }
}
