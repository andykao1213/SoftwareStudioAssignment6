package main.java;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	public int row , col;
	public int rad = 45;
	public float x , y;
	public float originX , originY;
	public boolean reseting = false;
	public boolean inCircle = false;
	private String clr;
	private int color;
	private String name;
	private ArrayList<Character> targets;
	private ArrayList<Character> values;
	public Character(MainApplet parent, String name, String clr ,int row , int col  ){

		this.parent = parent;
		this.row = row;
		this.col = col;
		this.clr = clr;
		this.originX = col*60 + 40;
		this.originY = row*60 + 60;
		this.x = originX;
		this.y = originY;
		this.color = (int) Long.parseLong(clr.replace("#", ""), 16);
		this.inCircle = false;
		this.targets = new ArrayList<Character>();
		
	}

	public void display(){
		
		this.parent.noStroke();
		parent.fill(color, 200);
		parent.ellipse(x, y, rad, rad);
	}
	
	
	public void addTarget(Character target){ this.targets.add(target); }
	public void addValue(Character value){ this.targets.add(value); }
	public ArrayList<Character> getTargets(){ return this.targets; }
	public ArrayList<Character> getValues(){ return this.values; }
}
