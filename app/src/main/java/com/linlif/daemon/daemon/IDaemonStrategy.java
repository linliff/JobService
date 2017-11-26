package com.linlif.daemon.daemon;

import android.content.Context;
import android.os.Build;

/**
 * define strategy method
 * 
 * @author Mars
 *
 */
public interface IDaemonStrategy {
	/**
	 * Initialization some files or other when 1st time 
	 * 
	 * @param context
	 * @return
	 */
	boolean onInitialization(Context context);

	
	/**
	 * all about strategy on different device here
	 * 
	 * @author Mars
	 *
	 */
	public static class Fetcher {

		private static IDaemonStrategy mDaemonStrategy;

		/**
		 * fetch the strategy for this device
		 * 
		 * @return the daemon strategy for this device
		 */
		static IDaemonStrategy fetchStrategy() {
			if (mDaemonStrategy != null) {
				return mDaemonStrategy;
			}
			int sdk = Build.VERSION.SDK_INT;
			if (sdk >= 21){
				mDaemonStrategy = new DaemonStrategy21();
			}else {
				mDaemonStrategy = new DaemonStrategyUnder21();
			}
			return mDaemonStrategy;
		}
	}
}
