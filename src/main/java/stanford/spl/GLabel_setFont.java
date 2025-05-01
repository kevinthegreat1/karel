package stanford.spl;

import acm.graphics.GLabel;
import acm.util.TokenScanner;

class GLabel_setFont extends JBECommand {
   GLabel_setFont() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      GLabel var5 = (GLabel)var2.getGObject(var3);
      if (var5 != null) {
         var5.setFont(var4);
      }

   }
}
