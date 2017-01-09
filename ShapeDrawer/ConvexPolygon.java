/** Zach Scott
cpe102 hw3 **/

import java.awt.Color;
import java.awt.Point;
public class ConvexPolygon extends Shape{
   private Point[] vertices;
   public ConvexPolygon(Point[] vertices, Color color, boolean filled){
      super(color,filled);
      Point[] vert = new Point[vertices.length];
      for(int i=0; i<vertices.length;i++){
         vert[i] = new Point(vertices[i].x, vertices[i].y);
      }
      this.vertices = vert; 
     }
   public Point getVertex(int index){
      return new Point(vertices[index]);

   }
   public void setVertex(int index, Point vertex){
      Point vert = new Point(vertex.x,vertex.y);
      vertices[index] = vert;
   }
   public double getArea(){
      double value1=0;
      double value2=0;
      int size = vertices.length;
      int i;
      for(i=0;i<size;i++){
         if(i != size-1){
            value1=value1+ (vertices[i].getX()*vertices[i+1].getY());
            value2=value2+ (vertices[i].getY()*vertices[i+1].getX());
         }else{
            value1=value1+ (vertices[i].getX()*vertices[0].getY());
            value2=value2+ (vertices[i].getY()*vertices[0].getX());
         }
      }
      double area = (value1-value2)/2;
      return area;
   }

   public Point getPosition(){
      return new Point(vertices[0]);
   }
   public void move(Point delta){
      int i;
      int size = vertices.length;
      for(i=0;i<size;i++){   
         int dX = (int)(vertices[i].getX() + delta.getX());
         int dY = (int)(vertices[i].getY() + delta.getY());
         vertices[i] = new Point(dX, dY);
      }
   }
   public void setPosition(Point position){
      int deltaX = position.x - vertices[0].x;
      int deltaY = position.y - vertices[0].y;
      this.move(new Point(deltaX,deltaY));
   }
   public boolean equals(Object o){
      if(o == null){
         return false;
      }
      if(o.getClass() != this.getClass()){
         return false;
      }
      if(vertices.length != ((ConvexPolygon)o).vertices.length){
         return false;
      }
      int i;
      for(i=0;i<vertices.length;i++){
         if(!vertices[i].equals(((ConvexPolygon)o).vertices[i])){
            return false;
         }
      }
      if(!this.getColor().equals(((ConvexPolygon)o).getColor())){
         return false;
      }
      if(this.getFilled() != ((ConvexPolygon)o).getFilled()){
         return false;
      }
         return true;
      
   }
}

  