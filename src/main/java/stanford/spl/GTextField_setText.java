package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import javax.swing.JTextField;

class GTextField_setText extends JBECommand {
   GTextField_setText() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var5 = var2.getGObject(var3);
      if (var5 != null) {
         JTextField var6 = (JTextField)((GTextField)var5).getInteractor();
         var6.setText(var4);
      }

   }
}
