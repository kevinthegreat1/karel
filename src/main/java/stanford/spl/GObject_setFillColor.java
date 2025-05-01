package stanford.spl;

import acm.graphics.GFillable;
import acm.graphics.GObject;
import acm.util.JTFTools;
import acm.util.TokenScanner;
import java.awt.Color;

class GObject_setFillColor extends JBECommand {
   GObject_setFillColor() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var5 = var2.getGObject(var3);
      if (var5 != null) {
         Color var6 = var4.equals("") ? null : JTFTools.decodeColor(var4);
         ((GFillable)var5).setFillColor(var6);
      }

   }
}
