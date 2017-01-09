/** Zach Scott
cpe102 hw3 **/

import java.awt.Point;
import java.awt.Color;

public class Rectangle extends ConvexPolygon{
  
   public Rectangle(int width, int height, Point position, Color color, boolean filled){
      super(new Point[] {position,new Point(position.x+width,position.y),
      new Point(position.x+width,position.y+height),
      new Point(position.x,position.y+height)},color,filled);
      
   }
   public int getWidth(){
      int w = getVertex(1).x-getVertex(0).x;
      return w;
   }
   public void setVertex(int index, Point position){
      throw new UnsupportedOperationException();
   }
   public void setWidth(int width){
      super.setVertex(1, new Point((getVertex(0).x+width),getVertex(1).y));
      super.setVertex(2, new Point((getVertex(0).x+width),getVertex(2).y));
   }
   public int getHeight(){
      int h = getVertex(3).y-getVertex(0).y;
      return h;
   }
   public void setHeight(int height){
      super.setVertex(3, new Point((getVertex(3).x),(getVertex(0).y+height)));
      super.setVertex(2, new Point((getVertex(2).x),(getVertex(0).y+height)));
   }

   /**public void setPosition(Point position){
      this.position = position;
   }
   public void move(Point delta){
      double pos_x = position.getX();
      double pos_y = position.getY();
      double delta_x = delta.getX();
      double delta_y = delta.getY();
      Point newPoint = new Point((int)(pos_x+delta_x), (int)(pos_y+delta_y));
      this.position = newPoint;
   }**/
   /**public boolean equals(Object o){
      if(o == null){
         return false;
      }
      if(o.getClass() != this.getClass()){
         return false;
      }
      if(this.getWidth() != ((Rectangle)o).getWidth()){
         return false;
      }
      if(this.getHeight() != ((Rectangle)o).getHeight()){
         return false;
      }
      if(!this.getColor().equals(((Rectangle)o).getColor())){
         return false;
      }
      if(!this.getPosition().equals(((Rectangle)o).getPosition())){
         return false;
      }
      if(this.getFilled() != ((Rectangle)o).getFilled()){
         return false;
      }
         return true;
      
   }**/
}