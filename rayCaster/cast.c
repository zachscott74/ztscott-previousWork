/* Zach Scott
 * hw 4
 * section 17 */

#include "cast.h"
#include "data.h"
#include <stdio.h>
#include <math.h>

int find_smallest(double lengths[], int num_spheres){
   int index = 0;
   int mindex = 0;
   double minimum = lengths[0];
   for(index = 0; index < num_spheres; index++){
      if(lengths[index] < minimum){
         minimum = lengths[index];
         mindex = index;
      }

   }
   return mindex;
}

struct color cast_ray(struct ray r, struct sphere spheres[], int num_spheres, struct color ambient, struct light light, struct point eye){
   struct sphere hit_spheres[num_spheres];
   struct point intersection_points[num_spheres];
   double lengths[num_spheres];

   int ip;
   ip = find_intersection_points(spheres, num_spheres, r, hit_spheres, intersection_points);


   int index;
   for(index = 0; index < ip; index++){
      double len = length_vector(difference_point(r.p, intersection_points[index]));
      lengths[index] = len;

   }


   if(ip == 0){

      struct color c = create_color(1.0, 1.0, 1.0);
      return c;
   }else{

      int sm = find_smallest(lengths, ip);

      struct vector norm = sphere_normal_at_point(hit_spheres[sm], intersection_points[sm]);
      struct vector norm_scale = create_vector(norm.x * .01, norm.y * .01, norm.z * .01);

      norm = normalize_vector(norm_scale);
      struct point pz = translate_point(intersection_points[sm], norm_scale);

      struct vector ptl = normalize_vector(vector_from_to(pz, light.p));

      double is_light = dot_vector(norm, ptl);

      struct ray light_ray = create_ray(intersection_points[sm], ptl);
      struct sphere hit_spheres_light[num_spheres];
      struct point intersection_points_light[num_spheres];

      double n = 2 * is_light;
      struct vector r1 = scale_vector(norm, n); 
      struct vector reflect = difference_vector(ptl, r1);

      struct vector view_dir = normalize_vector(vector_from_to(eye, intersection_points[sm]));

      double spec_intense = dot_vector(reflect, view_dir);
      

      int inter_light = find_intersection_points(spheres, num_spheres, light_ray, hit_spheres_light, intersection_points_light);

   /*int index2;
   double lengths_light[num_spheres];
   for(index2 = 0; index2 < inter_light; index2++){
      double len_light = length_vector(difference_point(light_ray.p, intersection_points_light[index2]));
      lengths_light[index2] = len_light;

   }
   int sm_light = find_smallest(lengths_light, inter_light);
   if(lengths_light[sm_light] > length_vector(ptl)){
      inter_light = 0;
   }*/

      double r_difcol;
      double g_difcol;
      double b_difcol;
      double r_spec_col;
      double g_spec_col;
      double b_spec_col;

      if(is_light <=  0 || inter_light >  0){
         r_difcol = 0;
         g_difcol = 0;
         b_difcol = 0;
      }else{
         r_difcol = (is_light * light.color.r * hit_spheres[sm].color.r * hit_spheres[sm].finish.diffuse);
         g_difcol = (is_light * light.color.g * hit_spheres[sm].color.g * hit_spheres[sm].finish.diffuse);
         b_difcol = (is_light * light.color.b * hit_spheres[sm].color.b * hit_spheres[sm].finish.diffuse);
      }
      if(spec_intense > 0){
         r_spec_col = light.color.r * hit_spheres[sm].finish.specular * pow(spec_intense, 1/hit_spheres[sm].finish.roughness);
         g_spec_col = light.color.g * hit_spheres[sm].finish.specular * pow(spec_intense, 1/hit_spheres[sm].finish.roughness);
         b_spec_col = light.color.b * hit_spheres[sm].finish.specular * pow(spec_intense, 1/hit_spheres[sm].finish.roughness);
      }else{
         r_spec_col = 0;
         g_spec_col = 0;
         b_spec_col = 0;
      }
      double rcol = (hit_spheres[sm].color.r * hit_spheres[sm].finish.ambient * ambient.r) + r_difcol + r_spec_col;
      double gcol = (hit_spheres[sm].color.g * hit_spheres[sm].finish.ambient * ambient.g) + g_difcol + g_spec_col;
      double bcol = (hit_spheres[sm].color.b * hit_spheres[sm].finish.ambient * ambient.b) + b_difcol + b_spec_col;

      struct color col;
      col = create_color(rcol, gcol, bcol);
      return col;

   }

}


void cast_all_rays(double min_x, double max_x, double min_y, double max_y, int width, int height, struct point eye, struct sphere spheres[], int num_spheres, struct color ambient, struct light light){

   double xint = ((max_x - min_x) / (double) width);
   double yint = ((max_y - min_y) / (double) height);
   double current_x;
   double current_y;

   FILE * f = fopen("image.ppm", "w");
   fprintf(f, "P3\n");
   fprintf(f, "%d %d\n", width, height);
   fprintf(f, "%d\n", 255);

   for(current_y = max_y; current_y > min_y; (current_y = current_y - yint)){
      for(current_x = min_x; current_x < max_x; (current_x = current_x + xint)){

         struct point p1;
         p1 = create_point(current_x, current_y, 0);
         struct vector dir;
         dir = vector_from_to(eye, p1);
         struct ray r = create_ray(eye, dir);
         struct color col = cast_ray(r, spheres, num_spheres, ambient, light, eye);


         fprintf(f, "%d %d %d\n", (int)(col.r * 255), (int)(col.g * 255), (int)(col.b * 255));
         
      }
   }
   fclose(f);
}



