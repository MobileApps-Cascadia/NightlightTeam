package com.example.nightlightdemo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.view.View;

public class NightlightView extends View{

	public NightlightView(Context context) {
		super(context);
		setBackgroundColor(getResources().getColor(R.color.menu_background));
		initBackground();
	}
	
	public void initBackground (){
		
		int colorFrom = getResources().getColor(R.color.menu_background);
		int colorTo = getResources().getColor(R.color.background_color_2);
		
		ValueAnimator bgChange = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
		bgChange.setDuration(3000);
		
		bgChange.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				setBackgroundColor((Integer)animation.getAnimatedValue());				
			}
		});
		
	}
	
	
	@Override
	protected void onDraw (Canvas canvas){
		super.onDraw(canvas);
		
		int x = getWidth();
		
		
		
	}
	
	
}
