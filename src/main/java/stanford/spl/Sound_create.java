package stanford.spl;

import acm.util.TokenScanner;

class Sound_create extends JBECommand {
   Sound_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      String var5 = "ok";

      try {
         var2.createSound(var3, var4);
      } catch (Exception var7) {
         var5 = var7.getMessage();
      }

      var2.println("result:" + var5);
   }
}
