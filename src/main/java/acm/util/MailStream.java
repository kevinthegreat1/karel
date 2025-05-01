package acm.util;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

class MailStream extends PrintStream {
   private static final int SMTP_PORT = 25;
   private static SimpleDateFormat RFC2822_DATE_FORMAT;
   private Socket socket;
   private InputStream in;
   private OutputStream out;
   private String sender;
   private String recipient;

   public MailStream(String var1, String var2, String var3) {
      super(new NullOutputStream());

      try {
         this.sender = var2;
         this.recipient = var3;
         this.socket = new Socket(var1, 25);
         this.out = new BufferedOutputStream(this.socket.getOutputStream());
         this.in = new BufferedInputStream(this.socket.getInputStream());
         this.verify("220");
         this.println("HELO " + InetAddress.getLocalHost().getHostName());
         this.verify("250");
         this.println("MAIL FROM: " + this.sender);
         this.verify("250");
         StringTokenizer var4 = new StringTokenizer(var3, " ,");

         while(var4.hasMoreTokens()) {
            this.println("RCPT TO: " + var4.nextToken());
            this.verify("250");
         }

         this.println("DATA");
         this.verify("354");
      } catch (IOException var5) {
         throw new ErrorException(var5);
      }
   }

   public void print(boolean var1) {
      this.print("" + var1);
   }

   public void print(char var1) {
      this.print("" + var1);
   }

   public void print(int var1) {
      this.print("" + var1);
   }

   public void print(long var1) {
      this.print("" + var1);
   }

   public void print(float var1) {
      this.print("" + var1);
   }

   public void print(double var1) {
      this.print("" + var1);
   }

   public void print(char[] var1) {
      this.print(new String(var1));
   }

   public void print(String var1) {
      this.write(var1);
   }

   public void print(Object var1) {
      this.print("" + var1);
   }

   public void println() {
      try {
         this.out.write(10);
         this.out.flush();
      } catch (IOException var2) {
         throw new ErrorException(var2);
      }
   }

   public void println(boolean var1) {
      this.print(var1);
      this.println();
   }

   public void println(char var1) {
      this.print(var1);
      this.println();
   }

   public void println(int var1) {
      this.print(var1);
      this.println();
   }

   public void println(long var1) {
      this.print(var1);
      this.println();
   }

   public void println(float var1) {
      this.print(var1);
      this.println();
   }

   public void println(double var1) {
      this.print(var1);
      this.println();
   }

   public void println(char[] var1) {
      this.print(var1);
      this.println();
   }

   public void println(String var1) {
      this.print(var1);
      this.println();
   }

   public void println(Object var1) {
      this.print(var1);
      this.println();
   }

   public void write(int var1) {
      try {
         if (var1 > 255) {
            throw new ErrorException("Illegal character in mail stream");
         } else {
            this.out.write(var1);
         }
      } catch (IOException var3) {
         throw new ErrorException(var3);
      }
   }

   public void write(char[] var1, int var2, int var3) {
      try {
         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var1[var2 + var4];
            if (var5 > 255) {
               throw new ErrorException("Illegal character in mail stream");
            }

            this.out.write(var5);
         }

      } catch (IOException var6) {
         throw new ErrorException(var6);
      }
   }

   public void write(char[] var1) {
      this.write(var1, 0, var1.length);
   }

   public void write(String var1) {
      try {
         for(int var2 = 0; var2 < var1.length(); ++var2) {
            char var3 = var1.charAt(var2);
            if (var3 > 255) {
               throw new ErrorException("Illegal character in mail stream");
            }

            this.out.write(var3);
         }

      } catch (IOException var4) {
         throw new ErrorException(var4);
      }
   }

   public boolean checkError() {
      return false;
   }

   public void flush() {
      try {
         this.out.flush();
      } catch (IOException var2) {
         throw new ErrorException(var2);
      }
   }

   public void close() {
      try {
         this.println(".");
         this.verify("250");
         this.out.close();
         this.socket.close();
      } catch (IOException var2) {
         throw new ErrorException(var2);
      }
   }

   public void cancel() {
      try {
         this.socket.close();
         this.out.close();
      } catch (IOException var2) {
         throw new ErrorException(var2);
      }
   }

   public void sendStandardHeaders(String var1, String var2) {
      this.println("To: " + this.recipient);
      if (var1 != null && var1.length() != 0) {
         boolean var3 = false;

         for(int var4 = 0; var4 < var1.length(); ++var4) {
            char var5 = var1.charAt(var4);
            if (var5 != ' ' && var5 != '-' && !Character.isJavaIdentifierPart(var5)) {
               var3 = true;
               break;
            }
         }

         if (var3) {
            this.sender = '"' + var1 + '"' + " <" + this.sender + ">";
         } else {
            this.sender = var1 + " <" + this.sender + ">";
         }
      }

      this.println("From: " + this.sender);
      if (var2 != null && var2.length() != 0) {
         this.println("Subject: " + var2);
      }

      this.println("Date: " + RFC2822_DATE_FORMAT.format(new Date()));
   }

   private void verify(String var1) {
      try {
         String var2 = "";

         while(true) {
            int var3 = this.in.read();
            if (var3 == -1 || var3 == 10) {
               if (var1 != null && !var2.startsWith(var1)) {
                  throw new ErrorException("Unexpected SMTP response: " + var2);
               } else {
                  return;
               }
            }

            var2 = var2 + (char)var3;
         }
      } catch (IOException var4) {
         throw new ErrorException(var4);
      }
   }

   static {
      RFC2822_DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
   }
}
