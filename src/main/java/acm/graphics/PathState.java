package acm.graphics;

import java.awt.*;
import java.io.Serializable;

class PathState implements Serializable {
   double cx;
   double cy;
   double sx;
   double sy;
   Polygon region;
   Color fillColor;

   PathState() {
   }
}
