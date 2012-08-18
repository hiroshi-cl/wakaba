package com.github.hiroshi_cl.wakaba.v2010.sol.d2006;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class D {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		new D().run(sc);
	}

	int[] dx = new int[] { 1, 0, -1, 0 }, dy = new int[] { 0, 1, 0, -1 };

	void run(Scanner sc) {
		for (;;) {
			int w = sc.nextInt(), h = sc.nextInt();
			if (w + h == 0)
				return;
			int[][] fss = new int[h][w];
			int nx = 0, ny = 0;
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					fss[i][j] = sc.nextInt();
					if (fss[i][j] == 2) {
						nx = i;
						ny = j;
					}
				}
			}
			min = 11;
			dfs(fss, nx, ny, 0);
			System.out.println(min == 11 ? -1 : min);
		}
	}

	int min = 11;

	void dfs(int[][] fss, int x, int y, int step) {
		if (step >= min)
			return;
		int h = fss.length, w = fss[0].length;
		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d], ny = y + dy[d];
			if (nx < 0 || h <= nx || ny < 0 || w <= ny)
				continue;
			if (fss[nx][ny] == 3) {
				min = step + 1;
				continue;
			}
			if (fss[nx][ny] == 1)
				continue;
			boolean ok = true;
			for (;;) {
				nx += dx[d];
				ny += dy[d];
				if (nx < 0 || h <= nx || ny < 0 || w <= ny) {
					ok = false;
					break;
				}
				if (fss[nx][ny] == 1)
					break;
				if (fss[nx][ny] == 3) {
					ok = false;
					min = step + 1;
					break;
				}
			}
			if (!ok)
				continue;
			else {
				fss[nx][ny] = 0;
				dfs(fss, nx - dx[d], ny - dy[d], step + 1);
				fss[nx][ny] = 1;
			}
		}
	}

}