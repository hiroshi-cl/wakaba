package com.github.hiroshi_cl.wakaba.v2010.sol.dp2010;

import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class D2 {
	public static void main(String[] args) {
		new D2().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			String s = this.getClass().getSimpleName();
			sc = new Scanner(new File("D"));
			System.setOut(new PrintStream(s + ".out"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		for (;;) {
			int N = sc.nextInt(), M = sc.nextInt();
			if ((N | M) == 0)
				return;
			debug(N, M);
			V[][] vs = new V[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					vs[i][j] = new V(i, j);
				}
			}
			int[][] riku = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					riku[i][j] = 1 << 28;
				}
			}
			for (int i = 0; i < M; i++) {
				int x = sc.nextInt() - 1, y = sc.nextInt() - 1;
				int d = sc.nextInt();
				char c = sc.next().charAt(0);
				if (c == 'L') {
					// for(int j=0;j<N;j++){
					// vs[x][j].es.add(new E(vs[y][j],d));
					// vs[y][j].es.add(new E(vs[x][j],d));
					riku[x][y] = riku[y][x] = min(riku[x][y], d);
					// }
				} else {
					vs[x][x].es.add(new E(vs[y][y], d));
					vs[y][y].es.add(new E(vs[x][x], d));
				}
			}
			// for (int k = 0; k < N; k++) {
			// for (int i = 0; i < N; i++) {
			// for (int j = 0; j < N; j++) {
			// if(riku[i][j] > riku[i][k] + riku[k][j])riku[i][j] =
			// riku[i][k]+riku[k][j];
			// }
			// }
			// }
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					for (int j2 = 0; j2 < N; j2++) {
						if (riku[i][j] < 1 << 28) {
							vs[i][j2].es.add(new E(vs[j][j2], riku[i][j]));
							vs[j][j2].es.add(new E(vs[i][j2], riku[i][j]));
						}
					}
				}
			}

			int R = sc.nextInt();
			int[] zs = new int[R];
			for (int i = 0; i < R; i++) {
				zs[i] = sc.nextInt() - 1;
			}
			int INF = 1 << 28;
			PriorityQueue<E> que = new PriorityQueue<E>();
			// que.offer(new E(vs[zs[0]][zs[0]],0));
			vs[zs[0]][zs[0]].d = 0;
			for (int i = 0; i < R - 1; i++) {
				debug(i);
				for (int j = 0; j < N; j++)
					if (j != zs[i]) {
						for (int k = 0; k < N; k++) {
							vs[j][k].d = INF;
						}
					}
				HashSet<V> set = new HashSet<V>();

				for (int j = 0; j < N; j++) {
					que.offer(new E(vs[zs[i]][j], vs[zs[i]][j].d));
					// set.add(new E(vs[zs[i]][j], vs[zs[i]][j].d));
				}

				// PriorityQueue<E> nque = new PriorityQueue<E>();
				while (!que.isEmpty()) {
					V now = que.poll().to;
					set.add(now);
					for (E e : now.es)
						if (!set.contains(e.to)) {
							if (e.to.d > e.d + now.d) {
								e.to.d = e.d + now.d;
								// E nxt = new E(e.to, e.to.d);
								// if (!set.contains(nxt)){
								que.offer(new E(e.to, e.to.d));
								// set.add(nxt);
								// }
							}
						}
				}
			}
			int res = INF;
			for (int i = 0; i < N; i++) {
				res = min(res, vs[zs[R - 1]][i].d);
			}
			System.out.println(res);
		}
	}

	class V {
		ArrayList<E> es = new ArrayList<E>();
		int id;
		int p;

		int d = 1 << 28;

		V(int id, int p) {
			this.id = id;
			this.p = p;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + id;
			result = prime * result + p;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			V other = (V) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id != other.id)
				return false;
			if (p != other.p)
				return false;
			return true;
		}

		private D2 getOuterType() {
			return D2.this;
		}

	}

	class E implements Comparable<E> {
		V to;

		E(V to, int d) {
			this.to = to;
			this.d = d;
		}

		int d;

		@Override
		public int compareTo(E o) {
			// TODO 自動生成されたメソッド・スタブ
			return d - o.d;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			E other = (E) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (to == null) {
				if (other.to != null)
					return false;
			} else if (!to.equals(other.to))
				return false;
			return true;
		}

		private D2 getOuterType() {
			return D2.this;
		}

	}
}
