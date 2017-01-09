/* Zach Scott */

#include "data.h"

struct point create_point(double x, double y, double z)
{
   struct point myPoint;

   myPoint.x = x;
   myPoint.y = y;
   myPoint.z = z;

   return myPoint;
}

struct vector create_vector(double x, double y, double z)
{
   struct vector myVector;

   myVector.x = x;
   myVector.y = y;
   myVector.z = z;

   return myVector;
}

struct ray create_ray(struct point p, struct vector dir)
{
   struct ray myRay;

   myRay.p = p;
   myRay.dir = dir;

   return myRay;
}

struct color create_color(double r, double g, double b){
   struct color myColor;

   myColor.r = r;
   myColor.g = g;
   myColor.b = b;
   return myColor;
}

struct finish create_finish(double ambient, double diffuse, double specular, double roughness){
   struct finish myFinish;

   myFinish.ambient = ambient;
   myFinish.diffuse = diffuse;
   myFinish.specular = specular;
   myFinish.roughness = roughness;

   return myFinish;
}

struct light create_light(struct point p, struct color color){
   struct light myLight;

   myLight.p = p;
   myLight.color = color;

   return myLight;
}

struct sphere create_sphere(struct point center, double radius, struct color color, struct finish finish)
{
   struct sphere mySphere;

   mySphere.center = center;
   mySphere.radius = radius;
   mySphere.color = color;
   mySphere.finish = finish;


   return mySphere;
}

