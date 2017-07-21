package me.janeldq.algorithms.datastructure;
/**
 * TODO Weighted Quick Union
 * keep track of the size of each tree and always connect the smaller tree to the larger
 * 
 * @author Jane
 *
 */
public class WeightedQuickUnion extends AbstractUnion {
	
	protected int[] size = null;
	
	public WeightedQuickUnion(int n) {
		super(n);
		this.size = new int[n];
		for (int i = 0; i < n; i++) {
			this.size[i] = 1;
		}
	}

	public int root(int i) {
		while(this.nodes[i] != i) {
			i = this.nodes[i];
		}
		return i;
	}

	public void union(int i, int j) {
		int p = this.root(i);
		int q = this.root(j);
		if (this.size[p] < this.size[q]) {
			this.nodes[p] = q;
			this.size[q] += this.size[p];
		} else {
			this.nodes[q] = p;
			this.size[p] += this.size[q];
		}
	}

	public boolean connected(int i, int j) {
		return this.root(i) == this.root(j);
	}

	public int count() {
		int count = 0;
		for (int i = 0; i < this.nodes.length; i++) {
			if (this.nodes[i] == i)
				count++;
		}
		return count;
	}
	
	public static void main(String[] args) {
		WeightedQuickUnion q = new WeightedQuickUnion(10);
		q.union(1, 5);
		q.union(2, 7);
		q.union(7, 8);
		q.union(5, 6);
		q.display();
		System.out.println();
		System.out.println(q.root(6));
		System.out.println(q.root(5));
		System.out.println(q.root(7));
		System.out.println("Count: " + q.count());
	}

}
