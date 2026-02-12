package org.interview.questions;

import java.util.ArrayList;

public class PrimeFactorsOfNumber {
    public static ArrayList<Integer> primeFactor(int n) {
        ArrayList<Integer> ans = new ArrayList<>();

        // Loop from 2 to n
        for (int i = 2; i <= n; i++) {

            // n % i == 0 means n is divisible by i
            while (n % i == 0 && n > 0) {
                ans.add(i);

                // divide n by i to remove this factor
                n = n / i;
            }
        }
        return ans;
    }

    public static void main (String[] args) {
        int n = 18;
        ArrayList<Integer> ans = primeFactor(n);

        for (int x : ans) {
            System.out.print(x + " ");
        }
    }
}
