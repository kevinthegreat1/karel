package stanford.spl;

import acm.util.TokenScanner;
import java.awt.Dimension;
import java.awt.Toolkit;

class GWindow_getScreenHeight extends JBECommand {
   GWindow_getScreenHeight() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      var1.verifyToken(")");
      Dimension var3 = Toolkit.getDefaultToolkit().getScreenSize();
      var2.println("result:" + var3.height);
   }
}
