/* Zach Scott */

    #ifndef VECTOR_MATH_H
    #define VECTOR_MATH_H

   #include "data.h"

   struct vector scale_vector(struct vector v, double scalar);

   double dot_vector(struct vector v1, struct vector v2);

   double length_vector(struct vector v);

   struct vector normalize_vector(struct vector v);

   struct vector difference_point(struct point p1, struct point p2);

   struct vector difference_vector(struct vector v1, struct vector v2);

   struct point translate_point(struct point p, struct vector v);

   struct vector vector_from_to(struct point from, struct point to);

   #endif

