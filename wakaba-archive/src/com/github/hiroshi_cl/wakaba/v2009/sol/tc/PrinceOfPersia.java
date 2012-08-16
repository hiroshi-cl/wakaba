package com.github.hiroshi_cl.wakaba.v2009.sol.tc;
// END CUT HERE
public class PrinceOfPersia {
	char[][] fss;
	int n, m;
	int p1x = -1, p1y = -1, p2x = -1, p2y = -1;
	public int minObstacles(String[] maze) {
		n = maze.length;
		fss = new char[n][];
		for (int i = 0; i < n; i++) {
			fss[i] = maze[i].toCharArray();
			m = fss[i].length;
			for (int j = 0; j < m; j++) {
				if (fss[i][j] == 'P') {
					if (p1x == -1) {
						p1x = i;
						p1y = j;
					} else {
						p2x = i;
						p2y = j;
					}
				}
			}
		}
		for (int d = 0; d < 4; d++) {
			if (p1x + dx[d] == p2x && p1y + dy[d] == p2y) return -1;
		}
		dfs();
		return min;
	}
	int[] dx = new int[] { -1, 0, 1, 0 };
	int[] dy = new int[] { 0, -1, 0, 1 };
	int min = 4;
	void dfs() {
		dfs(0, 0, 0);
	}
	void dfs(int x, int y, int step) {
		if (step >= min) return;
		if (!canGo()) {
			min = step;
			return;
		}
		if (step >= min - 1) return;
		for (; x < n; x++) {
			for (; y < m; y++) {
				if (fss[x][y] == '.') {
					fss[x][y] = '#';
					dfs(x, y, step + 1);
					fss[x][y] = '.';
				}
			}
			y = 0;
		}
	}
	boolean[][] visited;
	boolean canGo() {
		visited = new boolean[n][m];
		return canGo(p1x, p1y);
	}
	boolean canGo(int x, int y) {
		if (visited[x][y]) return false;
		visited[x][y] = true;
		if (fss[x][y] == '#') return false;
		if (x == p2x && y == p2y) return true;
		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d], ny = y + dy[d];
			if (0 <= nx && nx < n && 0 <= ny && ny < m) {
				if (canGo(nx, ny)) return true;
			}
		}
		return false;
	}
}
