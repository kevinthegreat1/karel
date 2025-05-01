package stanford.spl;

import acm.graphics.GImage;
import acm.util.TokenScanner;

class GImage_create extends JBECommand {
   GImage_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      String var4 = this.nextString(var1);
      var1.verifyToken(")");

      try {
         GImage var5 = new GImage(var4);
         var2.defineGObject(var3, var5);
         System.out.println("result:GDimension(" + var5.getWidth() + ", " + var5.getHeight() + ")");
         System.out.flush();
      } catch (Exception var6) {
         System.out.println("result:" + var6.getMessage());
         System.out.flush();
      }

   }
}
