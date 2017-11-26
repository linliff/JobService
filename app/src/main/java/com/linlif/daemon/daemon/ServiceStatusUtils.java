package com.linlif.daemon.daemon;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

public class ServiceStatusUtils {

	/**
	 * 校验某个服务是否活着
	 * @param context 上下文
	 * @param serviceName 要校验的服务
	 * @return
	 */
	public static boolean isServiceRunning(Context context,String serviceName){
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
		String name;
		for (RunningServiceInfo info : runningServices) {
			name = info.service.getClassName();
			if (TextUtils.equals(name, serviceName)) {
				return true;
			}
		}
		return false;
	}
}
