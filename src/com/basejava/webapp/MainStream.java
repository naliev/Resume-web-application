package com.basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        System.out.println("Min value result: " + minValue(new int[]{8, 2, 2, 3, 9}));
        System.out.println("Odd or even result: " + oddOrEven(new ArrayList<>(Arrays.asList(1, 4, 6, 8, 9, 1))));
    }

    public static int minValue(int[] values) {
        System.out.println(Arrays.toString(values));

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

    public static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.println(integers);

        boolean odd = (integers.stream().mapToInt(Integer::intValue).sum() % 2 == 0);

        return integers.stream().
                filter(value -> value % 2 == 0 != odd).
                collect(Collectors.toList());
    }
}


