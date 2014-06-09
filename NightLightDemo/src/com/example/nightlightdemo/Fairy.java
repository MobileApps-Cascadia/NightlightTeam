package com.example.nightlightdemo;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

public class Fairy {

	private Point p; //point of bitmap
	private int dx, //velocity in x direction
	dy, //velocity in y direction
	frequency;
	private Bitmap fairy;//holds current fairy to use
	private Bitmap fairyRight;//holds right facing bitmap
	private Bitmap fairyLeft;//holds left facing bitmap

	/**
	 * 
	 * @param x: x coordinate of onDraw
	 * @param y: y coordinate of onDraw
	 * @param m: (not used yet)
	 * @param fairyR: left facing fairy bitmap
	 * @param fairyL: right facing fairy bitmap
	 */
	public Fairy (int x, int y, int m, Bitmap fairyR, Bitmap fairyL){
		p = new Point(x, y);
		frequency = m;
		fairyRight = fairyR;
		fairyLeft = fairyL;
		fairy = fairyRight;
	}
	/**
	 * @return current fairy
	 */
	public Bitmap getFairy (){
		return fairy;
	}
	
	/**
	 * returns current x coordinate
	 */
	public int getX(){
		return p.x;
	}

	/**
	 * returns current y coordinate
	 */
	public int getY(){
		return p.y;
	}
	
	/**
	 * returns velocity in y direction
	 */
	public int getDY(){
		return this.dy;
	}
	/**
	 * @return velocity in x direction
	 */
	public int getDX(){
		return this.dx;
	}
	/**
	 * sets x/y coordinates
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void goTo (int x, int y){
		p.x = x;
		p.y = y;
	}
	/**
	 * @param velocity sets x velocity
	 */
	public void setDX (int velocity){
		dx = velocity;
	}
	/**
	 * @param velocity sets y velocity
	 */
	public void setDY (int velocity){
		dy = velocity;
	}
	/**
	 * moves bitmap's x/y coordinates
	 */
	public void move(){
		p.x += dx;
		p.y += dy;
	}
	/**
	 * if the fairy is moving in the positive x direction,
	 * use the right facing fairy. Otherwise use the left facing fairy
	 */
	public void switchFairy(){
		if (dx >= 0)
			fairy = fairyRight;
		else
			fairy = fairyLeft;
	}
	/**
	 * Method detects if the fairy object is at the end of the canvas.
	 * If it is touching the canvas wall, the x or y velocity is switched and
	 * the fairy moves in the opposite direction
	 * @param canvas: canvas object the fairy is drawn to
	 */
	public void bounce (Canvas canvas){
		//move();
		if (p.x + fairy.getWidth() >= canvas.getWidth() || p.x <= 0){
			dx *= -1;
			while (p.x + fairy.getWidth() > canvas.getWidth())
				p.x += dx;
		}
		if (p.y + fairy.getHeight() >= canvas.getHeight() || p.y <= 0){
			dy *= -1;
			while (p.y + fairy.getHeight() > canvas.getHeight())
				p.y += dy;
		}
		switchFairy();
		move();
	}
}
