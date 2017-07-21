package me.janeldq.algorithms.datastructure;

/**
 * TODO Weighted Quick Union With Path Compression
 * making all the nodes that we do examine directly link to the root
 * 
 * @author Jane
 *
 */
public class QUWithPathCompression extends WeightedQuickUnion {
	
	public QUWithPathCompression(int n) {
		super(n);
	}

	@Override
	public void union(int i, int j) {
		int p = root(i);
		int q = root(j);
		if (this.size[p] < this.size[q]) {
			compress(j, q);
			this.nodes[p] = q;
			this.size[q] += this.size[p];
		} else {
			compress(i, p);
			this.nodes[q] = p;
			this.size[p] += this.size[q];
		}
		
	}
	
	private void compress(int i, int root) {
		int tmp = i;
		while(this.nodes[i] != i) {
			tmp = this.nodes[i];
			this.nodes[i] = root;
			i = tmp;
		}
	}
	public static void main(String[] args) {
		QUWithPathCompression q = new QUWithPathCompression(10);
		q.union(1, 5);
		q.union(2, 7);
		q.union(7, 8);
		q.union(5, 6);
		q.union(1, 2);
		q.union(8, 3);
		q.display();
	}
}
