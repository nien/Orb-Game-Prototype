package orbgameprototype;

import processing.core.PVector;
import processing.core.*;

//Attraction
//Daniel Shiffman <http://www.shiffman.net>

//A class for a draggable attractive body in our world

class Attractor {
float mass;    // Mass, tied to size
float G;       // Gravitational Constant
PVector loc;   // Location
boolean dragging = false; // Is the object being dragged?
boolean rollover = false; // Is the mouse over the ellipse?
PVector drag;  // holds the offset for when object is clicked on

PApplet parent;

Attractor(PApplet parent, PVector l_,float m_, float g_) {
 loc = l_.get();
 mass = m_;
 G = g_;
 drag = new PVector((float)0.0, (float)0.0);

 this.parent = parent;
}

void go() {
 render();
 drag();
}

PVector calcGravForce(Thing t) {
 PVector dir = PVector.sub(loc,t.getLoc());        // Calculate direction of force
 float d = dir.mag();                              // Distance between objects
 d = PApplet.constrain(d,(float)5.0,(float)25.0);                        // Limiting the distance to eliminate "extreme" results for very close or very far objects
 dir.normalize();                                  // Normalize vector (distance doesn't matter here, we just want this vector for direction)
 float force = (G * mass * t.getMass()) / (d * d); // Calculate gravitional force magnitude
 dir.mult(force);                                  // Get force vector --> magnitude * direction
 return dir;
}

// Method to display
void render() {
 parent.ellipseMode(PApplet.CENTER);
 parent.stroke(0);
 if (dragging) parent.fill (50);
 else if (rollover) parent.fill(100);
 else parent.fill(175,200);
 parent.ellipse(loc.x,loc.y,mass*2,mass*2);
}

// The methods below are for mouse interaction
void clicked(int mx, int my) {
 float d = PApplet.dist(mx,my,loc.x,loc.y);
 if (d < mass) {
   dragging = true;
   drag.x = loc.x-mx;
   drag.y = loc.y-my;
 }
}

void rollover(int mx, int my) {
 float d = PApplet.dist(mx,my,loc.x,loc.y);
 if (d < mass) {
   rollover = true;
 } else {
   rollover = false;
 }
}

void stopDragging() {
 dragging = false;
}



void drag() {
 if (dragging) {
   loc.x = parent.mouseX + drag.x;
   loc.y = parent.mouseY + drag.y;
 }
}

}

