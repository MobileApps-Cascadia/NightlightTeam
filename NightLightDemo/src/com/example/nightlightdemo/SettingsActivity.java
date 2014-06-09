package com.example.nightlightdemo;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Build;

public class SettingsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		initSettings();
		
		initTimerBar();
		initSpeedBar();
		initLullabySwitch();
		initFaeriesSwitch();
	
		initHomeButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	//for saving settings to Shared Preferences
	private void initSettings(){
		int timer= getSharedPreferences("MySettingsPreferences", Context.MODE_PRIVATE).getInt("timerBar", 90);
		int speed= getSharedPreferences("MySettingsPreferences", Context.MODE_PRIVATE).getInt("speedBar", 50);
		String lullaby= getSharedPreferences("MySettingsPreferences", Context.MODE_PRIVATE).getString("lulswitch", "true");
		String faeries= getSharedPreferences("MySettingsPreferences", Context.MODE_PRIVATE).getString("faeriesSwitch", "true");
		
		SeekBar timerBar = (SeekBar) findViewById(R.id.seekBarTimer);
		SeekBar speedBar = (SeekBar) findViewById(R.id.seekBarGameSpeed);
		Switch lulswitch = (Switch) findViewById(R.id.switchLullaby);
		Switch faeriesSwitch = (Switch) findViewById(R.id.switchfaeries);
		
		
		timerBar.setProgress(timer);
		
		speedBar.setProgress(speed);
		
		if (lullaby.equals("true")){
			lulswitch.setChecked(true);
		}
		else{
			lulswitch.setChecked(false);
		}
		
		if (faeries.equals("true")){
			faeriesSwitch.setChecked(true);
		}
		else{
			faeriesSwitch.setChecked(false);
		}
		
		
		
	}
	
	
	public void initTimerBar() {
		SeekBar timerBar = (SeekBar) findViewById(R.id.seekBarTimer);
		timerBar.setMax(100);
		//timerBar.incrementProgressBy(15);
		//timerBar.setProgress(90);
		
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
				
				getSharedPreferences("MySettingsPreferences", MODE_PRIVATE).edit().putInt("timerBar", progress).commit();
				
			}
		});
	}
	
	public void initSpeedBar (){
		
		SeekBar speedBar = (SeekBar) findViewById(R.id.seekBarGameSpeed);
		speedBar.setMax(100);
		//speedBar.incrementProgressBy(1);
		//speedBar.setProgress(50);
		
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
				
				getSharedPreferences("MySettingsPreferences", MODE_PRIVATE).edit().putInt("speedBar", progress).commit();
			}
		});	
	}
	
public void initLullabySwitch (){
		
		Switch lulswitch = (Switch) findViewById(R.id.switchLullaby);
		//lulswitch.setChecked(true);		
		lulswitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
			@Override
			   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			 
			    if(isChecked == true){
			     //use lullaby
			    	
			    	getSharedPreferences("MySettingsPreferences", MODE_PRIVATE).edit().putString("lulswitch", "true").commit();
			    }else{
			     //use regular bump sounds-twinkle,laugh,pop
			    	
			    	getSharedPreferences("MySettingsPreferences", MODE_PRIVATE).edit().putString("lulswitch", "false").commit();
			    }
			 
			}
		});
	}

	public void initFaeriesSwitch (){

		Switch faeriesSwitch = (Switch) findViewById(R.id.switchfaeries);
		//faeriesSwitch.setChecked(true);		
		faeriesSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
			@Override
			   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			 
			    if(isChecked == true){
			     //use faeries
			    	
			    	getSharedPreferences("MySettingsPreferences", MODE_PRIVATE).edit().putString("faeriesSwitch", "true").commit();	
			    }else{
			     //use stars only?
			    	
			    	getSharedPreferences("MySettingsPreferences", MODE_PRIVATE).edit().putString("faeriesSwitch", "false").commit();	
			    }
					
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
