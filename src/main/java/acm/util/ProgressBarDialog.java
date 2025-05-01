package acm.util;

import acm.gui.TableLayout;
import acm.program.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ProgressBarDialog extends JDialog implements ActionListener {
   private static final int DIALOG_WIDTH = 250;
   private static final int DIALOG_HEIGHT = 90;
   private static final int PROGRESS_BAR_HEIGHT = 25;
   private JProgressBar progressBar;

   public ProgressBarDialog(Program var1) {
      super(JTFTools.getEnclosingFrame(var1), "Progress", false);
      Container var2 = this.getContentPane();
      var2.setLayout(new TableLayout(2, 1));
      this.progressBar = new JProgressBar();
      var2.add(this.progressBar, "weightx=1 left=5 right=5 height=25");
      JButton var3 = new JButton("Cancel");
      var3.addActionListener(this);
      var2.add(var3, "top=10 fill=NONE");
      Rectangle var4 = var1.getBounds();
      int var5 = var4.x + (var4.width - 250) / 2;
      int var6 = var4.y + (var4.height - 90) / 2;
      this.setBounds(var5, var6, 250, 90);
   }

   public JProgressBar getProgressBar() {
      return this.progressBar;
   }

   public void actionPerformed(ActionEvent var1) {
      this.progressBar.setString("Cancel");
      this.setVisible(false);
   }

   public static boolean hasBeenCancelled(JProgressBar var0) {
      if (var0 == null) {
         return false;
      } else {
         String var1 = var0.getString();
         return var1 != null && var1.equals("Cancel");
      }
   }

   public static void popup(JProgressBar var0) {
      for(Object var1 = var0; var1 != null; var1 = ((Component)var1).getParent()) {
         if (var1 instanceof ProgressBarDialog) {
            ((Component)var1).setVisible(true);
            ((Component)var1).repaint();
            return;
         }
      }

   }

   public static void dismiss(JProgressBar var0) {
      for(Object var1 = var0; var1 != null; var1 = ((Component)var1).getParent()) {
         if (var1 instanceof ProgressBarDialog) {
            ((Component)var1).setVisible(false);
            return;
         }
      }

   }
}
