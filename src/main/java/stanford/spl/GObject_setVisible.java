package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;

class GObject_setVisible extends JBECommand {
   GObject_setVisible() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      GObject var4 = var2.getGObject(var3);
      var1.verifyToken(",");
      boolean var5 = this.nextBoolean(var1);
      var1.verifyToken(")");
      if (var4 != null) {
         var4.setVisible(var5);
      }

   }
}
