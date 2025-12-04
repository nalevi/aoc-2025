package org.example;

import java.util.ArrayList;
import java.util.List;

public class DayFour implements Solution {

    private class Result {
        private int counter;
        private List<String> newGrid = new ArrayList<>();

        public Result(int counter, List<String> newGrid) {
            this.counter = counter;
            this.newGrid = new ArrayList<>(newGrid);
        }

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        public List<String> getNewGrid() {
            return newGrid;
        }

        public void setNewGrid(List<String> newGrid) {
            this.newGrid = newGrid;
        }
    }

    private final List<String> exampleInput = List.of(
            "..@@.@@@@.",
            "@@@.@.@.@@",
            "@@@@@.@.@@",
            "@.@@@@..@.",
            "@@.@@@@.@@",
            ".@@@@@@@.@",
            ".@.@.@.@@@",
            "@.@@@.@@@@",
            ".@@@@@@@@.",
            "@.@.@@@.@."
    );

    @Override
    public void solutionOne(String fileName) {
        //List<String> rows = exampleInput;
        List<String> rows = readInput(fileName);
        List<String> resultGrid = new ArrayList<>();

        int res = countAccessibleRolls(rows, resultGrid);

        System.out.println("Day 4, part I: " + res);
    }

    @Override
    public void solutionTwo(String fileName) {
        //List<String> rows = new ArrayList<>(exampleInput);
        List<String> rows = readInput(fileName);
        Result res =  countAccessibleRollsV2(rows);
        int counter = res.getCounter();
        int sum = res.getCounter();
        int i = 0;

        while (counter > 0) {
            res = countAccessibleRollsV2(res.getNewGrid());
            sum += res.getCounter();
            counter = res.getCounter();
        }


        System.out.println("Day 4, part II: " + sum);
    }

    private int countAccessibleRolls (List<String> rows, List<String> resultGrid) {
        int sum = 0;
        for(int i = 0; i < rows.size(); i++) {
            StringBuilder rowBuilder = new StringBuilder();
            for(int j = 0; j < rows.get(i).length(); j++) {
                if (rows.get(i).charAt(j) == '@') {
                    int coutRolls = 0;
                    int left = j - 1;
                    int right = j + 1;
                    int up = i - 1;
                    int down = i + 1;

                    if (left >= 0 && rows.get(i).charAt(left) == '@') {
                        coutRolls++;
                    }
                    if (right < rows.get(i).length() && rows.get(i).charAt(right) == '@') {
                        coutRolls++;
                    }

                    if (up >= 0 && rows.get(up).charAt(j) == '@') {
                        coutRolls++;
                    }

                    if (down < rows.size() && rows.get(down).charAt(j) == '@') {
                        coutRolls++;
                    }

                    if (up >= 0 && left >= 0 && rows.get(up).charAt(left) == '@') {
                        coutRolls++;
                    }

                    if (up >= 0 && right < rows.get(i).length() && rows.get(up).charAt(right) == '@') {
                        coutRolls++;
                    }

                    if (down < rows.size() && right < rows.get(i).length() && rows.get(down).charAt(right) == '@') {
                        coutRolls++;
                    }

                    if (down < rows.size() && left >= 0 && rows.get(down).charAt(left) == '@') {
                        coutRolls++;
                    }

                    if (coutRolls < 4) {
                        rowBuilder.append("x");
                        sum++;
                    } else {
                        rowBuilder.append(rows.get(i).charAt(j));
                    }
                }
            }
            resultGrid.add(rowBuilder.toString());
        }

        return sum;
    }

    private Result countAccessibleRollsV2(List<String> rows) {
        int sum = 0;
        List<String> resultGrid = new ArrayList<>();
        for(int i = 0; i < rows.size(); i++) {
            StringBuilder rowBuilder = new StringBuilder();
            for(int j = 0; j < rows.get(i).length(); j++) {
                int coutRolls = 0;
                boolean removed = false;
                if (rows.get(i).charAt(j) == '@') {
                    int left = j - 1;
                    int right = j + 1;
                    int up = i - 1;
                    int down = i + 1;

                    if (left >= 0 && rows.get(i).charAt(left) == '@') {
                        coutRolls++;
                    }
                    if (right < rows.get(i).length() && rows.get(i).charAt(right) == '@') {
                        coutRolls++;
                    }

                    if (up >= 0 && rows.get(up).charAt(j) == '@') {
                        coutRolls++;
                    }

                    if (down < rows.size() && rows.get(down).charAt(j) == '@') {
                        coutRolls++;
                    }

                    if (up >= 0 && left >= 0 && rows.get(up).charAt(left) == '@') {
                        coutRolls++;
                    }

                    if (up >= 0 && right < rows.get(i).length() && rows.get(up).charAt(right) == '@') {
                        coutRolls++;
                    }

                    if (down < rows.size() && right < rows.get(i).length() && rows.get(down).charAt(right) == '@') {
                        coutRolls++;
                    }

                    if (down < rows.size() && left >= 0 && rows.get(down).charAt(left) == '@') {
                        coutRolls++;
                    }
                    if (coutRolls < 4) {
                        rowBuilder.append(".");
                        sum++;
                        removed = true;
                    }
                }
                 if (!removed) {
                    rowBuilder.append(rows.get(i).charAt(j));
                }
            }
            String clearedRow = rowBuilder.toString();
            resultGrid.add(clearedRow);
        }

        return new Result(sum, resultGrid);
    }
}
