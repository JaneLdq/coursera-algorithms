package me.janeldq.algorithms.datastructures.unionfind;

/**
 * Union Find Class with abstract interfaces
 * 
 * @author Jane
 *
 */
public abstract class AbstractUF {
	
	/**
	 * use an integer array to represent nodes
	 */
	protected int[] nodes = null;
	
	/**
	 * initialize n nodes with integer names(0 to n-1)
	 * @param n
	 */
	public AbstractUF(int n) {
		if (n <= 0) throw new IllegalArgumentException();
		this.nodes = new int[n];
		for (int i = 0; i < n; i++) {
			this.nodes[i] = i;
		}
	}
	
	/**
	 * print the node array
	 */
	public void display() {
		System.out.print("| ");
		for (int i = 0; i < this.nodes.length; i++) {
			System.out.print(i + " | ");
		};
		System.out.println();
		System.out.print("| ");
		for (int i = 0; i < this.nodes.length; i++) {
			System.out.print(this.nodes[i] + " | ");
		};
	}
	
	/**
	 * validate an index
	 * @param idx
	 * @throws IllegalArgumentException
	 */
	public void validate(int idx) throws IllegalArgumentException {
		if (idx <= 0 || idx > this.nodes.length-1) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * componnet identifier for i
	 * @param i
	 * @return
	 */
	public abstract int find(int i);
	
	/**
	 * add connection between i and j
	 * @param i
	 * @param j
	 */
	public abstract void union(int i, int j);
	
	/**
	 * return true if i and j are in the same component
	 * @param i
	 * @param j
	 * @return
	 */
	public abstract boolean connected(int i, int j);
	
	/**
	 * return the number of components
	 * @return
	 */
	public abstract int count();
	
}
