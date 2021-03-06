package orbgameprototype;

import processing.core.PApplet;
import processing.core.PVector;

//Attraction
//Daniel Shiffman <http://www.shiffman.net>

//A class to describe a thing in our world, has vectors for location, velocity, and acceleration
//Also includes scalar values for mass, maximum velocity, and elasticity

class Thing {
	 PVector loc;
	  PVector vel;
	  PVector acc;
	  float mass;
	  float max_vel;
	  float bounce = (float)1.0; // How "elastic" is the object

 
PApplet parent;

Thing(PApplet parent, PVector a, PVector v, PVector l, float m_) {
 acc = a.get();
 vel = v.get();
 loc = l.get();
 mass = m_;
 max_vel = (float)20.0;
 this.parent = parent;
}

PVector getLoc() {
 return loc;
}

PVector getVel() {
 return vel;
}
float getMass() {
 return mass;
}

void applyForce(PVector force) {
 force.div(mass);
 acc.add(force);
 /*
 if (showVectors) {
   drawVector(force,loc,1000);
 }    
*/
}

// Main method to operate object
void go() {
 update();
 render();
}

// Method to update location
void update() {
 vel.add(acc);
 vel.limit(max_vel);
 loc.add(vel);
 // Multiplying by 0 sets the all the components to 0
 acc.mult(0);
}

// Method to display
void render() {
 parent.ellipseMode(PApplet.CENTER);
 parent.stroke(0);
 parent.fill(175,100);
 parent.ellipse(loc.x,loc.y,mass*2,mass*2);
 /*
 if (showVectors) {
   drawVector(vel,loc,20);
 }
*/
}
}


