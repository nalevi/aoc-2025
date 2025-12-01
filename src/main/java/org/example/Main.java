package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("------------------------------");
        System.out.println("AoC 2025");
        System.out.println("------------------------------");

        Solution dayOne = new DayOne();
        dayOne.solutionOne("/Users/nalevi/IdeaProjects/aoc-2025/src/main/resources/day_one_p1.txt");
        dayOne.solutionTwo("/Users/nalevi/IdeaProjects/aoc-2025/src/main/resources/day_one_p1.txt");
    }
}