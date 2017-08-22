package me.janeldq.algorithms.assignments.puzzle;

import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Algorithms Week 4 Programming Assignment: 8-Puzzle
 * Write a program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.
 * 
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 * 
 * @author Jane
 *
 */
public class Board {
    
    private int n;
    
    private int hamming;
    
    private int manhattan;
    
    private int blankPos;
    
    private int[][] blocks;
    
    private Iterable<Board> neighbors;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }
        this.n = blocks[0].length;
        this.blankPos = -1;
        this.blocks = blocks;
        this.hamming = this.calculateHamming();
        this.manhattan = this.calculateManhattan();
        this.neighbors = null;
    }
    
    // board dimension n
    public int dimension() {
        return n;
    }
    
    // number of blocks out of place
    public int hamming() {
        return hamming;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;  
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int pos1 = getRandomBlock();
        int pos2 = getRandomBlock();
        while (pos1 == pos2) {
            pos2 = getRandomBlock();
        }
        int[][] twinBoard  = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                twinBoard[i][j] = this.blocks[i][j];
            }
        }
        this.exchange(twinBoard, pos1/this.n, pos1 % this.n, pos2 / this.n, pos2 % this.n);
        return new Board(twinBoard);
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        Board boardY = (Board) y;
        if (boardY.dimension() != this.n) {
            return false;
        }
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.blocks[i][j] != boardY.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Iterable<Board> neighbors() {
        if (this.neighbors == null)
            this.neighbors = this.getNeighbors();
        return this.neighbors;
    }
    
    // all neighboring boards
    private Iterable<Board> getNeighbors() {
        // the value of blankPos is initialized in constructor
        int x = this.blankPos / this.n;
        int y = this.blankPos % this.n;
        
        // a board may have 2, 3 or 4 neighboring boards according to the position of the blank block
        int[][] moves = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        ArrayList<int[]> exchanges = new ArrayList<int[]>();
        for (int i = 0; i < moves.length; i++) {
            exchanges.add(moves[i]);
        }
        if (y + 1 >= this.n)  exchanges.remove(3);
        if (y - 1 < 0)   exchanges.remove(2);
        if (x + 1 >= this.n)  exchanges.remove(1);
        if (x - 1 < 0)    exchanges.remove(0);
        
        // prepare a list of int[][] to store neighbors
        ArrayList<int[][]> neighborsList = new ArrayList<int[][]>();
        int neighborCount = exchanges.size();
        for (int i = 0; i < neighborCount; i++) {
            neighborsList.add(new int[this.n][this.n]);
        }
        
        // copy from origin board
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                for (int k = 0; k < neighborsList.size(); k++) {
                    neighborsList.get(k)[i][j] = this.blocks[i][j];
                }
            }
        }
        
        // do the position exchanges
        ArrayList<Board> boards = new ArrayList<Board>();
        for (int i = 0; i < neighborsList.size(); i++) {
            int[][] tmpBoard = neighborsList.get(i);
            this.exchange(tmpBoard, x, y, x + exchanges.get(i)[0], y + exchanges.get(i)[1]);
            boards.add(new Board(tmpBoard));
        }
        return boards;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        String str = "" + this.dimension() + "\n";
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                str += "\t" + this.blocks[i][j];
            }
            str += "\n";
        }
        return str;
    }
    
    private int getRandomBlock() {
        int x = StdRandom.uniform(this.n);
        int y = StdRandom.uniform(this.n);
        while (this.blocks[x][y] == 0) {
            x = StdRandom.uniform(this.n);
            y = StdRandom.uniform(this.n);
        }
        return x * this.n + y;
    }
    
    private int calculateManhattan() {
        int priority = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.blocks[i][j] == 0) continue;
                int val = this.blocks[i][j];
                int x = (val-1) / this.n;
                int y = (val-1) % this.n;
                int dis = Math.abs(x-i) + Math.abs(y-j);
                priority += dis;
            }
        }
        return priority;
    }
    
    private int calculateHamming() {
        int priority = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.blocks[i][j] == 0) {
                    if (this.blankPos == -1) {
                        this.blankPos = i * this.n + j;
                    }
                    continue;
                }
                if (this.blocks[i][j] != (i * this.n + j + 1)) {
                    priority++;
                }
            }
        }
        return priority;
    }
    
    private void exchange(int[][] arr, int x1, int y1, int x2, int y2) {
        int tmp = arr[x1][y1];
        arr[x1][y1] = arr[x2][y2];
        arr[x2][y2] = tmp;
    }
    
    // unit tests (not graded)
    public static void main(String[] args) {
    	int[][] blocks = new int[][]{{8,1,3},{4,0,2},{7,6,5}};
    	Board b = new Board(blocks);
    	System.out.println("Dimension: " + b.dimension());
    	System.out.println("Hamming: " + b.hamming());
    	System.out.println("Manhattan: " + b.manhattan());
    	System.out.println("isGoal: " + b.isGoal());
    	System.out.println("toString: \n" + b.toString());
    	Iterable<Board> neighbors = b.getNeighbors();
    	Iterator<Board> itr = neighbors.iterator();
    	while(itr.hasNext()) {
    		System.out.println("neighber: \n" + itr.next());
    	}
    }
}