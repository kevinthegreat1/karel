package stanford.spl;

import acm.graphics.GOval;
import acm.util.TokenScanner;

class GOval_create extends JBECommand {
   GOval_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      double var4 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var6 = this.nextDouble(var1);
      var1.verifyToken(")");
      GOval var8 = new GOval(var4, var6);
      var2.defineGObject(var3, var8);
   }
}
