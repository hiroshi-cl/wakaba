package com.github.hiroshi_cl.wakaba.v2009.sol.d2006;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class Main {
	public static void main(String[] args) {
		new C().run();
	}
	// static void debug(Object... objects) {
	// System.err.println(deepToString(objects));
	// }
	static class A {
		void run() {
			Scanner sc = new Scanner(System.in);
			boolean[] ps = new boolean[1000000];
			fill(ps, true);
			ps[0] = ps[1] = false;
			for (int i = 2; i * i <= ps.length; i++) {
				if (ps[i]) {
					for (int j = 2; j * i < ps.length; j++) {
						ps[i * j] = false;
					}
				}
			}
			for (;;) {
				int a = sc.nextInt(), d = sc.nextInt(), n = sc.nextInt();
				if ((a | d | n) == 0) return;
				int count = 0;
				for (int i = 0;; i++) {
					if (ps[a + i * d]) {
						count++;
					}
					if (count == n) {
						System.out.println(a + d * i);
						break;
					}
				}
			}
		}
	}
	static class B {
		void run() {
			Scanner sc = new Scanner(System.in);
			int m = sc.nextInt();
			while (m-- > 0) {
				String s = sc.next();
				StringBuilder sb = new StringBuilder(s);
				int n = sb.length();
				HashSet<String> set = new HashSet<String>();
				for (int i = 1; i < n; i++) {
					String a = s.substring(0, i);
					String b = s.substring(i);
					set.add(a + b);
					set.add(a + rev(b));
					set.add(rev(a) + b);
					set.add(rev(a) + rev(b));
					String t = a;
					a = b;
					b = t;
					set.add(a + b);
					set.add(a + rev(b));
					set.add(rev(a) + b);
					set.add(rev(a) + rev(b));
				}
				System.out.println(set.size());
			}
		}
		String rev(String s) {
			return new StringBuilder(s).reverse().toString();
		}
	}
	static class C {
		int	n;
		void run() {
			Scanner sc = new Scanner(System.in);
			for (;;) {
				n = sc.nextInt();
				Swamp swamp = new Swamp(n);
				swamp.input(sc);
				int k = sc.nextInt();
				rocks = new Cell[k];
				for (int i = 0; i < k; i++) {
					rocks[i] = new Cell(sc.nextInt(), sc.nextInt());
				}
				goal = new Cell(sc.nextInt(), sc.nextInt());
				System.out.println(bfs(swamp));
			}
		}
		Cell	goal;
		// HashMap<Swamp, Integer> set;
		int bfs(Swamp swamp) {
			Queue<Swamp> q = new LinkedList<Swamp>();
			q.offer(swamp);
			HashMap<Swamp, Integer> set = new HashMap<Swamp, Integer>();
			set.put(swamp, 0);
			// int res = 20;
			while (true) {
				Swamp now = q.poll();
				int step = set.get(now);
				Swamp[] nexts = now.allNext();
				for (Swamp next : nexts) {
					if (next.cells[0].equals(goal)) {}
					if (set.containsKey(next)) {
						if (set.get(next) > step + 1) {
							set.put(next, step + 1);
						}
					} else {
						set.put(next, step + 1);
						q.offer(next);
					}
				}
			}
		}
		Cell[]	rocks;
		class Cell {
			int dist(Cell c) {
				return max(abs(c.x - x), abs(c.y - y));
			}
			// @Override
			public String toString() {
				return x + " " + y;
			}
			boolean isNeighbor(Cell c) {
				for (int d = 0; d < 6; d++) {
					if (c.x + dx[d] == x && c.y + dy[d] == y) return true;
				}
				return false;
			}
			Cell move(int d) {
				return new Cell(x + dx[d], y + dy[d]);
			}
			final int	x, y;
			Cell(int x, int y) {
				this.x = x;
				this.y = y;
			}
			public boolean equals(Object obj) {
				Cell o = (Cell) obj;
				return o.x == x && o.y == y;
			}
		}
		int[]	dx	= new int[] { 1, 1, 0, -1, -1, 0 };
		int[]	dy	= new int[] { 0, -1, -1, 0, 1, 1 };
		class Swamp {
			public int hashCode() {
				int res = 0;
				for (int i = 0; i < n; i++) {
					res ^= (1 << 2 * i) * new Integer(cells[i].x * 10000 + cells[i].y);
				}
				return res;
			}
			public boolean equals(Object obj) {
				Swamp s = (Swamp) obj;
				for (int i = 0; i < n; i++) {
					if (!s.cells[i].equals(cells[i])) return false;
				}
				return true;
			}
			Swamp[] allNext() {
				ArrayList<Swamp> res = new ArrayList<Swamp>();
				loop: for (int i = 0; i < (1 << n); i++) {
					if ((i & (i << 1)) != 0) continue;
					Cell[] head = new Cell[2], tail = new Cell[2];
					if ((i & (1 << 0)) > 0) {
						for (int d = 0, j = 0; d < 6; d++) {
							Cell tmp = cells[0].move(d);
							if (cells[1].isNeighbor(tmp)) {
								head[j++] = tmp;
							}
						}
					} else {
						head[0] = head[1] = cells[0];
					}
					if ((i & (1 << n - 1)) > 0) {
						for (int d = 0, j = 0; d < 6; d++) {
							Cell tmp = cells[n - 1].move(d);
							if (cells[n - 2].isNeighbor(tmp)) {
								tail[j++] = tmp;
							}
						}
					} else {
						tail[0] = tail[1] = cells[n - 1];
					}
					Cell[] next = new Cell[n];
					for (int j = 1; j < n - 1; j++) {
						if ((i & (1 << j)) > 0) {
							for (int d = 0; d < 6; d++) {
								Cell tmp = cells[j].move(d);
								if (tmp.isNeighbor(cells[j - 1]) && tmp.isNeighbor(cells[j + 1])) {
									next[j] = tmp;
									break;
								}
							}
							if (next[j] == null) continue loop;
						} else {
							next[j] = cells[j];
						}
					}
					for (int j = 0; j < 2; j++) {
						for (int k = 0; k < 2; k++) {
							next[0] = head[j];
							next[n - 1] = tail[k];
							Swamp tmp = new Swamp(n);
							for (int l = 0; l < n; l++) {
								tmp.cells[l] = next[l];
								// debug(l, tmp.cells[l]);
							}
							if (tmp.isValid()) res.add(tmp);
						}
					}
				}
				return res.toArray(new Swamp[0]);
			}
			boolean isValid() {
				for (int i = 0; i < n; i++) {
					for (Cell rock : rocks) {
						if (rock.equals(cells[i])) return false;
					}
					for (int j = i + 2; j < n; j++) {
						if (cells[i].equals(cells[j])) return false;
						if (cells[i].isNeighbor(cells[j])) return false;
					}
				}
				return true;
			}
			protected Swamp clone() {
				Swamp res = new Swamp(n);
				for (int i = 0; i < n; i++) {
					res.cells[i] = new Cell(cells[i].x, cells[i].y);
				}
				return res;
			}
			int		n;
			Cell[]	cells;
			Swamp(int n) {
				this.n = n;
				cells = new Cell[n];
			}
			void input(Scanner sc) {
				for (int i = 0; i < n; i++) {
					cells[i] = new Cell(sc.nextInt(), sc.nextInt());
				}
			}
		}
	}
	static class D {
		void run() {
			Scanner sc = new Scanner(System.in);
			for (;;) {
				w = sc.nextInt();
				h = sc.nextInt();
				if ((w | h) == 0) return;
				int[][] map = new int[h][w];
				int sx = -1, sy = -1;
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						map[i][j] = sc.nextInt();
						if (map[i][j] == 2) {
							sx = i;
							sy = j;
							map[i][j] = 0;
						}
					}
				}
				// debug(map);
				min = 11;
				dfs(map, sx, sy, 0);
				System.out.println(min == 11 ? -1 : min);
			}
		}
		int		w, h;
		int[]	dx	= new int[] { 0, 1, 0, -1 };
		int[]	dy	= new int[] { 1, 0, -1, 0 };
		int		min;
		void dfs(int[][] map, int x, int y, int step) {
			// debug(x, y);
			if (step >= min - 1) return;
			if (map[x][y] != 0) return;
			if (map[x][y] == 3) {
				min = step;
				return;
			} else {
				for (int d = 0; d < 4; d++) {
					for (int i = 1;; i++) {
						int nx = x + dx[d] * i;
						int ny = y + dy[d] * i;
						if (0 <= nx && nx < h && 0 <= ny && ny < w) {
							if (map[nx][ny] == 1) {
								if (i == 1) break;
								else {
									map[nx][ny] = 0;
									dfs(map, nx - dx[d], ny - dy[d], step + 1);
									map[nx][ny] = 1;
									break;
								}
							} else if (map[nx][ny] == 3) {
								min = step + 1;
								return;
							}
							if (map[nx][ny] != 0) {}
						} else {
							break;
						}
					}
				}
			}
		}
	}
	static class E {
		void run() {
			Scanner sc = new Scanner(System.in);
			L: for (;;) {
				String s = sc.next();
				int id = sc.nextInt();
				if (s.equals("0") && id == 0) return;
				ix = 0;
				ArrayList<G> gs = p(s);
				// debug(gs);
				for (G g : gs) {
					// debug(g.len());
					char c = g.charAt(id);
					if (c != '0') {
						System.out.println(c);
						continue L;
					}
					id -= g.len();
					// if (id<0) break;
				}
				System.out.println("0");
			}
		}
		int	ix;
		ArrayList<G> p(String s) {
			ArrayList<G> gs = new ArrayList<G>();
			for (;;) {
				if (ix >= s.length()) return gs;
				if (Character.isDigit(s.charAt(ix))) {
					int n = 0;
					while (Character.isDigit(s.charAt(ix))) {
						n = n * 10 + (int) (s.charAt(ix) - '0');
						ix++;
					}
					if (s.charAt(ix) == '(') {
						G g = new G();
						g.n = n;
						ix++;
						g.gs = p(s);
						gs.add(g);
						// debug(s.charAt(ix));
						ix++;
					} else {
						G g, gin = new G();
						gin.s = Character.toString(s.charAt(ix));
						g = new G();
						g.n = n;
						g.gs = new ArrayList<G>();
						g.gs.add(gin);
						// debug(s.charAt(ix));
						ix++;
						gs.add(g);
					}
				} else if (Character.isUpperCase(s.charAt(ix))) {
					G g = new G();
					StringBuilder sb = new StringBuilder();
					while (ix < s.length() && Character.isUpperCase(s.charAt(ix))) {
						sb.append(s.charAt(ix));
						ix++;
					}
					// debug(sb);
					g.s = sb.toString();
					gs.add(g);
				} else return gs;
			}
		}
		class G {
			// public String toString() {
			// if (s != null) return s;
			// // else return n + "*" + gs.toString();
			// }
			char charAt(int id) {
				if (id >= len()) return '0';
				else {
					if (s == null) {
						int len = 0;
						for (G g : gs) {
							len += g.len();
							if (len > INF) {
								len = INF;
								break;
							}
						}
						id %= len;
						// debug(id, len());
						for (G g : gs) {
							// debug(g.len());
							if (g.len() > id) return g.charAt(id);
							id -= g.len();
						}
						throw new IllegalArgumentException();
					} else {
						return s.charAt(id);
					}
				}
			}
			int len() {
				if (s != null) {
					return s.length();
				} else {
					int l = 0;
					for (G g : gs) {
						l += g.len();
						if (l > INF) return INF;
					}
					if (n * l > INF) return INF;
					return n * l;
				}
			}
			String			s;
			int				n;
			ArrayList<G>	gs;
		}
		int			p	= 0;
		final int	INF	= 1000001;
	}
	static class F {
		void run() {
			Scanner sc = new Scanner(System.in);
		}
	}
}
