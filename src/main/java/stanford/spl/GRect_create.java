package stanford.spl;

import acm.graphics.GRect;
import acm.util.TokenScanner;

class GRect_create extends JBECommand {
   GRect_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      double var4 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var6 = this.nextDouble(var1);
      var1.verifyToken(")");
      GRect var8 = new GRect(var4, var6);
      var2.defineGObject(var3, var8);
   }
}
