package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.util.*;
import java.io.*;
import java.math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class F {
	public static void main(String[] args) throws Exception {
		try {
			System.setIn(new FileInputStream(new File("F.in")));
			System.setOut(new PrintStream("F.out"));
		} catch (Exception e) {
		}
		new F().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	int INF = 1 << 29;
	double EPS = 1e-9;

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int w = sc.nextInt(), h = sc.nextInt();
			if (h == 0 & w == 0)
				break;
			char[][] f = new char[h][w];
			int nx = 0, ny = 0;
			int[] gx = new int[10], gy = new int[10];
			int ng = 0;
			int[][] hh = new int[h][w];
			for (int i = 0; i < h; i++)
				fill(hh[i], -1);
			for (int i = 0; i < h; i++) {
				f[i] = sc.next().toCharArray();
				for (int j = 0; j < w; j++) {
					if (f[i][j] == 'o') {
						nx = i;
						ny = j;
					}
					if (f[i][j] == '*') {
						gx[ng] = i;
						gy[ng] = j;
						hh[i][j] = ng;
						ng++;
					}
				}
			}
			int[] dx = new int[] { 1, 0, -1, 0 }, dy = new int[] { 0, 1, 0, -1 };
			Queue<Integer> qx = new LinkedList<Integer>(), qy = new LinkedList<Integer>(), qg = new LinkedList<Integer>(), qv = new LinkedList<Integer>();
			qx.offer(nx);
			qy.offer(ny);
			qg.offer((1 << ng) - 1);
			qv.offer(0);
			int res = -1;
			int[][][] steps = new int[h][w][(1 << ng)];
			for (int i = 0; i < h; i++)
				for (int j = 0; j < w; j++)
					fill(steps[i][j], INF);
			steps[nx][ny][(1 << ng) - 1] = 0;
			while (!qx.isEmpty()) {
				nx = qx.poll();
				ny = qy.poll();
				int g = qg.poll(), v = qv.poll();
				if (g == 0) {
					res = v;
					break;
				}
				for (int i = 0; i < 4; i++) {
					int nnx = nx + dx[i], nny = ny + dy[i];
					if (!(0 <= nnx && nnx < h && 0 <= nny && nny < w))
						continue;
					if (f[nnx][nny] == 'x')
						continue;
					int nng = g;
					int id = hh[nnx][nny];
					if (f[nnx][nny] == '*')
						if ((nng & (1 << id)) != 0)
							nng -= 1 << id;
					if (steps[nnx][nny][nng] > v + 1) {
						steps[nnx][nny][nng] = v + 1;
						qx.offer(nnx);
						qy.offer(nny);
						qg.offer(nng);
						qv.offer(v + 1);
					}
				}
			}
			System.out.println(res);
		}
	}

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}
