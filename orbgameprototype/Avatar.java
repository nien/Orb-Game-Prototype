package orbgameprototype;

import processing.core.PApplet;

public class Avatar implements Entity 
{
	// Identifier for this class.
	static final String entityType = "Avatar";
	
	public float radius; 
	
	
	public Avatar(float radius)
	{
		this.radius = radius;	
	}
	
	
	// Drawing routine for the avatar.
	public void draw(PApplet parent) 
	{
		parent.smooth();
		parent.fill(255, 0, 0);
		parent.ellipse(0, 0, radius, radius);
	}

	
	public String getType()
	{
		return entityType;
	}
	
	
	public void growBigger()
	{
		radius += 30;
	}
}
