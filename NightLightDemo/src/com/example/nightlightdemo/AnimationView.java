package com.example.nightlightdemo;

import java.util.ArrayList;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AnimationView extends View {

	private final int FRAME_RATE = 30; //do not change

	private Handler h, h2;//handlers for balls and ball removal

	private int tool = 0; //tool selector

	private int[][] splashVector = new int [2][8]; //this holds the directional vector

	ArrayList <Ball> balls = new ArrayList<Ball>(); //arraylist of ball objects
	
	private SoundPool sounds; //holds sound effects. Called on ball removal/create
	private int num_of_sounds = 6; //arbitrary number of sounds held
	private int sBall, sPop; //integer flags holding the ball create and destroy sounds

	//runnable for the draw function
	private Runnable r = new Runnable(){
		@Override
		public void run() {
			invalidate();
		}		
	}; //end runnable r

	//runnable for the remove ball function
	private Runnable r2 = new Runnable(){
		@Override
		public void run() {			
			removeBall();
			h2.postDelayed(this, 5000);
		}		
	};
	
	/**
	 * Construtor for play's AnimationView. 3 balls are automatically 
	 * created with the view
	 * @param context of parent activity
	 * @param attrs of parent activity's view
	 */
	public AnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);

		h = new Handler();
		h2 = new Handler();

		balls.add(new Ball (100, 100, Color.BLUE, 50));
		balls.add(new Ball (200, 200, Color.GREEN, 25));
		balls.add(new Ball(50, 400, Color.RED, 75));

		balls.get(0).setDX(10);
		balls.get(0).setDY(10);

		balls.get(1).setDX(-20);
		balls.get(1).setDY(-15);
		balls.get(2).setDX(5);
		balls.get(2).setDY(-5);

		initSounds(context);//init sounds
		initBackground();//starts background color change
		initAddBalls();//inits the ontouch methods to create/destroy balls
		initSplashVector();//used for a random test in 'exploding' balls

		startRemoveBalls();//stars the remove balls handler runnable
	}
	
	/**
	 * initializes sounds for balls
	 * @param context of activity
	 */
	private void initSounds(Context context){
		sounds = new SoundPool(num_of_sounds, AudioManager.STREAM_MUSIC, 0);
		sBall = sounds.load(context, R.raw.tap, 1);
		sPop = sounds.load(context, R.raw.pop, 1);
	}

	public void startRemoveBalls(){
		h2.postDelayed(r2, 10000);
	}

	/**
	 * starts the color animator which controls the background color
	 */
	public void initBackground (){
		int color1 = getResources().getColor(R.color.background_color_5);
		int color2 = getResources().getColor(R.color.background_color_4);
		
		ValueAnimator colorAnimator = ObjectAnimator.ofInt(this, "backgroundColor", color1, color2);
		colorAnimator.setDuration(3000);
		colorAnimator.setEvaluator(new ArgbEvaluator());
		colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
		colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
		colorAnimator.start();	
	}

	/**
	 * sets ontouch listeners for ball creation/removal
	 */
	public void initAddBalls(){
		AnimationView view = (AnimationView) this;
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int xTouch = (int)event.getX();
				int yTouch = (int)event.getY();
				if (tool == 0){ createBalls1(xTouch, yTouch); 	}
				if (tool == 1){ createBallSplash(xTouch, yTouch); }

				return false;
			}

			private void addNewBall (int xTouch, int yTouch){
				balls.add(new Ball (xTouch, yTouch, getRandomColor(), (int)(Math.random() * 80 + 20) ));
				int dX = (int)( Math.random() * 15 );
				int dY = (int)( Math.random() * 15 );

				double dirX = (Math.random() * 2) - 1;
				double dirY = (Math.random() * 2) - 1;

				if (dirX < 0){ dX *= -1; }
				if (dirY < 0){ dY *= -1; }

				balls.get(balls.size() - 1).setDX(dX);
				balls.get(balls.size() - 1).setDY(dY);
				sounds.play(sBall, 1, 1, 1, 0, 1.0f);
			}

			private void createBalls1 (int xTouch, int yTouch){
				for (int i = 0, k = balls.size(); i < k; i++){

					int distanceX = balls.get(i).getX() - xTouch;
					int distanceY = balls.get(i).getY() - yTouch;
					int distanceTouched = (int) Math.sqrt( Math.pow(distanceX, 2) + Math.pow(distanceY, 2) );
					if (distanceTouched <= balls.get(i).getRadius()){
						balls.remove(i);
						sounds.play(sPop, 1, 1, 1, 0, 1.0f);
						return;
					}				
				}

				addNewBall(xTouch, yTouch);
			}

			private void createBallSplash (int xTouch, int yTouch){
				for (int i = 0, k = balls.size(); i < k; i++){

					int distanceX = balls.get(i).getX() - xTouch;
					int distanceY = balls.get(i).getY() - yTouch;
					int distanceTouched = (int) Math.sqrt( Math.pow(distanceX, 2) + Math.pow(distanceY, 2) );
					if (distanceTouched <= balls.get(i).getRadius()){
						int parentVectorX = balls.get(i).getDX();	
						int parentVectorY = balls.get(i).getDY();
						int parentRadius = balls.get(i).getRadius();
						int parentColor = balls.get(i).getPaint().getColor();		
						for (int j = 0; j < 8; j++){
							balls.add(new Ball (xTouch, yTouch, parentColor, parentRadius / 5 ));
							balls.get(balls.size() - 1).setDX(parentVectorX * splashVector[0][j]);
							balls.get(balls.size() - 1).setDY(parentVectorY * splashVector[1][j]);							
						}
						balls.remove(i);
						return;
					}				
				}
				addNewBall(xTouch, yTouch);
			}
		});

	}

	/**
	 * gets a random color for the balls
	 * @return random RGB color
	 */
	public int getRandomColor (){
		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);

		return Color.rgb(red, green, blue);
	}

	/**
	 * ondraw method for view's canvas
	 */
	protected void onDraw (Canvas c){

		for (int i = 0, k = balls.size(); i < k; i++){
			balls.get(i).bounce(c);
		}

		for (int i = 0, k = balls.size(); i < k; i++)
			c.drawCircle(balls.get(i).getX(), balls.get(i).getY(), 
					balls.get(i).getRadius(), balls.get(i).getPaint());

		h.postDelayed(r, 1000/FRAME_RATE);

	}
	/**
	 * removes the first ball in the balls arraylist
	 */
	public void removeBall (){
		if (balls.size() > 0){
			balls.remove(0);
		}
	}

	/**
	 * used to create explosion vector
	 */
	public void initSplashVector(){
		//x and y
		splashVector[0][0] = 1;
		splashVector[1][0] = 1;

		splashVector[0][1] = 1;
		splashVector[1][1] = 0;

		splashVector[0][2] = 1;
		splashVector[1][2] = -1;

		splashVector[0][3] = 0;
		splashVector[1][3] = -1;

		splashVector[0][4] = -1;
		splashVector[1][4] = -1;

		splashVector[0][5] = -1;
		splashVector[1][5] = 0;

		splashVector[0][6] = -1;
		splashVector[1][6] = 1;

		splashVector[0][7] = 0;
		splashVector[1][7] = 1;

	}
}
