/* Zach Scott and Martynas Budvytis
 * ztscott, mbudvyti */

import java.util.*;
import java.io.*;

public class ChangeMaker {

   public static int[] change_DP(int n, int[] d) {
      int[] c = new int[n];
      int[] a = new int[n];
      int[] r = new int[d.length];

      for (int j = 0; j < n; j++) {
         if (j == 0) {
            c[j] = 0;
            a[j] = d.length - 1;
         }
         else {
            int min = Integer.MAX_VALUE;
            int mindex = Integer.MAX_VALUE;

            for (int i = 0; i < d.length; i++) {
               if (j >= d[i]) {
                  int value = c[j-d[i]];
                  System.out.println("value: " + value + " min: " + min);
                  if (value < min) {
                     System.out.println("in the if");
                     min = value;
                     mindex = i;
                  }
               }
               System.out.println("min: " + min);
            }
            c[j] = min + 1;
            a[j] = mindex;
         }
      }
      
      int j = n;
      while (j > 0) {
         r[a[j-1]]++;
         j -= d[a[j - 1]];
      }

      
      return r;
   }

   public static void main(String[] args) {
      System.out.println("Enter the number of coin-denominations and the set of coin values:");

      Scanner scan = new Scanner(System.in);

      int k = scan.nextInt();
      int[] d = new int[k];
      boolean done = false;


      for (int i = 0; i < k; i++) {
         d[i] = scan.nextInt();
      }

      while (!done) {
         System.out.println("Enter a positive amount to be changed(enter 0 to quit):");

         int n = scan.nextInt();
         if (n <= 0) {
            done = true;
         }
         else {
            int[] r = change_DP(n, d);
            int coins = 0;
            System.out.println("r  " + Arrays.toString(r));
            System.out.println("d  " + Arrays.toString(d));
            System.out.print("Optimal distribution: ");
            for (int j = 0; j < r.length; j++) {
               if (r[j] != 0) {
                  coins += r[j];
                  System.out.print(r[j] + "*" + d[j] + "c");
                  if (j != r.length - 1) {
                     System.out.print(" + ");
                  }
               }
            }
            System.out.println();
            System.out.println("Optimal coin count: " + coins);
         }

      }
   }
   }

