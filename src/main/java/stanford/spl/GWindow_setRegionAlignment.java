package stanford.spl;

import acm.util.TokenScanner;

class GWindow_setRegionAlignment extends JBECommand {
   GWindow_setRegionAlignment() {
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
      if (var6 != null) {
         var6.setRegionAlignment(var4, var5);
      }

   }
}
