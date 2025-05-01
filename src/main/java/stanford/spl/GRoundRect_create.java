package stanford.spl;

import acm.graphics.GRoundRect;
import acm.util.TokenScanner;

class GRoundRect_create extends JBECommand {
   GRoundRect_create() {
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
      var1.verifyToken(")");
      GRoundRect var10 = new GRoundRect(0.0, 0.0, var4, var6, var8);
      var2.defineGObject(var3, var10);
   }
}
