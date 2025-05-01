package stanford.spl;

import acm.util.TokenScanner;

class JBEConsole_println extends JBECommand {
   JBEConsole_println() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      var1.verifyToken(")");
      var2.endLineConsole();
   }
}
