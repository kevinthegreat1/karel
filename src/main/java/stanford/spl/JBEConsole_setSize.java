package stanford.spl;

import acm.util.TokenScanner;

class JBEConsole_setSize extends JBECommand {
   JBEConsole_setSize() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      int var3 = this.nextInt(var1);
      var1.verifyToken(",");
      int var4 = this.nextInt(var1);
      var1.verifyToken(")");
      var2.setConsoleSize(var3, var4);
   }
}
