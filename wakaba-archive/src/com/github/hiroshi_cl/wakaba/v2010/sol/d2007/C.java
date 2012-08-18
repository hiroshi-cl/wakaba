package com.github.hiroshi_cl.wakaba.v2010.sol.d2007;

//090519
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

// まったく枝刈りなしで、単純に幅優先探索。でも４テストケース５分３０秒で解ける。
public class C {
	public static void main(String[] args) {
		new C().run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			if (n == 0)
				return;
			Wamp wamp = new Wamp(n);
			for (int i = 0; i < n; i++) {
				wamp.cells[i] = new Cell(sc.nextInt(), sc.nextInt());
			}
			int k = sc.nextInt();
			rock = new HashSet<Cell>();
			for (int i = 0; i < k; i++) {
				rock.add(new Cell(sc.nextInt(), sc.nextInt()));
			}
			goal = new Cell(sc.nextInt(), sc.nextInt());
			Queue<Wamp> q = new LinkedList<Wamp>();
			q.offer(wamp);
			HashSet<Wamp> visited = new HashSet<Wamp>();
			visited.add(wamp);
			while (true) {
				Wamp now = q.poll();
				if (now.cells[0].equals(goal)) {
					System.out.println(now.step);
					break;
				}
				Wamp[] nexts = now.allNext();
				for (Wamp next : nexts) {
					if (visited.contains(next))
						continue;
					next.step = now.step + 1;
					visited.add(next);
					q.offer(next);
				}
			}
		}
	}

	Cell goal;
	HashSet<Cell> rock;
	int[] dx = new int[] { 1, 1, 0, -1, -1, 0 };
	int[] dy = new int[] { -1, 0, 1, 1, 0, -1 };

	class Wamp {
		public String toString() {
			return cells.toString();
		}

		int step = 0;
		Cell[] cells;
		int n;

		Wamp(int n) {
			this.n = n;
			cells = new Cell[n];
		}

		int hash = -1;

		public int hashCode() {
			if (hash != -1)
				return hash;
			int res = 0;
			for (int i = 0; i < n; i++) {
				res += cells[i].x * (1 << 2 * i) + cells[i].y
						* (1 << (2 * i + 1));
			}
			return hash = res;
		}

		public boolean equals(Object obj) {
			Wamp w = (Wamp) obj;
			for (int i = 0; i < n; i++) {
				if (!cells[i].equals(w.cells[i]))
					return false;
			}
			return true;
		}

		Wamp[] allNext() {
			ArrayList<Wamp> res = new ArrayList<Wamp>();
			loop: for (int i = 0; i < (1 << n); i++) {
				if ((i & (i << 1)) == 0) {
					Cell[] head = new Cell[2];
					Cell[] tail = new Cell[2];
					if ((i & (1 << 0)) > 0) {
						for (int d = 0, j = 0; d < 6; d++) {
							Cell c = cells[0].move(d);
							if (c.isNeighbor(cells[1])) {
								head[j++] = c;
							}
						}
					} else {
						head[0] = cells[0];
					}
					if ((i & (1 << (n - 1))) > 0) {
						for (int d = 0, j = 0; d < 6; d++) {
							Cell c = cells[n - 1].move(d);
							if (c.isNeighbor(cells[n - 2])) {
								tail[j++] = c;
							}
						}
					} else {
						tail[0] = cells[n - 1];
					}
					Cell[] next = new Cell[n];
					for (int j = 1; j < n - 1; j++) {
						if ((i & (1 << j)) > 0) {
							for (int d = 0; d < 6; d++) {
								Cell c = cells[j].move(d);
								if (cells[j - 1].isNeighbor(c)
										&& cells[j + 1].isNeighbor(c)) {
									next[j] = c;
								}
							}
							if (next[j] == null)
								continue loop;
						} else {
							next[j] = cells[j];
						}
					}
					for (int j = 0; j < 2; j++) {
						if (head[j] == null)
							continue;
						for (int k = 0; k < 2; k++) {
							if (tail[k] == null)
								continue;
							next[0] = head[j];
							next[n - 1] = tail[k];
							Wamp w = new Wamp(n);
							for (int l = 0; l < n; l++)
								w.cells[l] = next[l];
							if (w.isValid())
								res.add(w);
						}
					}
				}
			}
			return res.toArray(new Wamp[0]);
		}

		boolean isValid() {
			for (int i = 0; i < n; i++) {
				if (rock.contains(cells[i]))
					return false;
				for (int j = i + 2; j < n; j++) {
					if (cells[i].equals(cells[j]))
						return false;
					if (cells[i].isNeighbor(cells[j]))
						return false;
				}
			}
			return true;
		}
	}

	class Cell {
		public String toString() {
			return x + " " + y;
		}

		public int hashCode() {
			return x * 0x000f0000 + y;
		}

		public boolean equals(Object obj) {
			Cell c = (Cell) obj;
			return c.x == x && c.y == y;
		}

		final int x, y;

		Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Cell move(int d) {
			return new Cell(x + dx[d], y + dy[d]);
		}

		boolean isNeighbor(Cell c) {
			for (int d = 0; d < 6; d++) {
				if (x == c.x + dx[d] && y == c.y + dy[d])
					return true;
			}
			return false;
		}
	}
}
