package com.basejava.webapp;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.ListStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {

    public static void main(String[] args) {
        System.out.println("Min value result: " + minValue(new int[]{8, 2, 2, 3, 9}));
        System.out.println("Odd or even result: " + oddOrEven(new ArrayList<>(Arrays.asList(1, 4, 6, 8, 9, 1))));
        System.out.println("Sorted odd number: " + oddSorted(new ArrayList<>(Arrays.asList(1, 9, 8, 5, 1, 3, 6, 6))));
        System.out.println();

        Path path = Paths.get("/home/naliev/IdeaProjects/BaseJava/storage");
        long size;
        try {
            size = Files.size(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Biggest file in the directory: " + path + " is " + biggestFile(path) + " (" + size + ")");


        ListStorage storage = new ListStorage();
        storage.save(ResumeTestData.newResumeWithSectionsNaliev("001", "Nikita Aliev"));
        storage.save(ResumeTestData.newResumeWithSectionsNaliev("002", "Sergey Pavlov"));
        storage.save(ResumeTestData.newResumeWithSectionsNaliev("003", "Irina Avdeeva"));

        storage.stream().forEach(System.out::println);

        System.out.println("Longest name: " + longestName(storage));

    }

    private static int minValue(int[] values) {
        System.out.println(Arrays.toString(values));

        return Arrays.stream(values).
                distinct().
                sorted().
                reduce(0, (a, b) -> a * 10 + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.println(integers);

        Map<Boolean, List<Integer>> collect = integers.stream().
                collect(Collectors.partitioningBy((value) -> value % 2 == 0));
        return collect.get(collect.get(false).size() % 2 != 0);
    }

    private static String longestName(ListStorage storage) {

        Optional<String> max = storage.stream().
                map(Resume::getFullName).
                max(Comparator.comparingInt(String::length));

        if (max.isPresent()) {
            return max.get();
        } else {
            throw new StorageException("Can't find longest name is storage");
        }
        /*
        return storage.stream().
         max(Comparator.comparingInt(o -> o.getFullName().length())).
          get().getFullName();

        */
    }

    private static List<Integer> oddSorted(List<Integer> integers) {
        System.out.println(integers);

        return integers.stream().
                filter((value -> value % 2 == 0)).
                sorted().
                collect(Collectors.toList());
    }

    private static Path biggestFile(Path directory) {
        try (Stream<Path> stream = Files.list(directory)) {
            return stream.max(Comparator.comparingLong((Path o) -> {
                try {
                    return Files.size(o);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })).orElseThrow((Supplier<Throwable>) IllegalStateException::new);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}


