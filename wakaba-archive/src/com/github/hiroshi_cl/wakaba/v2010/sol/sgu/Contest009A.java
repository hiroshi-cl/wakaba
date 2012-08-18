package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class Contest009A {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		// int o = 0;
		// while (sc.hasNext()) {
		// o++;
		// sc.next();
		// sc.next();
		// System.out.printf("Test #%d%n", o);
		int n = sc.nextInt(), m = sc.nextInt();
		V[] vs = new V[n];
		for (int i = 0; i < n; i++) {
			vs[i] = new V();
		}
		for (int i = 0; i < m; i++) {
			int s = sc.nextInt() - 1, t = sc.nextInt() - 1, c = sc.nextInt() - 1;
			vs[s].es.add(new E(vs[t], c));
			// vs[t].es.add(new E(vs[s], c));
		}
		fill(vs[0].step, 0);
		PriorityQueue<E> que = new PriorityQueue<E>();
		for (int c = 0; c < 3; c++)
			que.offer(new E(vs[0], c));
		while (!que.isEmpty()) {
			E e = que.poll();
			for (E ne : e.to.es)
				if (e.c != ne.c) {
					if (ne.to.step[ne.c] > e.to.step[e.c] + 1) {
						ne.to.step[ne.c] = e.to.step[e.c] + 1;
						que.offer(ne);
					}
				}
		}
		int res = INF;
		for (int i = 0; i < 3; i++) {
			res = min(res, vs[n - 1].step[i]);
		}
		System.out.println(res == INF ? -1 : res);
		System.out.println();
		// }
	}

	int INF = 1 << 28;

	class V {
		ArrayList<E> es = new ArrayList<E>();
		int[] step = new int[3];

		V() {
			fill(step, INF);
		}
	}

	class E implements Comparable<E> {
		int c;
		V to;

		E(V to, int c) {
			this.to = to;
			this.c = c;
		}

		public int compareTo(E o) {
			return to.step[c] - o.to.step[o.c];
		}
	}

	public static void main(String[] args) {
		new Contest009A().run();
	}
}
