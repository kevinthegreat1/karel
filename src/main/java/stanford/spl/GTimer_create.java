package stanford.spl;

import acm.util.TokenScanner;

class GTimer_create extends JBECommand {
   GTimer_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      double var4 = this.nextDouble(var1);
      var1.verifyToken(")");
      var2.createTimer(var3, var4);
   }
}
