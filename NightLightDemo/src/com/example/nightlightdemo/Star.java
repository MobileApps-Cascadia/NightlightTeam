package com.example.nightlightdemo;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Handler;

public class Star extends Fairy{

	private Bitmap star; //holds star bitmap
	
	private int alpha = 255; //holds alpha level of star [0-255]
	
	private Paint alphaPaint; //holds paint object for alpha levels
	private int alphaChange = 6;//rate of change of alpha levels
	private boolean fadeOut = true;//will only change alpha levels if true

	/**
	 * Same constructor as fairy, but initializes a paint object
	 * @param x coordinate
	 * @param y coordinate
	 * @param m not used
	 * @param s star bitmap
	 */
	public Star(int x, int y, int m, Bitmap s) {
		super(x, y, m, s, s);
		star = s;
		alphaPaint = new Paint();
		alphaPaint.setAlpha(alpha);
	}
	/**
	 * @return star bitmap
	 */
	public Bitmap getStar(){
		return star;
	}
	/**
	 * @return paint object holding the alpha level
	 */
	public Paint getAlphaPaint(){
		return new Paint(alphaPaint);
	}
	/**
	 * adjusts alpha levels on each call
	 */
	public void twinkleStar(){
		if (fadeOut == true)
			fadeStar();
		else
			brightenStar();
	}
	/**
	 * fades alpha levels
	 */
	private void fadeStar(){	
		if (alpha > 0){
			alpha -= alphaChange;
			if (alpha < 0){
				alpha = 0;	
				fadeOut = false;
			}
		}
		alphaPaint.setAlpha(alpha);
	}
	/**
	 * raises alpha levels
	 */
	private void brightenStar(){
		if (alpha < 255){
			alpha += alphaChange;
			if (alpha > 255){
				alpha = 255;
				fadeOut = true;
			}
		}
		alphaPaint.setAlpha(alpha);
	}
}
