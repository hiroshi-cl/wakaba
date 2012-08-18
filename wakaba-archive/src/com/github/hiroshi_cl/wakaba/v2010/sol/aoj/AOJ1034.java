package com.github.hiroshi_cl.wakaba.v2010.sol.aoj;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

class AOJ1034 {
	static final int[] di = { 1, 0, -1, 0 };
	static final int[] dj = { 0, 1, 0, -1 };

	public void run() {
		Scanner sc = new Scanner(System.in);
		for (n = sc.nextInt(); n > 0; n = sc.nextInt()) {
			map = new int[n][n];
			int sum = 0;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					sum += map[i][j] = sc.nextInt();
			if (sum != 0) {
				System.out.println("NO");
				continue;
			}

			m = 0;
			si = new int[n * n];
			sj = new int[n * n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (map[i][j] < 0) {
						si[m] = i;
						sj[m] = j;
						m++;
					}
			used = new boolean[n][n];
			System.out.println(dfs(si[0], sj[0], 0, 0) ? "YES" : "NO");
		}
	}

	int n, m;
	int[][] map;
	boolean[][] used;
	int[] si, sj;

	boolean in(int i, int j) {
		return 0 <= i && i < n && 0 <= j && j < n;
	}

	boolean dfs(int i, int j, int k, int sum) {
		if (!in(i, j) || (map[i][j] < 0 && sum != 0) || (sum += map[i][j]) > 0
				|| used[i][j])
			return false;
		used[i][j] = true;
		if (sum == 0) {
			if (++k == m)
				return true;
			else if (dfs(si[k], sj[k], k, 0))
				return true;
		} else
			for (int d = 0; d < 4; d++)
				if (dfs(i + di[d], j + dj[d], k, sum))
					return true;
		used[i][j] = false;
		return false;
	}

}
