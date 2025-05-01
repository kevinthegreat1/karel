package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import java.awt.Graphics2D;

class GWindow_draw extends JBECommand {
   GWindow_draw() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      JBEWindow var4 = var2.getWindow(var3);
      var1.verifyToken(",");
      var3 = this.nextString(var1);
      GObject var5 = var2.getGObject(var3);
      var1.verifyToken(")");
      if (var4 != null && var5 != null && var5.isVisible()) {
         JBECanvas var6 = var4.getCanvas();
         Graphics2D var7 = var6.getOSG();
         var7.setColor(var5.getColor());
         var5.paint(var7);
         var6.repaint();
      }

   }
}
