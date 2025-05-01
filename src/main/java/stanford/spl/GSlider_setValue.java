package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import javax.swing.JSlider;

class GSlider_setValue extends JBECommand {
   GSlider_setValue() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      int var4 = this.nextInt(var1);
      var1.verifyToken(")");
      GObject var5 = var2.getGObject(var3);
      if (var5 != null) {
         JSlider var6 = (JSlider)((GSlider)var5).getInteractor();
         var6.setValue(var4);
      }

   }
}
