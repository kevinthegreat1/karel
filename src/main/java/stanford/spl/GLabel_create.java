package stanford.spl;

import acm.util.TokenScanner;

class GLabel_create extends JBECommand {
   GLabel_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      JBELabel var5 = new JBELabel(var4);
      var2.defineGObject(var3, var5);
   }
}
