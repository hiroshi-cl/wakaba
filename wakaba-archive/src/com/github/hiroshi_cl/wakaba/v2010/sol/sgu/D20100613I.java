package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class D20100613I {
	public static void main(String[] args) {
		new D20100613I().run();
	}

	void debug(Object... os) {
		System.err.println(Arrays.deepToString(os));
	}

	static final int INF = 1 << 24;

	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		int P = sc.nextInt();
		int[] B = new int[K];
		for (int i = 0; i < K; i++)
			B[i] = sc.nextInt();
		int[] A = new int[N];
		int[] C = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = sc.nextInt();
			C[i] = sc.nextInt();
		}

		L[] lt = new L[K];
		for (int i = 0; i < K; i++)
			lt[i] = new L();
		for (int i = 0; i < N; i++)
			lt[C[i] - 1].add(new Tooth(i + 1, A[i], C[i] - 1));
		for (int i = 0; i < K; i++)
			sort(lt[i]);
		int[][] dp = new int[K + 1][N + 1];
		int[][] pre = new int[K + 1][N + 1];
		for (int i = 0; i <= K; i++)
			for (int j = 1; j <= N; j++)
				dp[i][j] = INF;
		for (int i = 0; i < K; i++) {
			int n = lt[i].size();
			int[] d = new int[n + 1];
			for (int j = 1; j <= n; j++)
				d[j] = (j > 1 ? d[j - 1] : B[i]) + lt[i].get(j - 1).treat;
			for (int j = 0; j < N; j++) {
				dp[i + 1][j + 1] = dp[i][j + 1];
				pre[i + 1][j + 1] = j + 1;
			}
			for (int j = 0; j < N; j++) {
				for (int k = 1; k <= n && j + k <= N; k++)
					if (dp[i][j] + d[k] < dp[i + 1][j + k]) {
						dp[i + 1][j + k] = dp[i][j] + d[k];
						pre[i + 1][j + k] = j;
					}
			}
		}
		int n = N;
		while (n > 0 && dp[K][n] > P)
			n--;
		System.out.println(n);
		for (int i = K, j = n; i > 0; j = pre[i][j], i--)
			for (int k = 0; k < j - pre[i][j]; k++)
				System.out.print(lt[i - 1].get(k).id + " ");
		System.out.println();
	}

	class L extends ArrayList<Tooth> {
	};

	class Tooth implements Comparable<Tooth> {
		final int id, treat, group;

		public Tooth(int id, int treat, int group) {
			super();
			this.id = id;
			this.treat = treat;
			this.group = group;
		}

		@Override
		public int compareTo(Tooth o) {
			return group != o.group ? group - o.group : treat - o.treat;
		}
	}
}
