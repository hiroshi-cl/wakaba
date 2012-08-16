package com.github.hiroshi_cl.wakaba.v2009.lib.notClassified;

import java.util.*;

public class Die {
	private static final int[][] FM = { { 1, 2, 4, 5 }, { 1, 5, 4, 2 } };
	private static final int[][] DR = { { 3, 3, 1, 3 }, { 1, 1, 3, 1 } };
	private static final int[] di = { 1, 0, -1, 0 };
	private static final int[] dj = { 0, 1, 0, -1 };
	private final int[] num = { 1, 2, 3, 6, 5, 4 };
	private int cf = 0, cd = 0;

	public int roll(int d) {
		return roll2(d, (cd + d) % 4);
	}

	public int iroll(int d) {
		return roll2(d, (cd + d + 2) % 4);
	}

	private int roll2(int d, int c) {
		int f = cf % 2;
		cf = (cf + FM[f][c]) % 6;
		cd = (cd + DR[f][c]) % 4;
		return num[cf];
	}

	public static int ni(int i, int d) {
		return i + di[d];
	}

	public static int nj(int j, int d) {
		return j + dj[d];
	}

	public static class Checker {
		private final int[][] map;
		private final int n, m;

		public Checker(int[][] min) {
			map = min;
			m = map.length;
			n = map[0].length;
		}

		public boolean check() {
			boolean res = true;

			int[] c = new int[7];
			for (int i = 0; i < m; i++)
				for (int j = 0; j < n; j++)
					c[map[i][j]]++;
			for (int i = 1; i <= 6; i++)
				res &= c[i] == 1;

			if (res) {
				int[] num = new int[6];
				int oi = 0, oj = 0;
				for (int i = 0; i < m; i++)
					for (int j = 0; j < n; j++)
						if (map[i][j] == 1) {
							oi = i;
							oj = j;
							num[0] = 1;
						}
				write(new Die(), map, num, oi, oj);

				if (res)
					for (int i = 0; i < 3; i++)
						res &= num[i] + num[i + 3] == 7;
			}
			return res;
		}

		private void write(Die c, int[][] map, int[] num, int i, int j) {
			for (int d = 0; d < 4; d++) {
				int ni = ni(i, d);
				int nj = nj(j, d);
				if (inRec(m, n, ni, nj)) {
					int t = map[ni][nj];
					if (t > 0) {
						map[ni][nj] = 0;
						c.roll(d);
						num[c.cf] = t;
						write(c, map, num, ni, nj);
						c.iroll(d);
						map[ni][nj] = t;
					}
				}
			}
		}

		private static boolean inRec(int m, int n, int i, int j) {
			return i >= 0 && j >= 0 && i < m && j < n;
		}
	}

	public static void main(String[] args) {
		final int S = 5;
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		while (N-- > 0) {
			int[][] map = new int[S][S];
			for (int i = 0; i < S; i++)
				for (int j = 0; j < S; j++)
					map[i][j] = sc.nextInt();

			System.out.println(new Checker(map).check() ? "true" : "false");
		}
	}
}