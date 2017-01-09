/* Zach Scott
 * hw 5
 * section 17 */


#include "commandline.h"

int scan_input_file(char *input, struct sphere spheres[]){
  char myFile[110000];

  FILE *f = fopen(input, "r");
  char *a;
  int i = 0;
  while((a = fgets(myFile, 110000, f)) != 0){
  
     double s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11;
     sscanf(myFile, "%lf %lf %lf %lf %lf %lf %lf %lf %lf %lf %lf", &s1, &s2, &s3, &s4, &s5, &s6, &s7, &s8, &s9, &s10, &s11);

     struct point p1 = create_point(s1, s2, s3);
     struct color c1 = create_color(s5, s6, s7);
     struct finish f1 = create_finish(s8, s9, s10, s11);
     spheres[i] = create_sphere(p1, s4, c1, f1);

     i++;
  }
  fclose(f);
  return i;
}




