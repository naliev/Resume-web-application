package com.basejava.webapp;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

public class MainStream {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{8, 2, 2, 3, 9}));
    }

    public static int minValue(int[] values) {
        AtomicInteger i = new AtomicInteger(10);
        IntBinaryOperator multiplier = (a, b) -> {
            a = a + b * i.get();
            i.updateAndGet(v -> v * 10);
            return a;
        };

        OptionalInt result = Arrays.stream(values).
                distinct().
                map(value -> -value).
                sorted().
                map(value -> -value).
                reduce(multiplier);

        if (result.isPresent()) {
            return result.getAsInt();
        } else {
            return 0;
        }
    }
}


