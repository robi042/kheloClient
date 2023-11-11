package code.fortomorrow.kheloNowAdmin.Firebase;

import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import code.fortomorrow.kheloNowAdmin.R;

public class MyFirebaseMessagingServiceSSS extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        notify(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        //getting the title and the body
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        String url = String.valueOf(remoteMessage.getNotification().getImageUrl());

        //Log.d("cvbdg", title + " " + body);

        MyNotificationManager notificationManager = new MyNotificationManager(getApplicationContext());
        notificationManager.showSmallNotification(title, body, null);
        //notificationManager.showBigNotification(title, body, url, null);

    }

    public void notify(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification_channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(123, builder.build());
    }
}
