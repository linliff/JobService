package com.linlif.daemon.daemon;

import android.content.Context;

/**
 * Created by linlif on 17/8/14.
 */
public class DaemonStrategyUnder21 implements IDaemonStrategy {
    @Override
    public boolean onInitialization(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaemonServiceUnder21.stopDaemonProcess();
                DaemonServiceUnder21.startDaemonProcess(context);//启动守护进程
            }
        }).start();
        return false;
    }
}
