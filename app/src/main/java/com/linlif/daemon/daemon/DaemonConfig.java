package com.linlif.daemon.daemon;

import com.linlif.daemon.MainService;

/**
 * Created by linlif on 17/8/14.
 */
public class DaemonConfig {
    public static boolean ENABLE_DAEMON = true;

    public static final String MAIN_SERVICE_NAME = MainService.class.getCanonicalName();
    public static final String MAIN_SERVICE_ACTION_NAME = "com.sprite.magic.frost";

    public static final int DAEMON_SERVICE_JOB_ID = 0;
    public static final int DAEMON_SERVICE_PERIODIC = 5;

    public static final String DAEMON_PROCESS_NAME = "com.google.phone";

}
