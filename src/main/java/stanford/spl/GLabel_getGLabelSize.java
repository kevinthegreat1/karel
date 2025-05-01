package stanford.spl;

import acm.graphics.GDimension;
import acm.graphics.GLabel;
import acm.util.TokenScanner;

class GLabel_getGLabelSize extends JBECommand {
   GLabel_getGLabelSize() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(")");
      GLabel var4 = (GLabel)var2.getGObject(var3);
      GDimension var5 = var4.getSize();
      System.out.println("result:GDimension(" + var5.getWidth() + ", " + var5.getHeight() + ")");
      System.out.flush();
   }
}
