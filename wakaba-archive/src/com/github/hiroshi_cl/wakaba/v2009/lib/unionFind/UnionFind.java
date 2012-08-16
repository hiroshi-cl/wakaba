package com.github.hiroshi_cl.wakaba.v2009.lib.unionFind;

import java.util.*;

public class UnionFind {
	int[] tree;

	UnionFind(int size) {
		tree = new int[size];
		Arrays.fill(tree, -1);
	}

	void union(int x, int y) {
		x = root(x);
		y = root(y);
		if (x == y)
			return;
		if (tree[x] > tree[y]) {
			x ^= y;
			y ^= x;
			x ^= y;
		}
		tree[x] += tree[y];
		tree[y] = x;
	}

	boolean find(int x, int y) {
		if (root(x) == root(y))
			return true;
		else
			return false;
	}

	int root(int x) {
		if (tree[x] < 0)
			return x;
		else
			return tree[x] = root(tree[x]);
	}
}
