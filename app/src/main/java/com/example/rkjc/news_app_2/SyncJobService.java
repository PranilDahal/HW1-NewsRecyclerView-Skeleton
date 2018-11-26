package com.example.rkjc.news_app_2;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class SyncJobService extends JobService {

    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {

        mBackgroundTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                NewsItemRepository newsItemRepository = new NewsItemRepository(SyncJobService.this);
                newsItemRepository.syncDatabase();


                Intent cancelIntent = new Intent(SyncJobService.this, CancelIntentService.class);
                cancelIntent.setAction("CANCEL_ACTION");

                PendingIntent cancelPendingIntent = PendingIntent.getService(
                        SyncJobService.this,
                        1499,
                        cancelIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationCompat.Action cancelAction = new NotificationCompat.Action(R.drawable.ic_visibility_off_black_24dp,
                        "Cancel",
                        cancelPendingIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(SyncJobService.this);
                String textContent = "News-App: " +"New news articles have been added to your app!";
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SyncJobService.this, "news_app")
                        .setSmallIcon(R.drawable.ic_public_black_24dp)
                        .setContentTitle("News Update")
                        .setContentText(textContent)
                        .addAction(cancelAction)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                notificationManager.notify(1599, mBuilder.build());

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                System.out.println("THIS IS SUPPOSED TO RUN EVERY 10 SECONDS.");
                super.onPostExecute(o);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        System.out.println("*** STOPPED SYNCJOBSERVICE *** ");
        return true;
    }

}
