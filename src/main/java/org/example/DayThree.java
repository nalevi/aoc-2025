package org.example;

import java.util.Arrays;
import java.util.List;

public class DayThree implements Solution {

    private final List<String> exampleInput =  Arrays.asList(
            "987654321111111",
            "811111111111119",
            "234234234234278",
            "818181911112111"
    );
    @Override
    public void solutionOne(String fileName) {
        List<String> input = readInput(fileName);
        //long result = exampleInput.stream().map(this::maxJoltage).reduce(0L, Long::sum);
        long result = input.stream().map(this::maxJoltage).reduce(0L, Long::sum);
        System.out.println("Day 3, part I: " + result);

    }

    @Override
    public void solutionTwo(String fileName) {

    }

    private long maxJoltage(String bank) {
        long maxNum = 0;
        for(int i = 0; i < bank.length() - 1; i++){
            for(int j = i + 1; j < bank.length(); j++){
                long newMax = Long.parseLong(bank.charAt(i) + "" + bank.charAt(j));
                if (maxNum < newMax) {
                    maxNum = newMax;
                }
            }
        }

        return maxNum;
    }
}
