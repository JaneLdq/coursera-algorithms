package me.janeldq.algorithms.assignments.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

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
public class PercolationStats {
    
    private double[] records = null;
    private int t = 0;
    
    public PercolationStats(int n, int trials) {
        if (n <= 0 && trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.t = trials;
        this.records = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            for (int j = 0; j < n*n; j++) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
                if (p.percolates()) {
                    break;
                }
            }
            this.records[i] = p.numberOfOpenSites() * 1.0 / (n * n);
        }
    }
    
    public double mean() {
        return StdStats.mean(this.records);
    }
    
    public double stddev() {
        return StdStats.stddev(this.records);
    }
    
    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(this.t);
    }
    
    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(this.t);
    }
    
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException();
        }
        PercolationStats  p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean \t\t\t= " + p.mean());
        System.out.println("stddev \t\t\t= " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLo() + "," + p.confidenceHi() + "]");
    }

}