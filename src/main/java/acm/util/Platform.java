package acm.util;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

public class Platform {
   public static final int UNKNOWN = 0;
   public static final int MAC = 1;
   public static final int UNIX = 2;
   public static final int WINDOWS = 3;
   private static final String JTF_VERSION = "1.99.1";
   private static int platform = -1;
   private static boolean areStandardFontFamiliesAvailable;
   private static boolean fontsChecked;
   private static boolean isSwingAvailable;
   private static boolean swingChecked;
   private static boolean areCollectionsAvailable;
   private static boolean collectionsChecked;
   private static boolean isSunAudioAvailable;
   private static boolean sunAudioChecked;
   private static boolean isJMFAvailable;
   private static boolean jmfChecked;

   private Platform() {
   }

   public static int getPlatform() {
      if (platform != -1) {
         return platform;
      } else {
         String var0 = System.getProperty("os.name", "").toLowerCase();
         if (var0.startsWith("mac")) {
            platform = 1;
            return 1;
         } else if (var0.startsWith("windows")) {
            platform = 3;
            return 3;
         } else if (var0.startsWith("microsoft")) {
            platform = 3;
            return 3;
         } else if (var0.startsWith("ms")) {
            platform = 3;
            return 3;
         } else if (var0.startsWith("unix")) {
            platform = 2;
            return 2;
         } else if (var0.startsWith("linux")) {
            platform = 2;
            return 2;
         } else {
            platform = 0;
            return 0;
         }
      }
   }

   public static boolean isMac() {
      return getPlatform() == 1;
   }

   public static boolean isWindows() {
      return getPlatform() == 3;
   }

   public static boolean isUnix() {
      return getPlatform() == 2;
   }

   public static void setFileTypeAndCreator(String var0, String var1, String var2) {
      if (isMac()) {
         try {
            setFileTypeAndCreator(new File(var0), var1, var2);
         } catch (Exception var4) {
         }

      }
   }

   public static void setFileTypeAndCreator(File var0, String var1, String var2) {
      if (isMac()) {
         try {
            Class var3 = Class.forName("com.apple.mrj.MRJOSType");
            Class var4 = Class.forName("com.apple.mrj.MRJFileUtils");
            Class[] var5 = new Class[]{Class.forName("java.lang.String")};
            Constructor var6 = var3.getConstructor(var5);
            Class[] var7 = new Class[]{Class.forName("java.io.File"), var3, var3};
            Method var8 = var4.getMethod("setFileTypeAndCreator", var7);
            Object[] var9 = new Object[]{(var1 + "    ").substring(0, 4)};
            Object var10 = var6.newInstance(var9);
            Object[] var11 = new Object[]{(var2 + "    ").substring(0, 4)};
            Object var12 = var6.newInstance(var11);
            Object[] var13 = new Object[]{var0, var10, var12};
            var8.invoke((Object)null, var13);
         } catch (Exception var14) {
         }

      }
   }

   public static void copyFileTypeAndCreator(File var0, File var1) {
      if (isMac()) {
         try {
            Class var2 = Class.forName("com.apple.mrj.MRJOSType");
            Class var3 = Class.forName("com.apple.mrj.MRJFileUtils");
            Class[] var4 = new Class[]{Class.forName("java.io.File")};
            Method var5 = var3.getMethod("getFileType", var4);
            Method var6 = var3.getMethod("getFileCreator", var4);
            Class[] var7 = new Class[]{Class.forName("java.io.File"), var2, var2};
            Method var8 = var3.getMethod("setFileTypeAndCreator", var7);
            Object[] var9 = new Object[]{var0};
            Object var10 = var5.invoke((Object)null, var9);
            Object var11 = var6.invoke((Object)null, var9);
            Object[] var12 = new Object[]{var1, var10, var11};
            var8.invoke((Object)null, var12);
         } catch (Exception var13) {
         }

      }
   }

   public static String getJTFVersion() {
      return "1.99.1";
   }

   public static int compareVersion(String var0) {
      return compareVersion(System.getProperty("java.version"), var0);
   }

   public static int compareVersion(String var0, String var1) {
      StringTokenizer var2 = new StringTokenizer(var0, ".");
      StringTokenizer var3 = new StringTokenizer(var1, ".");

      while(var2.hasMoreTokens() && var3.hasMoreTokens()) {
         int var4 = Integer.parseInt(var2.nextToken());
         int var5 = Integer.parseInt(var3.nextToken());
         if (var4 != var5) {
            return var4 < var5 ? -1 : 1;
         }
      }

      if (var2.hasMoreTokens()) {
         return 1;
      } else {
         return var3.hasMoreTokens() ? -1 : 0;
      }
   }

   public static boolean isSwingAvailable() {
      if (!swingChecked) {
         swingChecked = true;
         isSwingAvailable = false;
         if (compareVersion("1.2") >= 0) {
            try {
               isSwingAvailable = Class.forName("javax.swing.JComponent") != null;
            } catch (Exception var1) {
            }
         }
      }

      return isSwingAvailable;
   }

   public static boolean isSunAudioAvailable() {
      if (!sunAudioChecked) {
         sunAudioChecked = true;

         try {
            isSunAudioAvailable = Class.forName("sun.audio.AudioPlayer") != null;
         } catch (Exception var1) {
            isSunAudioAvailable = false;
         }
      }

      return isSunAudioAvailable;
   }

   public static boolean isJMFAvailable() {
      if (!jmfChecked) {
         jmfChecked = true;

         try {
            isJMFAvailable = Class.forName("javax.media.Player") != null;
         } catch (Exception var1) {
            isJMFAvailable = false;
         }
      }

      return isJMFAvailable;
   }

   public static boolean areCollectionsAvailable() {
      if (!collectionsChecked) {
         collectionsChecked = true;

         try {
            areCollectionsAvailable = Class.forName("java.util.ArrayList") != null;
         } catch (Exception var1) {
            areCollectionsAvailable = false;
         }
      }

      return areCollectionsAvailable;
   }

   public static boolean areStandardFontFamiliesAvailable() {
      if (!fontsChecked) {
         fontsChecked = true;

         try {
            Class var0 = Class.forName("java.awt.Toolkit");
            Method var1 = var0.getMethod("getFontList");
            String[] var2 = (String[])var1.invoke(Toolkit.getDefaultToolkit());
            int var3 = 0;

            for(int var4 = 0; var4 < var2.length; ++var4) {
               if (var2[var4].equals("Serif") || var2[var4].equals("SansSerif") || var2[var4].equals("Monospaced")) {
                  ++var3;
               }
            }

            areStandardFontFamiliesAvailable = var3 == 3;
         } catch (Exception var5) {
            areStandardFontFamiliesAvailable = false;
         }
      }

      return areStandardFontFamiliesAvailable;
   }
}
