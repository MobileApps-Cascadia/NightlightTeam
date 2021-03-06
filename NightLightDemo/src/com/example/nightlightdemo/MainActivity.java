package com.example.nightlightdemo;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initNightlightButton(); //init nightlight button
        initPlayButton();  //init play button
        initSettingButton(); //init setting button
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * sets onclick and intent listeners for nightlight button
     */
    public void initNightlightButton(){
    	Button nightlightButton = (Button) findViewById(R.id.buttonNightlight);
    	
    	nightlightButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent (MainActivity.this, NightlightActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
    }
    
    /**
     * sets onclick and intent listeners for settings button
     */
    public void initSettingButton(){
    	Button settingsButton = (Button) findViewById(R.id.buttonSettings);
    	
    	settingsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent (MainActivity.this, SettingsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
    }
    
    /**
     * sets onclick and intent listeners for play button
     */
    public void initPlayButton (){
    	Button playButton = (Button) findViewById(R.id.buttonPlay);
    	
    	playButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent (MainActivity.this, PlayActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
    }

}
