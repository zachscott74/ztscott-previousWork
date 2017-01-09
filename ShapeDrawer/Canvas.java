/** Zach Scott
cpe102 hw3 **/

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
public class Canvas{
   private ArrayList<Shape> shapes;
   public Canvas(){
      shapes = new ArrayList<Shape>();
   }
   public void add(Shape shape){
      if(shapes != null){
      shapes.add(shape);
      }
   }
   public Shape remove(int index){
      Shape removed = shapes.remove(index);
      return removed;
   }
   public Shape get(int index){
      Shape s = shapes.get(index);
      return s;
   }
   public int size(){
      if(shapes != null){
      return shapes.size();
      }else{
      
      return 0;
      }
   }
   public ArrayList<Circle> getCircles(){
      ArrayList<Circle> circles = new ArrayList<Circle>();
      int i;
      if(shapes != null){
      int size = shapes.size();
      for(i=0;i<size;i++){
         if(shapes.get(i).getClass() == Circle.class){
            circles.add(((Circle)shapes.get(i)));
         }
      }
      }
      return circles;
   }
   public ArrayList<Rectangle> getRectangles(){
      ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
      int i;
      if(shapes != null){
      int size = shapes.size();
      for(i=0;i<size;i++){
         if(shapes.get(i).getClass() == Rectangle.class){
            rectangles.add(((Rectangle)shapes.get(i)));
         }
      }
      }
      return rectangles;
   }
   public ArrayList<Triangle> getTriangles(){
      ArrayList<Triangle> triangles = new ArrayList<Triangle>();
      int i;
      if(shapes != null){
      int size = shapes.size();
      for(i=0;i<size;i++){
         if(shapes.get(i) instanceof Triangle){
            triangles.add(((Triangle)shapes.get(i)));
         }
      }
      }
      return triangles;
   }
   public ArrayList<ConvexPolygon> getConvexPolygons(){
      ArrayList<ConvexPolygon> convexes = new ArrayList<ConvexPolygon>();
      int i;
      if(shapes != null){
      int size = shapes.size();
      for(i=0;i<size;i++){
         if(shapes.get(i).getClass() == ConvexPolygon.class){
            convexes.add(((ConvexPolygon)shapes.get(i)));
         }
      }
      }
      return convexes;
   }
   public ArrayList<Shape> getShapesByColor(Color color){
      ArrayList<Shape> colored = new ArrayList<Shape>();
      int i;

      int size = shapes.size();
      for(i=0;i<size;i++){
         if(shapes.get(i).getColor().equals(color)){
            colored.add(shapes.get(i));
         }

      }
      return colored;
   }
   public double getAreaOfAllShapes(){
      double sum = 0;
      int i;
      if(shapes != null){
      int size = shapes.size();
      for(i=0;i<size;i++){
         sum = sum + shapes.get(i).getArea();
      }
      }
      return sum;
   }

}