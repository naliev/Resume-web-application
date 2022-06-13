package com.basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        System.out.println("Min value result: " + minValue(new int[]{8, 2, 2, 3, 9}));
        System.out.println("Odd or even result: " + oddOrEven(new ArrayList<>(Arrays.asList(1, 4, 6, 8, 9, 1))));
    }

    public static int minValue(int[] values) {
        System.out.println(Arrays.toString(values));

        return Arrays.stream(values).
                distinct().
                sorted().
                reduce(0, (a, b) -> a * 10 + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.println(integers);

        Map<Boolean, List<Integer>> collect = integers.stream().
                collect(Collectors.partitioningBy((value) -> value % 2 == 0));
        return collect.get(collect.get(false).size() % 2 != 0);
    }
}


