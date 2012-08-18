package com.github.hiroshi_cl.wakaba.v2010.sol.r2003;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class F2 {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	static final int D = 366;
	// static final int D = 12;
	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("A"));
			System.setOut(new PrintStream("A.out"));
		} catch (Exception e) {}
		loop : for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			Entry[] es = new Entry[n];
			for (int i = 0; i < n; i++) {
				// int s = sc.nextInt() - 1, t = sc.nextInt();
				es[i] = new Entry(sc.nextInt() - 1, sc.nextInt(), sc.nextInt());
			}
			Arrays.sort(es);
			long[][] dp = new long[D][D];
			//			long[][] ndp = new long[D][D];
			//			for (int k = 0; k < n; k++) {
			//				for (int i = 0; i < D; i++) {
			//					for (int j = 0; j < D; j++) {
			//						ndp[i][j] = dp[i][j];
			//						if (i == es[k].t) {
			//							ndp[i][j] = max(ndp[i][j], dp[es[k].s][j] + es[k].w);
			//						}
			//						if (j == es[k].t) {
			//							ndp[i][j] = max(ndp[i][j], dp[i][es[k].s] + es[k].w);
			//						}
			//						if (i > 0) ndp[i][j] = max(ndp[i][j], ndp[i - 1][j]);
			//						if (j > 0) ndp[i][j] = max(ndp[i][j], ndp[i][j - 1]);
			//					}
			//				}
			//				for (int i = 0; i < D; i++)
			//					for (int j = 0; j < D; j++)
			//						dp[i][j] = ndp[i][j];
			//			}
			for (int k = 0, p = 0; k < n; k++) {
				for(int i = p + 1; i <= es[k].t; i++)
					for(int j = 0; j <= p; j++) {
						dp[i][j] = max(dp[i-1][j], j > 0 ? dp[i][j-1] : 0);
						dp[j][i] = max(dp[j][i-1], j > 0 ? dp[j-1][i] : 0);
					}
				for(int i = p + 1; i <= es[k].t; i++)
					for(int j = p + 1; j <= es[k].t; j++)
						dp[i][j] = max(dp[j][i-1], dp[j-1][i]);
				for (int i = es[k].t; i >= 0; i--) {
					dp[i][es[k].t] = max(dp[i][es[k].t], dp[i][es[k].s] + es[k].w);
					dp[es[k].t][i] = max(dp[es[k].t][i], dp[es[k].s][i] + es[k].w);
				}
				p = es[k].t;
			}
			System.out.println(dp[D - 1][D - 1]);
		}
	}
	class Entry implements Comparable<Entry> {
		int s, t, w;
		public Entry(int s, int t, int w) {
			super();
			this.s = s;
			this.t = t;
			this.w = w;
		}
		public int compareTo(Entry o) {
			return t - o.t != 0 ? t - o.t : s - o.s;
		}
		@Override
		public String toString() {
			return String.format("[(%d,%d) w=%d]", s, t, w);
		}
	}
	public static void main(String[] args) {
		new F2().run();
	}
}
//6 1 7 1000 2 9 10000 3 6 5000 8 11 5500 5 10 9000 4 4 6000 0

