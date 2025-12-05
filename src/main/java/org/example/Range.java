package org.example;

public class Range {
    long start;
    long end;

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Range{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
