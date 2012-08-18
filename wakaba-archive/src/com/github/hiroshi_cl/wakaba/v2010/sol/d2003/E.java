package com.github.hiroshi_cl.wakaba.v2010.sol.d2003;

import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class E {
	public static void main(String[] args) {
		new E().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int w = sc.nextInt(), h = sc.nextInt();
			if ((w | h) == 0)
				return;
			boolean[][] m = new boolean[h][w];

			BitSet fb = new BitSet(100);
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					m[i][j] = sc.nextInt() == 1;
					fb.flip(j * 10 + i);
				}
			}
			int n = min(w, h);
			boolean[][][] car = new boolean[n][][];
			for (int i = 0; i < n; i++) {
				car[i] = new boolean[h - i][w - i];
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < h - i; j++) {
					for (int k = 0; k < w - i; k++) {
						boolean ok = true;
						label: for (int j2 = 0; j2 <= i; j2++) {
							for (int k2 = 0; k2 <= i; k2++) {
								if (!m[j + j2][k + k2]) {
									ok = false;
									break label;
								}
							}
						}
						car[i][j][k] = ok;
					}
				}
			}
			// debug(car);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < h - i; j++) {
					for (int k = 0; k < w - i; k++) {
						if (car[i][j][k]) {
							for (int i2 = 0; i2 < i; i2++) {
								for (int j2 = 0; j2 <= i - i2; j2++) {
									for (int k2 = 0; k2 <= i - i2; k2++) {
										if (car[i2][j + j2][k + k2])
											car[i2][j + j2][k + k2] = false;
									}
								}
							}
						}
					}
				}
			}
			// debug(car);
			for (int sz = n; sz > 0; sz--) {
				Queue<V2> q = new LinkedList<V2>();
				for (int i = 0; i <= h - sz; i++) {
					for (int j = 0; j <= w - sz; j++) {
						if (car[sz - 1][i][j])
							q.offer(new V2(sz, i, j));

					}
				}
				int max = q.size();
				Queue<V2> qq = new LinkedList<V2>(q);
				while (!qq.isEmpty()) {
					q = qq;
					qq = new LinkedList<V2>();
					for (V2 v : q) {
						BitSet bb = new BitSet(100);
						for (V2 e : q) {
							bb.or(e.bs);
						}
						BitSet bbb = ((BitSet) fb.clone());
						bbb.andNot(bb);
						if (bbb.cardinality() != 0) {
							qq.offer(v);
						}
					}
				}
			}
			int res = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < h - i; j++) {
					for (int k = 0; k < w - i; k++) {
						if (car[i][j][k])
							res++;
					}
				}
			}
			System.out.println(res);
		}
	}

	static class V2 {
		int x, y;

		V2(int sz, int X, int Y) {
			x = X;
			y = Y;
			bs = new BitSet(10 * 10);
			for (int i = 0; i < sz; i++)
				for (int j = 0; j < sz; j++)
					bs.flip(i * 10 + j);
		}

		BitSet bs;
	};
}