package com.github.hiroshi_cl.wakaba.v2010.sol.r2003;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class G2 {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}
		static final int D = 366;
//	static final int D = 12;
	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			Entry[] es = new Entry[n];
			for (int i = 0; i < n; i++) 
				es[i] = new Entry(sc.nextInt() - 1, sc.nextInt(), sc.nextInt());
			Arrays.sort(es);
			long[][] dp  = new long[D][D];
			long[][] ndp = new long[D][D];
			for (int k = 0; k < n; k++) {
				for (int i = 0; i < D; i++) {
					for (int j = 0; j < D; j++) {
						ndp[i][j] = dp[i][j];
						if (i == es[k].t) dp[i][j] = max(dp[i][j], ndp[es[k].s][j] + es[k].w);
						if (j == es[k].t) dp[i][j] = max(dp[i][j], ndp[i][es[k].s] + es[k].w);
						if (i > 0) dp[i][j] = max(dp[i][j], dp[i - 1][j]);
						if (j > 0) dp[i][j] = max(dp[i][j], dp[i][j - 1]);
					}
				}
			}
			System.out.println(dp[D-1][D-1]);
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
	}
}