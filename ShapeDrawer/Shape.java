/**
Zach Scott
cpe102 hw3 **/
import java.awt.Point;
import java.awt.Color;
public abstract class Shape implements Comparable<Shape>{
   private Color color;
   private boolean filled;
   public Shape(Color color, boolean filled){
      this.color=color;
      this.filled=filled;
   }
   /**
    * Returns the area of a shape.
    * @return returns a double that represents the area of a shape.
    */
   public abstract double getArea();
   /**
    * Returns the color of a shape.
    * @return returns a color.
    */
   public Color getColor(){
      return color;
   }
   /**
    * Sets the color of a shape to the specified color.
    * @param color The color to make the shape.
    */
   public void setColor(Color color){
      this.color = color;
   }
   /**
    * Determines whether the shape is filled with color.
    * @return Boolean that is true when shape is filled with color.
    */
   public boolean getFilled(){
      return filled;
   }
   /**
    * Sets the filled value of a shape. 
    * @param filled Specifies whether the shape is to be filled or not.
    */
   public void setFilled(boolean filled){
      this.filled = filled;
   }
   /**
    * Returns the position of a shape.
    * @return Returns a point that represents a shapes position.
    */
   public abstract Point getPosition();
   /**
    * Sets the shapes position to a new value.
    * @param position Specifies where to move the shape.
    */
   public abstract void setPosition(Point position);
   /**
    * Moves the shape by the specified values.
    * @param delta Specifies how far to move the point in the x and y directions.
    */
   public abstract void move(Point delta);
   
   public int compareTo(Shape s){
      String classNameThis = this.getClass().getName();
      String classNameS = s.getClass().getName();
      int name = classNameThis.compareTo(classNameS);
      if(name<0){
         return -1;
      }else if(name>0){
         return 1;
      }else{
         if(this.getArea() < s.getArea()){
            return -2;
         }else if(this.getArea() > s.getArea()){
            return 2;
         }else{
            return 0;
         }
      }
   }
   public boolean equals(Object o){
      if(o == null){
         return false;
      }
      if(this.getClass() != o.getClass()){
         return false;
      }
      if(!this.color.equals(((Shape)o).color)){
         return false;
      }
      if(filled != ((Shape)o).filled){
         return false;
      }
      return true;
   }
}