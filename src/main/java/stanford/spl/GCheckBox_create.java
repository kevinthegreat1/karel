package stanford.spl;

import acm.util.TokenScanner;

class GCheckBox_create extends JBECommand {
   GCheckBox_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      GCheckBox var5 = new GCheckBox(var4, var2);
      var2.defineGObject(var3, var5);
      var2.defineSource(var5.getInteractor(), var3);
   }
}
