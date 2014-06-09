package com.example.nightlightdemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class GameSounds {

	private SoundPool sounds;
	private int num_of_sounds = 6;
	private int sFairy;
	AudioManager audioManager;
	
	GameSounds(Context context){
		
		sounds = new SoundPool(num_of_sounds, AudioManager.STREAM_MUSIC, 0);
		sFairy = sounds.load(context, R.raw.fairy, 1);
	}
	
	public void fairyWav(){
		
		sounds.play(sFairy, 1, 1, 1, 0, 1f);
		
	}
	
}
