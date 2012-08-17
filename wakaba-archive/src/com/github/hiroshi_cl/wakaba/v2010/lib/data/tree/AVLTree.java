package com.github.hiroshi_cl.wakaba.v2010.lib.data.tree;
import static java.lang.Math.*;
/*
 * AVL木は平衡二分木の一つであり，任意のノードに対して左右の高さの差が高々 1 という制約を課したものである．
 * 挿入，削除などの操作によって制約を満たさなくなった頂点に対しては回転と呼ばれる操作で制約を満たすようにする．
 * 参考サイト(Spaghetti Source: http://www.prefield.com/algorithm/container/avl_tree.html)
 * 
 * find(x)   検索: O(log n)．
 * insert(x) 挿入: O(log n)．
 * remove(x) 削除: O(log n)．
 * rank(k) : k 番目に小さな要素を返す． O(log n).
 * 
 * verified: UVa501 pku1442 Black Box. removeはチェックされていない.
 */
public class AVLTree {
	Node root;

	class Node {
		long key;
		int size = 1, height = 1;
		Node[] child = new Node[2];// 0:left 1:right

		Node(long key) {
			this.key = key;
		}
	}

	Node find(long key) {
		return find(root, key);
	}// O(log n)

	Node find(Node x, long key) {
		if (x == null || x.key == key)
			return x;
		if (key < x.key)
			return find(x.child[0], key);
		return find(x.child[1], key);
	}

	void insert(long key) {
		root = insert(root, new Node(key));
	}// O(log n)

	Node insert(Node t, Node x) {
		if (t == null)
			return x;
		if (x.key <= t.key)
			t.child[0] = insert(t.child[0], x);
		else
			t.child[1] = insert(t.child[1], x);
		t.size++;
		return balance(t);
	}

	void remove(long key) {
		root = remove(root, key);
	}// O(log n)

	Node remove(Node t, long key) {
		if (t == null)
			return null;
		if (key == t.key)
			return moveDown(t.child[0], t.child[1]);
		if (key < t.key)
			t.child[0] = remove(t.child[0], key);
		else
			t.child[1] = remove(t.child[1], key);
		t.size--;
		return balance(t);
	}

	Node moveDown(Node t, Node rhs) {
		if (t == null)
			return rhs;
		t.child[1] = moveDown(t.child[1], rhs);
		return balance(t);
	}

	Node rotate(Node t, int l, int r) {
		Node s = t.child[r];
		t.child[r] = s.child[l];
		s.child[l] = balance(t);
		if (t != null)
			t.size = sz(t.child[0]) + sz(t.child[1]) + 1;
		if (s != null)
			s.size = sz(s.child[0]) + sz(s.child[1]) + 1;
		return balance(s);
	}

	Node balance(Node t) {
		for (int i = 0; i < 2; i++)
			if (ht(t.child[1 - i]) - ht(t.child[i]) < -1) {
				if (ht(t.child[i].child[1 - i]) - ht(t.child[i].child[i]) > 0)
					t.child[i] = rotate(t.child[i], i, 1 - i);
				return rotate(t, 1 - i, i);
			}
		if (t != null) {
			t.height = max(ht(t.child[0]), ht(t.child[1])) + 1;
			t.size = sz(t.child[0]) + sz(t.child[1]) + 1;
		}
		return t;
	}

	Node rank(int k) {
		return rank(root, k);
	}// O(log n)

	Node rank(Node t, int k) {
		if (t == null)
			return null;
		int m = sz(t.child[0]);
		if (k < m)
			return rank(t.child[0], k);
		if (k == m)
			return t;
		return rank(t.child[1], k - m - 1);
	}

	int ht(Node t) {
		return t == null ? 0 : t.height;
	}

	int sz(Node t) {
		return t == null ? 0 : t.size;
	}

	public static void main(String[] args) {// pku1442
		java.util.Scanner sc = new java.util.Scanner(System.in);
		java.util.Queue<Integer> qa = new java.util.LinkedList<Integer>(), qu = new java.util.LinkedList<Integer>();
		AVLTree tree = new AVLTree();
		int n = sc.nextInt(), m = sc.nextInt();
		for (int i = 0; i < n; i++)
			qa.offer(sc.nextInt());
		for (int i = 0; i < m; i++)
			qu.offer(sc.nextInt());
		for (int i = 0, r = 0; i < n; i++) {
			tree.insert(qa.poll());
			while (!qu.isEmpty() && qu.peek() == i + 1) {
				qu.poll();
				System.out.println(tree.rank(r++).key);
			}
		}
		sc.close();
	}
}