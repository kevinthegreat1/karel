package stanford.spl;

import acm.util.TokenScanner;

class JBEConsole_setFont extends JBECommand {
   JBEConsole_setFont() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      var2.setConsoleFont(var3);
   }
}
