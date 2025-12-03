package org.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public interface Solution {
    void solutionOne(String fileName);
    void solutionTwo(String fileName);

     default List<String> readInput(String fileName) {
        String line = "";
        List<String> inputLines = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedReader bf = new BufferedReader(new InputStreamReader(fis))){
            while ((line = bf.readLine()) != null) {
                inputLines.add(line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return inputLines;
    }
}
