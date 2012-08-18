package com.github.hiroshi_cl.wakaba.v2010.sol.codeforces;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.lang.Character.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class Beta013E {
	public void run() {
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner();
		StringBuilder sb = new StringBuilder(1000000);
		// System.setOut(new PrintStream(new BufferedOutputStream(System.out)));
		final int N = sc.nextInt();
		final int M = sc.nextInt();
		final int K = (int) floor(sqrt(N));
		final int[] A = new int[N + 1];
		A[0] = 1;
		for (int i = 1; i <= N; i++)
			A[i] = sc.nextInt();
		final int[] next = new int[N + 1];
		final int[] step = new int[N + 1];
		final int[] last = new int[N + 1];
		for (int i = N; i > 0; i--) {
			int j = i + A[i];
			if (j > N || j / K > i / K) {
				last[i] = i;
				step[i] = 1;
				next[i] = j;
			} else {
				last[i] = last[j];
				step[i] = step[j] + 1;
				next[i] = next[j];
			}
		}
		for (int t = 0; t < M; t++)
			if (sc.nextInt() == 1) {
				int i = sc.nextInt();
				int j = 0;
				int k = 0;
				while (i <= N) {
					j += step[i];
					k = last[i];
					i = next[i];
				}
				sb.append(k).append(' ').append(j).append('\n');
				// System.out.println(k + " " + j);
			} else {
				int k = sc.nextInt();
				int b = k / K * K;
				A[k] = sc.nextInt();
				for (int i = min(b + K - 1, N); i >= b; i--) {
					int j = i + A[i];
					if (j > N || j / K > i / K) {
						last[i] = i;
						step[i] = 1;
						next[i] = j;
					} else {
						last[i] = last[j];
						step[i] = step[j] + 1;
						next[i] = next[j];
					}
				}
			}
		// System.out.flush();
		System.out.print(sb);
	}

	void debug(Object... os) {
		System.err.println(Arrays.deepToString(os));
	}

	public static void main(String... args) {
		new Beta013E().run();
	}

	//
	// class Scanner {
	// final java.util.Scanner sc;
	//
	// public double nextDouble() {
	// return Double.parseDouble(sc.next());
	// }
	//
	// public Scanner(InputStream is) {
	// this.sc = new java.util.Scanner(is);
	// }
	//
	// public boolean hasNext() {
	// return sc.hasNext();
	// }
	//
	// public String next() {
	// return sc.next();
	// }
	//
	// public int nextInt() {
	// return Integer.parseInt(sc.next());
	// }
	//
	// public String nextLine() {
	// return sc.nextLine();
	// }
	//
	// public long nextLong() {
	// return Long.parseLong(sc.next());
	// }
	// }
	class Scanner {
		int nextInt() {
			try {
				int c = System.in.read();
				if (c == -1)
					return c;
				while (c != '-' && (c < '0' || '9' < c)) {
					c = System.in.read();
					if (c == -1)
						return c;
				}
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
	}
}
