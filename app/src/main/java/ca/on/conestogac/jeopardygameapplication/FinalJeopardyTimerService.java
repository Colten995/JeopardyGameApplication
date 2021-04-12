package ca.on.conestogac.jeopardygameapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class FinalJeopardyTimerService extends Service {
    private int counter;
    private final int TEN_SEC_IN_MILLIS = 10000;
    public FinalJeopardyTimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        final int NOTIFICATION_ID = 1;
        final int REQUEST_CODE = 0;
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent returnToFinalJeopardyIntent = new Intent(getApplicationContext(), FinalJeopardyActivity.class);
        returnToFinalJeopardyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingReturnToFinalJeopardyIntent = PendingIntent.getActivity(getApplicationContext(),
                REQUEST_CODE, returnToFinalJeopardyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        counter = 0;
        final Timer timerForFinalJeopardy = new Timer(true);
        final Toast finalJeopardyReminderToast = Toast.makeText(this, R.string.ten_sec_left_final_jeopardy, Toast.LENGTH_LONG);
        final Toast finalJeopardyOverToast = Toast.makeText(this, R.string.final_jeopardy_time_is_up,Toast.LENGTH_LONG);

        Toast.makeText(this, R.string.final_jeopardy_timer_started, Toast.LENGTH_LONG).show();

        timerForFinalJeopardy.schedule(new TimerTask() {
            @Override
            public void run() {

                if (counter == 2)
                {
                    finalJeopardyReminderToast.show();
                    //TODO: Replace the small icon here with the icon for the app
                    final Notification returnToFinalJeopardyNotif = new Notification.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.ic_schedule_black_24dp)
                            .setContentTitle(getString(R.string.final_jeopardy))
                            .setContentText(getString(R.string.ten_sec_left_final_jeopardy))
                            .setAutoCancel(true)
                            .setContentIntent(pendingReturnToFinalJeopardyIntent)
                            .build();

                    notificationManager.notify(NOTIFICATION_ID, returnToFinalJeopardyNotif);
                }
                else if(counter >= 3)
                {
                    finalJeopardyOverToast.show();
                    timerForFinalJeopardy.cancel();
                    stopSelf();
                }
                counter++;


            }
        }, 0 , TEN_SEC_IN_MILLIS);
        super.onCreate();
    }
}