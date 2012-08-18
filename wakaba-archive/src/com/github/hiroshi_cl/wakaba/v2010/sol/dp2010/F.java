package com.github.hiroshi_cl.wakaba.v2010.sol.dp2010;

import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class F {
	public static void main(String[] args) {
		new F().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

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
			int K = sc.nextInt();
			if (N == 0 && M == 0 && K == 0)
				return;
			int[] X = new int[N];
			int[] L = new int[N];
			int[] F = new int[N];
			int[] D = new int[N];
			boolean[] UD = new boolean[N];
			double[] mov = new double[N];
			double[] rev = new double[N];
			double[] nxt = new double[N];
			for (int i = 0; i < N; i++) {
				X[i] = sc.nextInt();
				L[i] = sc.nextInt();
				F[i] = sc.nextInt();
				D[i] = sc.nextInt();
				UD[i] = sc.nextInt() == 0;
				if (UD[i]) {
					mov[i] = 1. * L[i] / F[i];
					rev[i] = 1. * L[i] / D[i];
				} else {
					mov[i] = 1. * L[i] / D[i];
					rev[i] = 1. * L[i] / F[i];
					nxt[i] = rev[i];
				}
			}
			int[] id = new int[K + 2 * M + 1];
			fill(id, -1);
			for (int i = 0; i < N; i++)
				id[X[i]] = i;
			int[] V = new int[M];
			for (int i = 0; i < M; i++)
				V[i] = sc.nextInt();
			double[] time1 = new double[K + 2 * M + 1];
			double[] time2 = new double[K + 2 * M + 1];
			fill(time1, Double.NEGATIVE_INFINITY);
			fill(time2, Double.NEGATIVE_INFINITY);
			for (int i = 0; i < M; i++) {
				for (int j = 0; j < K + 2 * M; j++) {
					time1[j] = max(time1[j + 1], j == 0 ? 1. * i / V[i]
							: time2[j - 1] + 1. / V[i]);
					if (id[j] < 0) {
						time2[j] = max(time1[j], time2[j + 1]);
					} else {
						time2[j] = max(max(time1[j], nxt[id[j]]) + mov[id[j]],
								time2[j + 1]);
						nxt[id[j]] = max(time1[j], nxt[id[j]]) + mov[id[j]]
								+ rev[id[j]];
					}
				}
				// debug(time);
			}
			System.out.println(time2[K]);
		}
	}
}
