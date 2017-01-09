/** Zach Scott
cpe102 hw4 **/

import java.awt.Point;
import java.awt.Color;
public class Ellipse extends Shape{
   private double semiMajorAxis;
   private double semiMinorAxis;
   private Point center;
   public Ellipse(double semiMajorAxis, double semiMinorAxis, Point position, Color color,boolean filled){
      super(color,filled);
      this.semiMajorAxis = semiMajorAxis;
      this.semiMinorAxis = semiMinorAxis;
      Point pos = new Point(position.x,position.y);
      this.center = pos;
   }
   public double getSemiMajorAxis(){
      return semiMajorAxis;
   }
   public void setSemiMajorAxis(double semiMajorAxis){
      if(semiMajorAxis < this.semiMinorAxis){

         this.semiMajorAxis = this.semiMinorAxis;
         this.semiMinorAxis = semiMajorAxis;
      }else{
         this.semiMajorAxis = semiMajorAxis;
      }
   }
   public double getSemiMinorAxis(){
      return semiMinorAxis;
   }
   public void setSemiMinorAxis(double semiMinorAxis){            
      if(semiMinorAxis > this.semiMajorAxis){
         this.semiMinorAxis = this.semiMajorAxis;
         this.semiMajorAxis = semiMinorAxis;
      }else{
         this.semiMinorAxis = semiMinorAxis;
      }
   }
   public Point getPosition(){
      Point pos = new Point(center.x,center.y);
      return pos;
   }
   public void setPosition(Point position){
      Point pos = new Point(position);
      center = pos;
   }
   public void move(Point delta){
      int deltaX = (int)(delta.getX()+ center.getX());
      int deltaY = (int)(delta.getY()+ center.getY());
      center = new Point(deltaX,deltaY);
   } 
   public double getArea(){
      return Math.PI * semiMajorAxis * semiMinorAxis;
   }
   public boolean equals(Object o){
      if(o==null){
         return false;
      }
      if(this.getClass() != o.getClass()){
         return false;
      }
      if(!this.getColor().equals(((Ellipse)o).getColor())){
         return false;
      }
      if(this.getFilled() != ((Ellipse)o).getFilled()){
         return false;
      }
      if(!this.center.equals(((Ellipse)o).center)){
         return false;
      }
      if(this.semiMajorAxis != ((Ellipse)o).semiMajorAxis){
         return false;
      }
      if(this.semiMinorAxis != ((Ellipse)o).semiMinorAxis){
         return false;
      }
      return true;
   }


}
   