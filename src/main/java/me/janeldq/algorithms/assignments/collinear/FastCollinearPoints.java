package me.janeldq.algorithms.assignments.collinear;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Algorithms Week 3 Programming Assignment: Pattern Recognition
 * Write a program to recognize line patterns in a given set of points.
 * 
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * 
 * TODO Consider perfomance requirement
 * @author Jane
 *
 */
public class FastCollinearPoints {
    
    private final ArrayList<LineSegment> lines;
    
    /**
     * An arraylist of lines, used to check duplicate linesegments
     */
    private ArrayList<Line> slopePoints = new ArrayList<Line>();
    
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        lines = new ArrayList<LineSegment>();
        
        Point[] origin = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            origin[i] = points[i];
        }
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points, origin[i].slopeOrder());
            int lo = 1;
            int count = 1;
            if (points.length <= 1) return;
            double slp = origin[i].slopeTo(points[lo]);
            int j = lo + 1;
            for (; j < points.length; j++) {
                if (Double.compare(slp, Double.NEGATIVE_INFINITY) == 0) {
                    throw new IllegalArgumentException();
                }
                if (Double.compare(origin[i].slopeTo(points[j]), slp) == 0) {
                    count++;
                }
                else {
                    if (count >= 3) {
                        Point start = origin[i], end = origin[i];
                        for (int k = lo; k < j; k++) {
                            if (start.compareTo(points[k]) > 0) start = points[k];
                            if (end.compareTo(points[k]) < 0) end = points[k];
                        }
                        this.checkNAddLineSegment(start, end);
                    }
                    lo = j;
                    slp = origin[i].slopeTo(points[lo]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Point start = origin[i], end = origin[i];
                for (int k = lo; k < points.length; k++) {
                    if (start.compareTo(points[k]) > 0) start = points[k];
                    if (end.compareTo(points[k]) < 0) end = points[k];
                }
                this.checkNAddLineSegment(start, end);
            }
        }
    }
    
    /**
     * Get the number of line segments
     * @return number of line segments
     */
    public int numberOfSegments() {
        return lines.size();
    }
    
    /**
     * Get all the line segments which length >= 4;
     * @return an array of line segments
     */
    public LineSegment[] segments() {
        LineSegment[] arr = new LineSegment[lines.size()];
        return lines.toArray(arr);
    } 
    
    /**
     * A point and a slope define a line
     * @author Jane
     *
     */
    private class Line {
        private Point p;
        private double slope;
        
        public Line(Point p, double slope) {
            this.p = p;
            this.slope = slope;
        }
        
        public Point getPoint() {
            return p;
        }
        
        public double getSlope() {
        	return slope;
        }
        
    }
    
    /**
     * Check if the line segment with the two end points already added in line segment array, 
     * if not, add it to the array. Since a point and a slope can define a line, 
     * we can firstly calculate the slope (named as inputSlope) of the input line segment, 
     * and if this one is equal to a line's slope(name as lineSlope) in the list, then we pick one point from
     * each line and calculate the slope (named as pointSlope).
     * There are three cases need to be considered:
     * 1) the new line segment is parallel to some line already in the lines array
     * 		the condition is inputSlope == lineSlope <> pointSlope
     * 2) the new line segment is already in the lines array
     * 		the condition is inputSlope == lineSlope == pointSlope
     * 		Attention: the slope of a degenerate line segment (between a point and itself) is treated as negative infinity.
     * 3) the new line is a real new one
     * then the input line is already added.
     * 
     * @param start
     * @param end
     */
    private void checkNAddLineSegment(Point start, Point end) {
    	LineSegment newSeg = new LineSegment(start, end);
        double inputSlope = start.slopeTo(end);
        Iterator<Line> itr = slopePoints.iterator();
        if (slopePoints.size() > 0) {
            boolean isIn = false;
            while (itr.hasNext()) {
                Line line = itr.next();
                if ((Double.compare(line.getSlope(), inputSlope) == 0) 
                        && ((Double.compare(line.getPoint().slopeTo(start), inputSlope) == 0)
                                || (Double.compare(line.getPoint().slopeTo(start), Double.NEGATIVE_INFINITY) == 0))) {
                    isIn = true;
                    break;
                }
            }
            if (!isIn) {
                lines.add(newSeg);
                slopePoints.add(new Line(start, inputSlope));
            }
        } else {
            lines.add(newSeg);
            slopePoints.add(new Line(start, inputSlope));
        }
    }   
    
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println(collinear.numberOfSegments());
    }
    
}