package stanford.spl;

import acm.util.TokenScanner;

class TopCompound_create extends JBECommand {
   TopCompound_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      TopCompound var4 = new TopCompound();
      var2.defineGObject(var3, var4);
   }
}
