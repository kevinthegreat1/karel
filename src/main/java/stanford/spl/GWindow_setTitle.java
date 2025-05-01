package stanford.spl;

import acm.util.TokenScanner;

class GWindow_setTitle extends JBECommand {
   GWindow_setTitle() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      JBEWindow var4 = var2.getWindow(var3);
      var1.verifyToken(",");
      String var5 = this.nextString(var1);
      var1.verifyToken(")");
      if (var4 != null) {
         var4.setTitle(var5);
      }

   }
}
