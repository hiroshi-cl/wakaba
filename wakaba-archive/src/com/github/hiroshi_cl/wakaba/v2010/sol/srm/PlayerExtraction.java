package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class PlayerExtraction {
	static final int[] di = { 0, 1 };
	static final int[] dj = { 1, 0 };

	public String[] extractPlayers(String[] photo, int k, int threshold) {
		int w = photo[0].length();
		int h = photo.length;
		UnionFind uf = new UnionFind(w * h);

		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				for (int d = 0; d < 2; d++)
					if (i + di[d] < w && j + dj[d] < h
							&& photo[j].charAt(i) == k + '0'
							&& photo[j + dj[d]].charAt(i + di[d]) == k + '0')
						uf.union(i + j * w, i + di[d] + (j + dj[d]) * w);

		int[][] map = new int[w][h];
		for (int[] is : map)
			fill(is, -1);

		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				if (uf.size(i + j * w) * 4 >= threshold
						&& photo[j].charAt(i) == k + '0')
					map[i][j] = uf.root(i + j * w);

		// for(int[] is : map)
		// System.out.println(Arrays.toString(is));

		List<Point> lp = new ArrayList<Point>();
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				if (map[i][j] >= 0) {
					int minx = w, maxx = 0;
					int miny = h, maxy = 0;
					int t = map[i][j];
					for (int p = 0; p < w; p++)
						for (int q = 0; q < h; q++)
							if (map[p][q] == t) {
								minx = min(minx, p);
								maxx = max(maxx, p);
								miny = min(miny, q);
								maxy = max(maxy, q);
								map[p][q] = -1;
							}
					lp.add(new Point(minx + maxx, miny + maxy));
				}
		Collections.sort(lp);

		int m = lp.size();
		String[] ret = new String[m];
		for (int i = 0; i < m; i++)
			ret[i] = lp.get(i).toString();

		return ret;
	}

	class Point implements Comparable<Point> {
		int x, y;

		public Point(int x_, int y_) {
			x = x_ + 1;
			y = y_ + 1;
		}

		public int compareTo(Point o) {
			return x != o.x ? x - o.x : y - o.y;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	class UnionFind {
		int[] tree;

		public UnionFind(int s) {
			tree = new int[s];
			fill(tree, -1);
		}

		boolean union(int x, int y) {
			x = root(x);
			y = root(y);
			if (x == y)
				return false;
			if (tree[y] < tree[x]) {
				x ^= y;
				y ^= x;
				x ^= y;
			}
			tree[x] += tree[y];
			tree[y] = x;
			return true;
		}

		boolean find(int x, int y) {
			return root(x) == root(y);
		}

		int size(int x) {
			return -tree[root(x)];
		}

		int root(int x) {
			return tree[x] < 0 ? x : root(tree[x]);
		}
	}
}