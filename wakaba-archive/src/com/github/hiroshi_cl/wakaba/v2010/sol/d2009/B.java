package com.github.hiroshi_cl.wakaba.v2010.sol.d2009;

import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class B {
	void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("B"));
			System.setOut(new PrintStream("B.out"));
		} catch (Exception e) {
		}
		for (;;) {
			w = sc.nextInt();
			h = sc.nextInt();
			if (w + h == 0)
				return;
			map = new int[h][w];
			for (int i = 0; i < h; i++)
				for (int j = 0; j < w; j++)
					map[i][j] = sc.nextInt();
			int res = 0;
			boolean[][] check = new boolean[h][w];
			for (int i = 0; i < h; i++)
				for (int j = 0; j < w; j++) {
					if (map[i][j] == 1 && !check[i][j]) {
						dfs(check, i, j);
						res++;
					}
				}
			System.out.println(res);
		}
	}

	int h, w;
	int[] dx = { 1, 1, 1, 0, 0, -1, -1, -1 };
	int[] dy = { 1, 0, -1, 1, -1, 1, 0, -1 };

	void dfs(boolean[][] check, int x, int y) {
		if (check[x][y])
			return;
		check[x][y] = true;
		for (int d = 0; d < 8; d++) {
			int nx = x + dx[d], ny = y + dy[d];
			if (0 <= nx && nx < h && 0 <= ny && ny < w && map[nx][ny] == 1) {
				dfs(check, nx, ny);
			}
		}
	}

	int[][] map;

	public static void main(String[] args) {
		new B().run();
	}
}
