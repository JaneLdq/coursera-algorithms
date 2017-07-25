package me.janeldq.algorithms.unionfind;

/**
 * A union-find data type which supports union and find operations。
 * Initializing a data structrue with n nodes takes linear time.
 * The union, find and connected operations take linear time(in the worst case). And
 * the count operation takes constant time.
 * 
 * @author Jane
 *
 */
public class QuickUnionUF extends AbstractUF {
	
	public QuickUnionUF(int n) {
		super(n);
	}
	
	public void union(int i, int j) {
		validate(i);
		validate(j);
		int p = find(i);
		int q = find(j);
		this.nodes[p] = q;
	}
	
	public int find(int i) {
		validate(i);
		int next = this.nodes[i];
		while(this.nodes[next] != next) {
			next = this.nodes[next];
		}
		return next;
	}
	
	public boolean connected(int i, int j) {
		return (this.find(i) == this.find(j));
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
		QuickUnionUF q = new QuickUnionUF(10);
		q.union(1, 5);
		q.union(2, 7);
		q.union(7, 8);
		q.union(5, 6);
		q.union(1, 7);
		q.display();
		System.out.println();
		System.out.println(q.find(1));
		System.out.println(q.find(2));
		System.out.println(q.find(7));
		System.out.println("Count: " + q.count());
	}

}
