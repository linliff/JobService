package com.linlif.daemon.daemon;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by linlif on 17/8/7.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DaemonService21 extends JobService{
    private static final String TAG = DaemonService21.class.getSimpleName();
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "DaemonService21 --- onStartJob: " + params.getJobId());
        if (!ServiceStatusUtils.isServiceRunning(this,DaemonConfig.MAIN_SERVICE_NAME)){
            try {
                Intent intent = new Intent(DaemonConfig.MAIN_SERVICE_ACTION_NAME);
                intent.setPackage(getPackageName());
                startService(intent);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        jobFinished(params,true);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "DaemonService21 --- onStopJob: " + params.getJobId());
        return false;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "DaemonService21 --- onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "DaemonService21 --- onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "DaemonService21 --- onDestroy: ");
        super.onDestroy();
    }
}
