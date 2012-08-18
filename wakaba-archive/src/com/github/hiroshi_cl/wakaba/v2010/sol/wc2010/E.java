package com.github.hiroshi_cl.wakaba.v2010.sol.wc2010;

import java.util.*;
import java.math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;

public class E {
	void run() {
		Scanner sc = new Scanner(System.in);
		W = sc.nextInt();
		H = sc.nextInt();
		Item[][] init = new Item[H][W];
		for (int i = 0; i < H; i++) {
			char[] cs = sc.next().toCharArray();
			for (int j = 0; j < W; j++) {
				init[i][j] = Item.values()[items.indexOf(cs[j])];
				if (init[i][j] == Item.embrasure) {
					Lx = i;
					Ly = j;
					for (int d = 0; d < 8; d += 2) {
						int nx = i + dx8[d], ny = j + dy8[d];
						if (in(nx, ny) && init[nx][ny] == Item.empty) {
							Ld = d;
						}
					}
				}
			}
		}
		State now = new State(init);
		set = new HashSet<State>();
		que = new LinkedList<State>();
		set.add(now);
		que.offer(now);
		while (!que.isEmpty()) {
			now = que.poll();
			if (now.isGoal) {
				System.out.println("Yes");
				return;
			}
			now.addNexts();
		}
		System.out.println("No");
	}

	int W, H;
	String items = "#*S//\\\\--||OO@.LD";

	enum Item {
		wall, pillar, statue, slash, SLASH, backslash, BACKSLASH, horizontal, HORIZONTAL, vertical, VERTICAL, crystal, CRYSTAL, john, empty, embrasure, door;
		boolean isPushed() {
			return Character.isUpperCase(toString().charAt(0));
		}

		boolean canPush() {
			return 3 <= ordinal() && ordinal() <= 12;
		}
	}

	HashSet<State> set;
	Queue<State> que;
	int[] dx = { 0, 1, 0, -1 };
	int[] dy = { 1, 0, -1, 0 };
	int[] dx8 = { 0, 1, 1, 1, 0, -1, -1, -1 };
	int[] dy8 = { 1, 1, 0, -1, -1, -1, 0, 1 };

	boolean in(int x, int y) {
		return 0 <= x && x < H && 0 <= y && y < W;
	}

	int Lx, Ly, Ld;

	class State {
		boolean isGoal;

		Item[][] boardCopy() {
			Item[][] res = new Item[H][W];
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					res[i][j] = board[i][j];
				}
			}
			L = new boolean[H][W][8];
			dfs(Lx, Ly, Ld);
			return res;
		}

		boolean[][][] L;
		Item[][] board;
		int x, y;

		State(Item[][] board_) {
			this.board = board_;
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					if (board[i][j] == Item.john) {
						x = i;
						y = j;
					}
				}
			}
		}

		boolean die() {
			for (int i = 0; i < 8; i++) {
				if (L[x][y][i])
					return true;
			}
			return false;
		}

		void dfs(int x, int y, int d) {
			if (board[x][y] == Item.statue) {
				L[x][y][d] = true;
			}
			if (L[x][y][d] || board[x][y] == Item.pillar
					|| board[x][y] == Item.wall || board[x][y] == Item.door
					|| board[x][y] == Item.embrasure)
				return;
			L[x][y][d] = true;
			int nx = x + dx8[d], ny = y + dy8[d];
			switch (board[nx][ny]) {
			case empty:
			case john:
				dfs(nx, ny, d);
				break;
			case crystal:
			case CRYSTAL:
				break;
			case slash:
			case SLASH:
				break;

			case backslash:
			case BACKSLASH:
				break;
			case horizontal:
			case HORIZONTAL:
				break;
			case vertical:
			case VERTICAL:
				break;
			default:
				break;
			}
		}

		State move(int d) {
			Item[][] next = boardCopy();
			int nx = x + dx[d], ny = y + dy[d];
			if (in(nx, ny)) {
				if (next[nx][ny] == Item.empty) {
					next[nx][ny] = Item.john;
					next[x][y] = Item.empty;
					return new State(next);
				} else if (next[nx][ny].canPush() && in(nx + dx[d], ny + dy[d])
						&& next[nx + dx[d]][ny + dy[d]] == Item.empty) {
					if (!next[nx][ny].isPushed()) {
						if (pushedCount() >= 2)
							return null;
						next[nx][ny] = Item.values()[next[nx][ny].ordinal() + 1];
					}
					next[nx + dx[d]][ny + dy[d]] = next[nx][ny];
					next[nx][ny] = Item.john;
					next[x][y] = Item.empty;
					return new State(next);
				} else if (next[nx][ny] == Item.door) {
					if (allStatueActivated()) {
						next[nx][ny] = Item.john;
						next[x][y] = Item.empty;
						State res = new State(next);
						res.isGoal = true;
						return res;
					}
				}
			}
			return null;
		}

		int pushedCount() {
			int res = 0;
			for (Item[] is : board)
				for (Item i : is)
					if (i.isPushed())
						res++;
			return res;
		}

		boolean allStatueActivated() {
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					if (board[i][j] == Item.statue) {
						boolean ok = false;
						for (int k = 0; k < 8; k += 2) {
							if (L[i][j][k])
								ok = true;
						}
						if (!ok)
							return false;
					}
				}
			}
			return true;
		}

		void addNexts() {
			for (int d = 0; d < 4; d++) {
				State next = move(d);
				if (next != null) {
					if (!next.die()) {
						if (!set.contains(next)) {
							set.add(next);
							que.offer(next);
						}
					}
				}
			}
		}

		@Override
		public int hashCode() {
			int res = 1;
			for (Item[] is : board)
				for (Item i : is)
					res = (res + i.ordinal()) * 31;
			return res;
		}

		@Override
		public boolean equals(Object obj) {
			State o = (State) obj;
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					if (board[i][j] != o.board[i][j])
						return false;
				}
			}
			return true;
		}
	}
}
