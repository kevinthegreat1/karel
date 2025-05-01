package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;

class GObject_setLocation extends JBECommand {
   GObject_setLocation() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      double var4 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var6 = this.nextDouble(var1);
      var1.verifyToken(")");
      GObject var8 = var2.getGObject(var3);
      if (var8 != null) {
         var8.setLocation(var4, var6);
      }

   }
}
