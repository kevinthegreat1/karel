package stanford.spl;

import acm.util.TokenScanner;

class GWindow_requestFocus extends JBECommand {
   GWindow_requestFocus() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      JBEWindow var4 = var2.getWindow(var3);
      var1.verifyToken(")");
      if (var4 != null) {
         var4.toFront();
         var4.getCanvas().requestFocus();
      }

   }
}
