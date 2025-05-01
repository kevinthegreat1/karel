package stanford.spl;

import java.awt.event.ActionListener;
import javax.swing.Timer;

class GTimer extends Timer {
   private String id;

   public GTimer(String var1, double var2) {
      super((int)Math.round(var2), (ActionListener)null);
      this.id = var1;
   }

   public String getId() {
      return this.id;
   }
}
