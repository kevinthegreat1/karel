package stanford.spl;

import acm.graphics.GArc;
import acm.util.TokenScanner;

class GArc_create extends JBECommand {
   GArc_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      double var4 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var6 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var8 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var10 = this.nextDouble(var1);
      var1.verifyToken(")");
      GArc var12 = new GArc(var4, var6, var8, var10);
      var2.defineGObject(var3, var12);
   }
}
