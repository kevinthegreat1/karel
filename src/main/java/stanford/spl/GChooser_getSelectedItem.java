package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import javax.swing.JComboBox;

class GChooser_getSelectedItem extends JBECommand {
   GChooser_getSelectedItem() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var4 = var2.getGObject(var3);
      if (var4 != null) {
         JComboBox var5 = (JComboBox)((GChooser)var4).getInteractor();
         System.out.println("result:" + var5.getSelectedItem());
         System.out.flush();
      } else {
         System.out.println("result:");
         System.out.flush();
      }

   }
}
