package com.github.hiroshi_cl.wakaba.v2009.lib.game;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

// 未完成
public class PKU3435 {
	void debug(Object... o) {
		 System.err.println(deepToString(o));
	}
	void run() {
		int n = nextInt();
		Sudoku_advance sudoku = new Sudoku_advance(n);
		for (int i = 0; i < n * n; i++) {
			for (int j = 0; j < n * n; j++) {
				int num = nextInt();
				if (num > 0) if (!sudoku.set(i, j, num)) {
					System.out.println("INCORRECT");
					return;
				}
			}
		}
		// debug(sudoku.answer);
		System.out.println(sudoku.solve() ? "CORRECT" : "INCORRECT");
	}
	class Sudoku_advance {
		int n;
		int[][] answer;
		// boolean inValid;
		int[][][] timesOfdeleted;
		BitSet[][] deleted;
		
		Sudoku_advance(int n) {// n*n * n*n の盤面
			this.n = n;
			answer = new int[n * n][n * n];
			timesOfdeleted = new int[n * n][n * n][n * n + 1];
			deleted = new BitSet[n * n][n * n];
			for (int i = 0; i < n * n; i++)
				for (int j = 0; j < n * n; j++)
					deleted[i][j] = new BitSet();
		}
		boolean set(int x, int y, int num) {
			return num > 0 && assignNum(x, y, num);
		}
		boolean deleteCandCell(int x, int y, int num) {
			debug("deleteCandCell", x, y, num);
			// if (inValid) return false;
			timesOfdeleted[x][y][num]++;
			BitSet bit = deleted[x][y];
			if (!bit.get(num)) {
				bit.set(num);
				if (answer[x][y] == 0 && bit.cardinality() == n * n) {
					// inValid = true;
					return false;
				}
				// if (answer[x][y] == 0 && bit.cardinality() == n * n - 1)
				// assignNum(x, y, bit.nextClearBit(1));
			}
			return true;
		}
		boolean checkAndAssignNum(int x, int y) {
			debug("checkAndAssignNum", x, y);
			if (deleted[x][y].cardinality() != n * n - 1) return true;
			return assignNum(x, y, deleted[x][y].nextClearBit(1));
		}
		boolean deleteCand(int x,int y,int num){
			debug("deleteCand", x, y, num);
			boolean ok = true;
			for(int i=0;i<n*n;i++){
				if(i!=x && !deleteCandCell(i, y, num))ok=false;
				if(i!= y && !deleteCandCell(x, i, num))ok=false;
			}
			int r = x / n * n, c = y / n * n;
			for (int i = r; i < r + n; i++)
				for (int j = c; j < c + n; j++)
					if (!(i == x && j == y) && !deleteCandCell(i, j, num))
						ok = false;
			if (!ok) return false;
			for (int i = 0; i < n * n; i++) {
				if (i != x) if (!checkAndAssignNum(i, y)) return false;
				if (i != y) if (!checkAndAssignNum(x, i)) return false;
			}
			for (int i = r; i < r + n; i++)
				for (int j = c; j < c + n; j++)
					if (!(i == x && j == y))
						if (!checkAndAssignNum(i, j)) return false;
			return true;
		}
		void reviveCandCell(int x, int y, int num) {
			debug("reviveCandCell", x, y, num);
			timesOfdeleted[x][y][num]--;
			if (timesOfdeleted[x][y][num] == 0) {
				deleted[x][y].clear(num);
			}
		}
		void reviveCand(int x, int y, int num) {
			debug("reviveCand", x, y, num);
			for (int i = 0; i < n * n; i++) {
				if (i != x) reviveCandCell(i, y, num);
				if (i != y) reviveCandCell(x, i, num);
			}
			int r = x / n * n, c = y / n * n;
			for (int i = r; i < r + n; i++)
				for (int j = c; j < c + n; j++)
					if (!(i == x && j == y)) reviveCandCell(i, j, num);
		}
		
		private boolean assignNum(int x, int y, int num) {
			debug("assingNum", x, y, num);
			if (answer[x][y] != 0) return answer[x][y] == num;
			answer[x][y] = num;
			if (deleteCand(x, y, num)) return true;
			reviveCand(x, y, num);
			answer[x][y] = 0;
			return false;
		}
//		private boolean assignNum_Dfs()
		boolean solve() {// 解なしの時のみfalseを返す。
			return dfs(0, 0);
		}
		int count = 0;
		boolean dfs(int x, int y) {
			if (x == n * n) {
				x = 0;
				y++;
			}
			if (count++ % 1 == 0) {
				debug(x, y);
				debug(deleted[x][y]);
				show();
			}
			if (y == n * n) return true;
			if (answer[x][y] != 0) return dfs(x + 1, y);
			for (int num = 1; num <= n * n; num++)
				if (!deleted[x][y].get(num)) {
					if (assignNum(x, y, num)) if (dfs(x + 1, y)) return true;
				}
			return false;
		}
		void show() {
			for (int i = 0; i < n * n; i++) {
				debug(answer[i]);
			}
			debug();
		}
	}
	int nextInt() {
		int res = 0, c = 0;
		do {
			try {
				c = System.in.read();
				if (c == -1) return -1;
				if (c == '-') return -nextInt();
			} catch (Exception e) {
			}
		} while (c < '0' || c > '9');
		do {
			res = res * 10 + c - '0';
			try {
				c = System.in.read();
			} catch (Exception e) {
			}
		} while ('0' <= c && c <= '9');
		return res;
	}
	public static void main(String[] args) {
		new PKU3435().run();
	}
}