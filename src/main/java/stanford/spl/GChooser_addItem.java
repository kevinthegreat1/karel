package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import java.lang.reflect.Method;
import javax.swing.JComboBox;

class GChooser_addItem extends JBECommand {
   GChooser_addItem() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var5 = var2.getGObject(var3);
      if (var5 != null) {
         JComboBox var6 = (JComboBox)((GChooser)var5).getInteractor();
         Class var7 = var6.getClass();
         Class[] var8 = new Class[]{Object.class};
         Object[] var9 = new Object[]{var4};

         try {
            Method var10 = var7.getMethod("addItem", var8);
            var10.invoke(var6, var9);
         } catch (Exception var11) {
            System.out.println("error: " + var11);
         }
      }

   }
}
