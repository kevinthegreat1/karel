package stanford.spl;

import acm.util.TokenScanner;

class GTimer_pause extends JBECommand {
   GTimer_pause() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      double var3 = this.nextDouble(var1);
      var1.verifyToken(")");

      try {
         Thread.sleep((long)((int)Math.round(var3)));
      } catch (Exception var6) {
      }

      var2.println("result:ok");
   }
}
