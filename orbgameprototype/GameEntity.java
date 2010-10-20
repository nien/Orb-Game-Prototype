package orbgameprototype;

import processing.core.PApplet;
import processing.core.PVector;


public class GameEntity 
{
	private PApplet parent;
	private PVector position;
	private PVector velocity; 
	private Entity  entity;
	private String  entityName;
	

	public GameEntity(PApplet parent, Entity entity, String entityName, float x, float y)
	{
		this.parent     = parent;
		this.entity     = entity; 
		this.entityName = new String(entityName);
		
		position = new PVector(x, y);
		velocity = new PVector();
	}

	
	public void setPosition(float x, float y) 
	{
		position.x = x;
		position.y = y;
	}

	
	public String getEntityName()
	{
		return entityName;
	}
	
	
	public void display() 
	{
		parent.pushMatrix();
		parent.translate(position.x, position.y);
		entity.draw(parent);
		parent.popMatrix();
	}
}
