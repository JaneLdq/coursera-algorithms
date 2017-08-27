package me.janeldq.algorithms.datastructures.unionfind;

public class PathCompressionQuickUnionUF extends AbstractUF {

	public PathCompressionQuickUnionUF(int n) {
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
		int tmp = i;
		while(this.nodes[i] != i) {
			tmp = this.nodes[i];
			this.nodes[i] = next;
			i = tmp;
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
		PathCompressionQuickUnionUF q = new PathCompressionQuickUnionUF(10);
		q.union(1, 5);
		q.union(2, 7);
		q.union(7, 8);
		q.union(5, 6);
		q.union(1, 2);
		q.union(8, 3);
		q.display();
	}
}
