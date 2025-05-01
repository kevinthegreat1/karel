package stanford.spl;

import acm.util.TokenScanner;

class Sound_play extends JBECommand {
   Sound_play() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      var2.playSound(var3);
   }
}
