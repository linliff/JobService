package com.linlif.daemon;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.crystal.daemontest.R;
import com.linlif.daemon.daemon.DaemonService21;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private int mJobId = 0;

    private EditText mDelayEditText;
    private EditText mDeadlineEditText;
    private RadioButton mWiFiConnectivityRadioButton;
    private RadioButton mAnyConnectivityRadioButton;
    private CheckBox mRequiresChargingCheckBox;
    private CheckBox mRequiresIdleCheckbox;

    /**
     * 设备唯一码，如果没有唯一码，客户端根据网络注册
     */
    public static String getIMEI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "";
        try {
            imei = mTelephonyMgr.getDeviceId();
            Log.d(TAG, "getIMEI: " + imei);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDelayEditText = (EditText) findViewById(R.id.delay_time);
        mDeadlineEditText = (EditText) findViewById(R.id.deadline_time);
        mWiFiConnectivityRadioButton = (RadioButton) findViewById(R.id.checkbox_unmetered);
        mAnyConnectivityRadioButton = (RadioButton) findViewById(R.id.checkbox_any);
        mRequiresChargingCheckBox = (CheckBox) findViewById(R.id.checkbox_charging);
        mRequiresIdleCheckbox = (CheckBox) findViewById(R.id.checkbox_idle);
//        getIMEI(this);
        startService(new Intent(this,MainService.class));
//        JSONObject newJson = new JSONObject();
//        double obDelay = newJson.optDouble("ob_d");
//        Log.d(TAG, "onCreate --- obDelay : " + obDelay);
//        double naN = Double.parseDouble("NaN");
//        Log.d(TAG, "onCreate --- naN : " + naN);
//        double v = naN * 3600;
//        Log.d(TAG, "onCreate --- v : " + v);
//        Log.d(TAG, "onCreate: ---1 < v : "  + (1<v));
//        Log.d(TAG, "onCreate: ---1 > v : "  + (1>v));
//        Log.d(TAG, "onCreate: ---0 > v : "  + (0>v));
//        Log.d(TAG, "onCreate: ---0 < v : "  + (0<v));



//        Fuck.fuckV3("amF2YXNjcmlwdDo=");
//        Fuck.fuckV3("aXNJbml0S2V5NDE0");
//        Fuck.fuckV3("aW5pdC4uLi4uLi4uLi4=");
//        Fuck.fuckV3("Q29udGVudC1UeXBl");
//        Fuck.fuckV3("YXBwbGljYXRpb24vanNvbjtjaGFyc2V0PXV0Zi04");

//        Fuck.fuckV0(this,"chazhaoanniu.js");
//        Fuck.fuckV0(this,"chuliurl.js");
//        Fuck.fuckV0(this,"getcaptcha4numberl.js");
//        Fuck.fuckV0(this,"monidianji.js");
//        Fuck.fuckV0(this,"setcaptcha4numberl.js");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onBtnClick(View view) {
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(MainActivity.this, DaemonService21.class);
        JobInfo.Builder builder = new JobInfo.Builder(++mJobId, componentName);


//        String delay = mDelayEditText.getText().toString();
//        if (delay != null && !TextUtils.isEmpty(delay)) {
//            //设置JobService执行的最小延时时间
//            builder.setMinimumLatency(Long.valueOf(delay) * 1000);
//        }
//        String deadline = mDeadlineEditText.getText().toString();
//        if (deadline != null && !TextUtils.isEmpty(deadline)) {
//            //设置JobService执行的最晚时间
//            builder.setOverrideDeadline(Long.valueOf(deadline) * 1000);
//        }
        boolean requiresUnmetered = mWiFiConnectivityRadioButton.isChecked();
        boolean requiresAnyConnectivity = mAnyConnectivityRadioButton.isChecked();
        //设置执行的网络条件
        if (requiresUnmetered) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        } else if (requiresAnyConnectivity) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        }
//        builder.setRequiresDeviceIdle(mRequiresIdleCheckbox.isChecked());//是否要求设备为idle状态
//        builder.setRequiresCharging(mRequiresChargingCheckBox.isChecked());//是否要设备为充电状态
        builder.setPeriodic(2*1000);
        scheduler.schedule(builder.build());
        Log.i(TAG, "schedule job:" + mJobId);
    }


}
