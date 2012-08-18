package com.github.hiroshi_cl.wakaba.v2010.sol.gcj2009.r2;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class B {
	static final int INF = 1 << 20;
	static final int[] di = { -1, 0, 0, 1 };
	static final int[] dj = { 0, -1, 1, 0 };
	static final char PROB = 'B';
	static final boolean SAMP = false;
	static final boolean PRAC = true;

	private String solve(Scanner sc) {
		int R = sc.nextInt();
		int C = sc.nextInt();
		int F = sc.nextInt();
		boolean[][] rock = new boolean[R][C];
		for (int i = 0; i < R; i++) {
			char[] cs = sc.next().toCharArray();
			for (int j = 0; j < C; j++)
				rock[i][j] = cs[j] == '#';
		}
		int[][] height = new int[R][C];
		for (int i = R - 2; i >= 0; i--)
			for (int j = 0; j < C; j++)
				height[i][j] = (i == R - 2 || rock[i + 2][j] ? 1
						: height[i + 1][j] + 1);

		int[][][] dp = new int[R][C][C];
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				for (int k = 0; k < C; k++)
					dp[i][j][k] = INF;
		dp[0][0][0] = 0;

		for (int i = 0; i < R - 1; i++)
			for (int j = 0; j < C; j++)
				for (int k = 0; k < C; k++)
					if (dp[i][j][k] < INF) {
						int l = j;
						int r = j;
						while (l > 0 && rock[i + 1][l - 1]
								&& (!rock[i][l - 1] || k < l))
							l--;
						while (r < C - 1 && rock[i + 1][r + 1]
								&& (!rock[i][r + 1] || k > r))
							r++;
						// debug(i, j, k, l, r);
						if (l > 0 && !rock[i + 1][l - 1]
								&& (!rock[i][l - 1] || k < l)
								&& height[i][l - 1] <= F)
							dp[i + height[i][l - 1]][l - 1][l - 1] = min(dp[i
									+ height[i][l - 1]][l - 1][l - 1],
									dp[i][j][k]);
						if (r < C - 1 && !rock[i + 1][r + 1]
								&& (!rock[i][r + 1] || k > r)
								&& height[i][r + 1] <= F)
							dp[i + height[i][r + 1]][r + 1][r + 1] = min(dp[i
									+ height[i][r + 1]][r + 1][r + 1],
									dp[i][j][k]);
						if (l < r)
							for (int nj = l; nj <= r; nj++)
								if (height[i][nj] <= F)
									if (height[i][nj] > 1 || nj == l || nj == r)
										dp[i + height[i][nj]][nj][nj] = min(
												dp[i + height[i][nj]][nj][nj],
												dp[i][j][k] + 1);
									else
										for (int nk = l; nk <= r; nk++)
											dp[i + 1][nj][nk] = min(
													dp[i + 1][nj][nk],
													dp[i][j][k] + abs(nk - nj)
															+ 1);
					}

		// for(int[][] iss : dp) {
		// for(int i = 0; i < C; i++)
		// System.err.print((char)(iss[i][i] < INF ? '0' + iss[i][i] : '#') +
		// " ");
		// System.err.println();
		// }
		// for(int[][] iss : dp) {
		// for(int[] is : iss)
		// debug(is);
		// System.err.println();
		// }

		int D = INF;
		for (int i = 0; i < C; i++)
			D = min(D, dp[R - 1][i][i]);
		return (D == INF ? "No" : "Yes " + D);
	}

	public void run() {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		sc.nextLine();
		for (int t = 1; t <= T; t++)
			System.out.printf("Case #%d: %s%n", t, solve(sc));
	}

	public static void main(String... args) throws IOException {
		if (!SAMP) {
			for (int i = 0; new File(smallName(i) + ".in").exists(); i++)
				if (!new File(smallName(i) + ".out").exists()) {
					System.setIn(new FileInputStream(smallName(i) + ".in"));
					System.setOut(new PrintStream(smallName(i) + ".out"));
					new B().run();
				}
			if (new File(largeName() + ".in").exists()) {
				System.setIn(new FileInputStream(largeName() + ".in"));
				System.setOut(new PrintStream(largeName() + ".out"));
				new B().run();
			}
		} else
			new B().run();
	}

	private static String largeName() {
		return PROB + "-large" + (PRAC ? "-practice" : "");
	}

	private static String smallName(int a) {
		return PROB + "-small"
				+ (PRAC ? a == 0 ? "-practice" : "" : "-attempt" + a);
	}

	private static void debug(Object... os) {
		// System.err.println(Arrays.deepToString(os));
	}
}