package complex;

public class MinPQ<Key extends Comparable<Key>> {

	private Key[] pq;
	private int n;

	public MinPQ(int capacity) {
		pq = (Key[]) new Comparable[capacity + 1];
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public void insert(Key key) {
		if(n>=pq.length-1){
			Key[] copy = (Key[]) new Comparable[2*pq.length];
			for(int i=0;i<pq.length;i++)
				copy[i]=pq[i];
			pq=copy;
		}
		pq[++n] = key;
		swim(n);
	}

	public Key delMin() {
		Key min = pq[1];
		exch(1, n--);
		sink(1);
		pq[n + 1] = null;
		return min;
	}

	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(j, j + 1))
				j++;
			if (!less(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	private boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) > 0;
	}

	private void exch(int i, int j) {
		Key t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MinPQ [n=" + n + "]";
	}

}
