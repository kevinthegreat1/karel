package stanford.spl;

import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public class GChooser extends GInteractor {
   public GChooser(ActionListener var1) {
      super(new JComboBox());
      JComboBox var2 = (JComboBox)this.getInteractor();
      if (var1 != null) {
         var2.setEditable(false);
         var2.addActionListener(var1);
      }

   }
}
