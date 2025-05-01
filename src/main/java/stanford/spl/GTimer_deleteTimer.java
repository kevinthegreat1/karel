package stanford.spl;

import acm.util.TokenScanner;

class GTimer_deleteTimer extends JBECommand {
   GTimer_deleteTimer() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      var2.deleteTimer(var3);
   }
}
