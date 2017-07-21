package me.janeldq.algorithms.datastructure;

/**
 * Quick find, slow union
 * 
 * @author Jane
 *
 */
public class QuickFind extends AbstractUnion {
	
	private int count = 0;
	
	public QuickFind(int n){
		super(n);
		count = n;
	}
	
	public void union(int i, int j) {
		if ( i < 0 || i > this.nodes.length || j < 0 || j > this.nodes.length) {
			throw new IllegalArgumentException();
		}
		if (this.nodes[i] == this.nodes[j]) return;
		for (int k = 0; k < this.nodes.length; k++ ) {
			if (this.nodes[k] == i) {
				this.nodes[k] = j;
			}
			this.nodes[i] = j;
		}
		count--;
	}
	
	public int root(int m) {
		if ( m < 0 || m > this.nodes.length) {
			throw new IllegalArgumentException();
		}
		return this.nodes[m];
	}
	
	public boolean connected(int i, int j) {
		return this.nodes[i] == this.nodes[j];
	}
	

	public int count() {
		return count;
	}
	
	
	public static void main(String[] args) {
		QuickFind q = new QuickFind(10);
		q.union(1, 5);
		q.union(2, 7);
		q.union(7, 8);
		q.union(5, 6);
		q.display();
	}

}
