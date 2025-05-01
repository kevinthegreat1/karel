package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;

class GWindow_addToRegion extends JBECommand {
   GWindow_addToRegion() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(",");
      String var5 = this.nextString(var1);
      var1.verifyToken(")");
      JBEWindow var6 = var2.getWindow(var3);
      GObject var7 = var2.getGObject(var4);
      if (var6 != null && var7 != null) {
         var6.addToRegion(var2.getInteractor(var7), var5);
      }

   }
}
