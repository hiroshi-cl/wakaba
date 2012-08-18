package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class D2 {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("D.in"));
			System.setOut(new PrintStream("D.out"));
		} catch (Exception e) {
		}
		new D2().run(sc);
	}

	static void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	static int INF = 1 << 27;
	static double EPS = 1e-9;

	static int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	static boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}

	void run(Scanner sc) {
		for (;;) {
			int n = sc.nextInt(), m = sc.nextInt(), p = sc.nextInt(), a = sc
					.nextInt(), b = sc.nextInt();
			if (n + m + p + a + b == 0)
				return;
			a--;
			b--;
			double[] ts = new double[n];
			for (int i = 0; i < n; i++) {
				ts[i] = sc.nextDouble();
			}
			double[][] es = new double[m][m];
			for (int i = 0; i < m; i++)
				fill(es[i], INF);
			V[][] vss = new V[m][1 << n];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < (1 << n); j++) {
					vss[i][j] = new V(i, j);
					vss[i][j].cost = INF;
				}
			}

			for (int i = 0; i < p; i++) {
				int from = sc.nextInt() - 1, to = sc.nextInt() - 1;
				double cost = sc.nextDouble();
				for (int j = 0; j < (1 << n); j++) {
					for (int k = 0; k < n; k++) {
						if ((j & (1 << k)) == 0) {
							vss[from][j].es.add(new E(vss[to][j | (1 << k)],
									cost / ts[k]));
							vss[to][j].es.add(new E(vss[from][j | (1 << k)],
									cost / ts[k]));
						}
					}
				}
			}
			vss[a][0].cost = 0;
			PriorityQueue<E> q = new PriorityQueue<E>();
			q.offer(new E(vss[a][0], 0));
			double res = INF;
			while (!q.isEmpty()) {
				V v = q.poll().to;
				// debug(v.node, v.cost);
				if (v.node == b)
					res = min(res, v.cost);
				for (E e : v.es) {
					if (e.to.cost > v.cost + e.cost) {
						e.to.cost = v.cost + e.cost;
						q.offer(e);
					}
				}
			}
			System.out.println(res == INF ? "Impossible" : res);
		}
	}

	class V {
		ArrayList<E> es = new ArrayList<E>();
		int id;
		int node;
		double cost;

		V(int node, int id) {
			this.id = id;
			this.node = node;
		}
	}

	class E implements Comparable<E> {
		V to;
		double cost;

		E(V to, double cost) {
			this.to = to;
			this.cost = cost;
		}

		public int compareTo(E o) {
			return signum(to.cost + cost - (o.to.cost + o.cost));
		}
	}
}