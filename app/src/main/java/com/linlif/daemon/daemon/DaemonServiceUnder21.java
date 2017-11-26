package com.linlif.daemon.daemon;

import android.content.Context;
import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linlif on 17/8/14.
 */
public class DaemonServiceUnder21 {

    public static void main(String[] args) {
        Method setArgV0;
        try {
            setArgV0 = android.os.Process.class.getDeclaredMethod("setArgV0",
                    new Class[] { String.class });
            setArgV0.setAccessible(true);
            setArgV0.invoke(android.os.Process.class,
                    new Object[] {DaemonConfig.DAEMON_PROCESS_NAME});
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // 主进程ID
        final int hostProcessID;
        if (args.length > 0) {
            try {
                hostProcessID = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return;
            }
        } else {
            return;
        }

        while (true) {
            try {
                File hostProcess = new File("/proc/" + hostProcessID);
                if (!hostProcess.exists()) {
                    // 证明主进程已经被kill掉，所以重新启动服务
                    String cmdLine = "am startservice --user 0 -a "
                            + DaemonConfig.MAIN_SERVICE_ACTION_NAME;
                    if (Build.VERSION.SDK_INT <= 15) {
                        cmdLine = "am startservice -a "
                                + DaemonConfig.MAIN_SERVICE_ACTION_NAME;
                    }
                    try {
                        Runtime.getRuntime().exec(cmdLine);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                Thread.sleep(10000);// 十秒
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * 得到守护进程的ID
     */
    public static int getDaemonProcessID() {
        for (File processDir : getAllProcessDirs()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(new File(processDir, "cmdline"))));
                String processName = br.readLine();
                if (processName == null) {
                    continue;
                }
                processName = processName.trim();
                if (DaemonConfig.DAEMON_PROCESS_NAME.equals(processName)) {
                    try {
                        return Integer.parseInt(processDir.getName());
                    } catch (NumberFormatException e) {
                    }
                }
            } catch (FileNotFoundException e) {
                //ignore
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return -1;
    }

    private static File[] getAllProcessDirs() {
        File procDir = new File("/proc");
        return procDir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                try {
                    Integer.parseInt(pathname.getName());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
    }

    public static void startDaemonProcess(Context c) {
        int daemonPid = getDaemonProcessID();
        if (daemonPid == -1) {
            try {
                // System.getenv()读取系统环境变量
                Map<String, String> envMap = new HashMap<String, String>(
                        System.getenv());
                envMap.put("CLASSPATH", c
                        .getApplicationInfo().sourceDir);
                String[] envs = new String[envMap.size()];
                int index = 0;
                for (Map.Entry<String, String> entry : envMap.entrySet()) {
                    envs[index++] = entry.getKey() + "=" + entry.getValue();
                }
                // app_process用于启动java类,最终会调main函数
                Runtime.getRuntime().exec("/system/bin/app_process /system/bin "
                        + DaemonServiceUnder21.class.getName() + " "
                        + android.os.Process.myPid() + "\n", envs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止守护进程
     */
    public static void stopDaemonProcess() {
        int daemonPid = getDaemonProcessID();
        if (daemonPid != -1) {
            android.os.Process.killProcess(daemonPid);
        }
    }
}
