package edu.csc4360.threads;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class Looper2Activity extends AppCompatActivity {
    private TextView mCountTextView;
    private Thread mThread;
    private CountHandler mThreadHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper2);
        mCountTextView = findViewById(R.id.count2);

        mThreadHandler = new CountHandler(this);

        // Create a background thread
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                try {
                    while (true) {
                        // Delay for one second
                        Thread.sleep(1000);

                        // Send a new count to the main thread
                        count++;
                        Message msg = mThreadHandler.obtainMessage();
                        msg.arg1 = count;
                        mThreadHandler.sendMessage(msg);
                    }
                } catch (InterruptedException e) {
                }
            }
        });
        mThread.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Interrupt the background thread
        mThread.interrupt();
    }

    // Handler for the main thread
    private static class CountHandler extends Handler {
        private final WeakReference<Looper2Activity> mActivity;

        public CountHandler(Looper2Activity activity) {
            // Create a weak reference to Activity
            mActivity = new WeakReference<Looper2Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // Ensure MainActivity has not been garbage collected
            Looper2Activity activity = mActivity.get();
            if (activity != null) {
                activity.mCountTextView.setText(Integer.toString(msg.arg1));
            }
        }
    }
}
