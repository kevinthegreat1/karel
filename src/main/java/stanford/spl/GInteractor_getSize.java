package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import java.awt.Dimension;
import javax.swing.JComponent;

class GInteractor_getSize extends JBECommand {
   GInteractor_getSize() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var4 = var2.getGObject(var3);
      if (var4 != null) {
         JComponent var5 = ((GInteractor)var4).getInteractor();
         Dimension var6 = var5.isShowing() ? var5.getSize() : var5.getPreferredSize();
         System.out.println("result:GDimension(" + var6.width + ", " + var6.height + ")");
         System.out.flush();
      } else {
         System.out.println("result:GDimension(0, 0)");
         System.out.flush();
      }

   }
}
