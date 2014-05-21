package com.example.nightlightdemo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.os.Build;

public class NightlightActivity extends Activity {

	ImageView[] stars = new ImageView [10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nightlight);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nightlight, menu);
		return true;
	}
	//never called
	/*
	public void initStars(){
		//RelativeLayout starLayout = (RelativeLayout) findViewById(R.id.widgetContainer);
	
		for (int i = 0; i < 10; i++){
			stars[i] = new ImageView(this);
			int x = (int)(Math.random() * starLayout.getWidth());
			int y = (int)(Math.random() * starLayout.getHeight());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(40, 40);
			params.topMargin = y;
			params.leftMargin = x;		
			
			this.addContentView(stars[i], params);
			
		}
		
	}*/

}
