package stanford.spl;

import acm.graphics.GObject;
import acm.util.JTFTools;
import acm.util.TokenScanner;

class GObject_setColor extends JBECommand {
   GObject_setColor() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      GObject var4 = var2.getGObject(var3);
      var1.verifyToken(",");
      String var5 = this.nextString(var1);
      var1.verifyToken(")");
      if (var4 != null) {
         var4.setColor(var5.equals("") ? null : JTFTools.decodeColor(var5));
      }

   }
}
