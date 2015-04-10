package com.kiddoware.kidsplace_sdk.samples.advanced;



import com.kiddoware.kidsplace.sdk.KPUtility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.preference.PreferenceManager;

public class Utility {
	
	// Global debug flag - use when you have alternate logic so we can easily switch
	// everything at once - change this when release
	public static boolean DEBUG_MODE = false;
	public static String KIDSPLACE_PKG_NAME = "com.kiddoware.kidsplace";
	
	//APP MARKETS Constants
	protected static final int ANDROID_MARKET = 1;
	protected static final int AMAZON_MARKET = 2;

	//market switch logic
	protected static int APP_MARKET = ANDROID_MARKET;//comment this for Amazon market	
	//private static int APP_MARKET = AMAZON_MARKET;//comment this for Android market
	    
    protected static final String KEY_CHILD_LOCK_SETTING = "childLockEnabled";//This flag controls if Kids Place will be started on launch or not
	private static final String TAG = "Utility";
	private static boolean LOGGING_ERR = true;
	private static boolean runningStandAlone = false;//flag to indicate if app is running stand alone or part of Kids Place
	
	
	public static boolean _isKidsPlaceLocked = false;

	private Utility() {
	}

	protected static void logMsg(String messgae, String tag) {
		if (DEBUG_MODE)
			Log.v(tag, messgae);
	}

	protected static void logErrorMsg(String messgae, String tag) {
		if (LOGGING_ERR) {
			Log.e(tag, messgae);
		}
	}

	protected static boolean getChildLockSetting(Context ctxt) {
		boolean value = false;
		try {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ctxt);
			value = settings.getBoolean(KEY_CHILD_LOCK_SETTING,false);
		} catch (Exception ex) {
			Utility.logErrorMsg("getChildLockSetting:"+ex.getMessage(), TAG);

		}
		return value;
	}
	protected static void setChildLockSetting(Context ctxt, boolean value) {
		try {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ctxt);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean(KEY_CHILD_LOCK_SETTING, value);
			editor.commit();
			
		} catch (Exception ex) {
			Utility.logErrorMsg("setChildLockSetting:"+ex.getMessage(), TAG);

		}
	}

	
	
	//check if specified package exists or not
	protected static boolean isPackageExists(String targetPackage, final Context mContext){
		boolean retValue = true;
		try {
			//check if package exists
			mContext.getPackageManager().getApplicationInfo(targetPackage, PackageManager.GET_META_DATA);    
			Utility.logMsg(targetPackage + " exists", TAG);

		}
		catch (NameNotFoundException nameNotFoundEx){
			Utility.logMsg(targetPackage + "does not exists", TAG);			
			//package does not exists
			retValue = false;
		}
        return retValue;
    }

	protected static boolean isKidsPlaceInstalled(Context context) {
		// TODO Auto-generated method stub
		return isPackageExists(KIDSPLACE_PKG_NAME, context);
	}

	/*
	 * Evaluates if KP need to be started or not or KP install/update need to be prompted
	 * or not based on user settings. Returns true if a dialog is show to upgrade/install 
	 * otherwise return false
	 */
	protected static void handleKPIntegration(Activity activity) {
		try{
			//check if child lock is enabled or not in app's settings
			if(Utility.getChildLockSetting(activity.getApplicationContext())){
				
				KPUtility.handleKPIntegration(activity, APP_MARKET);
			}
		}
		catch (Exception ex){
			Utility.logErrorMsg("handleKPIntegration:"+ex.getMessage(), TAG);
		}
		
	}
	protected static boolean enforceChildLock(Activity activity){
		boolean value = false;
		try{
		if(KPUtility.isKidsPlaceRunning(activity) && getChildLockSetting(activity)){
			//child lock setting is enabled and Kids place is running
			value = true;
		}
		}
		catch (Exception ex){
			Utility.logErrorMsg("enforceChildLock:"+ex.getMessage(), TAG);
			
		}
		return value;
	}
	protected static boolean isRunningStandAlone() {
		// TODO Auto-generated method stub
		return runningStandAlone;
	}

	protected static void setRunningStandAlone(boolean value) {
		 runningStandAlone = value;
	}	
}
