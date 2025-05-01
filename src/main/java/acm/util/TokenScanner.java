package acm.util;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

public class TokenScanner {
   public static final int EOF = -1;
   public static final int SEPARATOR = 0;
   public static final int WORD = 1;
   public static final int NUMBER = 2;
   public static final int STRING = 3;
   public static final int OPERATOR = 4;
   private static final int INITIAL_STATE = 0;
   private static final int BEFORE_DECIMAL_POINT = 1;
   private static final int AFTER_DECIMAL_POINT = 2;
   private static final int STARTING_EXPONENT = 3;
   private static final int FOUND_EXPONENT_SIGN = 4;
   private static final int SCANNING_EXPONENT = 5;
   private static final int FINAL_STATE = 6;
   private Reader input;
   private String wordChars;
   private String savedChars;
   private Stack<String> savedTokens;
   private ArrayList<String> operators;
   private int cpos;
   private boolean ignoreWhitespaceFlag;
   private boolean ignoreCommentsFlag;
   private boolean scanNumbersFlag;
   private boolean scanStringsFlag;

   public TokenScanner() {
      this.ignoreWhitespaceFlag = false;
      this.ignoreCommentsFlag = false;
      this.scanNumbersFlag = false;
      this.scanStringsFlag = false;
      this.cpos = 0;
      this.input = null;
      this.wordChars = "";
      this.savedTokens = new Stack();
      this.operators = new ArrayList();
   }

   public TokenScanner(String var1) {
      this();
      this.setInput(var1);
   }

   public TokenScanner(Reader var1) {
      this();
      this.setInput(var1);
   }

   public void setInput(String var1) {
      this.setInput((Reader)(new StringReader(var1)));
   }

   public void setInput(Reader var1) {
      this.savedChars = "";
      this.savedTokens.clear();
      this.cpos = 0;
      this.input = var1;
   }

   public boolean hasMoreTokens() {
      String var1 = this.nextToken();
      this.saveToken(var1);
      return !var1.isEmpty();
   }

   public String nextToken() {
      return !this.savedTokens.isEmpty() ? (String)this.savedTokens.pop() : this.scanToken();
   }

   public void saveToken(String var1) {
      this.savedTokens.push(var1);
   }

   public int getPosition() {
      return this.savedTokens.isEmpty() ? this.cpos : this.cpos - ((String)this.savedTokens.peek()).length();
   }

   public void ignoreWhitespace() {
      this.ignoreWhitespaceFlag = true;
   }

   public void ignoreComments() {
      this.ignoreCommentsFlag = true;
   }

   public void scanNumbers() {
      this.scanNumbersFlag = true;
   }

   public void scanStrings() {
      this.scanStringsFlag = true;
   }

   public void addWordCharacters(String var1) {
      for(int var2 = 0; var2 < var1.length(); ++var2) {
         char var3 = var1.charAt(var2);
         if (this.wordChars.indexOf(var3) == -1) {
            this.wordChars = this.wordChars + var3;
         }
      }

   }

   public boolean isWordCharacter(int var1) {
      if (var1 == -1) {
         return false;
      } else {
         return Character.isLetterOrDigit(var1) || this.wordChars.indexOf((char)var1) >= 0;
      }
   }

   public void addOperator(String var1) {
      this.operators.add(var1);
   }

   public void verifyToken(String var1) {
      String var2 = this.nextToken();
      if (!var2.equals(var1)) {
         String var3 = "Found " + var2 + " when expecting " + var1;
         throw new ErrorException(var3);
      }
   }

   public int getTokenType(String var1) {
      if (var1 != null && var1.length() != 0) {
         char var2 = var1.charAt(0);
         if (Character.isWhitespace(var2)) {
            return 0;
         } else if (Character.isDigit(var2)) {
            return 2;
         } else if (var2 != '"' && var2 != '\'') {
            return this.isWordCharacter(var2) ? 1 : 4;
         } else {
            return 3;
         }
      } else {
         return -1;
      }
   }

   public int getChar() {
      try {
         ++this.cpos;
         if (!this.savedChars.isEmpty()) {
            char var1 = this.savedChars.charAt(0);
            this.savedChars = this.savedChars.substring(1);
            return var1;
         } else {
            return this.input.read();
         }
      } catch (IOException var2) {
         throw new ErrorException(var2);
      }
   }

   public void ungetChar(int var1) {
      --this.cpos;
      if (this.input instanceof PushbackReader) {
         try {
            ((PushbackReader)this.input).unread(var1);
            return;
         } catch (IOException var3) {
         }
      }

      if (var1 >= 0) {
         this.savedChars = (char)var1 + this.savedChars;
      }

   }

