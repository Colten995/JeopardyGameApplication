package ca.on.conestogac.jeopardygameapplication;

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
                    //TODO: Send notification reminding user to return to the app
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