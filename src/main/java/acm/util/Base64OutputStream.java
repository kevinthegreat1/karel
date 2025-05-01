package acm.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

class Base64OutputStream extends OutputStream {
   private static final int COLUMNS = 76;
   private static final char[] BASE64 = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
   private int buffer;
   private int columnCount;
   private int byteCount;
   private PrintStream out;

   public Base64OutputStream(PrintStream var1) {
      this.out = var1;
      this.out.flush();
      this.byteCount = 0;
      this.columnCount = 0;
      this.buffer = 0;
   }

   public void write(int var1) throws IOException {
      this.buffer = this.buffer << 8 | var1 & 255;
      ++this.byteCount;
      if (this.byteCount == 3) {
         this.out.write(BASE64[this.buffer >> 18 & 63]);
         this.out.write(BASE64[this.buffer >> 12 & 63]);
         this.out.write(BASE64[this.buffer >> 6 & 63]);
         this.out.write(BASE64[this.buffer & 63]);
         this.columnCount += 4;
         if (this.columnCount >= 76) {
            this.columnCount = 0;
            this.out.println();
         }

         this.byteCount = 0;
         this.buffer = 0;
      }

   }

   public void flush() {
      this.out.flush();
   }

   public void close() {
      this.pad();
      this.out.close();
   }

   public void pad() {
      switch (this.byteCount) {
         case 0:
         default:
            break;
         case 1:
            this.out.write(BASE64[this.buffer >> 2 & 63]);
            this.out.write(BASE64[this.buffer << 4 & 63]);
            this.out.write(61);
            this.out.write(61);
            break;
         case 2:
            this.out.write(BASE64[this.buffer >> 10 & 63]);
            this.out.write(BASE64[this.buffer >> 4 & 63]);
            this.out.write(BASE64[this.buffer << 2 & 63]);
            this.out.write(61);
      }

      this.out.println();
      this.out.println();
   }
}
