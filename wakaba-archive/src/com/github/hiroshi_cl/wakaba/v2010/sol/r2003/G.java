package com.github.hiroshi_cl.wakaba.v2010.sol.r2003;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import java.math.*;
import java.util.*;
class G {
	public static void main(String[] args) {
		new G().run();
	}
	static void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner();
		for (;;) {
			int n = sc.nextInt();
			if (n == 0)
				return;
			int d = 365;
			ArrayList<Entry>[] lists = new ArrayList[d+1];
			for (int i = 0; i <= d; i++) {
				lists[i] = new ArrayList<Entry>();
			}
			for (int i = 0; i < n; i++) {
				int s = sc.nextInt() - 1, t = sc.nextInt();
				lists[t].add(new Entry(s, sc.nextInt()));
			}
			int[][] dp = new int[d + 1][d + 1];
			for (int i = 1; i <= d; i++) {
				for (Entry e : lists[i]) {
					for (int j = 0; j <= i; j++) {
						dp[j][i] = max(dp[j][i], dp[j][i - 1]);
						if (j > 0)
							dp[j][i] = max(dp[j][i], dp[j - 1][i]);
						dp[j][i] = max(dp[j][i], dp[j][e.s] + e.w);
					}
					for (int j = 0; j < i; j++) {
						dp[i][j] = max(dp[i][j], dp[i - 1][j]);
						if (j > 0)
							dp[i][j] = max(dp[i][j], dp[i][j - 1]);
						dp[i][j] = max(dp[i][j], dp[e.s][j] + e.w);
					}
				}
				for (int j = 0; j <= i; j++) {
					dp[j][i] = max(dp[j][i], dp[j][i - 1]);
					if (j > 0)
						dp[j][i] = max(dp[j][i], dp[j - 1][i]);
				}
				for (int j = 0; j < i; j++) {
					dp[i][j] = max(dp[i][j], dp[i - 1][j]);
					if (j > 0)
						dp[i][j] = max(dp[i][j], dp[i][j - 1]);
				}
			}
			System.out.println(dp[d][d]);
		}
	}
	class Entry {
		int s, w;
		public Entry(int t, int w) {
			super();
			this.s = t;
			this.w = w;
		}

	}
	class Scanner {
		int nextInt() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextInt();
				int res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		long nextLong() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextLong();
				long res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		double nextDouble() {
			return Double.parseDouble(next());
		}
		String next() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (Character.isWhitespace(c))
					c = System.in.read();
				do {
					res.append((char) c);
				} while (!Character.isWhitespace(c = System.in.read()));
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
		String nextLine() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (c == '\r' || c == '\n')
					c = System.in.read();
				do {
					res.append((char) c);
					c = System.in.read();
				} while (c != '\r' && c != '\n');
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
	}
}