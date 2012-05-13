package com.kiddoware.kidsplace_sdk.samples.advanced;

import java.util.Timer;
import java.util.TimerTask;

import com.kiddoware.kidsplace.sdk.KPUtility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Kidsplace_sdk_advancedActivity extends Activity {
	private TextView mTextView;
	private boolean promptForKPInconsistency;
	private AlertDialog startKPdlg;
	private AlertDialog passwordDialog;
	private Timer t;
	private static final int RESULT_EXIT_APP = 999;
	private static final int REQUEST_EXIT_APP = 998;
	private static final String TAG = "Kidsplace_sdk_advancedActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mTextView = (TextView) findViewById(R.id.textView1);
	}

	@Override
	public void onResume() {
		super.onResume();

		mTextView.setText("Kids Place not running");
		if (KPUtility.isKidsPlaceRunning(this)) {
			mTextView.setText("Kids Place is running");
		} // Check for KP Service running and child lock consistency

		promptForKPInconsistency = true;

		// This logic ensures that if KP is shutdown after user elected to start
		// KP from app, next time when app is started user will be prompted to
		// to start KP. This is important as app may still be running in
		// background
		// when KP is exited.
		if (!KPUtility.isKidsPlaceRunning(this)
				&& Utility.getChildLockSetting(getApplicationContext())
				&& promptForKPInconsistency) {
			promptForKPInconsistency = false;// set flag to false as we are
												// prompting now
			showStartKPMessage();
			return;// this make sure subsequent checks will not happen
		}

		// check if KP integration is in valid state
		// check to make sure KP is running if isRunningStandAlone is set to
		// false
		// and child lock is enabled
		if (!Utility.isRunningStandAlone()
				&& Utility.getChildLockSetting(getApplicationContext())
				&& !KPUtility.isKidsPlaceRunning(this)) {
			// handle KP Integration as system is not in a valid state
			Utility.handleKPIntegration((Activity) this);

		}

		// set global is running stand-alone flag based on status of Kids Place
		// running or not
		// isRunningStandAlone = !KPUtility.isKidsPlaceRunning(this);
		Utility.setRunningStandAlone(!KPUtility
				.isKidsPlaceRunning(this));
		if (!Utility.isRunningStandAlone()
				&& Utility.getChildLockSetting(getApplicationContext())) {
			// KP is running when child lock is set so set
			// promptForKPInconsistency to true
			// so that we can notify user if child lock gets in inconsistent
			// state. This can
			// happen when user exit out of KP
			promptForKPInconsistency = true;
		}

	}
	@Override
	public void onPause() {
		super.onPause();
			if (t != null) {
				t.cancel();
				t = null;
			}


	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Utility.logMsg("onActivityResult", TAG);
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_EXIT_APP:
			if (resultCode == RESULT_EXIT_APP) {
				finish();
			}
		}
	}
	

	public void btnClickHandler(View v) {
		Button myBtn = (Button) v;
		if (myBtn != null) {
			if (myBtn.getId() == R.id.btnSettings) {

				if(!Utility.enforceChildLock(this)){
					startSettingsActivity();
				}
				else {
					showPasswordFields();
				}						
			}

		}
	}

	private void startSettingsActivity() {
		// Show Settings activity
		Intent intent = new Intent(getApplicationContext(), PrefsActivity.class);
		startActivityForResult(intent, REQUEST_EXIT_APP);
	}

	private void showStartKPMessage() {

		if (startKPdlg != null && startKPdlg.isShowing()) {
			startKPdlg.dismiss();
			startKPdlg = null;
		}
		startKPdlg = new AlertDialog.Builder(this)
				.setTitle(R.string.startKPTitle)
				.setMessage(R.string.startKPMsg)
				.setPositiveButton(R.string.yesBtn,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {
								try {
									dialog.dismiss();
									Utility.handleKPIntegration(getActivity());

								} catch (Exception ex) {

								}
							}
						})
				.setNegativeButton(R.string.cancelBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
								try {
									dialog.dismiss();
									Utility.setRunningStandAlone(true);
								} catch (Exception ex) {

								}
							}
						}).show();
	}
	private void showPasswordFields() {
		passwordDialog = null;
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		Context mContext = getApplicationContext();

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.enter_pin, null);
		alert.setTitle(R.string.pin_request);
		alert.setMessage(R.string.pin_message);
		// Set an EditText view to get user input
		final EditText input = (EditText) layout.findViewById(R.id.pin);
		input.setInputType(InputType.TYPE_CLASS_PHONE);
		input.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		alert.setView(layout);
		final TextView pinHintText = (TextView) layout
				.findViewById(R.id.pin_hintTextView);
		pinHintText.setText(KPUtility.getPinHint(getApplicationContext()));
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				if (KPUtility.validatePin(getApplicationContext(), value)) {

					Toast.makeText(getApplicationContext(),
							R.string.loading_settings,
							Toast.LENGTH_SHORT).show();					
						startSettingsActivity();
						

				} else {
					// hide the password layout
					Toast.makeText(getApplicationContext(),
							R.string.incorrect_pin, Toast.LENGTH_SHORT).show();
				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		passwordDialog = alert.create();
		passwordDialog.getWindow().setGravity(Gravity.TOP);
		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if(passwordDialog != null){
					passwordDialog
							.getWindow()
							.setSoftInputMode(
									WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
					}
				}
			}
		});
		passwordDialog.show();
		// auto close password dialog after 20 seconds
		final Timer t = new Timer();

		t.schedule(new TimerTask() {

			public void run() {
				try {
					passwordDialog.dismiss();
				} catch (Exception ex) {

				}
				t.cancel(); // also just stop the timer thread, otherwise, you
							// may receive a crash report

			}

		}, 20000); //
	}	
	private Activity getActivity() {
		return this;
	}
}