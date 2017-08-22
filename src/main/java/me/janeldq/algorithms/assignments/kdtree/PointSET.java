package me.janeldq.algorithms.assignments.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

/**
 * Algorithms Week 5 Programming Assignment: kd-tree
 * Write a data type to represent a set of points in the unit square 
 * (all points have x- and y-coordinates between 0 and 1) using a 2d-tree 
 * to support efficient range search (find all of the points contained 
 * in a query rectangle) and nearest-neighbor search 
 * (find a closest point to a query point). 
 * 
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
 * 
 * This implementation is the brute-force implementation using a red–black BST.
 * 
 * @author Jane
 *
 */
public class PointSET {
	
	private SET<Point2D> points;
	
	public PointSET() {
		points = new SET<Point2D>();
	}
	
	public boolean isEmpty() {
		return points.isEmpty();
	}
	
	public int size() {
		return points.size();
	}
	
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		points.add(p);
	}
	
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		return points.contains(p);
	}
	
	public void draw() {
		for(Point2D p: points) {
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		SET<Point2D> results = new SET<Point2D>();
		for(Point2D p: points) {
			if (rect.contains(p)) {
				results.add(p);
			}
		}
		return results;
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		double min = Double.MAX_VALUE;
		
		if (this.points.isEmpty()) {
			return null;
		}
		Point2D result = null;
		for(Point2D tmp: this.points) {
			if (min > tmp.distanceTo(p)) {
				min = tmp.distanceTo(p);
				result = tmp;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {}
	
}
