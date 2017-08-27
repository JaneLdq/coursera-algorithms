package me.janeldq.algorithms.datastructures.unionfind;

/**
 * A union-find data type which supports union and find operations。
 * Initializing a data structrue with n nodes takes linear time. And then
 * The find, connected and count operations take constant time, but the union
 * operation takes linear time.
 * 
 * @author Jane
 *
 */
public class QuickFindUF extends AbstractUF {
	
	private int count = 0;
	
	public QuickFindUF(int n){
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
	
	public int find(int m) {
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
		QuickFindUF q = new QuickFindUF(10);
		q.union(1, 5);
		q.union(2, 7);
		q.union(7, 8);
		q.union(5, 6);
		q.display();
	}

}
