package orbgameprototype;

import java.util.ArrayList;

import ddf.minim.AudioSample;
import ddf.minim.Minim;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.xml.XMLElement;


public class OrbGamePrototype extends PApplet 
{
	private static final long serialVersionUID = 1L;

	Minim minim;
	AudioSample hit;
	AudioSample loseorb;


	ArrayList<PVector> points;
	ArrayList<Float> times;

	double LENGTH_TOLER = 2;
	double TIME_TOLER   = 1500;
	
	double ADD_TIME_TOLER = 100;
	

	PImage img;
	PImage img2;
	float counter;
	float counter2;
	float newX;
	float newY;

	boolean gameStart = false;
	
	boolean resetGame = false;
	
	double lastAddTime = 0;
	
	// List of all game objects.
	ArrayList<GameEntity> gameEntities;
	
	GameEntity heroGameEntity = null;
	Entity     heroEntity = null;
	Timer      timer = null;
	
	static boolean levelStart;
	int level = 1;

	int orbCount = 0;
	int orbCollectedCount = 0;

	int backgroundTimer = 0;
	
	Thing t;
	Attractor a;
	boolean showVectors = true;

	
	public static void setLevelStart(boolean levelStart) {
		OrbGamePrototype.levelStart = levelStart;
	}


	public void setup() 
	{
		size(1024, 768);
		img = loadImage("bg1.jpg");
		img2 = loadImage("bg3.jpg");
		imageMode(CENTER);

		//background(0);
		stroke(255);
		//smooth();
		strokeCap(ROUND);
		strokeWeight(4);
		noFill();

		// Create array to store all game elements.
		gameEntities = new ArrayList<GameEntity>();

		// Setup and create game elements.
		parseLevelXml(level);
		points = new ArrayList<PVector>();
		
		
		  PVector ac = new PVector((float)0.0,(float)0.0);
		  PVector ve = new PVector((float)0.0,(float)1.0);
		  PVector lo = new PVector(50,50);
		  // Create new thing with some initial settings
		  t = new Thing(this, ac,ve,lo,10);
		  // Create an attractive body
		  a = new Attractor(this, new PVector(50,50), 20, (float)5.0);
		
		  minim = new Minim(this);
		  hit = minim.loadSample("hit.wav");
		  loseorb = minim.loadSample("loseorb.wav");

	}


	public void stop()
	{
	  hit.close();
	  minim.stop(); 
	  super.stop();
	}
	
