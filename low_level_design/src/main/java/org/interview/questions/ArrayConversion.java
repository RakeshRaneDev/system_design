package org.interview.questions;

import java.util.Arrays;
import java.util.List;

public class ArrayConversion {

    public static void main(String[] args) {
        int [] primes = {2,7,13,19};
        // array to list
        List<Integer> list = Arrays.stream(primes)
                .boxed()
                .toList();
    }
}
