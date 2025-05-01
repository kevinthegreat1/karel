package stanford.spl;

import acm.util.TokenScanner;

class GWindow_delete extends JBECommand {
   GWindow_delete() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      var2.deleteWindow(var3);
   }
}
