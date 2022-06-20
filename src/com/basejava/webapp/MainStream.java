package com.basejava.webapp;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.ListStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {

    public static void main(String[] args) {
        System.out.println("Min value result: " + minValue(new int[]{8, 2, 2, 3, 9}));
        System.out.println("Odd or even result: " + oddOrEven(new ArrayList<>(Arrays.asList(1, 4, 6, 8, 9, 1))));
        System.out.println("Sorted odd number: " + oddSorted(new ArrayList<>(Arrays.asList(1, 9, 8, 5, 1, 3, 6, 6))));
        System.out.println();

        System.out.println("Is sum of digits in number 1034 is odd: " + isDigitsSumIsOdd(1034));

        Path path = Config.getConfig().getStorageDir().toPath();
        long size;
        try {
            size = Files.size(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Biggest file in the directory: " + path + " is " + biggestFile(path) + " (" + size + ")\n");

        System.out.println("Delete all words from string that longer than 5 letters \n" +
                "One two three four five, bigger stronger, harder = " +
                deleteAllWordsLongerThen5Symbols("One two three four five, bigger stronger, harder") + "\n");

        System.out.println("Take form xml file strings at " + "version" + "tag : \n" +
                Arrays.toString(takeFromXML("version")) + "\n");

        ListStorage storage = new ListStorage();
        storage.save(ResumeTestData.newResumeWithSectionsNaliev("001", "Nikita Aliev"));
        storage.save(ResumeTestData.newResumeWithSectionsPavlov("002", "Sergey Pavlov"));
        storage.save(ResumeTestData.newResumeWithSectionsAvdeeva("003", "Irina Avdeeva"));

        storage.stream().forEach(System.out::println);

        System.out.println("Longest name: " + longestName(storage));

        System.out.println("Resumes with less than 5 qualifications " + lessThenFiveQualificationsOnly(storage).
                stream().map(Resume::toString).collect(Collectors.joining(" ")));

        System.out.println("First three organizations, ordered by name: " +
                Arrays.toString(firstThreeOrganizationsFromStorage(storage)));

        System.out.println("Full names of resume owners that starts with letters: A B C D F" +
                Arrays.toString(fullNamesWhichStartsWithLetter(storage, "A", "B", "C", "D", "F")));

    }

    private static boolean isDigitsSumIsOdd(int number) {
        OptionalInt sum = String.valueOf(number).chars().reduce(Integer::sum);
        if (sum.isPresent()) {
            return sum.getAsInt() % 2 == 0;
        } else {
            return false;
        }
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

    private static String deleteAllWordsLongerThen5Symbols(String s) {
        return Arrays.stream(s.split(" ")).
                filter(string -> string.length() <= 5).
                collect(Collectors.joining(" "));
    }

    private static ListStorage lessThenFiveQualificationsOnly(ListStorage storage) {
        List<Resume> list = storage.stream().
                filter(resume -> ((ListSection) resume.getSection(SectionType.QUALIFICATIONS)).getList().size() < 5).
                sorted().collect(Collectors.toList());
        ListStorage newList = new ListStorage();
        list.forEach(newList::save);
        return newList;
    }

    private static String[] takeFromXML(String tag) {
        String[] xmlStrings = {
                "<dependency>",
                "<groupId>junit</groupId>", "<artifactId>junit</artifactId>",
                "<version>4.4</version>", "<scope>test</scope>",
                "</dependency>",
                "<dependency>",
                "<groupId>org.powermock</groupId>", "<artifactId>powermock-reflect</artifactId>",
                "<version>3.2</version>",
                "</dependency>"};
        return Arrays.stream(xmlStrings).filter(s -> s.startsWith("<" + tag + ">")).
                map(s -> s.replaceAll("[<>/" + tag + "]", "")).toArray(String[]::new);

    }

    private static String[] firstThreeOrganizationsFromStorage(ListStorage storage) {
        return storage.stream().flatMap(resume -> resume.getSection(SectionType.EDUCATION)
                        == null ? Stream.empty() : ((OrganizationSection) resume.getSection(SectionType.EDUCATION)).
                        getOrganizations().stream().map(Organization::getTitle)).distinct().sorted(
                        String::compareToIgnoreCase)
                .limit(3).toArray(String[]::new);
    }

    private static String[] fullNamesWhichStartsWithLetter(ListStorage storage, String... letters) {
        return storage.stream().flatMap((Function<Resume, Stream<String>>) resume ->
                Arrays.stream(resume.getFullName().split(" "))).filter(s -> {
            for (String letter : letters) {
                if (s.startsWith(letter)) return true;
            }
            return false;
        }).toArray(String[]::new);
    }
}


