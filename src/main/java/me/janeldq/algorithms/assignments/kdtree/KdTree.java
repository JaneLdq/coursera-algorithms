package me.janeldq.algorithms.assignments.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {

	private int size;
	
	private Node root;
	
	private SET<Point2D> inRangePoints;
		
	private class Node {
		
		private Point2D p;
		
		private Node left;
		
		private Node right;
		
		private boolean isVertical;
		
		public Node(Point2D p, boolean isVertical, Node left, Node right) {
			this.p = p;
			this.isVertical = isVertical;
			this.left = left;
			this.right = right;
		}
	}
	
	public KdTree() {
		size = 0;
		root = null;
	}
	
	public int size() {
		return size;
	}
	
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		size++;
		if (root == null) {
			root = new Node(p, true, null, null);
			return;
		}
		doInsert(root, p);
	}
	
	private void doInsert(Node node, Point2D p) {
		if ((node.isVertical && node.p.y() < p.y())
				|| (!node.isVertical && node.p.x() < p.x())) {
			if (node.right == null) {
				node.right = new Node(p, !node.isVertical, null, null);
				return;
			}
			doInsert(node.right, p);
		} else {
			if (node.left == null) {
				node.left = new Node(p, !node.isVertical, null, null);
				return;
			}
			doInsert(node.left, p);
		}
	}
	
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		return contains(p, root);
	}
	
	private boolean contains(Point2D p, Node root) {
		if (root == null) return false;
		if (root.p.compareTo(p) == 0) 
			return true;
		if ((root.isVertical && root.p.y() < p.y()) || 
				(!root.isVertical && root.p.x() < p.x())) {
			return contains(p, root.right);
		}
		else {
			return contains(p, root.left);
		}
	}
	
	// TODO draw
	public void draw() {}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		inRangePoints = new SET<Point2D>();
		range(root, rect);
		return inRangePoints;
	}
	
	private void range(Node node, RectHV rect) {
		if (node == null)
			return;
		if (!rect.contains(node.p)) {
			if ((node.isVertical && rect.ymax() < node.p.y()) || 
					(!node.isVertical && rect.xmax() < node.p.x())) {
				range(node.left, rect);
			} else if ((node.isVertical && rect.ymin() > node.p.y()) || 
					(!node.isVertical && rect.xmin() > node.p.x())) {
				range(node.right, rect);
			} else {
				range(node.right, rect);
				range(node.left, rect);
			}
		} else {
			inRangePoints.add(node.p);
			range(node.right, rect);
			range(node.left, rect);
		}
	}
	
	// TODO nearest
	public Point2D nearest(Point2D p) {
		return null;
	}
	
	
}
