package com.github.hiroshi_cl.wakaba.v2010.sol.d2004;

import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;

public class F {
	public static void main(String[] args) {
		new F().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in).useDelimiter("[\\s-]+");
		for (;;) {
			int N = sc.nextInt();
			if (N == 0)
				break;
			HashMap<String, Integer> h = new HashMap<String, Integer>();
			String cross1[] = new String[N];
			String cross2[] = new String[N];
			int id = 0;
			for (int i = 0; i < N; i++) {
				cross1[i] = sc.next();
				if (!h.containsKey(cross1[i])) {
					h.put(cross1[i], id);
					id++;
				}
				cross2[i] = sc.next();
				if (!h.containsKey(cross2[i])) {
					h.put(cross2[i], id);
					id++;
				}
			}
			int m = h.size();
			boolean[][] f = new boolean[m][m];
			boolean[][] g = new boolean[m][m];
			for (int i = 0; i < N; i++) {
				f[h.get(cross1[i])][h.get(cross2[i])] = true;
				g[h.get(cross1[i])][h.get(cross2[i])] = true;
			}
			// debug(h);
			for (int j = 0; j < m; j++) {
				label: for (int k = j + 1; k < m; k++) {
					for (int i = 0; i < m; i++) {
						if (f[i][j] && f[k][i] || f[j][i] && f[i][k])
							continue label;
					}
					for (int i = 0; i < m; i++) {
						if (f[i][j] && f[i][k] || f[j][i] && f[k][i]) {
							g[j][k] = g[k][j] = true;
						}
					}
				}
			}
			// for(int i=0;i<m;i++){
			// debug(f[i],g[i]);
			// }
			for (int k = 0; k < m; k++) {
				for (int i = 0; i < m; i++) {
					for (int j = 0; j < m; j++) {
						g[i][j] |= g[i][k] && g[k][j];
					}
				}
			}
			int[][] color = new int[m][m];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < m; j++) {
					if (f[i][j] || f[j][i]) {
						color[i][j] = 1;
					} else {
						color[i][j] = 1 << 20;
					}
				}
			}
			for (int k = 0; k < m; k++) {
				for (int i = 0; i < m; i++) {
					for (int j = 0; j < m; j++) {
						color[i][j] = Math.min(color[i][k] + color[k][j],
								color[i][j]);
					}
				}
			}

			int oo = sc.nextInt();
			System.out.println(m);
			for (int o = 0; o < oo; o++) {
				String ss = sc.next(), st = sc.next();
				if (!h.containsKey(ss) || !h.containsKey(st)) {
					System.out.println("NO");
					continue;
				}
				int s = h.get(ss), t = h.get(st);
				if (s == t
						|| (color[s][t] % 2 == 0 || color[s][t] >= (1 << 20))) {
					System.out.println("NO");
					continue;
				}
				System.out.println(g[s][t] ? "YES" : "NO");
			}
		}
	}
}