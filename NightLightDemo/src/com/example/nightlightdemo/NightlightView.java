package com.example.nightlightdemo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.view.View;

public class NightlightView extends View{

	private final int color1 = getResources().getColor(R.color.background_color_2);
	private final int color2 = getResources().getColor(R.color.background_color_3);
	private final int color3 = getResources().getColor(R.color.background_color_4);
	//private final int color4 = getResources().getColor(R.color.background_color_5);
	
	private boolean changeBackground = true;
	
	public NightlightView(Context context) {
		super(context);
		
		initBackground();
	}
	
	
	
	
	
	public void initBackground (){
		
		ValueAnimator colorAnimator = ObjectAnimator.ofInt(this, "backgroundColor", color1, color2);
		colorAnimator.setDuration(3000);
		colorAnimator.setEvaluator(new ArgbEvaluator());
		colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
		colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
		colorAnimator.start();	
	}
	
	
	
	/*
	@Override
	protected void onDraw (Canvas canvas){
		super.onDraw(canvas);
		
		int x = getWidth();
		
		
		
	}*/
	
	
}
