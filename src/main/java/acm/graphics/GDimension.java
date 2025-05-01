package acm.graphics;

import java.awt.*;
import java.io.Serializable;

public class GDimension implements Serializable {
   private double width;
   private double height;
   static final long serialVersionUID = 21L;

   public GDimension() {
      this(0.0, 0.0);
   }

   public GDimension(double var1, double var3) {
      this.width = var1;
      this.height = var3;
   }

   public GDimension(GDimension var1) {
      this(var1.width, var1.height);
   }

   public GDimension(Dimension var1) {
      this((double)var1.width, (double)var1.height);
   }

   public double getWidth() {
      return this.width;
   }

   public double getHeight() {
      return this.height;
   }

   public void setSize(double var1, double var3) {
      this.width = var1;
      this.height = var3;
   }

   public void setSize(GDimension var1) {
      this.setSize(var1.width, var1.height);
   }

   public GDimension getSize() {
      return new GDimension(this.width, this.height);
   }

   public Dimension toDimension() {
      return new Dimension((int)Math.round(this.width), (int)Math.round(this.height));
   }

   public int hashCode() {
      return (new Float((float)this.width)).hashCode() ^ 37 * (new Float((float)this.height)).hashCode();
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof GDimension)) {
         return false;
      } else {
         GDimension var2 = (GDimension)var1;
         return (float)this.width == (float)var2.width && (float)this.height == (float)var2.height;
      }
   }

   public String toString() {
      return "(" + (float)this.width + "x" + (float)this.height + ")";
   }
}
