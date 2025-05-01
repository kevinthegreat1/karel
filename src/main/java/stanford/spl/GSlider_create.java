package stanford.spl;

import acm.util.TokenScanner;

class GSlider_create extends JBECommand {
   GSlider_create() {
   }

   public void execute(TokenScanner var1, JavaBackEnd var2) {
      var1.verifyToken("(");
      String var3 = this.nextString(var1);
      var1.verifyToken(",");
      int var4 = this.nextInt(var1);
      var1.verifyToken(",");
      int var5 = this.nextInt(var1);
      var1.verifyToken(",");
      int var6 = this.nextInt(var1);
      var1.verifyToken(")");
      GSlider var7 = new GSlider(var4, var5, var6, var2);
      var2.defineGObject(var3, var7);
      var2.defineSource(var7.getInteractor(), var3);
   }
}
