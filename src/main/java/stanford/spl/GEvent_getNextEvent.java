package stanford.spl;

import acm.util.TokenScanner;

class GEvent_getNextEvent extends JBECommand {
   GEvent_getNextEvent() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      int var3 = this.nextInt(var1);
      var1.verifyToken(")");
      var2.getNextEvent(var3);
   }
}
