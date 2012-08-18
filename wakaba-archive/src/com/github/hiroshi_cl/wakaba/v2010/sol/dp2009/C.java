package com.github.hiroshi_cl.wakaba.v2010.sol.dp2009;

import java.math.*;
import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class C {
	static void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	public static void main(String[] args) {
		new C().run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("C"));
			System.setOut(new PrintStream("C.out"));
		} catch (Exception e) {
		}
		for (;;) {
			int m = sc.nextInt(), n = sc.nextInt(), l = sc.nextInt();
			if ((m | n | l) == 0)
				return;
			vs = new V[m][l + 1];
			for (int i = 0; i < m; i++)
				for (int j = 0; j <= l; j++)
					vs[i][j] = new V(i,j);
			for (int i = 0; i < n; i++) {
				int a = sc.nextInt() - 1, b = sc.nextInt() - 1;
				int dist = sc.nextInt(), arm = sc.nextInt();
				for (int j = 0; j <= l; j++) {
					vs[a][j].es.add(new E(vs[b][j], arm));
					vs[b][j].es.add(new E(vs[a][j], arm));
					if (j + dist <= l) {
						vs[a][j].es.add(new E(vs[b][j + dist], 0));
						vs[b][j].es.add(new E(vs[a][j + dist], 0));
					}
				}
			}
			Queue<E> que = new PriorityQueue<E>();
			vs[0][0].arm = 0;
			que.offer(new E(vs[0][0], 0));
			while (!que.isEmpty()) {
				V v = que.poll().to;
				debug(v.id,v.arm,v.dist);
				for (E e : v.es) {
					if (e.to.arm > e.arm + v.arm) {
						e.to.arm = e.arm + v.arm;
						que.offer(e);
					}
				}
			}
			int res = INF;
			for (int i = 0; i <= l; i++) {
				debug(vs[m-1][i].arm);
				res = min(res, vs[m - 1][i].arm);
			}
			System.out.println(res);
		}
	}

	int INF = 1 << 28;

	V[][] vs;

	class V {
		ArrayList<E> es = new ArrayList<E>();
		int id,dist;
		V(int id,int dist){
			this.id=id;this.dist=dist;
		}
		int arm = INF;
	}

	class E implements Comparable<E> {
		V to;
		int arm;

		E(V to, int arm) {
			this.to = to;
			this.arm = arm;
		}

		public int compareTo(E o) {
			return arm - o.arm;
		}
	}
}