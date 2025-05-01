package stanford.spl;

import acm.graphics.GCompound;

public class TopCompound extends GCompound {
   private JBECanvas owner;

   public TopCompound() {
   }

   public void setCanvas(JBECanvas var1) {
      this.owner = var1;
   }

   public JBECanvas getCanvas() {
      return this.owner;
   }
}
