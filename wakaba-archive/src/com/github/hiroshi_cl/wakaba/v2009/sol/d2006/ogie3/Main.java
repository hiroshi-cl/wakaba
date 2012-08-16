package com.github.hiroshi_cl.wakaba.v2009.sol.d2006.ogie3;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
/*
 * 影の秘密の解法
 * -影のトポロジーが変化する角度を列挙する。これは、すべての２円の接線の角度の集合と等しい。
 * -トポロジーが変化しない区間において、角度を微小変化させたときの影の長さの変化量は、連続する影の左端と右端に対応するtowerの微少変化量によって決まる。これの和が０になるときが（あれば）、極値である。計算式は、単純なものになる。
 * -あとは、トポロジーが変化する角度と、極値をとる角度について、影の長さを実際に計算してやればよい。
*/
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		new F().run(sc);
	}
	static class A {
		void run(Scanner sc) {
			for (;;) {
				int a = sc.nextInt(), d = sc.nextInt(), n = sc.nextInt();
				if ((a | d | n) == 0) return;
				int count = 0;
				int i = 0;
				for (; count < n; i++) {
					if (isPrime(a + i * d)) count++;
				}
				System.out.println(a + (i - 1) * d);
			}
		}
		boolean isPrime(int p) {
			if (p == 1) return false;
			for (int i = 2; i * i <= p; i++)
				if (p % i == 0) return false;
			return true;
		}
	}
	static class B {
		void run(Scanner sc) {
			for (int i = sc.nextInt(); i > 0; i--) {
				String s = sc.next();
				TreeSet<String> t = new TreeSet<String>();
				for (int j = 1; j < s.length(); j++) {
					String a = s.substring(0, j);
					String b = s.substring(j);
					for (int k = 0; k < 2; k++) {
						t.add(a + b);
						t.add(a + rev(b));
						t.add(rev(a) + b);
						t.add(rev(a) + rev(b));
						String l = a; a = b; b = l;
					}
				}
				System.out.println(t.size());
			}
		}
		static String rev(String s) {
			return new StringBuilder(s).reverse().toString();
		}
	}
	static class C {
		void run(Scanner sc) {
			for (;;) {
				int n = sc.nextInt();
				if (n == 0) return;
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
				Queue<Wamp> q = new PriorityQueue<Wamp>();
				q.offer(wamp);
				HashMap<Wamp, Integer> visited = new HashMap<Wamp, Integer>();
				visited.put(wamp, 0);
				min = 20;
				if (wamp.cells[0].equals(goal)) min = 0;
				while (!q.isEmpty()) {
					Wamp now = q.poll();
					if (!now.canReach()) continue;
					Wamp[] nexts = now.allNext();
					for (Wamp next : nexts) {
						next.step = now.step + 1;
						if (visited.containsKey(next) && visited.get(next) <= next.step) continue;
						if (!next.canReach()) continue;
						if (next.cells[0].equals(goal)) {
							min = next.step;
							continue;
						}
						visited.put(next, next.step);
						q.offer(next);
					}
				}
				System.out.println(min);
			}
		}
		int min;
		Cell goal;
		HashSet<Cell> rock;
		int[] dx = new int[] { 1, 1, 0, -1, -1, 0 };
		int[] dy = new int[] { -1, 0, 1, 1, 0, -1 };
		class Wamp implements Comparable<Wamp> {
			boolean canReach() {
				int dist = cells[0].dist(goal);
				if (dist <= 1) return min - step > dist;
				return dist * 2 - 2 < min - step;
			}
			public int compareTo(Wamp o) {
				return cells[0].dist(goal) - o.cells[0].dist(goal);
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
				if (hash != -1) return hash;
				int res = 0;
				for (int i = 0; i < n; i++) {
					res += cells[i].x * (1 << 2 * i) + cells[i].y * (1 << (2 * i + 1));
				}
				return hash = res;
			}
			public boolean equals(Object obj) {
				Wamp w = (Wamp) obj;
				for (int i = 0; i < n; i++) {
					if (!cells[i].equals(w.cells[i])) return false;
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
									if (cells[j - 1].isNeighbor(c) && cells[j + 1].isNeighbor(c)) {
										next[j] = c;
									}
								}
								if (next[j] == null) continue loop;
							} else {
								next[j] = cells[j];
							}
						}
						for (int j = 0; j < 2; j++) {
							if (head[j] == null) continue;
							for (int k = 0; k < 2; k++) {
								if (tail[k] == null) continue;
								next[0] = head[j];
								next[n - 1] = tail[k];
								Wamp w = new Wamp(n);
								for (int l = 0; l < n; l++)
									w.cells[l] = next[l];
								if (w.isValid() && w.canReach()) res.add(w);
							}
						}
					}
				}
				return res.toArray(new Wamp[0]);
			}
			boolean isValid() {
				for (int i = 0; i < n; i++) {
					if (rock.contains(cells[i])) return false;
					for (int j = i + 2; j < n; j++) {
						if (cells[i].equals(cells[j])) return false;
						if (cells[i].isNeighbor(cells[j])) return false;
					}
				}
				return true;
			}
		}
		class Cell {
			int dist(Cell c) {
				int dx = abs(x - c.x);
				int dy = abs(y - c.y);
				int dz = abs(x + y - (c.x + c.y));
				return min(dx + dy, min(dy + dz, dz + dx));
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
					if (x == c.x + dx[d] && y == c.y + dy[d]) return true;
				}
				return false;
			}
		}
	}
	static class D {
		int[] dx = new int[] { 1, 0, -1, 0 }, dy = new int[] { 0, 1, 0, -1 };
		void run(Scanner sc) {
			for (;;) {
				int w = sc.nextInt(), h = sc.nextInt();
				if (w + h == 0) return;
				int[][] fss = new int[h][w];
				int nx = 0, ny = 0;
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						fss[i][j] = sc.nextInt();
						if (fss[i][j] == 2) {
							nx = i;
							ny = j;
						}
					}
				}
				min = 11;
				dfs(fss, nx, ny, 0);
				System.out.println(min == 11 ? -1 : min);
			}
		}
		int min = 11;
		void dfs(int[][] fss, int x, int y, int step) {
			if (step >= min) return;
			int h = fss.length, w = fss[0].length;
			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d], ny = y + dy[d];
				if (nx < 0 || h <= nx || ny < 0 || w <= ny) continue;
				if (fss[nx][ny] == 3) {
					min = step + 1;
					continue;
				}
				if (fss[nx][ny] == 1) continue;
				boolean ok = true;
				for (;;) {
					nx += dx[d];
					ny += dy[d];
					if (nx < 0 || h <= nx || ny < 0 || w <= ny) {
						ok = false;
						break;
					}
					if (fss[nx][ny] == 1) break;
					if (fss[nx][ny] == 3) {
						ok = false;
						min = step + 1;
						break;
					}
				}
				if (!ok) continue;
				else {
					fss[nx][ny] = 0;
					dfs(fss, nx - dx[d], ny - dy[d], step + 1);
					fss[nx][ny] = 1;
				}
			}
		}
		
	}
	static class E {
		int max;
		StringBuilder term() {
			if (cs[p] == '=' || cs[p] == ')') {
				return new StringBuilder("");
			}
			StringBuilder res;
			if (Character.isDigit(cs[p])) {
				int n = number();
				StringBuilder tmp = fact();
				res = new StringBuilder();
				for (int i = 0; i < n; i++) {
					res.append(tmp);
					if (res.length() > max) break;
				}
			} else {
				res = new StringBuilder(fact());
			}
			if (res.length() > max) return res;
			res.append(term());
			return res;
		}
		StringBuilder fact() {
			if (cs[p] == '(') {
				p++;
				StringBuilder res = term();
				p++;
				return res;
			} else {
				return new StringBuilder(String.valueOf(cs[p++]));
			}
		}
		int number() {
			int res = 0;
			while (Character.isDigit(cs[p])) {
				res *= 10;
				res += cs[p++] - '0';
			}
			return res;
		}
		void run(Scanner sc) {
			for (;;) {
				String s = sc.next();
				int n = sc.nextInt();
				max = n;
				if (s.equals("0") && n == 0) break;
				cs = (s + "=").toCharArray();
				p = 0;
				StringBuilder res = term();
				System.out.println(res.length() <= n ? '0' : res.charAt(n));
			}
		}
		char[] cs;
		int p;
	}
	static class F {
		double[] tod(Double[] Ds) {
			double[] ds = new double[Ds.length];
			for (int i = 0; i < ds.length; i++) {
				ds[i] = Ds[i];
			}
			return ds;
		}
		void run(Scanner sc) {
			for (;;) {
				int n = sc.nextInt();
				if (n == 0) return;
				Tower[] towers = new Tower[n];
				for (int i = 0; i < n; i++) {
					double x = sc.nextDouble(), y = sc.nextDouble();
					towers[i] = new Tower(-y, x, i);
				}
				TreeSet<Double> set = new TreeSet<Double>();
				set.add(0.);
				set.add(PI);
				for (int i = 0; i < n; i++)
					for (int j = i + 1; j < n; j++)
						calcTheta(set, towers[i].center, towers[j].center);
				double[] thetas = tod(set.toArray(new Double[0]));
				int m = thetas.length;
				double min = Integer.MAX_VALUE;
				double minTheta = 0;
				double max = 0;
				double maxTheta = 0;
				for (int i = 0; i < m; i++) {
					double length = calcSum(normalize(thetas[i]), towers);
					if (length < min) {
						min = length;
						minTheta = thetas[i];
					}
					if (length > max) {
						max = length;
						maxTheta = thetas[i];
					}
				}
				for (int i = 0; i < m - 1; i++) {
					double theta = (thetas[i] + thetas[i + 1]) / 2;
					Entry[] shadows = new Entry[n];
					for (int j = 0; j < n; j++) {
						shadows[j] = towers[j].shadow(theta);
					}
					sort(shadows);
					double child = 0;
					double mother = 0;
					for (int j = 0; j < n; j++) {
						int k = j;
						while (k < n - 1 && shadows[k].r >= shadows[k + 1].l)
							k++;
						int l = shadows[j].id;
						int r = shadows[k].id;
						child += -towers[r].center.y + towers[l].center.y;
						mother += -towers[r].center.x + towers[l].center.x;
						j = k;
					}
					if (mother == 0) continue;
					double pole = normalize(atan(-child / mother));
					if (pole < thetas[i] || thetas[i + 1] < pole) continue;
					double length = calcSum(normalize(pole), towers);
					if (length < min) {
						min = length;
						minTheta = pole;
					}
					if (length > max) {
						max = length;
						maxTheta = pole;
					}
				}
				System.out.printf("%.10f%n", minTheta);
				System.out.printf("%.10f%n", maxTheta);
			}
		}
		double calcSum(double theta, Tower[] towers) {
			int n = towers.length;
			Entry[] shadows = new Entry[n];
			for (int i = 0; i < n; i++) {
				shadows[i] = towers[i].shadow(theta);
			}
			sort(shadows);
			double res = 0;
			for (int i = 0; i < n; i++) {
				int j = i;
				while (j < n - 1 && shadows[j].r >= shadows[j + 1].l)
					j++;
				res += shadows[j].r - shadows[i].l;
				i = j;
			}
			return res;
		}
		class Entry implements Comparable<Entry> {
			double l, r;
			int id;
			Entry(double l, double r, int id) {
				this.l = l;
				this.r = r;
				this.id = id;
			}
			public int compareTo(Entry o) {
				return (int) signum(l - o.l);
			}
		}
		void calcTheta(TreeSet<Double> set, P c1, P c2) {
			P[][] inTan = innerTangent(c1, 1, c2, 1);
			P[][] ouTan = outerTangent(c1, 1, c2, 1);
			for (int i = 0; i < 2; i++) {
				if (inTan != null && i < inTan.length) {
					set.add(normalize(PI / 2 - inTan[i][1].sub(inTan[i][0]).angle()));
				}
				if (ouTan != null && i < ouTan.length) {
					set.add(normalize(PI / 2 - ouTan[i][1].sub(ouTan[i][0]).angle()));
				}
			}
		}
		double normalize(double theta) {
			return ((theta % PI) + PI) % PI;
		}
		int signum(double d) {
			return d < -EPS ? -1 : d > EPS ? 1 : 0;
		}
		double EPS = 1e-7;
		P[][] innerTangent(P o1, double r1, P o2, double r2) {// 内接線
			P o1o2 = o2.sub(o1);
			double l = o1o2.norm2();
			double d = l - (r1 + r2) * (r1 + r2);
			if (d < 0) return null;
			if (eq(d, 0)) {
				P p = o1.add(o1o2.mul(r1 / (r1 + r2)));
				return new P[][] { { p, p.add(o1o2.rot90().mul(1 / (r1 + r2))) } };
			}
			P p = o1o2.mul((r1 + r2) / l);
			P q = o1o2.rot90().mul(sqrt(d) / l);
			return new P[][] {
			{ o1.add(p.mul(r1)).add(q.mul(r1)), o2.sub(p.mul(r2)).sub(q.mul(r2)) },
			{ o1.add(p.mul(r1)).sub(q.mul(r1)), o2.sub(p.mul(r2)).add(q.mul(r2)) } };
		}
		boolean eq(double a, double b) {
			return signum(a - b) == 0;
		}
		static P[][] outerTangent(P o1, double r1, P o2, double r2) {// 外接線
			P o1o2 = o2.sub(o1);
			double l = o1o2.norm2();
			double d = l - (r1 - r2) * (r1 - r2);
			if (d < 0) return null;
			P p = o1o2.mul((r1 - r2) / l);
			P q = o1o2.rot90().mul(sqrt(d) / l);
			return new P[][] {
			{ o1.add(p.mul(r1)).add(q.mul(r1)), o2.add(p.mul(r2)).add(q.mul(r2)) },
			{ o1.add(p.mul(r1)).sub(q.mul(r1)), o2.add(p.mul(r2)).sub(q.mul(r2)) } };
		}
		class Tower {
			final int id;
			P center;
			final double l;
			final double phi;
			Tower(double x, double y, int id) {
				this.id = id;
				center = new P(x, y);
				l = center.norm();
				phi = center.angle();
			}
			Entry shadow(double theta) {
				double c = l * cos(phi + theta);
				return new Entry(c - 1, c + 1, id);
			}
		}
		class P {
			double x, y;
			P(double x, double y) {
				this.x = x;
				this.y = y;
			}
			P add(P p) {
				return new P(x + p.x, y + p.y);
			}
			P sub(P p) {
				return new P(x - p.x, y - p.y);
			}
			P mul(double d) {
				return new P(x * d, y * d);
			}
			P rot90() {
				return new P(-y, x);
			}
			P rot(double t) {// verified
				return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
			}
			double norm() {
				return sqrt(norm2());
			}
			double norm2() {
				return x * x + y * y;
			}
			double angle() {
				return atan2(y, x);// [-PI, PI]
			}
		}
	}
	
}
