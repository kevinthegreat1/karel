package stanford.spl;

import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

public class GCheckBox extends GInteractor {
   public GCheckBox(String var1, ActionListener var2) {
      super(new JCheckBox(var1));
      JCheckBox var3 = (JCheckBox)this.getInteractor();
      if (var2 != null) {
         var3.addActionListener(var2);
      }

   }
}
