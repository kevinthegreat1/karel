package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;

class GInteractor_setActionCommand extends JBECommand {
   GInteractor_setActionCommand() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var5 = var2.getGObject(var3);
      if (var5 != null) {
         ((GInteractor)var5).setActionCommand(var4);
      }

   }
}
