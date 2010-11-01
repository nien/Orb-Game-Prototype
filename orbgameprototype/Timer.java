package orbgameprototype;

import processing.core.PApplet;

public class Timer implements Entity {

	static final String entityType = "Timer";
	
	float time;
	float counter = 0;
	float x;
	float y;
	float angle;
	float startPos;
	float radius;
	
	boolean timerStart = false;
	boolean timeOver   = false;
	
	
	public Timer(float time){
		this.time = time;
		radius = 80;
	}
	
	public void startTimer() 
	{
		timerStart = true;
		timeOver   = false;
	}

	public boolean isTimeOver() 
	{
		return timeOver;
	}

	
	
	public void draw(PApplet parent) 
	{
		parent.ellipse(0,0,radius,radius);
		
		parent.strokeWeight(3);
		parent.line(0, 0, 0, -radius/2);
		
		if (!timerStart)
			return;

		
		startPos = PApplet.TWO_PI - PApplet.PI / 2; 	
	
		angle = startPos + counter;
		
		counter += PApplet.TWO_PI/time;
		
		if (counter >= PApplet.TWO_PI){
			PApplet.println("GAME OVER");
			timeOver = true;
		}
		
		
		x = PApplet.cos(angle) * radius;
		y = PApplet.sin(angle) * radius;
		
		parent.line(0, 0, x, y);
		
	}

	public String getType() {
		return entityType;
	}
	
	public void growBigger()
	{
	//	radius += 30;
	}

	public float getRadius()
	{
		return 0;
	}
}
