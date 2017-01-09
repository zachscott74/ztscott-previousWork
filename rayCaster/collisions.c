/* Zach Scott
 * Program 3 */

#include "collisions.h"
#include <math.h>


struct maybe_point create_maybe_point(int isPoint, struct point p){

   struct maybe_point myPoint;
   myPoint.isPoint = isPoint;
   myPoint.p.x = p.x;
   myPoint.p.y = p.y;
   myPoint.p.z = p.z;

   return myPoint;

}

struct maybe_point sphere_intersection_point(struct ray r, struct sphere s){

   double a = dot_vector(r.dir, r.dir);
   double b = (2 * dot_vector(difference_point(r.p, s.center), r.dir));
   double c = (dot_vector(difference_point(r.p, s.center), difference_point(r.p, s.center)) - (s.radius * s.radius));

   double root1 = ((-b + sqrt(((b * b) - (4 * a * c))))/(2 * a));

   double root2 = ((-b - sqrt(((b * b) - (4 * a * c))))/(2 * a));

   if(root1 > 0 && root2 > 0){
      if(root1 > root2){
         struct point p1 = create_point(r.p.x + (root2 * r.dir.x), r.p.y + (root2 * r.dir.y), r.p.z + (root2 * r.dir.z));
         return create_maybe_point(1, p1);
      }else{
         struct point p2 = create_point(r.p.x + (root1 * r.dir.x), r.p.y + (root1 * r.dir.y), r.p.z + (root1 * r.dir.z));

         return create_maybe_point(1, p2);
      }

      if(root1 > 0 && root2 < 0){
         struct point p3 = create_point(r.p.x + (root1 * r.dir.x), r.p.y + (root1 * r.dir.y), r.p.z + (root1 * r.dir.z));

         return create_maybe_point(1, p3);
      }

      if(root1 < 0 && root2 > 0){
         struct point p4 = create_point(r.p.x + (root2 * r.dir.x), r.p.y + (root2 * r.dir.y), r.p.z + (root2 * r.dir.z));

         return create_maybe_point(1, p4);
      }

      if(root1 < 0 && root2 < 0){
         return create_maybe_point(0, create_point(0, 0, 0));
      }
   } else{
      return create_maybe_point(0, create_point(0, 0, 0));

   }
}


int find_intersection_points(struct sphere spheres[], int num_spheres, struct ray r, struct sphere hit_spheres[], struct point intersection_points[]){

   int spheres_index = 0;
   int hit_index = 0;
   int intersection_index = 0;

   for(spheres_index=0; spheres_index < num_spheres; spheres_index++){
      struct maybe_point mp;
      mp = sphere_intersection_point(r, spheres[spheres_index]);
      if(mp.isPoint == 1){
         hit_spheres[hit_index] = spheres[spheres_index];
         hit_index = hit_index + 1;
         intersection_points[intersection_index] = mp.p;
         intersection_index = intersection_index + 1;
      }
   }

   return intersection_index;

}


   struct vector sphere_normal_at_point(struct sphere s, struct point p){
      struct vector v;
      v = vector_from_to(s.center, p);
      normalize_vector(v);
      return v;
   }


   
   
