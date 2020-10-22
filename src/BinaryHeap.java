import java.util.ArrayList;
/**
 * @author AJ Thut
 * Last Modified: 11/5/2019
 *
 */

//search arraylist with binary heap if needed

public class BinaryHeap<T1 extends Comparable<? super T1>, T2 extends Object> {
	// implements a binary heap where the heap rule is the value
	// of the key in the parent node is less than
	// or equal to the values of the keys in the child nodes

	// the implementation uses parallel arrays to store the priorities and the trees
	// you must use this implementation
	ArrayList<Data> pq; // you have to decide what to put in place of the ?

	private class Data {
		private T2 tree;
		private T1 priority;

		public Data(T1 k, T2 t) {
			tree = t;
			priority = k;
		}

		public T1 getpriority() {
			return priority;
		}
	}

	public BinaryHeap() {
		pq = new ArrayList<>();
		pq.add(null);

	}

	public void removeMin() {
		// PRE: size != 0
		// removes the item at the root of the heap
		pq.set(1, pq.get(pq.size() - 1));
		pq.remove(pq.size() - 1);
		fix(1);
	}

	private void fix(int i) {
		int left = 2 * i;
		int right = 2 * i + 1;
		int smallest = i;

		if (left <= pq.size() - 1 && pq.get(left).getpriority().compareTo(pq.get(i).getpriority()) < 0) {
			smallest = left;
		}
		if (right <= pq.size() - 1 && pq.get(right).getpriority().compareTo(pq.get(smallest).getpriority()) < 0) {
			smallest = right;
		}

		if (smallest != i) {
			swap(i, smallest);
			fix(smallest);
		}
	}

	private void swap(int i, int smallest) {
		Data d = pq.get(i);
		pq.set(i, pq.get(smallest));
		pq.set(smallest, d);
	}

	public T1 getMinKey() {
		// PRE: size != 0
		// return the priority (key) in the root of the heap
		Data d = pq.get(1);
		return d.priority;

	}

	public T2 getMinOther() {
		// PRE: size != 0
		// return the other data in the root of the heap
		Data d = pq.get(1);
		return d.tree;
	}

	public void insert(T1 k, T2 t) {
		// insert the priority k and the associated data into the heap
		pq.add(null);
		int index = pq.size() - 1;

		while (index > 1 && pq.get(index / 2).getpriority().compareTo(k) > 0) {
			pq.set(index, pq.get(index / 2));
			index = index / 2;
		}

		pq.set(index, new Data(k, t));

	}

	public T1 getP(int index) {
		Data d = pq.get(index);
		return d.getpriority();
	}

	public int getSize() {
		// return the number of values (key, other) pairs in the heap
		return pq.size();
	}

}
