package stanford.spl;

import acm.util.TokenScanner;

class GButton_create extends JBECommand {
   GButton_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      GButton var5 = new GButton(var4, var2);
      var5.setActionCommand(var4);
      var2.defineGObject(var3, var5);
      var2.defineSource(var5.getInteractor(), var3);
   }
}
