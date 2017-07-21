package me.janeldq.algorithms.datastructure;

public abstract class AbstractUnion {
	
	protected int[] nodes = null;
	
	public AbstractUnion(int n) {
		if (n <= 0) throw new IllegalArgumentException();
		this.nodes = new int[n];
		for (int i = 0; i < n; i++) {
			this.nodes[i] = i;
		}
	}
	
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
	
	public void validate(int idx) throws IllegalArgumentException {
		if (idx <= 0 || idx > this.nodes.length-1) {
			throw new IllegalArgumentException();
		}
	}
	
	public abstract int root(int i);
	
	public abstract void union(int i, int j);
	
	public abstract boolean connected(int i, int j);
	
	public abstract int count();
	
}
