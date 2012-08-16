package com.github.hiroshi_cl.wakaba.v2009.lib.game;
/*
 * 数独を深さ優先探索で解く。問題に書き込む。複数解チェックはしていない。
 * コードが短い分効率は悪く、９×９までしか対応できない。
 * 
 * verified pku2676.
 */
class Sudoku {
	int n;
	int[][] answer;

	Sudoku(int n) {// n*n * n*n の盤面
		this.n = n;
		answer = new int[n * n][n * n];
	}

	void set(int x, int y, int num) {
		answer[x][y] = num;
	}

	boolean solve() {// 解なしの時のみfalseを返す。
		return dfs(0, 0);
	}

	private boolean dfs(int x, int y) {
		if (x == n * n) {
			x = 0;
			y++;
		}
		if (y == n * n)
			return true;
		if (answer[x][y] != 0)
			return dfs(x + 1, y);
		for (int i = 0; i < n * n; i++) {
			answer[x][y] = i + 1;
			if (isValid(x, y) && dfs(x + 1, y))
				return true;
		}
		answer[x][y] = 0;
		return false;
	}

	private boolean isValid(int x, int y) {
		for (int i = 0; i < n * n; i++) {
			if (i != x && answer[i][y] == answer[x][y])
				return false;
			if (i != y && answer[x][i] == answer[x][y])
				return false;
		}
		int r = x / n * n, c = y / n * n;
		for (int i = r; i < r + n; i++)
			for (int j = c; j < c + n; j++)
				if (i != x && j != y && answer[i][j] == answer[x][y])
					return false;
		return true;
	}
}