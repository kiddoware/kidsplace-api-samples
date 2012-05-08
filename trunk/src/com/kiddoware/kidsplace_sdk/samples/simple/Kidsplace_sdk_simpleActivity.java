package com.kiddoware.kidsplace_sdk.samples.simple;

import com.kiddoware.kidsplace.sdk.KPUtility;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Kidsplace_sdk_simpleActivity extends Activity {
	private TextView mTextView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextView = (TextView)findViewById(R.id.textView1);
        //Call to Kids Place SDK to start Kids Place app or prompt to install when 
        //this activity is started
        KPUtility.handleKPIntegration(this, KPUtility.GOOGLE_MARKET);
    }
    public void onResume() {
        super.onResume();
        
        mTextView.setText("Kids Place not running");
        if(KPUtility.isKidsPlaceRunning(this)){
        	mTextView.setText("Kids Place is running");
        }
    }
}