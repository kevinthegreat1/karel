package stanford.spl;

import java.awt.event.ActionListener;
import javax.swing.JButton;

public class GButton extends GInteractor {
   public GButton(String var1, ActionListener var2) {
      super(new JButton(var1));
      JButton var3 = (JButton)this.getInteractor();
      if (var2 != null) {
         var3.addActionListener(var2);
      }

   }
}
