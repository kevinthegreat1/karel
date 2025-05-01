package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import javax.swing.JTextField;

class GTextField_getText extends JBECommand {
   GTextField_getText() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var4 = var2.getGObject(var3);
      if (var4 != null) {
         JTextField var5 = (JTextField)((GTextField)var4).getInteractor();
         System.out.println("result:" + var5.getText());
         System.out.flush();
      } else {
         System.out.println("result:");
         System.out.flush();
      }

   }
}
