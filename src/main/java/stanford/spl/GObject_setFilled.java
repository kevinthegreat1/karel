package stanford.spl;

import acm.graphics.GFillable;
import acm.graphics.GObject;
import acm.util.TokenScanner;

class GObject_setFilled extends JBECommand {
   GObject_setFilled() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      boolean var4 = var1.nextToken().equals("true");
      var1.verifyToken(")");
      GObject var5 = var2.getGObject(var3);
      if (var5 != null) {
         ((GFillable)var5).setFilled(var4);
      }

   }
}
