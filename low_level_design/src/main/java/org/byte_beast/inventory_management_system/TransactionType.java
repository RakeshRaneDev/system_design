package org.byte_beast.inventory_management_system;

import java.util.Arrays;
import java.util.Comparator;

public enum TransactionType {
    STOCK_IN,
    STOCK_OUT,
    ADJUSTMENT,
    TRANSACTION_IN,
    TRANSACTION_OUT,
    DAMAGE,
    RETURN,
    EXPIRY;



        public static void main(String[] args) {
            Integer[] arr1 = {0, 1, 2, 3};
            int[] arr2 = {20, 10, 40, 30};

            // Step 1: create index array [0, 1, 2, 3]
            Arrays.sort(arr1, Comparator.comparingInt(i -> arr2[i]));
            System.out.println(Arrays.toString(arr1));


        }




}
