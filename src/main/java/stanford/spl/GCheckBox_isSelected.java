package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import javax.swing.JCheckBox;

class GCheckBox_isSelected extends JBECommand {
   GCheckBox_isSelected() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var4 = var2.getGObject(var3);
      if (var4 != null) {
         JCheckBox var5 = (JCheckBox)((GCheckBox)var4).getInteractor();
         System.out.println("result:" + var5.isSelected());
         System.out.flush();
      } else {
         System.out.println("result:false");
         System.out.flush();
      }

   }
}
