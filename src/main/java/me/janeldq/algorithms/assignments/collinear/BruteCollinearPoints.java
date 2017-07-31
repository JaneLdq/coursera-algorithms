package me.janeldq.algorithms.assignments.collinear;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Algorithms Week 3 Programming Assignment: Pattern Recognition
 * Write a program to recognize line patterns in a given set of points.
 * 
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * 
 * @author Jane
 *
 */
public class BruteCollinearPoints {
    
    private final ArrayList<LineSegment> segments;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        segments = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
        double s1, s2, s3;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                s1 = points[i].slopeTo(points[j]);
                if (Double.compare(s1, Double.NEGATIVE_INFINITY) == 0) throw new IllegalArgumentException();
                for (int k = j + 1; k < points.length; k++) {
                    s2 = points[i].slopeTo(points[k]);
                    if (Double.compare(s1, s2) != 0) continue;
                    for (int m = k + 1; m < points.length; m++) {
                        s3 = points[i].slopeTo(points[m]);
                        if (Double.compare(s1, s3) == 0) {
                            Point[] tmp = new Point[4];
                            tmp[0] = points[i];
                            tmp[1] = points[j];
                            tmp[2] = points[k];
                            tmp[3] = points[m];
                            Arrays.sort(tmp);
                            segments.add(new LineSegment(tmp[0], tmp[3]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }
    
    public LineSegment[] segments() {
        // the line segments
        LineSegment[] lines = new LineSegment[segments.size()];
        return segments.toArray(lines);
    }
}