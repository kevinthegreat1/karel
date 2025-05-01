package acm.graphics;

import java.awt.*;

class SetColorElement extends PathElement {
   private Color color;

   public SetColorElement(Color var1) {
      this.color = var1;
   }

   public void paint(Graphics2D var1, PathState var2) {
      var1.setColor(this.color);
   }
}
