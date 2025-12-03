package org.example;


import java.util.Arrays;
import java.util.List;

public class DayOne implements Solution {
    class Result {
        int start;
        int zeroCount;

        public Result(int start, int zeroCount) {
            this.start = start;
            this.zeroCount = zeroCount;
        }

        public int getStart() {
            return start;
        }

        public int getZeroCount() {
            return zeroCount;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public void setZeroCount(int zeroCount) {
            this.zeroCount = zeroCount;
        }
    }


    List<String> exampleInput = Arrays.asList("L68",
        "L30",
        "R48",
        "L5",
        "R60",
        "L155",
        "L1",
        "L99",
        "R14",
        "L82");

    @Override
    public void solutionOne(String fileName) {

        List<String> exampleInputLoc = readInput(fileName);


        int start = 50;
        int countZero = 0;

        List<Integer> steps = exampleInputLoc.stream().map(this::parseStep).toList();

        for(var s : steps) {
            start = turn(start, s);
            // System.out.println("DEBUG - start: " + start);
            if (start == 0) {
                countZero++;
            }
        }

        System.out.println("Day 1, part I: " + countZero);
    }

    private int parseStep(String step) {
        if(step.charAt(0) == 'L') {
            return - Integer.parseInt(step.substring(1));
        } else {
            return Integer.parseInt(step.substring(1));
        }
    }

    private int turn(final int start, final int step) {
        int finish = (step % 100) + start;

        if (finish == 100) {
            finish = 0;
        }

        if (finish < 0) {
            finish = 100 + finish;
        } else if (finish > 99) {
            finish = finish - 100;
        }


        return finish;
    }

    private void turnV2(final int step, Result res) {
        int zeroCounter = step / 100 ;
        if (zeroCounter < 0) {
            zeroCounter *= -1;
        }

        zeroCounter = res.getZeroCount() + zeroCounter;
        int finish = (step % 100) + res.getStart();

        if (finish == 100) {
            finish = 0;
        }

        if (finish < 0) {
            finish = 100 + finish;
            if (res.getStart() != 0) {
                zeroCounter++;
            }
        } else if (finish > 99) {
            finish = finish - 100;
            zeroCounter++;
        }

        if (finish == 0) {
            zeroCounter++;
        }

        res.setStart(finish);
        res.setZeroCount(zeroCounter);
    }

    @Override
    public void solutionTwo(String fileName) {
        Result res = new Result(50, 0);

        List<String> exampleInputLoc = readInput(fileName);

        List<Integer> steps = exampleInputLoc.stream().map(this::parseStep).toList();

        for(var s : steps) {
            turnV2(s, res);
        }

        System.out.println("Day 1, part II: " + res.getZeroCount());
    }
}
