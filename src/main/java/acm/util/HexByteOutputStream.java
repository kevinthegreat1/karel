package acm.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

class HexByteOutputStream extends OutputStream {
   private static final int COLUMNS = 76;
   private int columnCount;
   private PrintStream out;

   public HexByteOutputStream(PrintStream var1) {
      this.out = var1;
      this.out.flush();
      this.columnCount = 0;
   }

   public void write(int var1) throws IOException {
      String var2 = Integer.toHexString(256 + (var1 & 255)).toUpperCase();
      this.out.write(var2.charAt(1));
      this.out.write(var2.charAt(2));
      this.columnCount += 2;
      if (this.columnCount >= 76) {
         this.columnCount = 0;
         this.out.println();
      }

   }

   public void flush() {
      this.out.flush();
   }

   public void close() {
      this.out.close();
   }
}
