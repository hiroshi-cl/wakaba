package com.github.hiroshi_cl.wakaba.v2010.sol.dp2010;

import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class C {
	public static void main(String[] args) {
		new C().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	static final long INF = 1L << 40;

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			String s = this.getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(s + ".out"));
		} catch (Exception e) {
		}
		for (;;) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			if (N == 0 && M == 0)
				return;
			int[] C = new int[M];
			int[] x = new int[N];
			for (int i = 0; i < M; i++)
				C[i] = sc.nextInt();
			for (int i = 0; i < N; i++)
				x[i] = sc.nextInt();
			long[][] dp = new long[N + 1][256];
			for (int i = 0; i < 256; i++)
				dp[0][i] = INF;
			dp[0][128] = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < 256; j++)
					dp[i + 1][j] = INF;
				for (int j = 0; j < 256; j++)
					for (int k = 0; k < M; k++)
						dp[i + 1][max(min(j + C[k], 255), 0)] = min(
								dp[i + 1][max(min(j + C[k], 255), 0)], dp[i][j]);
				for (int j = 0; j < 256; j++)
					dp[i + 1][j] += (j - x[i]) * (j - x[i]);
			}
			long ret = Long.MAX_VALUE;
			for (int i = 0; i < 256; i++)
				ret = min(ret, dp[N][i]);
			System.out.println(ret);
		}
	}
}
