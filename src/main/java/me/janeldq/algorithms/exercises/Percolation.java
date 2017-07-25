package me.janeldq.algorithms.exercises;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Algorithms Week 1 Programming Assignment: Percolation
 * Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.
 * 
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 * 
 * @author Jane
 *
 */
public class Percolation {
    
    private WeightedQuickUnionUF quickUnion = null;
    private int unionNodeCount = 0;
    private int n = 0;
    private int numberOfOpenSites = 0;
    private int[] openSites = null;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.openSites = new int[n*n];
        this.n = n;
        this.unionNodeCount = n*n + 1;
        this.quickUnion = new WeightedQuickUnionUF(this.unionNodeCount);
    }
    
    public void open(int row, int col) {
        this.validate(row, col);
        int idx = (row - 1) * n + col - 1;
        if (this.openSites[idx] == 1) return;
        this.openSites[idx] = 1;
        if (row == 1) {
            this.quickUnion.union(idx+1, 0);
        }
        if (row > 1 && this.isOpen(row-1, col)) {
            this.quickUnion.union(idx+1, (row-2) * n + col);
        }
//        if (row == n) {
//            this.quickUnion.union(idx+1, n*n+1);
//        }
        if (row < n && this.isOpen(row+1, col)) {
            this.quickUnion.union(idx+1, row * n + col);
        }
        if (col > 1 && this.isOpen(row, col - 1)) {
            this.quickUnion.union(idx+1, idx);
        }
        if (col < n && this.isOpen(row, col + 1)) {
            this.quickUnion.union(idx+1, idx+2);
        }
        this.numberOfOpenSites++;
    }
    
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return (this.openSites[(row-1) * n + col - 1] == 1);
    }
    
    public boolean isFull(int row, int col) {
        validate(row, col);
        return (this.quickUnion.connected(0, (row - 1) * n + col));
    }
    
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }
    
    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            if (this.quickUnion.connected(0, this.unionNodeCount - 1 - i)) {
                return true;
            }
        }
        return false;
    }
        
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }
    
    public static void main(String[] args) {
    }

}