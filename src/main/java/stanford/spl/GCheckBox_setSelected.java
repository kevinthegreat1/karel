package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import javax.swing.JCheckBox;

class GCheckBox_setSelected extends JBECommand {
   GCheckBox_setSelected() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      boolean var4 = this.nextBoolean(var1);
      var1.verifyToken(")");
      GObject var5 = var2.getGObject(var3);
      if (var5 != null) {
         JCheckBox var6 = (JCheckBox)((GCheckBox)var5).getInteractor();
         var6.setSelected(var4);
      }

   }
}
