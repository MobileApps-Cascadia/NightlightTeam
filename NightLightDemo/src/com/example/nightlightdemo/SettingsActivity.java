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
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Build;

public class SettingsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		initTimerBar();
		initSpeedBar();
		initBrightnessBar();
		initHomeButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void initTimerBar() {
		SeekBar timerBar = (SeekBar) findViewById(R.id.seekBarTimer);
		timerBar.setMax(180);
		timerBar.incrementProgressBy(5);
		timerBar.setProgress(90);
		
		final TextView timerText = (TextView) findViewById(R.id.textTimer);
		timerText.setText("Time: " + timerBar.getProgress() + " minutes");
		
		timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				timerText.setText("Time: " + progress + " minutes");			
			}
		});
	}
	
	public void initSpeedBar (){
		
		SeekBar speedBar = (SeekBar) findViewById(R.id.seekBarGameSpeed);
		speedBar.setMax(100);
		speedBar.incrementProgressBy(1);
		speedBar.setProgress(50);
		
		final TextView speedText = (TextView) findViewById(R.id.textViewGameSpeed);
		speedText.setText("Speed: " + speedBar.getProgress() + "%");
		
		speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				speedText.setText("Speed: " + progress + "%");			
			}
		});	
	}
	
	public void initBrightnessBar (){
		
		SeekBar brightnessBar = (SeekBar) findViewById(R.id.seekBarBrightness);
		brightnessBar.setMax(100);
		brightnessBar.incrementProgressBy(1);
		brightnessBar.setProgress(50);
		
		final TextView brightnessText = (TextView) findViewById(R.id.textViewBrightness);
		brightnessText.setText("Speed: " + brightnessBar.getProgress() + "%");
		
		brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				brightnessText.setText("Speed: " + progress + "%");			
			}
		});	
	}
	
	public void initHomeButton(){
		
		Button homeButton = (Button) findViewById(R.id.buttonHome);
		
		homeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent (SettingsActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
	}
}
