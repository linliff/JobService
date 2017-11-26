package com.linlif.daemon.daemon;

import android.content.Context;

/**
 * Created by linlif on 17/8/14.
 */
public class DaemonClient {

    public static void initDaemon(Context context){
        if (DaemonConfig.ENABLE_DAEMON)
            IDaemonStrategy.Fetcher.fetchStrategy().onInitialization(context);
    }
}
