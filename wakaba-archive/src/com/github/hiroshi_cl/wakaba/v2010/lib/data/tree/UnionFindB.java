package com.github.hiroshi_cl.wakaba.v2010.lib.data.tree;

import static java.util.Arrays.*;

/**
 * union(x,y) : くっつける.
 * find(x,y)  : 同じグループか？ を返す.
 * これらの操作が実用上はほぼO(1).
 * 経路圧縮がついていることが計算量的に重要.
 * 
 * 各グループに値を付加する実装。演算は可換な方が無難。
 * 
 * union(x,y) : くっつける.
 * find(x,y)  : 同じグループか？ を返す.
 * root(x)    : 根のid を返す。内部でしか使わなさそう.
 * size(x)    : x を含むグループの要素数を返す.
 *
 */
public class UnionFindB {
	final int size;
	final int[] tree;
	final int[] vals;

	public UnionFindB(int s) {
		size = s;
		tree = new int[size];
		vals = new int[size]; // values on roots
		fill(tree, -1);
		fill(vals, 1); // for size of tree
	}

	int root(int x) {
		return tree[x] < 0 ? x : (tree[x] = root(tree[x]));
	}

	boolean find(int x, int y) {
		x = root(x);
		y = root(y);
		return x == y;
	}

	void union(int x, int y) {
		x = root(x);
		y = root(y);
		if (x != y) {
			tree[y] = x;
			vals[x] += vals[y]; // for size of tree
		}
	}

	int getVal(int x) {
		return vals[root(x)];
	}
}
