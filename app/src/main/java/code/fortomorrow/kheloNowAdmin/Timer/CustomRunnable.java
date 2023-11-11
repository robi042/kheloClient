package code.fortomorrow.kheloNowAdmin.Timer;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomRunnable implements Runnable {

    public long millisUntilFinished = 40000;
    public TextView textView;
    Handler handler;

    public CustomRunnable(Handler handler, TextView textView, long millisUntilFinished) {
        this.handler = handler;
        this.textView = textView;
        this.millisUntilFinished = millisUntilFinished;
    }

    @Override
    public void run() {
        /* do what you need to do */
        long seconds = millisUntilFinished / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String time = days + " " + "days" + " :" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
        textView.setText(time);

        millisUntilFinished -= 1000;

        handler.postDelayed(this, 1000);
    }

}