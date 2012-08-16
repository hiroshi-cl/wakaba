package com.github.hiroshi_cl.wakaba.v2009.lib.game;
import java.util.*;

// 未完成
class Sudoku_advance {
	int n;
	int placed = 0;
	int[][] problem;
	int[][] answer;
	boolean solved;
	BitSet[][] used;
	Sudoku_advance(int n) {// n*n * n*n の盤面
		this.n = n;
		problem = new int[n * n][n * n];
		answer = new int[n * n][n * n];
		used = new BitSet[n * n][n * n];
		for (int i = 0; i < n * n; i++)
			for (int j = 0; j < n * n; j++)
				used[i][j] = new BitSet();
	}
	void set(int x, int y, int num) {
		problem[x][y] = num;
		assignNum(x, y, num);
	}
	void deleteCand(int x, int y, int num) {
		BitSet bit = used[x][y];
		if (!bit.get(num)) {
			bit.set(num);
			if (answer[x][y] == 0 && bit.cardinality() == n * n - 1)
				assignNum(x, y, bit.nextClearBit(1));
		}
	}
	private void assignNum(int x, int y, int num) {
		answer[x][y] = num;
		for (int i = 0; i < n * n; i++) {
			if (i != x) deleteCand(i, y, num);
			if (i != y) deleteCand(x, i, num);
			if (i + 1 != num) deleteCand(x, y, i + 1);
		}
		int r = x / n * n, c = y / n * n;
		for (int i = r; i < r + n; i++) {
			for (int j = c; j < c + n; j++) {
				if (!(i == x && j == y)) deleteCand(i, j, num);
			}
		}
	}
	boolean solve() {// 破綻するときのみfalseを返す。
		return dfs(0, 0);
	}
	boolean dfs(int x, int y) {
		if (x == n * n) {
			x = 0;
			y++;
		}
		if (y == n * n) {
			solved = true;
			return true;
		}
		if (answer[x][y] != 0) return dfs(x + 1, y);
		else {
			for (int i = 0; i < n * n; i++) {
				answer[x][y] = i + 1;
				if (isValid(x, y)) if (dfs(x + 1, y)) return true;
			}
			answer[x][y] = 0;
		}
		return false;
	}
	int count = 0;
	boolean isValid(int x, int y) {
		for (int i = 0; i < n * n; i++) {
			if (i != x && answer[i][y] == answer[x][y]) return false;
			if (i != y && answer[x][i] == answer[x][y]) return false;
		}
		int r = x / n * n, c = y / n * n;
		for (int i = r; i < r + n; i++) {
			for (int j = c; j < c + n; j++) {
				if (i != x && j != y && answer[i][j] == answer[x][y])
					return false;
			}
		}
		return true;
	}
	
}