/* Zach Scott
 * hw 5
 * section 17 */

#include "commandline.h"

int main(int argc, char *argv[]){
   struct sphere spheres[10000];
   int amount = scan_input_file(argv[1], spheres);

   int if_eye = 0;
   int if_view = 0;
   int if_light = 0;
   int if_ambient = 0;

   int i;

   double eye_x = 0;
   double eye_y = 0;
   double eye_z = -14;

   double min_x = -10;
   double max_x = 10;
   double min_y = -7.5;
   double max_y = 7.5;
   int width = 1024;
   int height = 768;

   double light_x = -100;
   double light_y = 100;
   double light_z = -100;
   double light_r = 1.5;
   double light_g = 1.5;
   double light_b = 1.5;

   double ambient_r = 1.0;
   double ambient_g = 1.0;
   double ambient_b = 1.0;

   for(i = 0; i < argc; i++){
      char *eye = "-eye";
      if_eye = strcmp(eye, argv[i]);
      
      if(if_eye == 0){
         if((i + 2) < argc){
         int is_x = sscanf(argv[i + 1],"%lf", &eye_x);
         int is_y = sscanf(argv[i + 2],"%lf", &eye_y);
         int is_z = sscanf(argv[i + 3],"%lf", &eye_z);
         if(is_x != 1 || is_y != 1 || is_z != 1){
            printf("eye error");
            exit(0);
         }
         }
      }
   
      char *view = "-view";
      if_view = strcmp(view, argv[i]);
      if(if_view == 0){
         int is_min_x = sscanf(argv[i + 1], "%lf", &min_x);
         int is_max_x = sscanf(argv[i + 2], "%lf", &max_x);
         int is_min_y = sscanf(argv[i + 3], "%lf", &min_y);
         int is_max_y = sscanf(argv[i + 4], "%lf", &max_y);
         int is_width = sscanf(argv[i + 5], "%d", &width);
         int is_height = sscanf(argv[i + 6], "%d", &height);
         if(is_min_x != 1 || is_max_x != 1 || is_min_y != 1 || is_max_y != 1|| is_width != 1 || is_height != 1){
            printf("view error");
            exit(0);
         }
      }
   
      char *light = "-light";
      if_light = strcmp(light, argv[i]);
      if(if_light == 0){
         int is_l_x = sscanf(argv[i + 1], "%lf", &light_x);
         int is_l_y = sscanf(argv[i + 2], "%lf", &light_y);
         int is_l_z = sscanf(argv[i + 3], "%lf", &light_z);
         int is_l_r = sscanf(argv[i + 4], "%lf", &light_r);
         int is_l_g = sscanf(argv[i + 5], "%lf", &light_g);
         int is_l_b = sscanf(argv[i + 6], "%lf", &light_b);
         if(is_l_x != 1 || is_l_y != 1 || is_l_z != 1 || is_l_r != 1|| is_l_g != 1 || is_l_b != 1){
         printf("light error");
         exit(0);
         }
      }
   
      char *ambient = "-ambient";
      if_ambient = strcmp(ambient, argv[i]);
      if(if_ambient == 0){
         int is_a_r = sscanf(argv[i + 1], "%lf", &ambient_r);
         int is_a_g = sscanf(argv[i + 2], "%lf", &ambient_g);
         int is_a_b = sscanf(argv[i + 3], "%lf", &ambient_b);
         if(is_a_r != 1 || is_a_g != 1 || is_a_b != 1){
            printf("ambient error");
            exit(0);
         }
      }
   
   }

   struct point eye_point = create_point(eye_x, eye_y, eye_z);
   struct color ambient_color = create_color(ambient_r, ambient_g, ambient_b);
   struct point light_point = create_point(light_x, light_y, light_z);
   struct color light_color = create_color(light_r, light_g, light_b);
   struct light light = create_light(light_point, light_color);

   cast_all_rays(min_x, max_x, min_y, max_y, width, height, eye_point, spheres, amount, ambient_color, light);

   return 0;
}


