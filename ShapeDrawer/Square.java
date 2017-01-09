/** Zach Scott
cpe102 hw4 **/

import java.awt.Color;
import java.awt.Point;

public class Square extends Rectangle{
   public Square(int sideLength,Point position,Color color,boolean filled){
      super(sideLength,sideLength,position,color,filled);
   }

   public int getSize(){
      return this.getWidth();
   }
   public void setSize(int sideLength){
      this.setWidth(sideLength);
   }
   public void setWidth(int side){
      super.setWidth(side);
      super.setHeight(side);
   }
   public void setHeight(int side){
       super.setWidth(side);
      super.setHeight(side);
   }
      
}