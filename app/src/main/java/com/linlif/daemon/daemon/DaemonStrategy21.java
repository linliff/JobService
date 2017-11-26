package com.linlif.daemon.daemon;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;


/**
 * Created by linlif on 17/8/14.
 */
public class DaemonStrategy21 implements IDaemonStrategy{
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onInitialization(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(context, DaemonService21.class);
        JobInfo.Builder builder = new JobInfo.Builder(DaemonConfig.DAEMON_SERVICE_JOB_ID, componentName);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPeriodic(DaemonConfig.DAEMON_SERVICE_PERIODIC*1000);
        scheduler.schedule(builder.build());
        return false;
    }
}
