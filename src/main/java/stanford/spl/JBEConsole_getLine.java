package stanford.spl;

import acm.util.TokenScanner;

class JBEConsole_getLine extends JBECommand {
   JBEConsole_getLine() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      var1.verifyToken(")");
      var2.println("result:" + var2.getConsole());
   }
}
