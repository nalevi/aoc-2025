package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;

public class DaySeven implements Solution {

    class Element {
        int x;
        int y;
        boolean isSplitter;
        boolean isActivated;
        boolean isStart;
        boolean isBeam;

        public Element(int x, int y, boolean isSplitter, boolean isActivated, boolean isStart) {
            this.x = x;
            this.y = y;
            this.isSplitter = isSplitter;
            this.isActivated = isActivated;
            this.isStart = isStart;
            this.isBeam = false;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Element element)) {
                return false;
            }
            return x == element.x && y == element.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Element{" + "x=" + x + ", y=" + y + ", isSplitter=" + isSplitter + ", isActivated="
                    + isActivated + ", isBeam=" +  isBeam +'}';
        }
    }

    List<String> example = List.of(
            ".......S.......",
            "...............",
            ".......^.......",
            "...............",
            "......^.^......",
            "...............",
            ".....^.^.^.....",
            "...............",
            "....^.^...^....",
            "...............",
            "...^.^...^.^...",
            "...............",
            "..^...^.....^..",
            "...............",
            ".^.^.^.^.^...^.",
            "..............."
    );
    @Override
    public void solutionOne(String fileName) {
        List<Element> elements = new ArrayList<>();
        List<String> input = readInput(fileName);

        for(int i = 0; i < example.size(); i++) {
            String line = example.get(i);
            var newElements = parseLine(line, i);
            elements.addAll(newElements);
        }


        List<Element> activatedSplitters = new ArrayList<>();
        calculateBeamProperties(elements, example.size()-1, activatedSplitters);
        System.out.println("Day 7, part I: " + activatedSplitters.size());


    }

    @Override
    public void solutionTwo(String fileName) {
        List<String> input = readInput(fileName);

        long timelines = calculateBeamPropertiesV2(input);
        System.out.println("Day 7, part II: " + timelines);
    }

    private List<Element> parseLine(String line, int lineIdx) {
        List<Element> elemLine = new ArrayList<>();

        for (int j = 0; j < line.length(); j++) {
            char c = line.charAt(j);
            switch (c) {
                case '.' -> elemLine.add(new Element(lineIdx, j, false, false, false));
                case 'S' -> elemLine.add(new Element(lineIdx, j, false, false, true));
                case '^' -> elemLine.add(new Element(lineIdx, j, true, false, false));
                default -> throw new IllegalArgumentException("Illegal character " + c);
            };
        }

        return elemLine;
    }
    private long calculateBeamPropertiesV2(List<String> lines) {
        Map<Integer, Long> timelineMap = new HashMap<>();


        var start = findStartV2(lines);

        timelineMap.put(start, 1L);
        for (int row = 1; row < lines.size(); row++) {
            Map<Integer, Long> newTimeLineMap = new HashMap<>();

            for(var entry : timelineMap.entrySet()) {
                if(lines.get(row).charAt(entry.getKey()) == '^') {
                    if(entry.getKey() - 1 >= 0) {
                        newTimeLineMap.put(entry.getKey() - 1, newTimeLineMap.getOrDefault(entry.getKey() - 1, 0L) + entry.getValue());
                    }
                    if(entry.getKey() + 1 < lines.size()) {
                        newTimeLineMap.put(entry.getKey() + 1, newTimeLineMap.getOrDefault(entry.getKey() + 1, 0L) + entry.getValue());
                    }
                } else {
                    newTimeLineMap.put(entry.getKey(), newTimeLineMap.getOrDefault(entry.getKey(), 0L) + entry.getValue());
                }
            }
            timelineMap = newTimeLineMap;
        }


        long sum = 0;

        for (var entry : timelineMap.entrySet()) {
            sum += entry.getValue();
        }

        return sum;
    }

    private int calculateBeamProperties(List<Element> elements, int lastRow, List<Element> activatedSplitters) {
        Queue<Element> traverseQueue = new LinkedList<>();


        var start = findStart(elements);
        if (start != null) {
            traverseQueue.add(new Element(start.x+1, start.y, false, false, false));

            while (!traverseQueue.isEmpty()) {
                var elem = traverseQueue.poll();
                if (elem == null) {
                    continue;
                }

                if (elem.isSplitter && !elem.isActivated) {
                    var newElement = new Element(elem.x, elem.y, true, true, false);
                    var idx = calculateElemIndex(elements, newElement.x, newElement.y);
                    elements.get(idx).isActivated = true;
                    activatedSplitters.add(newElement);
                    var aboveIdx = calculateElemIndex(elements, elem.x-1, elem.y);
                    var aboveElem =  elements.get(aboveIdx);
                    if (newElement.y > 0) {
                        if (!traverseQueue.contains(elements.get(idx - 1))) {
                            traverseQueue.add(elements.get(idx - 1));
                        }
                    }
                    if (newElement.y < elements.size() / (lastRow + 1) ) {
                        traverseQueue.add(elements.get(idx + 1));
                    }
                } else if (!elem.isSplitter) {
                    var currIdx = calculateElemIndex(elements, elem.x, elem.y);
                    var nextIdx = currIdx + (elements.size() / (lastRow + 1));
                    elements.get(currIdx).isBeam = true;
                    if (elem.x != lastRow && !traverseQueue.contains(elements.get(nextIdx))) {
                        traverseQueue.add(elements.get(nextIdx));
                    }

                }
            }
        }

        return 0;
    }

    private Element findStart(List<Element> elements) {
        for (Element element : elements) {
            if (element.isStart) {
                return element;
            }
        }
        return null;
    }

    private Integer findStartV2(List<String> elements) {
        return elements.get(0).indexOf("S");
    }

    private int calculateElemIndex(List<Element> elements, int x, int y) {
        int index = 0;
        for (Element element : elements) {
            if (element.x == x && element.y == y) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private void printTachyod(List<Element> elements, int colLength) {
        for(int i = 0; i < elements.size(); i++) {
            String c = ".";
            if (elements.get(i).isBeam) {
                c = "|";
            } else if (elements.get(i).isSplitter) {
                c = "^";
            } else if (elements.get(i).isStart) {
                c = "S";
            }

            System.out.print(c);
            if ((i + 1) % colLength == 0) {
                System.out.println();
            }
        }

    }
}
