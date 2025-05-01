package stanford.spl;

import acm.graphics.G3DRect;
import acm.graphics.GObject;
import acm.util.TokenScanner;

class G3DRect_setRaised extends JBECommand {
   G3DRect_setRaised() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      boolean var4 = this.nextBoolean(var1);
      var1.verifyToken(")");
      GObject var5 = var2.getGObject(var3);
      if (var5 != null) {
         ((G3DRect)var5).setRaised(var4);
      }

   }
}
