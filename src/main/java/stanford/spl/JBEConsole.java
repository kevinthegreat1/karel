package stanford.spl;

import acm.io.IOConsole;
import java.awt.Dimension;

public class JBEConsole extends IOConsole {
   private int preferredWidth;
   private int preferredHeight;

   public JBEConsole() {
   }

   public void setPreferredSize(int var1, int var2) {
      this.preferredWidth = var1;
      this.preferredHeight = var2;
   }

   public Dimension getPreferredSize() {
      return new Dimension(this.preferredWidth, this.preferredHeight);
   }
}
