package code.fortomorrow.kheloNowAdmin.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;

import androidx.core.app.NotificationCompat;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import code.fortomorrow.kheloNowAdmin.MainActivity;
import code.fortomorrow.kheloNowAdmin.R;

/**
 * Created by tonmoy on 12/16/2017.
 */

public class MyNotificationManager {


    public static final String CHANNEL_ID = "a";


    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;


    private Context mCtx;

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public void showBigNotification(String title, String message, String url, Intent intent) {
        String split[] = title.split(",,");

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent resultIntenet = new Intent(mCtx, MainActivity.class);
        resultIntenet.putExtra("title", split[1]);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        m,
                        resultIntenet,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Bitmap icon1 = BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.ludo_dice_icon);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(split[0]);
        //bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.drawable.icon).setTicker(split[0]).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(split[0])
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.icon))
                .setContentText(message)
                .setSound(alarmSound)
                .setChannelId(CHANNEL_ID)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;


        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            PendingIntent resultPendingIntent1 =
                    PendingIntent.getActivity(
                            mCtx,
                            m,
                            resultIntenet,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );


            NotificationCompat.BigPictureStyle bigPictureStyle1 = new NotificationCompat.BigPictureStyle();
            bigPictureStyle1.setBigContentTitle(split[0]);
            //bigPictureStyle1.setSummaryText(Html.fromHtml(message).toString());
            bigPictureStyle1.bigPicture(getBitmapFromURL(url));


            String CHANNEL_ID = "my_channel_011";
            CharSequence name = CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);


            NotificationCompat.Builder mBuilder1 = new NotificationCompat.Builder(mCtx);
            Notification notification1;
            notification1 = mBuilder1.setSmallIcon(R.mipmap.icon)
                    .setAutoCancel(true)
                    .setContentIntent(resultPendingIntent1)
                    .setContentTitle(split[0])
                    .setStyle(bigPictureStyle1)
                    .setSmallIcon(R.drawable.icon)
                    .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.icon))
                    .setContentText(message)
                    .setSound(alarmSound)
                    .setChannelId(CHANNEL_ID)
                    .build();


            try {
                Uri notification2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(mCtx, notification2);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }


            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            NotificationManager mNotificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(m, notification1);


        }


    }


    public void showSmallNotification(String title, String message, Intent intent) {

        //title = "khelo";
        String split = title;

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        Intent resultIntenet = new Intent(mCtx, MainActivity.class);
        resultIntenet.putExtra("title", split);

//        PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(
//                        mCtx,
//                        m,
//                        resultIntenet,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
        PendingIntent resultPendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            resultPendingIntent = PendingIntent.getActivity
                    (mCtx, 0, resultIntenet, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            resultPendingIntent = PendingIntent.getActivity
                    (mCtx, 0, resultIntenet, PendingIntent.FLAG_ONE_SHOT);
        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.icon).setTicker(split).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(split)
                .setSmallIcon(R.mipmap.icon)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.icon))
                .setContentText(message)
                .setSound(alarmSound)
                .setChannelId(CHANNEL_ID)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;


        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


//            PendingIntent resultPendingIntent1 =
//                    PendingIntent.getActivity(
//                            mCtx,
//                            m,
//                            resultIntenet,
//                            PendingIntent.FLAG_UPDATE_CURRENT
//                    );
            PendingIntent resultPendingIntent1 = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                resultPendingIntent1 = PendingIntent.getActivity
                        (mCtx, 0, resultIntenet, PendingIntent.FLAG_MUTABLE);
            }
            else
            {
                resultPendingIntent1 = PendingIntent.getActivity
                        (mCtx, 0, resultIntenet, PendingIntent.FLAG_ONE_SHOT);
            }


            String CHANNEL_ID = "my_channel_01";
            CharSequence name = CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            Notification notification1 = new Notification.Builder(mCtx)
                    .setAutoCancel(true)
                    .setContentTitle(split)
                    .setContentText(message)
                    .setContentIntent(resultPendingIntent1)
                    .setSmallIcon(R.mipmap.icon)
                    .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.icon))
                    .setChannelId(CHANNEL_ID)
                    .build();

            try {
                Uri notification2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(mCtx, notification2);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            NotificationManager mNotificationManager =
                    (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(m, notification1);


        }


    }


    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}