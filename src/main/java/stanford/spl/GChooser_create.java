package stanford.spl;

import acm.util.TokenScanner;

class GChooser_create extends JBECommand {
   GChooser_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GChooser var4 = new GChooser(var2);
      var2.defineGObject(var3, var4);
      var2.defineSource(var4.getInteractor(), var3);
   }
}
