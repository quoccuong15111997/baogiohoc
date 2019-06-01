package com.example.tkb;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    String body="";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ManagerSharePreferense.isStart()==true){
            body="Bắt đầu nghĩ lễ";
            ManagerSharePreferense.setStart(false);
        }
        else if (ManagerSharePreferense.isStart()==false){
            body="Kết thúc nghĩ lễ";
            ManagerSharePreferense.setStart(true);
        }
        Log.e("NOTE",body);
        showNotification(context);

    }
    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        Intent maybeReceive = new Intent();
        maybeReceive.setAction("a");
        PendingIntent pendingIntentMaybe = PendingIntent.getBroadcast(context, 12345, maybeReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        @SuppressLint("ResourceAsColor") NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setColor(R.color.colorBlack)
                        .setSound(Uri.parse("android.resource://"
                                + context.getPackageName() + "/" + R.raw.music))
                        .setContentTitle("Đã đến giờ báo")
                        .setContentText(body)
                        .setLights(R.color.red,1000,1000)
                .setVibrate(new long[]{250,250,250,250})
                .addAction(R.drawable.ic_setting,"Vuốt lên để dừng ↑ ",pendingIntentMaybe);

        mBuilder.setFullScreenIntent(contentIntent,true);
        mBuilder.setContentIntent(contentIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
