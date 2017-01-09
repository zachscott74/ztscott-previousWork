/* Zach Scott
 * hw 5
 * section 17 */

#include <stdio.h>
#include "checkit.h"
#include "commandline.h"


void test_input(void){

   char *spheres1[10000];
   int i1 = scan_input_file("testfile.c", spheres1);

   checkit_int(i1, 1);
   checkit_string(spheres1, "1 2 3 4 5 6 7 8 9 10 11");

}
int main(int argc, char *argv[]){
   test_input();

   return 0;
}
