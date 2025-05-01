package stanford.spl;

import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.util.TokenScanner;

class GLine_setStartPoint extends JBECommand {
   GLine_setStartPoint() {
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
         ((GLine)var8).setStartPoint(var4, var6);
      }

   }
}
