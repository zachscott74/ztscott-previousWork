/** Zach Scott
cpe102 hw3 **/

import java.awt.Point;
import java.awt.Color;
public class Triangle extends ConvexPolygon{

   public Triangle(Point a, Point b, Point c, Color color, boolean filled){
      super(new Point[]{a,b,c},color,filled);
   }
   public Point getVertexA(){
      Point a = new Point(getVertex(0).x,getVertex(0).y);
      return a;
   }
   public void setVertexA(Point point){
      setVertex(0,point);
   }
   public Point getVertexB(){
      Point b = new Point(getVertex(1).x,getVertex(1).y);
      return b;
   }
   public void setVertexB(Point point){
      setVertex(1,point);
   }
   public Point getVertexC(){
      Point c = new Point(getVertex(2).x,getVertex(2).y);
      return c;
   }
   public void setVertexC(Point point){
      setVertex(2,point);
   }


   public double getArea(){
      Point a = this.getVertex(0);
      Point b = this.getVertex(1);
      Point c = this.getVertex(2);
      double length1 = Math.sqrt(((a.getX()-b.getX())*(a.getX()-b.getX())) + 
      ((a.getY()-b.getY())*(a.getY()-b.getY())));
      double length2 = Math.sqrt(((a.getX()-c.getX())*(a.getX()-c.getX())) + 
      ((a.getY()-c.getY())*(a.getY()-c.getY())));
      double length3 = Math.sqrt(((c.getX()-b.getX())*(c.getX()-b.getX())) + 
      ((c.getY()-b.getY())*(c.getY()-b.getY())));
      double semi = (length1+length2+length3)/2;
      double area = Math.sqrt(semi*(semi-length1)*(semi-length2)*(semi-length3));
      return area;
   }
      /**
   public void move(Point delta){
      int aX = (int)(a.getX() + delta.getX());
      int aY = (int)(a.getY() + delta.getY());
      a = new Point(aX, aY);
      int bX = (int)(b.getX() + delta.getX());
      int bY = (int)(b.getY() + delta.getY());
      b = new Point(bX, bY);
      int cX = (int)(c.getX() + delta.getX());
      int cY = (int)(c.getY() + delta.getY());
      c = new Point(cX, cY);
   }
   public void setPosition(Point position){
      int deltaX = (int)(position.getX() - a.getX());
      int deltaY = (int)(position.getY() - a.getY());
      this.move(new Point(deltaX,deltaY));
   }**/
   /**public boolean equals(Object o){
      if(o==null){
         return false;
      }
      if(this.getClass()!= o.getClass()){
         return false;
      }
      Point a = this.getVertex(0);
      Point b = this.getVertex(1);
      Point c = this.getVertex(2);
      if(!a.equals(((Triangle)o).getVertex(0))){
         return false;
      }
      if(!b.equals(((Triangle)o).getVertex(1))){
         return false;
      }
      if(!c.equals(((Triangle)o).getVertex(2))){
         return false;
      }
      if(!this.getColor().equals(((Triangle)o).getColor())){
         return false;
      }
      if(this.getFilled() != ((Triangle)o).getFilled()){
         return false;
      }  
         return true;
      }**/
   }

