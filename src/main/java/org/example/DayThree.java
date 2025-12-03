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
        int result = exampleInput.stream().map(this::maxJoltage).reduce(0, Integer::sum);
        System.out.println("Day 3, part I: " + result);

    }

    @Override
    public void solutionTwo(String fileName) {

    }

    private int maxJoltage(String bank) {
        int maxNum = 0;
        for(int i = 0; i < bank.length() - 1; i++){
            for(int j = i + 1; j < bank.length(); j++){
                int newMax = Integer.parseInt(bank.substring(i, j));
                if (maxNum < newMax) {
                    maxNum = newMax;
                }
            }
        }

        return maxNum;
    }
}
