package org.example;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DayEight implements Solution {
    class Coordinate {
        int x;
        int y;
        int z;

        public Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Coordinate that)) {
                return false;
            }
            return x == that.x && y == that.y && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "," + z + ")";
        }
    }

    class Distance implements Comparable<Distance> {
        Coordinate from;
        Coordinate to;
        double distance;

        public Distance(Coordinate from, Coordinate to, double distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        @Override
        public int compareTo(Distance distance) {
            if (this.distance < distance.distance) {
                return -1;
            }  else if (this.distance > distance.distance) {
                return 1;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Distance that)) {
                return false;
            }
            return from == that.from && to == that.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Objects.hashCode(from), Objects.hashCode(to), distance);
        }

        @Override
        public String toString() {
            return "(" + from + ", " + to + ", " + distance + ")";
        }
    }

    private final List<String> example = List.of(
            "162,817,812",
            "57,618,57",
            "906,360,560",
            "592,479,940",
            "352,342,300",
            "466,668,158",
            "542,29,236",
            "431,825,988",
            "739,650,466",
            "52,470,668",
            "216,146,977",
            "819,987,18",
            "117,168,530",
            "805,96,715",
            "346,949,466",
            "970,615,88",
            "941,993,340",
            "862,61,35",
            "984,92,344",
            "425,690,689"
    );

    @Override
    public void solutionOne(String fileName) {
        List<String> input = readInput(fileName);
        List<Coordinate> coordinates = input.stream().map(this::parseCoordinate).toList();
        List<Distance> distances = new ArrayList<>();

        for(int i = 0; i < coordinates.size(); i++) {
            for(int j = i + 1; j < coordinates.size(); j++) {
                Coordinate a = coordinates.get(i);
                Coordinate b = coordinates.get(j);
                var dist = euclideanDistance(a, b);
                distances.add(new Distance(a, b, dist));
            }
        }

        Collections.sort(distances);

        List<Distance> toWorkWith = new ArrayList<>();
        Map<Coordinate, Set<Coordinate>> circuits = new HashMap<>();
        for(Coordinate c :  coordinates) {
            circuits.putIfAbsent(c, new HashSet<>(Set.of(c)));
        }

        for(int i = 0; i < 1000; i++) {
            toWorkWith.add(distances.get(i));
        }

        createCircuits(toWorkWith, circuits);

        var resCircuits = circuits.values().stream().distinct().sorted(Comparator.<Set<?>>comparingInt(Set::size).reversed())
                .mapToInt(Set::size).boxed().toList();

        var answer1 = resCircuits.get(0) * resCircuits.get(1) * resCircuits.get(2);

        System.out.println("Day 8, part I:" + answer1);


    }

    @Override
    public void solutionTwo(String fileName) {
        List<String> input = readInput(fileName);
        List<Coordinate> coordinates = input.stream().map(this::parseCoordinate).toList();
        List<Distance> distances = new ArrayList<>();

        for(int i = 0; i < coordinates.size(); i++) {
            for(int j = i + 1; j < coordinates.size(); j++) {
                Coordinate a = coordinates.get(i);
                Coordinate b = coordinates.get(j);
                var dist = euclideanDistance(a, b);
                distances.add(new Distance(a, b, dist));
            }
        }

        Collections.sort(distances);

        Map<Coordinate, Set<Coordinate>> circuits = new HashMap<>();
        for(Coordinate c :  coordinates) {
            circuits.putIfAbsent(c, new HashSet<>(Set.of(c)));
        }

        var last = createCircuitsV2(distances, circuits);

        long answer2 = (long) last.from.x * last.to.x;

        System.out.println("Day 8, part II:" + answer2);

    }

    private Coordinate parseCoordinate(String coordinate) {
        String[] split = coordinate.split(",");
        return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    private double euclideanDistance(Coordinate a, Coordinate b) {
        return Math.sqrt(
                Math.pow(a.x-b.x, 2)
                        + Math.pow(a.y-b.y, 2)
                        + Math.pow(a.z-b.z, 2)
        );
    }

    private void createCircuits(List<Distance> distances, Map<Coordinate, Set<Coordinate>> circuits) {
        for (Distance distance : distances) {
            Set<Coordinate> setFrom = circuits.get(distance.from);
            Set<Coordinate> setTo = circuits.get(distance.to);
            if (!setFrom.contains(distance.to)) {
                setFrom.addAll(setTo);
                setFrom.forEach(coord -> circuits.put(coord, setFrom));
            }
        }
    }

    private Distance createCircuitsV2(List<Distance> distances, Map<Coordinate, Set<Coordinate>> circuits) {
        boolean isLastPair = false;
        Distance lastDistance = null;
        int i = 0;
        while(!isLastPair && i < distances.size()) {
            Set<Coordinate> setFrom = circuits.get(distances.get(i).from);
            Set<Coordinate> setTo = circuits.get(distances.get(i).to);
            if (!setFrom.contains(distances.get(i).to)) {
                setFrom.addAll(setTo);
                setFrom.forEach(coord -> circuits.put(coord, setFrom));
            }
            isLastPair = setFrom.size() == circuits.size();
            lastDistance = distances.get(i);
            i++;
        }

        return lastDistance;
    }

}
