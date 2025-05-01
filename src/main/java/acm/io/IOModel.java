package acm.io;

public interface IOModel {
   void print(String var1);

   void print(boolean var1);

   void print(char var1);

   void print(double var1);

   void print(float var1);

   void print(int var1);

   void print(long var1);

   void print(Object var1);

   void println();

   void println(String var1);

   void println(boolean var1);

   void println(char var1);

   void println(double var1);

   void println(float var1);

   void println(int var1);

   void println(long var1);

   void println(Object var1);

   void showErrorMessage(String var1);

   String readLine();

   String readLine(String var1);

   int readInt();

   int readInt(int var1, int var2);

   int readInt(String var1);

   int readInt(String var1, int var2, int var3);

   double readDouble();

   double readDouble(double var1, double var3);

   double readDouble(String var1);

   double readDouble(String var1, double var2, double var4);

   boolean readBoolean();

   boolean readBoolean(String var1);

   boolean readBoolean(String var1, String var2, String var3);
}
