package com.github.hiroshi_cl.wakaba.v2010.sol.dp2008;

import java.util.*;

public class D {
	public static void main(String[] args) {
		final int INF = 1 << 25;
		Scanner sc = new Scanner(System.in);
		while (true) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			int L = sc.nextInt();
			int K = sc.nextInt();
			int A = sc.nextInt();
			int H = sc.nextInt();
			if (N == 0)
				break;
			int[] ctrs = new int[L + 2];
			ctrs[0] = A;
			ctrs[L + 1] = H;
			for (int i = 1; i <= L; i++)
				ctrs[i] = sc.nextInt();
			int[][] m = new int[N][N];
			for (int i = 0; i < N; i++)
				for (int j = i; j < N; j++)
					m[i][j] = m[j][i] = INF;
			for (int i = 0; i < K; i++) {
				int p = sc.nextInt();
				int q = sc.nextInt();
				m[p][q] = m[q][p] = sc.nextInt();
			}
			for (int k = 0; k < N; k++)
				for (int i = 0; i < N; i++)
					for (int j = 0; j < N; j++)
						m[i][j] = Math.min(m[i][j], m[i][k] + m[k][j]);
			int[][] l = new int[L + 2][L + 2];
			for (int i = 0; i < L + 2; i++)
				for (int j = 0; j < L + 2; j++)
					if (m[ctrs[i]][ctrs[j]] > M)
						l[i][j] = INF;
					else
						l[i][j] = m[ctrs[i]][ctrs[j]];
			for (int k = 0; k < L + 2; k++)
				for (int i = 0; i < L + 2; i++)
					for (int j = 0; j < L + 2; j++)
						l[i][j] = Math.min(l[i][j], l[i][k] + l[k][j]);
			int ans = l[0][L + 1];
			if (ans >= INF)
				System.out.println("Help!");
			else if (ans <= M)
				System.out.println(ans);
			else
				System.out.println(ans * 2 - M);
		}

	}
}