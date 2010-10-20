package orbgameprototype;

import processing.core.PApplet;

public class Orb implements Entity 
{
	// Identifier for this class.
	static final String entityType = "Orb";
	
	public float radius; 
	
	
	public Orb(float radius)
	{
		this.radius = radius;	
	}

	
	// Drawing routine for the avatar.
	public void draw(PApplet parent) 
	{
		parent.smooth();
		parent.fill(255, 255, 255);
		parent.ellipse(0, 0, radius, radius);
	}

	
	public String getType()
	{
		return entityType;
	}
}
