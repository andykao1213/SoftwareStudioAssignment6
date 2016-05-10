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
	public int index = 0;
	public float x , y;
	public float originX , originY;
	public boolean reseting = false;
	public boolean inCircle = false;
	private String clr;
	private int color;
	private String name;
	private ArrayList<Character> targets;
	private ArrayList<Integer> values;
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
		this.values =  new ArrayList<Integer>();
	}

	public void display(){
		
		
		if(this.inCircle){
			for(Character c : targets){
				parent.noFill();
				parent.stroke(200);
				if(values.get(targets.indexOf(c)) > 10)parent.strokeWeight((float)values.get(targets.indexOf(c)) / 6);
				else parent.strokeWeight((float)values.get(targets.indexOf(c)) / 2);
				float a1 = (x + 600) / 2;
				float b1 = (y + 360) / 2;
				float a2 = (c.x + 600) / 2;
				float b2 = (c.y + 360)  / 2;
				if (c.inCircle == true) parent.bezier(x, y, a1, b1, a2, b2, c.x, c.y); // 550 340
			}
		}
		this.parent.noStroke();
		parent.fill(color, 200);
		parent.ellipse(x, y, rad, rad);
	}
	
	
	public void addTarget(Character target){ this.targets.add(target); }
	public void addValue(Integer value){ this.values.add(value); }
	public ArrayList<Character> getTargets(){ return this.targets; }
	public ArrayList<Integer> getValues(){ return this.values; }
}
