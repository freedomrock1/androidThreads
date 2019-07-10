package edu.csc4360.threads;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LooperActivity extends AppCompatActivity {
    private TextView mCountTextView;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        mCountTextView = findViewById(R.id.count);

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

    // Handler for the main thread
    private Handler mThreadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // Show the count from the message
            mCountTextView.setText(Integer.toString(msg.arg1));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Interrupt the background thread
        mThread.interrupt();
    }


}
