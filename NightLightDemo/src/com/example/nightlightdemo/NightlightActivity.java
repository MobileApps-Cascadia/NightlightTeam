package com.example.nightlightdemo;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;


public class NightlightActivity extends Activity {

	NightLightAnimation nightlightView; //holds the view

	private MediaPlayer player; //holds background music

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//sets contect view to this activity, which holds an instance of
		//nightlightview
		setContentView(R.layout.activity_nightlight);
		//this will be how settings will be accessed/passed
		nightlightView = (NightLightAnimation)
				findViewById(R.id.nightlightAnimationView);	

		//returns shared preferences for lullaby
		String lullaby= getSharedPreferences("MySettingsPreferences", 
				Context.MODE_PRIVATE).getString("lulswitch", "true");
		
		player = new MediaPlayer();//creates media player
		player = MediaPlayer.create(this, R.raw.musicbox); //adds background lullaby
		player.setVolume(0.8f, 0.8f); //sets volume for background music
		player.setLooping(true); //sets lullaby to loop
		//if lullaby is switched to 'true', music will play
		if (lullaby.equals("true")){		
			player.start(); //starts player
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nightlight, menu);
		return true;
	}
	@Override
	/**
	 * Restarts the activity and stops music, on activity pause
	 */
	public void onPause(){
		super.onPause();
		player.stop(); //stops player
		nightlightView.pause(); //stops all handlers
	}

}
