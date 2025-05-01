package acm.io;

interface DialogModel {
   void popupMessage(String var1);

   void popupErrorMessage(String var1);

   String popupLineInputDialog(String var1, boolean var2);

   Boolean popupBooleanInputDialog(String var1, String var2, String var3, boolean var4);
}
