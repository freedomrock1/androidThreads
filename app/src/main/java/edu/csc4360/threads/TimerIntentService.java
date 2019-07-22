package edu.csc4360.threads;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class TimerIntentService extends IntentService {
    public static final String EXTRA_MILLIS_LEFT = "edu.csc4360.threads.extra.EXTRA_MILLIS_LEFT";

    public TimerIntentService() {
        super("TimerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Get millis from the activity and start a new TimerModel
        long millisLeft = intent.getLongExtra(EXTRA_MILLIS_LEFT, 0);
        TimerModel timerModel = new TimerModel();
        timerModel.start(millisLeft);

        // Create notification channels
        createTimerNotificationChannel();
        //createFinishedNotificationChannel();
        //createFinishedNotificationChannel();

        while (timerModel.isRunning()) {
            try {
                createTimerNotification(timerModel.toString());
                Thread.sleep(1000);

                if (timerModel.getRemainingMilliseconds() == 0) {
                    timerModel.stop();
                    createTimerNotification("Timer is finished!");
                }
            } catch (InterruptedException e) {
            }
        }
    }

    private final String CHANNEL_ID_TIMER = "channel_timer";

    private void createTimerNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }

        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_TIMER, name, importance);
        channel.setDescription(description);

        // Register channel with system
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private final int NOTIFICATION_ID = 0;

    private void createTimerNotification(String text) {

        // Create notification with various properties
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_TIMER)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        // Get compatibility NotificationManager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Post notification using ID.  If same ID, this notification replaces previous one
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

}
