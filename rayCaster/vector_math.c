/* Zach Scott*/

   #include "data.h"
   #include "vector_math.h"
   #include <math.h>


   struct vector scale_vector(struct vector v, double scalar)
{
   struct vector ve;
   ve = create_vector(v.x * scalar, v.y * scalar, v.z * scalar);
   return ve;
}

   double dot_vector(struct vector v1, struct vector v2)
{
   return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
}

   double length_vector(struct vector v)
{
   double v_square_x = v.x * v.x;
   double v_square_y = v.y * v.y;
   double v_square_z = v.z * v.z;

   return sqrt(v_square_x + v_square_y + v_square_z);
}

   struct vector normalize_vector(struct vector v)
{
   struct vector ve;
   ve = create_vector(v.x/length_vector(v), v.y/length_vector(v), v.z/length_vector(v));
   return ve;
}

   struct vector difference_point(struct point p1, struct point p2)
{
   struct vector v;
   v = create_vector(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
   return v;
}

   struct vector difference_vector(struct vector v1, struct vector v2)
{
   struct vector v;
   v = create_vector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
   return v;
}

   struct point translate_point(struct point p, struct vector v)
{
   struct point po;
   po = create_point(p.x + v.x, p.y + v.y, p.z + v.z);
   return po;
}

   struct vector vector_from_to(struct point from, struct point to)
{
   struct vector v;
   v = create_vector(to.x - from.x, to.y - from.y, to.z - from.z);
   return v;
}


