package main.java;

import java.util.ArrayList;

import processing.core.PApplet;
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
	private Character dragNode;
	JSONObject data;
	JSONArray nodes, links;
	private ArrayList<Character> characters = new ArrayList<Character>();
	
	private final static int width = 1200, height = 650;
	
	public void setup() {

		size(width, height);
		smooth();
		loadData();
		
	}

	public void draw() {
		background(255);
		fill(255);
		stroke(203, 218, 161);
		if (dragNode != null && dist(dragNode.x, dragNode.y, 600, 390) < 470 / 2) {
			strokeWeight(7);
		} else strokeWeight(5);
		ellipse(600, 390, 470, 470);
		for(Character character: this.characters){
			if(character.reseting == true) this.nodeReset(character);
			character.display();
		}
	}
	
	public void mousePressed() {
		
		for(Character character: this.characters){
			if( dist(character.x , character.y , mouseX , mouseY) < character.rad / 2){
				character.rad = 40;
				dragNode = character;
			}
			else character.rad = 45;	
			
		}
			
	}
	public void mouseReleased() {
		for(Character character: this.characters){
			 character.rad = 45;
			 if(character.inCircle == false){
					 character.reseting = true;
			 }
		}
		dragNode = null;
	}
	public void mouseDragged() {
		if (dragNode != null) {
			dragNode.x = mouseX;
			dragNode.y = mouseY;
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
			characters.get(link.getInt("source")).addTarget(characters.get(link.getInt("value")));
		}
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
}
