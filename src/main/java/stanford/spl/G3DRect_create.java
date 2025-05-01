package stanford.spl;

import acm.graphics.G3DRect;
import acm.util.TokenScanner;

class G3DRect_create extends JBECommand {
   G3DRect_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      double var4 = this.nextDouble(var1);
      var1.verifyToken(",");
      double var6 = this.nextDouble(var1);
      var1.verifyToken(",");
      boolean var8 = var1.nextToken().equals("true");
      var1.verifyToken(")");
      G3DRect var9 = new G3DRect(0.0, 0.0, var4, var6, var8);
      var2.defineGObject(var3, var9);
   }
}
