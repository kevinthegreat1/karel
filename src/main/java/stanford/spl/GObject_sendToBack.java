package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;

class GObject_sendToBack extends JBECommand {
   GObject_sendToBack() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      GObject var4 = var2.getGObject(var3);
      var1.verifyToken(")");
      if (var4 != null) {
         var4.sendToBack();
      }

   }
}
