package com.example.nightlightdemo;

/**
 * 
 * TODO: 
 * 	*Sound: music and oncreate 
 * 				-fairie sound added
 * 				-music added
 * 				-star sound added
 * 	*Add splash screen
 * 	*Settings
 * 				-settings 90% complete
 * 				-need to get and set speed/timer
 * 	*Nightlight stars background
 * 				-complete
 * 	*Set up fairies/no fairies
 * 	*
 * 
 */

import java.io.Console;
import java.util.ArrayList;
import java.util.Random;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class NightLightAnimation extends View {

	private final int FRAME_RATE = 30; //draw refresh framerate

	private Paint paint;//for bitmap editing

	//handlers for onDraw, fairy spawn, and star removal
	private Handler drawHandler, fairySpawnHandler,
	starHandler;	
	//pointer to this class. Instead of using this.activity
	private NightLightAnimation currentView = (NightLightAnimation) this;
	//resource pointer
	private Resources res = getResources();

	//holds fairy objects
	private ArrayList<Fairy> fairies = new ArrayList <Fairy>();
	//holds bitmaps of right facing fairies, held in index cooresponding to left
	private ArrayList<Bitmap> fairyBitmapsRight = new ArrayList<Bitmap>();
	//holds bitmaps of left facing fairy bitmaps
	private ArrayList<Bitmap> fairyBitmapsLeft = new ArrayList<Bitmap>();
	//holds star bitmaps
	private ArrayList<Star> stars = new ArrayList<Star>();

	private static final int MAX_FAIRES = 6;//max num of fairies allowed on screen
	private int fairy_timer = 5000; //fairy spawn rate (5 seconds)
	private int currentFairy = 0; //index of current fairy in the arraylist
	private int star_fade = 8000; //star removal rate (8 seconds)

	//random number generators for fairy spawn locations
	private Random randX;//random number object (for fairy spawn)
	private Random randY;//random number object (for fairy spawn)

	private SoundPool sounds; //holds sound effects
	private int num_of_sounds = 2; //number of sounds used
	private int sFairy; //index pointer for fairy sound
	private int sStar; //index pointer for star creation sound

	boolean spawnFairies = true; //if true, fairies will spawn. Must be set
	//in parent view constructor

	//star bitmap from res folder
	Bitmap star = BitmapFactory.decodeResource(res, R.drawable.widgetstar2);
	//moon bitmap from res folder
	Bitmap moon = BitmapFactory.decodeResource(res, R.drawable.moon);
	//runnable to invalidate the canvas for ondraw
	private Runnable drawRun = new Runnable(){
		@Override
		public void run() {
			invalidate();	
		}
	};
	//fairy spawn runnable
	private Runnable fairySpawn = new Runnable(){
		@Override
		public void run() {
			if (spawnFairies == true)
				pushFairy();
			fairySpawnHandler.postDelayed(fairySpawn, fairy_timer);
		}	
	};
	//star removal runnable
	private Runnable starRemove = new Runnable(){
		@Override
		public void run() {			
			if (stars.size() > 0) { stars.remove(0); }
			starHandler.postDelayed(starRemove, star_fade);
		}
	};
	/**
	 * Constructor for nightlightanimation view
	 * @param context holds parent view's context
	 * @param attrs holds attributes of current view created on
	 */
	public NightLightAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);//superconstructor 
		setBackgroundColor(Color.BLACK);//background color is black (night sky)

		randX = new Random();//init
		randY = new Random();//init
		//returns shared preferences for faeries
		String faeries = context.getSharedPreferences("MySettingsPreferences", 
				Context.MODE_PRIVATE).getString("faeriesSwitch", "true");
		setFairyPreference(faeries.equals("true"));

		initSounds(context);//init sounds
		initFairyBitmaps();//adds fairy bitmaps from res folder to arraylists
		initHandlers();//initializes the handlers for runnable methods
		setTouchListener();//initializes ontouch listeners 

		fairySpawnHandler.postDelayed(fairySpawn, fairy_timer);//stars fairy spawn
		starHandler.postDelayed(starRemove, star_fade); //stars star fade 
	}

	public void setFairyPreference(boolean spawn){
		spawnFairies = spawn;
	}

	/**
	 * initializes the sounds
	 * @param context the parent view context
	 */
	private void initSounds(Context context){
		sounds = new SoundPool(num_of_sounds, AudioManager.STREAM_MUSIC, 0);
		sFairy = sounds.load(context, R.raw.fairy, 1);
		sStar = sounds.load(context, R.raw.star, 1);
	}

	/**
	 * initializes the handlers for runnable methods
	 */
	private void initHandlers(){
		drawHandler = new Handler();
		fairySpawnHandler = new Handler();
		starHandler = new Handler();
	}

	/**
	 * draws to canvas on invalidate() call
	 */
	public void onDraw (Canvas c){
		drawStars(c); //stars on bottom
		drawMoon(c); //moon over stars
		moveFaries(c);	
		drawFaries(c); //fairies over all

		drawHandler.postDelayed(drawRun, 1000/FRAME_RATE);
	}

	/**
	 * initializes the on touch listener for the view
	 * 
	 * 	-Stars created on empty x/y locations on touch and sound effect played
	 * 	-Fairies play fairy sound effect on touch (no star creation)
	 */
	private void setTouchListener (){
		currentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int xTouch = (int)event.getX();//x location of touch
				int yTouch = (int)event.getY();//y location of touch
				//iterate through all fairies
				//if x/yTouch are inside a fairy bitmap; play sound effect
				//end method call on return, if true
				for (int i = 0, k = fairies.size(); i < k; i++){
					int xMin = fairies.get(i).getX();
					int xMax = xMin + fairies.get(i).getFairy().getWidth();
					int yMin = fairies.get(i).getY();
					int yMax = yMin + fairies.get(i).getFairy().getHeight();

					boolean inXBounds = ( xTouch >= xMin & xTouch <= xMax );
					boolean inYBounds = ( yTouch >= yMin & yTouch <= yMax );

					if (inXBounds & inYBounds) {
						sounds.play(sFairy, 1, 1, 1, 0, 1.0f);
						return false;
					}
				}
				//create new star bitmap on x/yTouch location
				stars.add(new Star(xTouch, yTouch, 0, star));
				sounds.play(sStar, 1, 1, 1, 0, 1.0f);

				return false;//end method call
			}
		});
	}
	/**
	 * draw star to canvas
	 * calls the 'twinkleStars()' method for all stars which adjusts the alpha level
	 * of the stars, making them transparent
	 * @param c: is the canvas on which the stars are drawn to
	 */
	private void drawStars(Canvas c){
		for (int i = 0; i < stars.size(); i++){
			c.drawBitmap(stars.get(i).getStar(), stars.get(i).getX(),
					stars.get(i).getY(), stars.get(i).getAlphaPaint());
			stars.get(i).twinkleStar(); //changes alpha level
		}
	}
	/**
	 * moves all fairies in the fairies arraylist on the canvas
	 * @param c: canvas object which fairies are drawn to
	 */
	private void moveFaries (Canvas c){
		for (int i = 0, k = fairies.size(); i < k; i++)
			fairies.get(i).bounce(c); //fairie object call to move += dX & dY
	}

	/**
	 * draws fairy bitmaps in the fairies arraylist to canvas
	 * @param c: canvas object which fairies are drawn to
	 */
	private void drawFaries(Canvas c){
		for (int i = 0, k = fairies.size(); i < k; i++)
			c.drawBitmap(fairies.get(i).getFairy(), fairies.get(i).getX(), 
					fairies.get(i).getY(), null); //no paint object passed
	}

	/**
	 * draws the moon to canvas
	 * @param c: canvas object which moon is drawn to
	 */
	private void drawMoon(Canvas c){
		c.drawBitmap(moon, -75, -75, null);
	}

	/**
	 * adds all fairy bitmaps for the res folder to the fairy arraylists
	 * 2 bitmaps per fairy: on lefy facing and one right facing
	 */
	private void initFairyBitmaps(){

		fairyBitmapsRight.add(BitmapFactory.decodeResource(res, R.drawable.fairy1r));
		fairyBitmapsRight.add(BitmapFactory.decodeResource(res, R.drawable.fairy2r));
		fairyBitmapsRight.add(BitmapFactory.decodeResource(res, R.drawable.fairy3r));
		fairyBitmapsRight.add(BitmapFactory.decodeResource(res, R.drawable.fairy4r));
		fairyBitmapsRight.add(BitmapFactory.decodeResource(res, R.drawable.fairy5r));
		fairyBitmapsRight.add(BitmapFactory.decodeResource(res, R.drawable.fairy6r));

		fairyBitmapsLeft.add(BitmapFactory.decodeResource(res, R.drawable.fairy1l));
		fairyBitmapsLeft.add(BitmapFactory.decodeResource(res, R.drawable.fairy2l));
		fairyBitmapsLeft.add(BitmapFactory.decodeResource(res, R.drawable.fairy3l));
		fairyBitmapsLeft.add(BitmapFactory.decodeResource(res, R.drawable.fairy4l));
		fairyBitmapsLeft.add(BitmapFactory.decodeResource(res, R.drawable.fairy5l));
		fairyBitmapsLeft.add(BitmapFactory.decodeResource(res, R.drawable.fairy6l));
	}
	/**
	 * adds a new fairy to the arraylist and sets its directional vector to a random
	 * direction
	 */
	public void pushFairy(){
		//resets the current fairy to the original bitmap, if the currentFairy 
		//is the last fairy in the arraylist
		if (currentFairy == fairyBitmapsRight.size()){ currentFairy = 0; } 
		//if there are 6 fairies on screen, remove one
		if (fairies.size() == MAX_FAIRES){ fairies.remove(0); }

		int x = randX.nextInt(currentView.getWidth() - 80);//get random x coord
		int y = randY.nextInt(currentView.getHeight() - 80);//get random y coord

		double xDir = (Math.random() * 2) - 1; //random x direction (left or right)
		double yDir = (Math.random() * 2) - 1; //random y direction (up or down)
		//add new fairy to fairies arraylist
		fairies.add(new Fairy(x, y, 10, fairyBitmapsRight.get(currentFairy),
				fairyBitmapsLeft.get(currentFairy)));
		currentFairy++;//use the next fairy bitmap on the next method call

		//x and y velocities
		int dX = 4;
		int dY = 2;

		if (xDir < 0){ dX *= -1; }
		if (yDir < 0){ dY *= -1; }
		//set x and y velocities
		fairies.get(fairies.size() - 1).setDX(dX);
		fairies.get(fairies.size() - 1).setDY(dY);
	}

	/**
	 * method pauses all runnables
	 */
	public void pause(){
		drawHandler.removeCallbacks(drawRun);
		fairySpawnHandler.removeCallbacks(fairySpawn);
		starHandler.removeCallbacks(starRemove);
	}
}
