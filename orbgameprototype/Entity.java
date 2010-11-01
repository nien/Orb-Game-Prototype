package orbgameprototype;

import processing.core.PApplet;

public interface Entity 
{
	void draw(PApplet parent);
	String getType();
	void growBigger();
	float getRadius();
}
