package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class DayTwo implements Solution {
    class Range {
        long start;
        long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Range{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    List<String> exampleInput = List.of("11-22",
            "95-115",
            //"11110-11111" ,
            "998-1012","1188511880-1188511890","222220-222224",
            "1698522-1698528","446443-446449","38593856-38593862","565653-565659","824824821-824824827","2121212118-2121212124"
            );

//    List<String> exampleInput = List.of("2121212120-2121212121"
//    );
    @Override
    public void solutionOne(String fileName) {
        //List<Range> ranges = exampleInput.stream().map(this::parseRange).toList();
        List<String> input = readInput(fileName);

        List<String> splittedInput = new ArrayList<>();
        for (String line : input) {
            String[] rangeStrs = line.split(",");
            var lst = Arrays.stream(rangeStrs).toList();
            splittedInput.addAll(lst);
        }


        List<Range> ranges = splittedInput.stream().map(this::parseRange).toList();

        List<Long> resultIds = new ArrayList<>();

        for (var range : ranges) {
            //System.out.println(" Range: " + range);
            resultIds.addAll(collectInvalidIds(range));
        }

        //resultIds.forEach(System.out::println);
        var result = resultIds.stream().reduce(0L, Long::sum);
        System.out.println("Day 2, part I: " + result);
    }

    @Override
    public void solutionTwo(String fileName) {
//        List<Range> ranges = exampleInput.stream().map(this::parseRange).toList();
        List<String> input = readInput(fileName);

        List<String> splittedInput = new ArrayList<>();
        for (String line : input) {
            String[] rangeStrs = line.split(",");
            var lst = Arrays.stream(rangeStrs).toList();
            splittedInput.addAll(lst);
        }


        List<Range> ranges = splittedInput.stream().map(this::parseRange).toList();

        List<Long> resultIds = new ArrayList<>();

        for (var range : ranges) {
            System.out.println(" Range: " + range);
            resultIds.addAll(collectInvalidIdsV2(range));
        }

        resultIds.forEach(System.out::println);
        var result = resultIds.stream().reduce(0L, Long::sum);
        System.out.println("Day 2, part II: " + result);

    }

    private Range parseRange(String rangeStr) {
        String[] splitted = rangeStr.split("-");
        long start = Long.parseLong(splitted[0]);
        long end = Long.parseLong(splitted[1]);
        return new Range(start, end);
    }

    private List<Long> collectInvalidIds(Range range) {
        return LongStream.rangeClosed(range.start, range.end).filter(this::isInvalidId).boxed().toList();

    }

    private List<Long> collectInvalidIdsV2(Range range) {
        return LongStream.rangeClosed(range.start, range.end).filter(this::isInvalidIdV2).boxed().toList();

    }

    private boolean isInvalidId(Long id) {
        var numStr = id.toString();
        if (numStr.length() % 2 != 0) {
            return false;
        }
        var middle = numStr.length() / 2;
        return numStr.substring(0, middle).equals(numStr.substring(middle));
    }

    private boolean isInvalidIdV2(Long id) {
        var numStr = id.toString();
        boolean isRepeating = false;
        if (id < 10) {
            return isRepeating;
        }

        int maxIt = numStr.length() / 2;

        for (int i = 1; i <= maxIt; i++) {
            if (numStr.length() % i == 0 && numStr.equals(numStr.substring(0, i).repeat(numStr.length()/i))) {
                isRepeating = true;
                break;
            }
        }

        return isRepeating;
    }

}
