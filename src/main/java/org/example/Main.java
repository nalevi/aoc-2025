package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("------------------------------");
        System.out.println("AoC 2025");
        System.out.println("------------------------------");

        Solution dayOne = new DayOne();
        dayOne.solutionOne("/home/lnagy/IdeaProjects/aoc-2025/src/main/resources/day_one_p1.txt");
        dayOne.solutionTwo("/home/lnagy/IdeaProjects/aoc-2025/src/main/resources/day_one_p1.txt");

        System.out.println("------------------");

        Solution dayTwo = new DayTwo();
        dayTwo.solutionOne("/home/lnagy/IdeaProjects/aoc-2025/src/main/resources/day_two.txt");
        dayTwo.solutionTwo("/home/lnagy/IdeaProjects/aoc-2025/src/main/resources/day_two.txt");

        System.out.println("------------------");

        Solution dayThree = new DayThree();
        dayThree.solutionOne("/home/lnagy/IdeaProjects/aoc-2025/src/main/resources/day_three.txt");
        dayThree.solutionTwo("/home/lnagy/IdeaProjects/aoc-2025/src/main/resources/day_three.txt");
    }
}