package acm.graphics;

import java.awt.*;
import java.awt.geom.Arc2D;

public class GArc extends GObject implements GFillable {
   public static final double ARC_TOLERANCE = 2.5;
   private double frameWidth;
   private double frameHeight;
   private double arcStart;
   private double arcSweep;
   private Color fillColor;
   private boolean isFilled;
   static final long serialVersionUID = 21L;

   public GArc(double var1, double var3, double var5, double var7) {
      this(0.0, 0.0, var1, var3, var5, var7);
   }

   public GArc(double var1, double var3, double var5, double var7, double var9, double var11) {
      this.frameWidth = var5;
      this.frameHeight = var7;
      this.arcStart = var9;
      this.arcSweep = var11;
      this.setLocation(var1, var3);
   }

   public void setStartAngle(double var1) {
      this.arcStart = var1;
      this.repaint();
   }

   public double getStartAngle() {
      return this.arcStart;
   }

   public void setSweepAngle(double var1) {
      this.arcSweep = var1;
      this.repaint();
   }

   public double getSweepAngle() {
      return this.arcSweep;
   }

   public GPoint getStartPoint() {
      return this.getArcPoint(this.arcStart);
   }

   public GPoint getEndPoint() {
      return this.getArcPoint(this.arcStart + this.arcSweep);
   }

   protected void paint2d(Graphics2D var1) {
      Arc2D.Double var2 = new Arc2D.Double(0.0, 0.0, this.frameWidth, this.frameHeight, this.arcStart, this.arcSweep, this.isFilled() ? 2 : 0);
      if (this.isFilled()) {
         var1.setColor(this.getFillColor());
         var1.fill(var2);
         var1.setColor(this.getColor());
      }

      var1.draw(var2);
   }

   public GRectangle getBounds() {
      double var1 = this.frameWidth / 2.0;
      double var3 = this.frameHeight / 2.0;
      double var5 = this.getX() + var1;
      double var7 = this.getY() + var3;
      double var9 = var5 + GMath.cosDegrees(this.arcStart) * var1;
      double var11 = var7 - GMath.sinDegrees(this.arcStart) * var3;
      double var13 = var5 + GMath.cosDegrees(this.arcStart + this.arcSweep) * var1;
      double var15 = var7 - GMath.sinDegrees(this.arcStart + this.arcSweep) * var3;
      double var17 = Math.min(var9, var13);
      double var19 = Math.max(var9, var13);
      double var21 = Math.min(var11, var15);
      double var23 = Math.max(var11, var15);
      if (this.containsAngle(0.0)) {
         var19 = var5 + var1;
      }

      if (this.containsAngle(90.0)) {
         var21 = var7 - var3;
      }

      if (this.containsAngle(180.0)) {
         var17 = var5 - var1;
      }

      if (this.containsAngle(270.0)) {
         var23 = var7 + var3;
      }

      if (this.isFilled()) {
         var17 = Math.min(var17, var5);
         var21 = Math.min(var21, var7);
         var19 = Math.max(var19, var5);
         var23 = Math.max(var23, var7);
      }

      return new GRectangle(var17, var21, var19 - var17, var23 - var21);
   }

   public boolean contains(double var1, double var3) {
      double var5 = this.frameWidth / 2.0;
      double var7 = this.frameHeight / 2.0;
      if (var5 != 0.0 && var7 != 0.0) {
         double var9 = var1 - (this.getX() + var5);
         double var11 = var3 - (this.getY() + var7);
         double var13 = var9 * var9 / (var5 * var5) + var11 * var11 / (var7 * var7);
         if (this.isFilled()) {
            if (var13 > 1.0) {
               return false;
            }
         } else {
            double var15 = 2.5 / ((var5 + var7) / 2.0);
            if (Math.abs(1.0 - var13) > var15) {
               return false;
            }
         }

         return this.containsAngle(GMath.toDegrees(Math.atan2(-var11, var9)));
      } else {
         return false;
      }
   }

   public void setFrameRectangle(double var1, double var3, double var5, double var7) {
      this.frameWidth = var5;
      this.frameHeight = var7;
      this.setLocation(var1, var3);
   }

   public final void setFrameRectangle(GRectangle var1) {
      this.setFrameRectangle(var1.getX(), var1.getY(), var1.getWidth(), var1.getHeight());
   }

   public GRectangle getFrameRectangle() {
      return new GRectangle(this.getX(), this.getY(), this.frameWidth, this.frameHeight);
   }

   public void setFilled(boolean var1) {
      this.isFilled = var1;
      this.repaint();
   }

   public boolean isFilled() {
      return this.isFilled;
   }

   public void setFillColor(Color var1) {
      this.fillColor = var1;
      this.repaint();
   }

   public Color getFillColor() {
      return this.fillColor == null ? this.getColor() : this.fillColor;
   }

   public String paramString() {
      String var1 = super.paramString();
      var1 = var1.substring(var1.indexOf(41) + 1);
      GRectangle var2 = this.getFrameRectangle();
      String var3 = "frame=(" + var2.getX() + ", " + var2.getY() + ", " + var2.getWidth() + ", " + var2.getHeight() + ")";
      var3 = var3 + ", start=" + this.arcStart + ", sweep=" + this.arcSweep;
      return var3 + var1;
   }

   private GPoint getArcPoint(double var1) {
      double var3 = this.frameWidth / 2.0;
      double var5 = this.frameHeight / 2.0;
      double var7 = this.getX() + var3;
      double var9 = this.getY() + var5;
      return new GPoint(var7 + var3 * GMath.cosDegrees(var1), var9 - var5 * GMath.sinDegrees(var1));
   }

   private boolean containsAngle(double var1) {
      double var3 = Math.min(this.getStartAngle(), this.getStartAngle() + this.getSweepAngle());
      double var5 = Math.abs(this.getSweepAngle());
      if (var5 >= 360.0) {
         return true;
      } else {
         var1 = var1 < 0.0 ? 360.0 - -var1 % 360.0 : var1 % 360.0;
         var3 = var3 < 0.0 ? 360.0 - -var3 % 360.0 : var3 % 360.0;
         if (var3 + var5 > 360.0) {
            return var1 >= var3 || var1 <= var3 + var5 - 360.0;
         } else {
            return var1 >= var3 && var1 <= var3 + var5;
         }
      }
   }
}
