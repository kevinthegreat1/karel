package acm.util;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SwingTimer extends Timer {
   static final long serialVersionUID = 1L;

   public SwingTimer(int var1, ActionListener var2) {
      super(var1, var2);
   }
}
