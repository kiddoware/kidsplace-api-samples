package com.kiddoware.kidsplace_sdk.samples.advanced;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import android.view.WindowManager;

public class PrefsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	public static final String KEY_MY_PREFERENCE = "my_preference";
	private static final CharSequence EXIT_APP = "exitApp";//key for Exit App pref setting
	private static final int RESULT_EXIT_APP = 999;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		addPreferencesFromResource(R.xml.preferences);				
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