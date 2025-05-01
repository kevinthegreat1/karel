package stanford.spl;

import acm.graphics.GObject;
import acm.util.TokenScanner;
import javax.swing.JSlider;

class GSlider_getValue extends JBECommand {
   GSlider_getValue() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var4 = var2.getGObject(var3);
      if (var4 != null) {
         JSlider var5 = (JSlider)((GSlider)var4).getInteractor();
         System.out.println("result:" + var5.getValue());
         System.out.flush();
      } else {
         System.out.println("result:0");
         System.out.flush();
      }

   }
}
