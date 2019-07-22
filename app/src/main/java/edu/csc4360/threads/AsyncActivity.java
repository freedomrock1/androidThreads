package edu.csc4360.threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncActivity extends AppCompatActivity {
    ProgressBar mDownloadProgressBar;
    TextView mSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        mDownloadProgressBar = findViewById(R.id.mDownloadProgressBar);
        mSummary = findViewById(R.id.mSummary);

    }



    private boolean downloadUrl(String url){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    private class DownloadUrlsTask extends AsyncTask<String, Integer, Integer> {
        protected void onPreExecute() {
            mDownloadProgressBar.setProgress(0);
        }

        protected Integer doInBackground(String... urls) {
            int downloadSuccess = 0;
            for (int i = 0; i < urls.length; i++) {
                if (downloadUrl(urls[i])) {
                    downloadSuccess++;
                }
                publishProgress((i + 1) * 100 / urls.length);
            }
            return downloadSuccess;
        }

        protected void onProgressUpdate(Integer... progress) {
            mDownloadProgressBar.setProgress(progress[0]);
        }

        protected void onPostExecute(Integer numDownloads) {
            mSummary.setText("Downloaded " + numDownloads + " URLs");
        }
    }

    public void onClick(View view){

        DownloadUrlsTask task = new DownloadUrlsTask();
        task.execute("http://google.com/", "http://wikipedia.org/", "http://mit.edu/", "hello");

    }



}
