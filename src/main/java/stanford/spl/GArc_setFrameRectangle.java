package stanford.spl;

import acm.graphics.GArc;
import acm.graphics.GObject;
import acm.util.TokenScanner;

class GArc_setFrameRectangle extends JBECommand {
   GArc_setFrameRectangle() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      double var4 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var6 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var8 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var10 = this.nextDouble(var1);
      var1.verifyToken(")");
      GObject var12 = var2.getGObject(var3);
      if (var12 != null) {
         ((GArc)var12).setFrameRectangle(var4, var6, var8, var10);
      }

   }
}
