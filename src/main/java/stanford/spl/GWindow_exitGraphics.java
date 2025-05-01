package stanford.spl;

import acm.util.TokenScanner;

class GWindow_exitGraphics extends JBECommand {
   GWindow_exitGraphics() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      var1.verifyToken(")");
      System.exit(0);
   }
}
