package stanford.spl;

import acm.graphics.GLabel;
import acm.util.TokenScanner;

class GLabel_getFontDescent extends JBECommand {
   GLabel_getFontDescent() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GLabel var4 = (GLabel)var2.getGObject(var3);
      System.out.println("result:" + var4.getDescent());
      System.out.flush();
   }
}
