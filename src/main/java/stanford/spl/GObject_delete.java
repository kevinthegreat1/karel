package stanford.spl;

import acm.util.TokenScanner;

class GObject_delete extends JBECommand {
   GObject_delete() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      var2.deleteGObject(var3);
   }
}
