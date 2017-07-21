package me.janeldq.algorithms.datastructure;

/***
 * Quick union, slow find
 * @author Jane
 *
 */
public class QuickUnion extends AbstractUnion {
	
	public QuickUnion(int n) {
		super(n);
	}
	
	public void union(int i, int j) {
		validate(i);
		validate(j);
		int p = root(i);
		int q = root(j);
		this.nodes[p] = q;
	}
	
	public int root(int i) {
		validate(i);
		int next = this.nodes[i];
		while(this.nodes[next] != next) {
			next = this.nodes[next];
		}
		return next;
	}
	
	public boolean connected(int i, int j) {
		return (this.root(i) == this.root(j));
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
		QuickUnion q = new QuickUnion(10);
		q.union(1, 5);
		q.union(2, 7);
		q.union(7, 8);
		q.union(5, 6);
		q.union(1, 7);
		q.display();
		System.out.println();
		System.out.println(q.root(1));
		System.out.println(q.root(2));
		System.out.println(q.root(7));
		System.out.println("Count: " + q.count());
	}

}
