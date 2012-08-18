package com.github.hiroshi_cl.wakaba.v2010.sol.dp2009;

import java.math.*;
import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class E {
	static void debug(Object... os) {
//		 System.err.println(deepToString(os));
	}

	public static void main(String[] args) {
		new E().run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("E"));
			System.setOut(new PrintStream("E.out"));
		} catch (Exception e) {
		}
		for (;;) {
			w = sc.nextInt();
			h = sc.nextInt();
			if (h == 0)
				return;
			check = new boolean[h][w][h][w];
			map = new char[2][h][w];
			int[] sx = new int[2], sy = new int[2];
			int[] gx = new int[2], gy = new int[2];
			for (int i = 0; i < h; i++) {
				for (int k = 0; k < 2; k++) {
					map[k][i] = sc.next().toCharArray();
					for (int j = 0; j < w; j++) {
						if (map[k][i][j] == 'L' || map[k][i][j] == 'R') {
							sx[k] = i;
							sy[k] = j;
						}
						if (map[k][i][j] == '%') {
							gx[k] = i;
							gy[k] = j;
						}
					}
				}
			}
			System.out.println(dfs(sx[0], sy[0], sx[1], sy[1]) ? "Yes" : "No");
		}
	}

	int[] dx = { 1, 0, -1, 0 };
	int[] dy = { 0, 1, 0, -1 };
	int[] dyrev = { 0, -1, 0, 1 };

	class P{
		int lx,ly,rx,ry;
		P(int lx,int ly,int rx,int ry){
			this.lx=lx; this.ly=ly; this.rx=rx; this.ry=ry;
		}
	}
	
	boolean dfs(int lx, int ly, int rx, int ry) {
		int cnt=0;
		Queue<P> st = new LinkedList<P>();
		st.offer(new P(lx,ly,rx,ry));
		while(!st.isEmpty()) {
			cnt++;
			debug(cnt);
			P np =st.poll();
			lx = np.lx; ly=np.ly; rx = np.rx; ry = np.ry;
			debug(" ", lx, ly, rx, ry);
			if (check[lx][ly][rx][ry])
				//			return false;
				continue;
			check[lx][ly][rx][ry] = true;
			if (map[0][lx][ly] == '%' && map[1][rx][ry] == '%')
				return true;
			if (map[0][lx][ly] == '%')
				//			return false;
				continue;
			if (map[1][rx][ry] == '%')
				//			return false;
				continue;
			for (int d = 0; d < 4; d++) {
				int nlx = lx + dx[d];
				int nly = ly + dy[d];
				if (nlx < 0 || h <= nlx || nly < 0 || w <= nly || get0(nlx, nly) == '#') {
					nlx = lx;
					nly = ly;
				}
				int nrx = rx + dx[d];
				int nry = ry + dy[(d + 2) % 4];
				if (nrx < 0 || h <= nrx || nry < 0 || w <= nry || get1(nrx, nry) == '#') {
					nrx = rx;
					nry = ry;
				}
				debug(nlx, nly, nrx, nry);
				if (rx == nrx && ry == nry && lx == nlx && ly == nly)
					continue;
				if (check[nlx][nly][nrx][nry])
					//			return false;
					continue;
				st.offer(new P(nlx,nly,nrx,nry));
				//			if (dfs(nlx, nly, nrx, nry)) {
				//				return true;
				//			}
			}
		}
		return false;
	}

	int w, h;

	char get0(int x, int y) {
		return map[0][x][y];
	}

	char get1(int x, int y) {
		return map[1][x][y];
	}

	boolean[][][][] check; // 0 yet, 1 ok , -1 無理
	char[][][] map;
}
