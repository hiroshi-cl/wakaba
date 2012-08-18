package com.github.hiroshi_cl.wakaba.v2010.sol.d2003;

import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class D {
	public static void main(String[] args) {
		new D().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			if (n == 0)
				return;
			UnionFind uf = new UnionFind(n);
			double[] xs = new double[n], ys = new double[n], zs = new double[n], rs = new double[n];
			for (int i = 0; i < n; i++) {
				xs[i] = sc.nextDouble();
				ys[i] = sc.nextDouble();
				zs[i] = sc.nextDouble();
				rs[i] = sc.nextDouble();
			}
			PriorityQueue<Edge> q = new PriorityQueue<Edge>();
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					double dis = hypot(hypot(xs[i] - xs[j], ys[i] - ys[j]),
							zs[i] - zs[j]) - (rs[i] + rs[j]);
					if (dis <= 0) {
						uf.union(i, j);
					} else {
						q.offer(new Edge(i, j, dis));
					}
				}
			}
			double res = 0;
			while (!q.isEmpty()) {
				Edge e = q.poll();
				if (uf.find(e.u, e.v)) {

				} else {
					res += e.length;
					uf.union(e.u, e.v);
				}
			}
			System.out.printf("%.3f%n", res);
		}
	}

	class Edge implements Comparable<Edge> {
		double length;
		int u, v;

		public int compareTo(Edge o) {
			return (int) signum(length - o.length);
		}

		Edge(int u, int v, double dis) {
			this.u = u;
			this.v = v;
			length = dis;
		}
	}

	static class UnionFind {
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
}
