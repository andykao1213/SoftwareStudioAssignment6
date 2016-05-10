package main.java;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import controlP5.Button;
import controlP5.CColor;
import controlP5.ControlFont;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	
	private String path = "main/resources/";
	private String file = "starwars-episode-1-interactions.json";
	private int versionNum = 1;
	private Character dragNode;
	private int inCircle = 0;
	JSONObject data;
	JSONArray nodes, links;
	private ArrayList<Character> characters = new ArrayList<Character>();
	private ArrayList<Character> nodeIncircle = new ArrayList<Character>();
	private ControlP5 cp5;
	private final static int width = 1200, height = 650;
	
	public void setup() {

		size(width, height);
		smooth();
		loadData();
		cp5 = new ControlP5(this);
		// Create two buttons.
	    cp5.addButton("button1").setLabel("ADD ALL")
	    		                .setPosition(950, 50)
								.setSize(200, 50)
								.setColorBackground(0xFFA8C6AF)
								.setColorForeground(0xFFB9E4C7)
								.setColorActive(0xFF7ACB95);
								
	    cp5.addButton("button2").setLabel("CLEAR")
								.setPosition(950, 150)
								.setSize(200, 50)
								.setColorBackground(0xFFA8C6AF)
								.setColorForeground(0xFFB9E4C7)
								.setColorActive(0xFF7ACB95);
	}
	
	public void button1() {
		for (Character c : characters) {
			c.reseting = false;
			c.inCircle = true;
		}
	}
	
	public void button2() {
		// Remove all the characters out.
		for (Character c : characters) {
			c.inCircle = false;

		}
	}
	public void draw() {
		background(255);
		textSize(50);
		fill(0xFF5D3F32);
		text("Star War " + versionNum , 478 , 60);
		fill(255);
		stroke(203, 218, 161);
		inCircle = 0;
		if (dragNode != null && dist(dragNode.x, dragNode.y, 600, 360) < 530 / 2) {
			strokeWeight(7);
		} else strokeWeight(5);
		ellipse(600, 360, 530, 530);
		nodeInCircleSetting();
		for(Character character: this.characters){
			if( dist(character.x , character.y , mouseX , mouseY) < character.rad / 2){
				character.rad = 45;
			}
			else character.rad = 40;
		
			if(character.reseting == true) this.nodeReset(character);
			
			character.display();
		}
		
	}
	
	public void mousePressed() {
		
		for(Character character: this.characters){
			if( dist(character.x , character.y , mouseX , mouseY) < character.rad / 2){
				dragNode = character;
			}
		}
			
	}
	public void mouseReleased() {
		for(Character character: this.characters){
			 character.rad = 45;
			 
			 if(character.inCircle == false){
				character.reseting = true;
			 }else{
				 character.reseting = false;
			 }
			 
		}
		dragNode = null;
	}
	public void mouseDragged() {
		if (dragNode != null) {
			dragNode.x = mouseX;
			dragNode.y = mouseY;	
			if (dist(dragNode.x, dragNode.y, 600, 360) < 530 / 2) {
				dragNode.inCircle = true;
			} else dragNode.inCircle = false;
		}
		
	}
	private void loadData(){
		
		data = loadJSONObject(path + file);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");

		System.out.println("Number of nodes: " + nodes.size());
		System.out.println("Number of links: " + links.size());

		for(int i=0; i<nodes.size(); i++){
			JSONObject node = nodes.getJSONObject(i);
			characters.add(new Character(this , node.getString("name") , node.getString("colour") , i %10 , i /10));
		}

		for(int i=0; i<links.size(); i++){
			JSONObject link = links.getJSONObject(i);
			characters.get(link.getInt("source")).addTarget(characters.get(link.getInt("target")));
			characters.get(link.getInt("source")).addValue(link.getInt("value"));
		}
	}
	public void keyPressed() {
		// Change the versions.
		if (keyCode == KeyEvent.VK_1){
			file = "starwars-episode-1-interactions.json"; 	versionNum = 1;
		}
		else if (keyCode == KeyEvent.VK_2){
			file = "starwars-episode-2-interactions.json";	versionNum = 2;
		}
		else if (keyCode == KeyEvent.VK_3){
			file = "starwars-episode-3-interactions.json";	versionNum = 3;
		}
		else if (keyCode == KeyEvent.VK_4){
			file = "starwars-episode-4-interactions.json"; versionNum = 4;
		}
		else if (keyCode == KeyEvent.VK_5){
			file = "starwars-episode-5-interactions.json"; versionNum = 5;
		}
		else if (keyCode == KeyEvent.VK_6){
			file = "starwars-episode-6-interactions.json"; versionNum = 6;
		}
		else if (keyCode == KeyEvent.VK_7){
			file = "starwars-episode-7-interactions.json"; versionNum = 7;
		}
		characters.clear();
		loadData();
	}
	
	private void nodeReset(Character c){
		
			c.x = c.originX + (c.x - c.originX)* (float)0.9;
			c.y = c.originY + (c.y - c.originY)* (float)0.9;
			if(c.x - c.originX < 0.01 || c.y - c.originY < 0.01){
				c.x = c.originX;
				c.y = c.originY;
				c.reseting = false;
			}
	}
	private void nodeInCircleSetting(){
		
		int num = 0;
		for(Character cc: this.characters){
			if(cc.inCircle == true) num++;
		}
		int index = 0;
		for(Character cc: this.characters){
			if(cc.inCircle == true && cc != dragNode){
				cc.x = 600 + 530/2* (float)Math.cos(Math.PI*2/num*index);
				cc.y = 360 + 530/2* (float)Math.sin(Math.PI*2/num*index);
				index++;
			}
		}
	}
}
