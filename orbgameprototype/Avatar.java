package orbgameprototype;

import processing.core.PApplet;

public class Avatar implements Entity 
{
	// Identifier for this class.
	static final String entityType = "Avatar";
	public float radius; 

	private float growFactor;
	private float initRadius;
	
	public Avatar(float radius)
	{
		this.radius = radius;	
		initRadius  = radius;
		growFactor  = 1;
	}
	
	
	// Drawing routine for the avatar.
	public void draw(PApplet parent) 
	{
		
		if (radius < initRadius*growFactor) {
			radius+=1;
		}
		
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
		growFactor *= 1.5;
	}

	public float getRadius()
	{
		return radius;
	}

}
