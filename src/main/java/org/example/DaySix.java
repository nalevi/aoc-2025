package org.example;

import java.util.ArrayList;
import java.util.List;

public class DaySix implements Solution {
    @Override
    public void solutionOne(String fileName) {
        var input = readInput(fileName);
        List<List<Integer>> numMatrix = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        for (var line : input) {
            String[] nums = line.split(" ");
            List<Integer> numbers = new ArrayList<>();
            for (int i= 0; i < nums.length; i++) {
                nums[i] = nums[i].trim();
            }

            for (var n: nums) {
                if (n.equals("*") || n.equals("+")) {
                    operators.add(n);
                } else {
                    try {
                        numbers.add(Integer.valueOf(n));
                    } catch (NumberFormatException ex) {
                        // skip
                    }
                }
            }

            numMatrix.add(numbers);
        }

//        numMatrix.forEach(r -> {
//            for (var n : r) {
//                System.out.print(n + " ");
//            }
//            System.out.println();
//        });
//        operators.forEach(System.out::println);
        long sum = grandTotal(numMatrix, operators);
        System.out.println("Day 6, part I: " + sum);

    }

    @Override
    public void solutionTwo(String fileName) {
//        List<String> input = List.of(
//                "123 328  51 64 ",
//                " 45 64  387 23 ",
//                "  6 98  215 314",
//                "*   +   *   +  "
//        );
        List<List<String>> inputMatrix = new ArrayList<>();

        List<String> input = readInput(fileName);

        List<Integer> colWidth = detectColumnWidths(input.get(input.size()-1));
        List<String> ops = new ArrayList<>();

        for(var line : input) {
            List<String> numLine = new ArrayList<>();
            String currNum = "";
            int col = 0;
            int pos = 0;
            while (col < colWidth.size()) {
                int shift = colWidth.get(col);

                if (pos + shift >= line.length()) {
                    currNum = line.substring(pos);
                } else {
                    currNum = line.substring(pos, pos + shift);
                }

                if (currNum.charAt(0) == '*' || currNum.charAt(0) == '+') {
                    ops.add(currNum);
                } else {
                    numLine.add(currNum);
                }
                pos += shift + 1;
                col++;
            }
            if (currNum.charAt(0) != '*' && currNum.charAt(0) != '+') {
                inputMatrix.add(numLine);
            }
        }


        long grandTotal = grandTotalV2(inputMatrix, ops);

        System.out.println("Day 6, part II: " + grandTotal);

    }

    private long grandTotal(List<List<Integer>> matrix, List<String> operators) {
        long sum = 0;
        for(int col = 0; col < matrix.get(0).size(); col++) {
            List<Integer> operands = new ArrayList<>();
            for(int row = 0; row < matrix.size() -1; row++) {
//                System.out.println("row:" + row + ", col: " + col);
                operands.add(matrix.get(row).get(col));
            }

            long result = 0L;
            if (operators.get(col).trim().equals("*")) {
                result = 1L;
            }
            for (var o : operands) {
                if (operators.get(col).trim().equals("*")) {
                    result *= o.longValue();
                } else {
                    result += o.longValue();
                }
            }
            sum += result;
        }
        return sum;
    }

    private long grandTotalV2(List<List<String>> matrix, List<String> operators) {
        long sum = 0;
        for(int col = 0; col < matrix.get(0).size(); col++) {
            List<String> operandsStr = new ArrayList<>();
            for (List<String> strings : matrix) {
                operandsStr.add(strings.get(col));
            }
            List<Integer> operands = new ArrayList<>();
            for (int c = 0; c < operandsStr.get(0).length(); c++) {
                String num = "";
                for (var n : operandsStr) {
                    if (Character.isDigit(n.charAt(c))) {
                        num += n.substring(c, c+1);
                    }
                }
                operands.add(Integer.valueOf(num));
            }

            long result = 0L;
            if (operators.get(col).contains("*")) {
                result = 1L;
            }
            for (var o : operands) {
                if (operators.get(col).contains("*")) {
                    result *= o.longValue();
                } else {
                    result += o.longValue();
                }
            }
            sum += result;
        }
        return sum;
    }

    private List<Integer> detectColumnWidths(String operatorLine) {
        List<Integer> widths = new ArrayList<>();
        int i = 0;

        while (i < operatorLine.length()) {
            if (operatorLine.charAt(i) != ' ') {
                int width = 1;
                int j = i + 1;

                while (j < operatorLine.length() && operatorLine.charAt(j) == ' ') {
                    width++;
                    j++;
                }

                if (j != operatorLine.length()) {
                    width = width - 1;
                }
                widths.add(width);
                i = j;
            } else {
                i++;
            }
        }

        // IDEA bug... cuts the last spaces
        widths.set(widths.size()-1, 2);
        return widths;
    }
}
