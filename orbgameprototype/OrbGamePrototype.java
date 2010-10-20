package orbgameprototype;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.xml.XMLElement;

public class OrbGamePrototype extends PApplet 
{
	private static final long serialVersionUID = 1L;

	// List of all game objects.
	ArrayList<GameEntity> gameEntities;
	
	
	public void setup() 
	{
		size(1024, 768);

		// Create array to store all game elements.
		gameEntities = new ArrayList<GameEntity>();

		// Setup and create game elements.
		parseLevelXml();
	}

	
	public void draw() 
	{
		background(0);
		smooth();
		
		// Display all game objects.
		for (GameEntity gameEntity : gameEntities) {
			gameEntity.display();
		}
	}
	
	
	public void parseLevelXml()
	{
		XMLElement xmlLevelData     = new XMLElement(this, "level1.xml"); 	
		XMLElement entityCollection = xmlLevelData.getChild(0);

		for (int ii = 0; ii< entityCollection.getChildCount(); ii++) {
			Entity entity = null;
			GameEntity gameEntity;
			
			XMLElement entityData = entityCollection.getChild(ii);
			String type = entityData.getAttribute("type");
			
			if (type.equals("Avatar")) {
				entity = new Avatar(20);
			}
			else if (type.equals("Orb")) {
				entity = new Orb(30);
			}
			
			String name = entityData.getAttribute("name");
			float x = Float.valueOf(entityData.getChild("position").getChild("x").getContent());
			float y = Float.valueOf(entityData.getChild("position").getChild("y").getContent());
					
			gameEntity = new GameEntity(this, entity, name, x, y);  
			gameEntities.add(gameEntity);
		}
	}
}
