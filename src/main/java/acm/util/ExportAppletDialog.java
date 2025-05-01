package acm.util;

import acm.program.Program;

import javax.swing.*;
import java.awt.*;
import java.io.File;

class ExportAppletDialog extends JFileChooser {
   private JCheckBox exportFilesCheckBox;
   private Component target;

   public ExportAppletDialog(File var1, Program var2) {
      super(var1);
      this.setFileSelectionMode(1);
      this.setDialogTitle("Export Applet");
      String var3 = var2.getClass().getName();
      String var4 = var3.substring(var3.lastIndexOf(46) + 1);
      this.setSelectedFile(new File(var1, var4));
      this.exportFilesCheckBox = new JCheckBox("Export resource files");
      this.exportFilesCheckBox.setSelected(true);
      this.target = var2;
   }

   protected JDialog createDialog(Component var1) {
      JDialog var2 = super.createDialog(var1);
      JPanel var3 = new JPanel();
      var3.setLayout(new FlowLayout(0));
      var3.add(this.exportFilesCheckBox);
      var2.getContentPane().add(var3, "South");
      var2.validate();
      return var2;
   }

   public File chooseOutputDirectory() {
      int var1 = this.showSaveDialog(this.target);
      return var1 == 1 ? null : this.getSelectedFile();
   }

   public boolean exportFiles() {
      return this.exportFilesCheckBox.isSelected();
   }
}
