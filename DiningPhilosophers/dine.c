#include <errno.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>
#include <limits.h>

#define DEFAULT_STRING " -----       "
#define STRING_STATE 7
#define  NUM_PHILOSOPHERS 5

pthread_mutex_t forks[NUM_PHILOSOPHERS];
pthread_mutex_t printLock;
int cycles = 1;
char *printStrings[NUM_PHILOSOPHERS];


void dawdle() {
   struct timespec tv;
   int msec = (int)(((double)random() / INT_MAX) * 1000);

   tv.tv_sec = 0;
   tv.tv_nsec = 1000000 * msec;
   if (-1 == nanosleep(&tv,NULL)) {
      perror("nanosleep");
   }
}

void printAll() {
   printf("|");
   printf("%s", printStrings[0]);
   printf("|");
   printf("%s", printStrings[1]);
   printf("|");
   printf("%s", printStrings[2]);
   printf("|");
   printf("%s", printStrings[3]);
   printf("|");
   printf("%s", printStrings[4]);
   printf("|\n");
   pthread_mutex_unlock(&printLock);

}

void *philosopher(void *id) {
   char numb = *(char *)id;
   int number = (numb - 'A');
   //double position = number + .5;
   int odd = (number % 2);
   int currCycles = 0;
   int state = 0; /* can be 0, 1, 2 (changing, eating, thinking) */
   int hungry = 1;
   int myForks[2];
   char *oneChar = malloc(2);

   myForks[0] = number;
   myForks[1] = number + 1;
   if (myForks[1] == NUM_PHILOSOPHERS) {
      myForks[1] = 0;
   }
   while (currCycles < cycles) {
      if (hungry) {
         if (odd) {
            pthread_mutex_lock(&forks[myForks[0]]);
            pthread_mutex_lock(&printLock);
            sprintf(oneChar, "%d", myForks[0]);
            strncpy(printStrings[number] + myForks[0] + 1, oneChar, 1);
            printAll();

            pthread_mutex_lock(&forks[myForks[1]]);
            pthread_mutex_lock(&printLock);
            sprintf(oneChar, "%d", myForks[1]);
            strncpy(printStrings[number] + myForks[1] + 1, oneChar, 1);
            printAll();

            state = 1; //eating
            pthread_mutex_lock(&printLock);
            strcpy(printStrings[number] + STRING_STATE, "Eat   ");
            printAll();

            dawdle();
            hungry = 0;
            state = 0;
            pthread_mutex_lock(&printLock);
            strcpy(printStrings[number] + STRING_STATE, "      ");
            printAll();
         }
         else {
            pthread_mutex_lock(&forks[myForks[1]]);
            pthread_mutex_lock(&printLock);
            sprintf(oneChar, "%d", myForks[1]);
            strncpy(printStrings[number] + myForks[1] + 1, oneChar, 1);
            printAll();

            pthread_mutex_lock(&forks[myForks[0]]);
            pthread_mutex_lock(&printLock);
            sprintf(oneChar, "%d", myForks[0]);
            strncpy(printStrings[number] + myForks[0] + 1, oneChar, 1);
            printAll();

            state = 1; //eating
            pthread_mutex_lock(&printLock);
            strcpy(printStrings[number] + STRING_STATE, "Eat   ");
            printAll();

            dawdle();
            hungry = 0;
            state = 0;
            pthread_mutex_lock(&printLock);
            strcpy(printStrings[number] + STRING_STATE, "      ");
            printAll();
         }
      }
      else {
         pthread_mutex_lock(&printLock);
         pthread_mutex_unlock(&forks[myForks[0]]);
         strncpy(printStrings[number] + myForks[0] + 1, "-", 1);
         printAll();

         pthread_mutex_lock(&printLock);
         pthread_mutex_unlock(&forks[myForks[1]]);
         strncpy(printStrings[number] + myForks[1] + 1, "-", 1);
         printAll();

         state = 2; //thinking
         pthread_mutex_lock(&printLock);
         strcpy(printStrings[number] + STRING_STATE, "Think ");
         printAll();

         dawdle();
         hungry = 1;
         state = 0;
         pthread_mutex_lock(&printLock);
         strcpy(printStrings[number], DEFAULT_STRING);
         printAll();
         currCycles++;
      }
   }
   return NULL;
}

int main(int argc, char *argv[]) {
   pid_t ppid;
   int i;

   char id[NUM_PHILOSOPHERS];
   pthread_t childid[NUM_PHILOSOPHERS];

   if (argv[1]) {
      cycles = strtol(argv[1], NULL, 10);
   }

   ppid = getpid();
   for (i = 0; i < NUM_PHILOSOPHERS; i++) {
      pthread_mutex_init(&forks[i], NULL);
   }
   pthread_mutex_init(&printLock, NULL);

   for (i = 0; i < NUM_PHILOSOPHERS; i++) {
      id[i] = 'A' + i;
      printStrings[i] = malloc(14);
      strcpy(printStrings[i], DEFAULT_STRING);
   }
   for (i = 0; i < 5; i++) {
      printf("|=============");
   }
   printf("|\n");

   printf("|      A      |      B      |      C      |      D      ");
   printf("|      E      |\n");

   for (i = 0; i < 5; i++) {
      printf("|=============");
   }
   printf("|\n");
   printAll();

   for (i = 0; i < NUM_PHILOSOPHERS; i++) {
      int res;
      res = pthread_create( &childid[i], NULL,
            philosopher, (void *)(&id[i]));

      if (-1 == res) {
         fprintf(stderr, "Child %i: %s\n", i, strerror(errno));
         exit(-1);
      }
   }

   for (i = 0; i < NUM_PHILOSOPHERS; i++) {
      pthread_join(childid[i], NULL);
   }
   for (i = 0; i < 5; i++) {
      printf("|=============");
   }
   printf("|\n");

   return 0;
}




