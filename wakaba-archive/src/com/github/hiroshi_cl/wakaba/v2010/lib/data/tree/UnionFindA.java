package com.github.hiroshi_cl.wakaba.v2010.lib.data.tree;
import java.util.Arrays;

/**
 * union(x,y) : くっつける.
 * find(x,y)  : 同じグループか？ を返す.
 * これらの操作が実用上はほぼO(1).
 * 経路圧縮がついていることが計算量的に重要.
 * 
 * union(x,y) : くっつける.
 * find(x,y)  : 同じグループか？ を返す.
 * root(x)    : 根のid を返す。内部でしか使わなさそう.
 * getVal(x)  : x を含むグループに付加した値を得る. この実装例では要素数。
 *
 */
public class UnionFindA {
	final int[] tree;

	public UnionFindA(int n) {
		this.tree = new int[n];
		Arrays.fill(tree, -1);
	}

	void union(int x, int y) {
		x = root(x);
		y = root(y);
		if (x != y) {
			if (tree[x] < tree[y]) {
				x ^= y;
				y ^= x;
				x ^= y;
			}
			tree[x] += tree[y];
			tree[y] = x;
		}
	}

	boolean find(int x, int y) {
		return root(x) == root(y);
	}

	int root(int x) {
		return tree[x] < 0 ? x : (tree[x] = root(tree[x]));
	}

	int size(int x) {
		return -tree[root(x)];
	}
}