   public String getStringValue(String var1) {
      String var2 = "";
      byte var3 = 0;
      int var4 = var1.length();
      if (var4 > 1 && (var1.charAt(0) == '"' || var1.charAt(0) == '\'')) {
         var3 = 1;
         --var4;
      }

      for(int var5 = var3; var5 < var4; ++var5) {
         char var6 = var1.charAt(var5);
         if (var6 == '\\') {
            ++var5;
            var6 = var1.charAt(var5);
            if (!Character.isDigit(var6) && var6 != 'x') {
               switch (var6) {
                  case '"':
                     var6 = '"';
                     break;
                  case '\'':
                     var6 = '\'';
                     break;
                  case '\\':
                     var6 = '\\';
                     break;
                  case 'a':
                     var6 = '\u0007';
                     break;
                  case 'b':
                     var6 = '\b';
                     break;
                  case 'f':
                     var6 = '\f';
                     break;
                  case 'n':
                     var6 = '\n';
                     break;
                  case 'r':
                     var6 = '\r';
                     break;
                  case 't':
                     var6 = '\t';
                     break;
                  case 'v':
                     var6 = '\u000b';
               }
            } else {
               byte var7 = 8;
               byte var8 = 3;
               if (var6 == 'x') {
                  var7 = 16;
                  var8 = 2;
                  ++var5;
               }

               int var9 = 0;
               int var10 = 0;

               for(int var11 = Math.min(var4, var5 + var8); var5 < var11; ++var5) {
                  var6 = var1.charAt(var5);
                  if (Character.isDigit(var6)) {
                     var10 = var6 - 48;
                  } else if (var7 == 16 && var6 >= 'A' && var6 <= 'F') {
                     var10 = var6 - 65 + 10;
                  } else {
                     if (var7 != 16 || var6 < 'a' || var6 > 'f') {
                        break;
                     }

                     var10 = var6 - 97 + 10;
                  }

                  var9 = var7 * var9 + var10;
               }

               var6 = (char)var9;
               --var5;
            }
         }

         var2 = var2 + var6;
      }

      return var2;
   }

   private String scanToken() {
      int var1 = this.scanChar();
      if (var1 == -1) {
         return "";
      } else if (this.scanNumbersFlag && Character.isDigit(var1)) {
         this.ungetChar(var1);
         return this.scanNumber();
      } else if (this.isWordCharacter(var1)) {
         this.ungetChar(var1);
         return this.scanWord();
      } else if (!this.scanStringsFlag || var1 != 34 && var1 != 39) {
         String var2;
         for(var2 = "" + (char)var1; this.isOperatorPrefix(var2); var2 = var2 + (char)var1) {
            var1 = this.getChar();
            if (var1 == -1) {
               break;
            }
         }

         while(var2.length() > 1 && !this.isOperator(var2)) {
            this.ungetChar(var2.charAt(var2.length() - 1));
            var2 = var2.substring(0, var2.length() - 1);
         }

         return var2;
      } else {
         this.ungetChar(var1);
         return this.scanString();
      }
   }

   private int scanChar() {
      boolean var1 = false;
      boolean var2 = false;

      int var3;
      while(true) {
         var3 = this.getChar();
         if (!this.ignoreWhitespaceFlag || !Character.isWhitespace(var3) || this.isOperator(Character.toString((char)var3))) {
            if (!var1) {
               if (!var2) {
                  if (!this.ignoreCommentsFlag || var3 != 47) {
                     break;
                  }

                  int var5 = this.getChar();
                  if (var5 == 42) {
                     var1 = true;
                  } else {
                     if (var5 != 47) {
                        this.ungetChar(var5);
                        break;
                     }

                     var2 = true;
                  }
               } else if (var3 == 10 || var3 == 13) {
                  var2 = false;
               }
            } else {
               int var4 = this.getChar();
               if (var3 == 42 && var4 == 47) {
                  var1 = false;
               } else {
                  this.ungetChar(var4);
               }
            }
         }
      }

      return var3;
   }

   private String scanWord() {
      String var1 = "";

      int var2;
      for(var2 = this.getChar(); this.isWordCharacter(var2); var2 = this.getChar()) {
         var1 = var1 + (char)var2;
      }

      this.ungetChar(var2);
      return var1;
   }

   private String scanNumber() {
      String var1 = "";
      byte var2 = 0;
      int var3 = 69;

      while(var2 != 6) {
         int var4 = this.getChar();
         switch (var2) {
            case 0:
               if (var4 == 48) {
                  int var5 = this.getChar();
                  if (var5 == 120 || var5 == 88) {
                     var1 = "0x";
                     continue;
                  }

                  this.ungetChar(var5);
               }

               var2 = 1;
               break;
            case 1:
               if (var4 == 46) {
                  var2 = 2;
               } else if (var4 != 69 && var4 != 101) {
                  if (!Character.isDigit(var4)) {
                     var2 = 6;
                  }
               } else {
                  var2 = 3;
               }
               break;
            case 2:
               if (var4 != 69 && var4 != 101) {
                  if (!Character.isDigit(var4)) {
                     var2 = 6;
                  }
               } else {
                  var3 = var4;
                  var2 = 3;
               }
               break;
            case 3:
               if (var4 != 43 && var4 != 45) {
                  if (Character.isDigit(var4)) {
                     var2 = 5;
                  } else {
                     var2 = 6;
                  }
               } else {
                  var2 = 4;
               }
               break;
            case 4:
               if (Character.isDigit(var4)) {
                  var2 = 5;
               } else {
                  this.ungetChar(var4);
                  var4 = var3;
                  var2 = 6;
               }
               break;
            case 5:
               if (!Character.isDigit(var4)) {
                  var2 = 6;
               }
         }

         if (var2 == 6) {
            this.ungetChar(var4);
            break;
         }

         var1 = var1 + (char)var4;
      }

      return var1;
   }

   private String scanString() {
      char var1 = (char)this.getChar();
      String var2 = "" + var1;
      int var3 = 0;

      while(true) {
         int var4 = this.getChar();
         if (var4 == -1 || var4 == var1 && var3 != 92) {
            return var2 + var1;
         }

         var2 = var2 + (char)var4;
         var3 = var4;
      }
   }

   private boolean isOperator(String var1) {
      for(int var2 = 0; var2 < this.operators.size(); ++var2) {
         if (((String)this.operators.get(var2)).equals(var1)) {
            return true;
         }
      }

      return false;
   }

   private boolean isOperatorPrefix(String var1) {
      for(int var2 = 0; var2 < this.operators.size(); ++var2) {
         if (((String)this.operators.get(var2)).startsWith(var1)) {
            return true;
         }
      }

      return false;
   }
}
