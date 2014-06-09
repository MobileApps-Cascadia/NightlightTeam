package com.example.nightlightdemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball {

	private Point p; //point of ball
	private int c, //int of color
				r, //radius of ball
				dx, //velocity in x direction
				dy; //velocity in y direction
	private Paint paint; //holds color for drawing on canvas
	
	public Ball (int x, int y, int col, int radius){
		p = new Point (x, y);
		setColor(col);
		r = radius;
		paint = new Paint();
		paint.setColor(col);
		
		dy = 0;
		dy = 0;
		
	}
	
	public int getX(){
		return p.x;
	}
	
	public int getY(){
		return p.y;
	}
	
	public int getRadius(){
		return r;
	}
	
	public Paint getPaint(){
		return paint;
	}
	
	public int getDY(){
		return this.dy;
	}
	
	public int getDX(){
		return this.dx;
	}
	
	public void setColor (int col){
		this.c = col;
	}
	
	public void goTo (int x, int y){
		p.x = x;
		p.y = y;
	}
	
	public void setDX (int velocity){
		dx = velocity;
	}
	
	public void setDY (int velocity){
		dy = velocity;
	}
	
	public void move(){
		p.x += dx;
		p.y += dy;
	}
	
	public void bounce (Canvas canvas){
		//move();
		if (p.x >= canvas.getWidth() || p.x <= 0){
			dx *= -1;
		}
		if (p.y >= canvas.getHeight() || p.y <= 0){
			dy *= -1;
		}
		move();
	}
	
	public void bounceOff (Ball b){
		if ( (Math.abs(b.getX() - p.x) < b.getRadius() + r) && 
				(Math.abs(b.getY() - p.y) < b.getRadius() + r) ){
			dx *= -1;
			dy *= -1;	
			move();
		}
	}
}
