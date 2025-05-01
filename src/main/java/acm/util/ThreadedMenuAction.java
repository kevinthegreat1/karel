package acm.util;

import acm.io.IOConsole;
import acm.io.IOModel;
import acm.program.Program;

import java.io.*;

class ThreadedMenuAction implements Runnable {
   private Program targetProgram;
   private String actionCommand;

   public ThreadedMenuAction(Program var1, String var2) {
      this.targetProgram = var1;
      this.actionCommand = var2;
   }

   public void run() {
      ProgressBarDialog var1 = new ProgressBarDialog(this.targetProgram);

      try {
         if (this.actionCommand.equals("Export Applet")) {
            JTFTools.exportApplet(this.targetProgram, var1.getProgressBar());
         } else if (this.actionCommand.equals("Submit Project")) {
            JTFTools.submitProject(this.targetProgram, var1.getProgressBar());
         }
      } catch (Exception var9) {
         var1.setVisible(false);
         Object var3 = this.targetProgram.getDialog();
         if (var3 == null) {
            var3 = this.targetProgram.getConsole();
         }

         if (var3 == null) {
            var3 = IOConsole.SYSTEM_CONSOLE;
         }

         String var4 = var9.getMessage();
         if (!(var9 instanceof ErrorException)) {
            var4 = "" + var9;
            StringWriter var5 = new StringWriter();
            PrintWriter var6 = new PrintWriter(var5);
            var9.printStackTrace(var6);
            var6.close();

            try {
               BufferedReader var7 = new BufferedReader(new StringReader(var5.toString()));
               var7.readLine();
               var4 = var4 + var7.readLine();
            } catch (IOException var8) {
            }
         }

         ((IOModel)var3).showErrorMessage(var4);
      }

   }
}
