package com.kiddoware.kidsplace_sdk.samples.advanced;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import android.view.WindowManager;
import android.widget.Toast;

public class PrefsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	public static final String KEY_MY_PREFERENCE = "my_preference";
	private static final String TAG = "KidsPlacePrefsActivity";
	private static final CharSequence EXIT_APP = "exitApp";//key for Exit App pref setting
	private static final int RESULT_EXIT_APP = 999;

	// TODO: if we don't know a device's system settings activity grey out the setting or remove
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		addPreferencesFromResource(R.xml.preferences);				
		Preference exitApp = (Preference) findPreference(EXIT_APP);
		if(exitApp != null){
		
			exitApp.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				public boolean onPreferenceClick(Preference preference) {
					setResult(RESULT_EXIT_APP);					
					finish();			
					return true;
				}
			});

		}	
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(Utility.KEY_CHILD_LOCK_SETTING)) {
			handleChildLockSettingChange();
		}	
	}
	private void handleChildLockSettingChange(){
		CheckBoxPreference mCheckBoxPref = (CheckBoxPreference) findPreference(Utility.KEY_CHILD_LOCK_SETTING);
		if(mCheckBoxPref != null && mCheckBoxPref.isChecked()){
			//reset running standalone flag
			Utility.setRunningStandAlone(false);
			Utility.handleKPIntegration(this);
		}
	}
}