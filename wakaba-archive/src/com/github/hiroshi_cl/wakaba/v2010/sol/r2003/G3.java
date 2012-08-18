package com.github.hiroshi_cl.wakaba.v2010.sol.r2003;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class G3 {
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
			for (int k = 0, p = 0; k < n; p = es[k].t + 1, k++) {
				for(int i = p; i <= es[k].t; i++)
					for(int j = 0; j < p; j++) {
						dp[i][j] = max(i > 0 ? dp[i-1][j] : 0, j > 0 ? dp[i][j-1] : 0);
						dp[j][i] = max(i > 0 ? dp[j][i-1] : 0, j > 0 ? dp[j-1][i] : 0);
					}
				for(int i = p; i <= es[k].t; i++)
					for(int j = p; j <= es[k].t; j++)
						dp[i][j] = max(i > 0 ? dp[i-1][j] : 0, j > 0 ? dp[i][j-1] : 0);
				for (int i = es[k].t; i >= 0; i--) {
					dp[i][es[k].t] = max(dp[i][es[k].t], dp[i][es[k].s] + es[k].w);
					dp[es[k].t][i] = max(dp[es[k].t][i], dp[es[k].s][i] + es[k].w);
				}
			}
			System.out.println(dp[es[n-1].t][es[n-1].t]);
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