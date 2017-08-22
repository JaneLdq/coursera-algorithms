package me.janeldq.algorithms.assignments.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

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
public class Solver {
 
    private int moves; 
    private SearchNode end;
 
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> mpq = new MinPQ<SearchNode>();
        moves = -1;
        mpq.insert(new SearchNode(initial, null, 0, true));
        mpq.insert(new SearchNode(initial.twin(), null, 0, false));
        
        SearchNode current = mpq.delMin();
        while (!(current.board.isGoal() || mpq.isEmpty())) {
            Iterable<Board> neighbors = current.board.neighbors();
            for (Board b: neighbors) {
                if (current.parent == null || 
                    !b.equals(current.parent.board)) {
                    mpq.insert(new SearchNode(b, current, current.moves+1, current.isInitial));
                }
            }
            current = mpq.delMin();
        }
        if (current.board.isGoal()) {
            if (current.isInitial) {
                this.moves = current.moves;
                this.end = current;
            }
            else {
                this.moves = -1;
            }
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return this.moves != -1;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (this.isSolvable()) {
            Stack<Board> stk = new Stack<Board>();
            SearchNode current = end;
            while (current != null) {
                stk.push(current.board);
                current = current.parent;
            }
            return stk;
        }
        return null;
    }
    
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode parent;
        private int moves;
        private boolean isInitial;
        
        public SearchNode(Board board, SearchNode parent, int moves, boolean isInitial) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
            this.isInitial = isInitial;
        }
        
        public int compareTo(SearchNode o) {
            return (this.board.manhattan() + this.moves) - (o.board.manhattan() + o.moves);
        }
    }
    
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);
	    StdOut.println(initial);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}