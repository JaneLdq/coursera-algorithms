package me.janeldq.algorithms.datastructures.unionfind;
/**
 * Weighted Quick Union
 * This implmentatation uses weighted quick union by size (without path compression).
 * Initialzing a data structure with linear time.
 * The union, find, and connected operations take logarithmic time (in the worst case) 
 * and the count operation takes constant time.
 * 
 * It keeps track of the size of each tree and always connect the smaller tree to the larger one
 * 
 * @author Jane
 *
 */
public class WeightedQuickUnionUF extends AbstractUF {
	
	/**
	 * size[i] = number of nodes in subtree rooted at i
	 */
	protected int[] size = null;
	
	/**
	 * number of components
	 */
	protected int count;
	
	public WeightedQuickUnionUF(int n) {
		super(n);
		this.count = n;
		this.size = new int[n];
		for (int i = 0; i < n; i++) {
			this.size[i] = 1;
		}
	}

	public int find(int i) {
		while(this.nodes[i] != i) {
			i = this.nodes[i];
		}
		return i;
	}

	public void union(int i, int j) {
		int p = this.find(i);
		int q = this.find(j);
		if (this.size[p] < this.size[q]) {
			this.nodes[p] = q;
			this.size[q] += this.size[p];
		} else {
			this.nodes[q] = p;
			this.size[p] += this.size[q];
		}
		count--;
	}

	public boolean connected(int i, int j) {
		return this.find(i) == this.find(j);
	}

	public int count() {
//		int count = 0;
//		for (int i = 0; i < this.nodes.length; i++) {
//			if (this.nodes[i] == i)
//				count++;
//		}
		return count;
	}
	
	public static void main(String[] args) {
		WeightedQuickUnionUF q = new WeightedQuickUnionUF(10);
		q.union(1, 5);
		q.union(2, 7);
		q.union(7, 8);
		q.union(5, 6);
		q.display();
		System.out.println();
		System.out.println(q.find(6));
		System.out.println(q.find(5));
		System.out.println(q.find(7));
		System.out.println("Count: " + q.count());
	}

}
