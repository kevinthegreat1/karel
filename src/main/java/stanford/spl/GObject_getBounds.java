package stanford.spl;

import acm.graphics.GObject;
import acm.graphics.GRectangle;
import acm.util.TokenScanner;

class GObject_getBounds extends JBECommand {
   GObject_getBounds() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GObject var4 = var2.getGObject(var3);
      if (var4 == null) {
         System.out.println("error: NULL object");
      } else {
         GRectangle var5 = var4.getBounds();
         System.out.println("result:GRectangle(" + var5.getX() + ", " + var5.getY() + ", " + var5.getWidth() + ", " + var5.getHeight() + ")");
      }

      System.out.flush();
   }
}
