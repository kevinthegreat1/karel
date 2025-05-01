package stanford.spl;

import acm.util.TokenScanner;

class GTextField_create extends JBECommand {
   GTextField_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      int var4 = this.nextInt(var1);
      var1.verifyToken(")");
      GTextField var5 = new GTextField(var4, var2);
      var2.defineGObject(var3, var5);
      var2.defineSource(var5.getInteractor(), var3);
   }
}
