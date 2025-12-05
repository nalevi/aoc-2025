package org.example;

import java.util.ArrayList;
import java.util.List;

public class DayFive implements Solution {
    private List<String> exampleRanges = List.of("3-5",
            "10-14",
            "16-20",
            "12-18",
            "56-99",
            "78-110",
            "10000-10001",
            "10000-10700");

    private List<Long> exampleIds = List.of(1L,
            5L,
            8L,
            11L,
            17L,
            32L);

    @Override
    public void solutionOne(String fileName) {
        List<String> input = readInput(fileName);
        List<Range> parsedRanges = new ArrayList<>();
        List<Long> parsedIds = new ArrayList<>();
        int i = 1;
        String line = input.get(0);
        while (i < input.size() && !line.isEmpty()) {
            parsedRanges.add(parseRanges(line));
            line = input.get(i++);
        }

        while (i < input.size()) {
            parsedIds.add(Long.parseLong(input.get(i++)));
        }

        //List <Range> parsedRanges = exampleRanges.stream().map(this::parseRanges).toList();
        long freshIngredients = parsedIds.stream().filter(id -> isFresh(parsedRanges, id)).count();

        System.out.println("Day 5, part I: " + freshIngredients);

    }

    @Override
    public void solutionTwo(String fileName) {
        List<String> input = readInput(fileName);
        List<Range> parsedRanges = new ArrayList<>();
        int l = 1;
        String line = input.getFirst();
        while (l < input.size() && !line.isEmpty()) {
            parsedRanges.add(parseRanges(line));
            line = input.get(l++);
        }
//        List<Range> parsedRanges = exampleRanges.stream().map(this::parseRanges).toList();
        boolean merged = true;
        while (merged) {
            merged = false;
            List<Range> newRanges = new ArrayList<>();
            boolean[] used = new boolean[parsedRanges.size()];

            for (int i = 0; i < parsedRanges.size(); i++) {
                if (used[i]) continue;

                Range currentRange = parsedRanges.get(i);
                System.out.println("Current range: " + currentRange);
                used[i] = true;

                // Try to merge with other ranges
                for (int j = i + 1; j < parsedRanges.size(); j++) {
                    if (used[j]) continue;

                    System.out.println("To be merged with:: " + parsedRanges.get(j));
                    Range unionRange = unionRange(currentRange, parsedRanges.get(j));
                    if (unionRange != null) {
                        System.out.println("New, merged range:" + unionRange);
                        currentRange = unionRange;
                        used[j] = true;
                        merged = true;
                    }
                }
                newRanges.add(currentRange);
            }
            parsedRanges = newRanges;
        }

        long freshIds = parsedRanges .stream().map(this::countNumbersInRange).reduce(0L, Long::sum);
        System.out.println("Day 5, part II: " + freshIds);

    }

    private Range parseRanges(String range) {
        var start = Long.parseLong(range.split("-")[0]);
        var end = Long.parseLong(range.split("-")[1]);
        return new Range(start, end);
    }

    private boolean isFresh(List<Range> ranges, Long ingredientId) {
        for (Range range : ranges) {
            if (range.start <= ingredientId && ingredientId <= range.end) {
                return true;
            }
        }
        return false;
    }

    private Range unionRange(Range a, Range b) {
        if (a.end >= b.start && b.end >= a.start) {
            return new Range(Math.min(a.start, b.start), Math.max(a.end, b.end));
        }
        return null;
    }

    private long countNumbersInRange(Range range) {
        return range.end - range.start + 1L;
    }
}
