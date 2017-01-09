/** Zach Scott
cpe102 hw3 **/

import java.awt.Color;
import java.awt.Point;
public class Circle extends Ellipse{
   public Circle(double radius, Point position, Color color, boolean filled){
      super(radius,radius,position,color,filled);
   }
   public void setSemiMajorAxis(double semiMajorAxis){
      super.setSemiMajorAxis(semiMajorAxis);
      super.setSemiMinorAxis(semiMajorAxis);
      
   }
   public void setSemiMinorAxis(double semiMinorAxis){
      super.setSemiMajorAxis(semiMinorAxis);
      super.setSemiMajorAxis(semiMinorAxis);

   }
   public double getRadius(){
      return super.getSemiMinorAxis();
   }
   public void setRadius(double radius){
      super.setSemiMajorAxis(radius);
      super.setSemiMajorAxis(radius);
      
   }




   /**public boolean equals(Object other){
      if(other == null){
         return false;
      }
      if(other.getClass() != this.getClass()){
         return false;
      }
      if(((Circle)other).getRadius() != this.getRadius()){
         return false;
         }else if(!this.getColor().equals(((Circle)other).getColor())){
            return false;
         }else if(!this.getPosition().equals(((Circle)other).getPosition())){
            return false;
         }else if(this.getFilled() != ((Circle)other).getFilled()){
            return false;
         }else{
            return true;
      }
   }**/
   
}       