package stanford.spl;

import acm.graphics.GArc;
import acm.graphics.GObject;
import acm.util.TokenScanner;

class GArc_setSweepAngle extends JBECommand {
   GArc_setSweepAngle() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      double var4 = this.nextDouble(var1);
      var1.verifyToken(")");
      GObject var6 = var2.getGObject(var3);
      if (var6 != null) {
         ((GArc)var6).setSweepAngle(var4);
      }

   }
}