	public void draw() 
	{
			
		background(0);

	
		noSmooth();
		drawBackground();

		if (resetGame) {
			gameEntities = new ArrayList<GameEntity>();
			parseLevelXml(level);
			resetGame = false;
			gameStart = false;
		}
		
		smooth();

		/*
		if(!gameStart)
			return;
*/
		
		// Display all game objects.
		for (int ii = 0; ii < gameEntities.size(); ii++) {
			PVector mousePos = new PVector(mouseX, mouseY);
			if ((PVector.dist(gameEntities.get(ii).getPosition(), mousePos) < 20)           ||  
					gameEntities.get(ii).getX() < 0 || gameEntities.get(ii).getX() > width ||
					gameEntities.get(ii).getY() < 0 || gameEntities.get(ii).getY() > height
				) {
				
				loseorb.trigger();
				resetGame = true; 
				return;
			} 

			
			
			gameEntities.get(ii).display();
			
			for (int jj = 0; jj < points.size(); jj++) {
				if (PVector.dist(gameEntities.get(ii).getPosition(), points.get(jj)) < 20) {
					gameEntities.remove(ii);
					hit.trigger();
					break;
				}
			}
		}

		
		if (points == null || points.isEmpty())
			return;

		if (PVector.dist(points.get(points.size() - 1), new PVector(mouseX,
				mouseY)) > LENGTH_TOLER) {
			points.add(new PVector(mouseX, mouseY));
			times.add(new Float(millis()));
			// println("adding vertex" + points.size());
		}

		if (points.size() < 3)
			return;

		noFill();
		stroke(0, 255, 0);
		beginShape();
		for (int ii = 0; ii + 3 < points.size(); ii++) {
			curveVertex(points.get(ii).x, points.get(ii).y);
			curveVertex(points.get(ii + 1).x, points.get(ii + 1).y);
			curveVertex(points.get(ii + 2).x, points.get(ii + 2).y);
			//println("curr:" + millis());
			//println("time:" + times.get(ii));
			if ((millis() - times.get(ii)) > TIME_TOLER) {
				// println("removing points");
				points.remove(ii);
				times.remove(ii);
			}
		}
		endShape();

		
		
			
		  a.rollover(mouseX,mouseY);
		  a.go();

		  // Calculate a force exerted by "attractor" on "thing"
		  PVector f = a.calcGravForce(t);
		  // Apply that force to the thing
		  t.applyForce(f);
		  // Update and render the positions of both objects
		  t.go();
		

		
	}
	
	
	public void parseLevelXml(int level)
	{
		XMLElement xmlLevelData;
		
		if (level == 1) {
			xmlLevelData = new XMLElement(this, "level1.xml"); 	
		} else if (level == 2) {
			xmlLevelData = new XMLElement(this, "level2.xml"); 	
		} else if (level == 3) {
			xmlLevelData = new XMLElement(this, "level3.xml"); 	
		} else {
			return;
		}
		
		
		XMLElement entityCollection = xmlLevelData.getChild(0);

		for (int ii = 0; ii< entityCollection.getChildCount(); ii++) {
			Entity entity = null;
			GameEntity gameEntity;
			
			XMLElement entityData = entityCollection.getChild(ii);
			String type = entityData.getAttribute("type");
			String tmp;
			
			
			if (type.equals("Avatar")) {
				entity = new Avatar(20);
				heroEntity = entity;
			}
			else if (type.equals("Orb")) {
				entity = new Orb(30);
				//println("addibng orbs");
				orbCount++;
			} 
			else if (type.equals("Timer")){
				tmp = entityData.getChild("time").getContent(); 
				println("time: " + tmp);
				
				timer = new Timer(Float.valueOf(tmp));
				entity = timer;
			}
			
			String name = entityData.getAttribute("name");
			float x = Float.valueOf(entityData.getChild("position").getChild("x").getContent());
			float y = Float.valueOf(entityData.getChild("position").getChild("y").getContent());
					
			gameEntity = new GameEntity(this, entity, name, x, y);  
			
			if (type.equals("Avatar")) {
				heroGameEntity = gameEntity;
			} else if (type.equals("Orb")) {
				float xVelocity = Float.valueOf(entityData.getChild("velocity").getChild("x").getContent());
				float yVelocity = Float.valueOf(entityData.getChild("velocity").getChild("y").getContent());
							
				gameEntity.setVelocity(xVelocity, yVelocity);
			}
			
			gameEntities.add(gameEntity);
		}
	
	}

	public void drawBackground() {
		counter  = (float) (counter + 0.009);
		counter2 = (float) (counter2 + 0.007);
		newX = sin(counter);
		newY = cos(counter2);
		newX = map(newX, -1, 1, width, width + 300);
		newY = map(newY, -1, 1, height, height + 300);

		tint(255, 255);
		image(img, width / 2, height / 2, newX, newY + 50);

		tint(255, 100);
		image(img2, width / 2, height / 2, newX + 50, newY);
	}

	
	public void mousePressed() {
		// curveTightness((float)5);
		// beginShape();

		// Initialize points array list.
		points = new ArrayList<PVector>();
		times = new ArrayList<Float>();

		points.add(new PVector(mouseX, mouseY));
		times.add(new Float(millis()));
		lastAddTime = millis();
	
		  a.clicked(mouseX,mouseY);
	}


	public void mouseReleased() {
		// points = null;
		// times = null;
		// endShape();
		  a.stopDragging();
	}

	public void keyPressed() {
		println(" >> key:"+ keyCode);
		
		if (keyCode == 32) {
			resetGame = true;
		}

		showVectors = !showVectors;

	}





	// Renders a vector object 'v' as an arrow and a location 'loc'
	void drawVector(PVector v, PVector loc, float scayl) {
	  if (v.mag() > 0.0) {
	    pushMatrix();
	    float arrowsize = 4;
	    // Translate to location to render vector
	    translate(loc.x,loc.y);
	    stroke(0);
	    // Call vector heading function to get direction (note that pointing up is a heading of 0) and rotate
	    rotate(v.heading2D());
	    // Calculate length of vector & scale it to be bigger or smaller if necessary
	    float len = v.mag()*scayl;
	    // Draw three lines to make an arrow (draw pointing up since we've rotate to the proper direction)
	    line(0,0,len,0);
	    line(len,0,len-arrowsize,+arrowsize/2);
	    line(len,0,len-arrowsize,-arrowsize/2);
	    popMatrix();
	  }
	}

	
	public void mouseClicked() {
		gameStart = true;
	}

	
	
}
