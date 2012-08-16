package com.github.hiroshi_cl.wakaba.v2009.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class Main {
	public static void main(String[] args) {
		new H().run();
	}
	static class A {
		TreeSet<Integer> set = new TreeSet<Integer>();
		void run() {
			Scanner sc = new Scanner(System.in);
			for (int i = 0; i < 54; i++) {
				for (int j = 0; j < 96; j++) {
					set.add(i * i * i + j * (j + 1) * (j + 2) / 6);
				}
			}
			for (;;) {
				int n = sc.nextInt();
				if (n == 0) return;
				System.out.println(tail(n));
			}
		}
		int tail(int n) {
			SortedSet<Integer> sortedSet = set.headSet(n + 1);
			return sortedSet.last();
		}
	}
	static class B {
		void run() {
			Scanner sc = new Scanner(System.in);
			for (;;) {
				int n = sc.nextInt();
				if (n == 0) return;
				Market market = new Market(n);
				for (int i = 0; i < n; i++) {
					market.input(sc);
				}
				market.output();
			}
		}
		class Market {
			void output() {
				sort(coms);
				sort(pers);
				for (Comodity c : coms) {
					c.show();
				}
				System.out.println("--");
				for (Person p : pers)
					p.show();
				System.out.println("----------");
			}
			Market(int n) {
				orders = new Order[n];
			}
			int n;
			Order[] orders;
			ArrayList<Comodity> coms = new ArrayList<Comodity>();
			ArrayList<Person> pers = new ArrayList<Person>();
			void input(Scanner sc) {
				Order o = new Order(sc.next(), sc.next(), sc.next(), sc.nextInt());
				orders[n] = o;
				match(n++);
			}
			void match(int id) {
				Order o = orders[id];
				Order p = null;
				for (int i = 0; i < n; i++) {
					if (o.match(orders[i])) {
						if (p == null) {
							p = orders[i];
							continue;
						}
						if (o.buy) {
							if (orders[i].price < p.price) {
								p = orders[i];
							}
						} else {
							if (orders[i].price > p.price) {
								p = orders[i];
							}
						}
					}
				}
				if (p != null) {
					if (!p.buy) {
						update(p, o);
					} else {
						update(o, p);
					}
				}
			}
			void update(Order sell, Order buy) {
				sell.dealed = true;
				buy.dealed = true;
				Comodity c = getCom(sell.comodity);
				Person p1 = getPer(sell.name);
				Person p2 = getPer(buy.name);
				if (c == null) {
					c = new Comodity(sell.comodity);
					coms.add(c);
				}
				int price = (sell.price + buy.price) / 2;
				c.prices.add(price);
				p1.rec += price;
				p2.pay += price;
			}
			Comodity getCom(String name) {
				for (Comodity c : coms) {
					if (c.name.equals(name)) return c;
				}
				return null;
			}
			Person getPer(String name) {
				for (Person p : pers) {
					if (p.name.equals(name)) return p;
				}
				return null;
			}
			class Order {
				boolean match(Order o) {
					if (o.dealed) return false;
					if (name.equals(o.name)) return false;
					if (buy == o.buy) return false;
					if (!comodity.equals(o.comodity)) return false;
					if (buy) {
						if (price < o.price) return false;
					} else {
						if (price > o.price) return false;
					}
					return true;
				}
				boolean dealed = false;
				String name;
				boolean buy;
				String comodity;
				int price;
				Order(String name, String type, String com, int pri) {
					this.name = name;
					if (getPer(name) == null) pers.add(new Person(name));
					buy = type.equals("BUY");
					comodity = com;
					price = pri;
				}
			}
		}
		class Comodity implements Comparable<Comodity> {
			String name;
			ArrayList<Integer> prices = new ArrayList<Integer>();
			Comodity(String name) {
				this.name = name;
			}
			public int compareTo(Comodity o) {
				return name.compareTo(o.name);
			}
			void show() {
				sort(prices);
				int mean = 0;
				for (int p : prices)
					mean += p;
				mean /= prices.size();
				System.out.println(name + " " + prices.get(0) + " " + mean + " "
				+ prices.get(prices.size() - 1));
			}
		}
		class Person implements Comparable<Person> {
			void show() {
				System.out.println(name + " " + pay + " " + rec);
			}
			String name;
			int pay = 0;
			int rec = 0;
			Person(String name) {
				this.name = name;
			}
			public int compareTo(Person o) {
				return name.compareTo(o.name);
			}
		}
	}
	static class C {
		void run() {
			Scanner sc = new Scanner(System.in);
			for (;;) {
				int N = sc.nextInt();
				if (N == 0) break;
				double[][] a = new double[4][N + 2];
				double[][] X = new double[N + 2][N + 2], Y = new double[N + 2][N + 2];
				double maxS = 0.0;
				for (int i = 0; i < 4; i++) {
					a[i][0] = 0.0;
					a[i][N + 1] = 1.0;
					for (int j = 1; j <= N; j++) {
						a[i][j] = sc.nextDouble();
					}
				}
				for (int i = 0; i <= N + 1; i++) {
					for (int j = 0; j <= N + 1; j++) {
						X[i][j] = CalX(a[0][i], a[1][i], a[2][j], a[3][j]);
						Y[i][j] = CalY(a[0][i], a[1][i], a[2][j], a[3][j]);
					}
				}
				for (int i = 0; i <= N; i++) {
					for (int j = 0; j <= N; j++) {
						maxS = Math.max(maxS, CalS(i, j, X, Y));
					}
				}
				System.out.printf("%.6f%n", maxS);
			}
		}
		double CalX(double a, double b, double c, double d) {
			return ((-a - (b - a) * c) / ((b - a) * (d - c) - 1.0));
		}
		double CalY(double a, double b, double c, double d) {
			return ((-c - (d - c) * a) / ((d - c) * (b - a) - 1.0));
		}
		double CalS(int x, int y, double[][] X, double[][] Y) {
			return (((X[x][y] - X[x + 1][y + 1]) * (Y[x][y + 1] - Y[x + 1][y]) + (X[x][y + 1] - X[x + 1][y])
			* (Y[x + 1][y + 1] - Y[x][y])) / -2);
		}
	}
	static class D {
		final int[] ds = new int[] { 0, 0, 3, 3, 3, 3, 3, 4, 3, 4 };
		final int[] toi = new int[255];
		final char[][] toc = new char[10][];
		void run() {
			Scanner sc = new Scanner(System.in);
			for (int i = 0, k = 0; i < 10; i++) {
				toc[i] = new char[ds[i]];
				for (int j = 0; j < ds[i]; j++, k++) {
					toc[i][j] = (char) (k + 'a');
					toi[k + 'a'] = i;
				}
			}
			for (;;) {
				int n = sc.nextInt();
				if (n == 0) return;
				final int[][] js = new int[n][];
				String[] ss = new String[n];
				for (int i = 0; i < n; i++)
					ss[i] = sc.next();
				sort(ss);
				for (int i = 0; i < n; i++) {
					js[i] = tois(ss[i]);
				}
				char[] cs = sc.next().toCharArray();
				int m = cs.length;
				int[] is = new int[m];
				for (int i = 0; i < m; i++)
					is[i] = cs[i] - '0';
				ArrayList<Integer>[] dp = new ArrayList[m];
				for (int i = 0; i < m; i++)
					dp[i] = new ArrayList<Integer>();
				for (int i = 0; i < m; i++) {
					for (int j = 0; j < n; j++) {
						if (fit(is, js[j], i)) {
							dp[i].add(j);
						}
					}
				}
				set = new TreeSet<String>();
				dfs(dp, ss, m - 1, "");
				for (String s : set) {
					System.out.println(s.substring(0, s.length() - 1) + ".");
				}
				System.out.println("--");
			}
		}
		TreeSet<String> set;
		void dfs(ArrayList<Integer>[] dp, String[] ss, int id, String now) {
			if (id == -1) {
				set.add(now);
				return;
			}
			if (dp[id].isEmpty()) return;
			for (int i : dp[id]) {
				dfs(dp, ss, id - ss[i].length(), ss[i] + " " + now);
			}
		}
		boolean fit(int[] is, int[] js, int end) {
			if (end - js.length + 1 < 0) return false;
			for (int i = 0; i < js.length; i++) {
				if (is[end - js.length + 1 + i] != js[i]) return false;
			}
			return true;
		}
		int[] tois(String s) {
			char[] cs = s.toCharArray();
			int[] js = new int[cs.length];
			for (int i = 0; i < cs.length; i++) {
				js[i] = toi[cs[i]];
			}
			return js;
		}
	}
	static class E {
		void run() {
			Scanner sc = new Scanner(System.in);
			int o = sc.nextInt();
			sc.nextLine();
			while (o-- > 0) {
				Shape s1 = new Shape(), s2 = new Shape();
				s1.input(sc.nextLine().toCharArray());
				s2.input(sc.nextLine().toCharArray());
				System.out.println(s1.same(s2) ? "true" : "false");
				sc.nextLine();
			}
		}
		class Shape {
			int n;
			boolean same(Shape s) {
				if (n != s.n) return false;
				s.normalize(0);
				for (int i = 0; i < n; i++) {
					normalize(i);
					for (int j = 0; j < 6; j++) {
						rotate();
						if (equals(s)) return true;
					}
				}
				return false;
			}
			void rotate() {
				for (int i = 0; i < n; i++) {
					cells[i] = cells[i].rotate();
				}
				sort(cells);
			}
			void normalize(int id) {
				Cell c = new Cell(cells[id].x, cells[id].y);
				for (int i = 0; i < n; i++) {
					cells[i] = cells[i].sub(c);
				}
				sort(cells);
			}
			Cell[] cells;
			void input(char[] cs) {
				int k = cs.length;
				TreeSet<Cell> set = new TreeSet<Cell>();
				Cell bef = new Cell(0, 0);
				set.add(bef);
				for (int i = 0; i < k; i++) {
					bef = bef.move(cs[i] - 'a');
					set.add(bef);
				}
				cells = set.toArray(new Cell[0]);
				n = cells.length;
			}
			public boolean equals(Object obj) {
				Shape s = (Shape) obj;
				if (n != s.n) return false;
				for (int i = 0; i < n; i++) {
					if (cells[i].x != s.cells[i].x || cells[i].y != s.cells[i].y) return false;
				}
				return true;
			}
		}
		class Cell implements Comparable<Cell> {
			int x, y;
			void show() {
				System.out.println(x + " " + y);
			}
			Cell rotate() {
				return new Cell(x + y, -x);
			}
			Cell sub(Cell c) {
				return new Cell(x - c.x, y - c.y);
			}
			public int compareTo(Cell o) {
				return x - o.x == 0 ? y - o.y : x - o.x;
			}
			Cell(int x, int y) {
				this.x = x;
				this.y = y;
			}
			public boolean equals(Object obj) {
				Cell c = (Cell) obj;
				return x == c.x && y == c.y;
			}
			Cell move(int d) {
				return new Cell(x + dx[d], y + dy[d]);
			}
		}
		int[] dx = new int[] { -1, 0, 1, 1, 0, -1 };
		int[] dy = new int[] { 1, 1, 0, -1, -1, 0 };
	}
	static class F {
		String[] ST = new String[] { "Hakodate", "Tokyo" };
		int[] TIME = new int[] { 0, 600 };
		int INF = 1 << 25;
		void run() {
			Scanner sc = new Scanner(System.in);
			for (;;) {
				int n = sc.nextInt();
				V[][] vs = new V[n * 2][601];
				if (n == 0) return;
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				int k = 0;
				for (int i = 0; i < n; i++) {
					int[] ts = new int[2];
					int[] is = new int[2];
					for (int j = 0; j < 2; j++) {
						String s = sc.next();
						if (!map.containsKey(s)) map.put(s, k++);
						ts[j] = time2i(sc.next());
						is[j] = map.get(s);
					}
					int cost = sc.nextInt();
					if (ts[0] == -1 || ts[1] == -1) continue;
					for (int j = 0; j < 2; j++)
						if (vs[is[j]][ts[j]] == null) vs[is[j]][ts[j]] = new V(is[j], ts[j]);
					make(vs[is[0]][ts[0]], vs[is[1]][ts[1]], cost);
				}
				for (int i = 0; i < k; i++) {
					if (vs[i][0] == null) vs[i][0] = new V(i, 0);
					if (vs[i][600] == null) vs[i][600] = new V(i, 600);
					for (int j = 1, l = 0; j <= 600; j++) {
						while (vs[i][j] == null)
							j++;
						make(vs[i][l], vs[i][j], 0);
						l = j;
					}
				}
				for (l = 0; l < 2; l++)
					for (r = 0; r < 2; r++) {
						V from = vs[map.get(ST[l])][TIME[r]];
						from.cost[l][r] = 0;
						Queue<E> que = new PriorityQueue<E>();
						que.offer(new E(from, 0));
						while (!que.isEmpty()) {
							V v = que.poll().to;
							for (E e : v.es[r]) {
								if (e.to.cost[l][r] > v.cost[l][r] + e.cost) {
									e.to.cost[l][r] = v.cost[l][r] + e.cost;
									que.offer(e);
								}
							}
						}
					}
				int res = INF;
				for (int i = 0; i < k; i++) {
					ArrayList<V> list = new ArrayList<V>();
					for (int j = 0; j <= 600; j++)
						if (vs[i][j] != null) list.add(vs[i][j]);
					V[] us = list.toArray(new V[0]);
					for (int j = 0; j < us.length; j++) {
						int[] come = new int[2];
						fill(come, INF);
						int[] ret = new int[2];
						fill(ret, INF);
						for (int m = 0; m < 2; m++) {
							for (int p = j; p >= 0; p--)
								come[m] = min(come[m], us[p].cost[m][0]);
							for (int p = j; p < us.length; p++)
								if (us[p].time == 600 || us[p].time >= us[j].time + 30) ret[m] =
								min(ret[m], us[p].cost[m][1]);
						}
						res = min(res, come[0] + come[1] + ret[0] + ret[1]);
					}
				}
				System.out.println(res == INF ? 0 : res);
			}
		}
		int l, r;
		void make(V from, V to, int cost) {
			from.es[0].add(new E(to, cost));
			to.es[1].add(new E(from, cost));
		}
		class V {
			ArrayList<E> es[] = new ArrayList[2];
			int id;
			int time;
			int[][] cost = new int[2][2];
			V(int id, int time) {
				this.id = id;
				this.time = time;
				for (int i = 0; i < 2; i++) {
					es[i] = new ArrayList<E>();
					for (int j = 0; j < 2; j++)
						cost[i][j] = INF;
				}
			}
		}
		class E implements Comparable<E> {
			V to;
			int cost;
			E(V to, int cost) {
				this.to = to;
				this.cost = cost;
			}
			public int compareTo(E o) {
				return (cost + to.cost[l][r]) - (o.cost + o.to.cost[l][r]);
			}
		}
		int time2i(String s) {
			String[] ss = s.split(":");
			int res = (Integer.valueOf(ss[0]) - 8) * 60 + Integer.valueOf(ss[1]);
			if (0 <= res && res <= 600) return res;
			return -1;
		}
	}
	static class G {
		void run() {
			Scanner sc = new Scanner(System.in);
			for (;;) {
				int n = sc.nextInt();
				if (n == 0) return;
				int s = sc.nextInt();
				int[] ms = new int[n * 2];
				for (int i = 0; i < n * 2; i++) {
					ms[i] = sc.nextInt();
				}
				boolean[][] dp = new boolean[s + 1][n * 2];
				for (int i = 1; i <= s; i++) {
					for (int j = 0; j < n * 2; j++) {
						boolean win = false;
						for (int k = 1; k <= ms[j]; k++) {
							if (i - k >= 1 && !dp[i - k][(j + 1) % (n * 2)]) {
								win = true;
								break;
							}
						}
						dp[i][j] = win;
					}
				}
				System.out.println(dp[s][0] ? 1 : 0);
			}
		}
	}
	static class H {
		P o;
		P[] ps;
		int n;
		double step;
		void run() {
			Scanner sc = new Scanner(System.in);
			for (;;) {
				n = sc.nextInt();
				if (n == 0) return;
				ps = new P[n];
				o = new P(0, 0, 0);
				for (int i = 0; i < n; i++) {
					ps[i] = new P(sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
					o = o.add(ps[i]);
				}
				o = o.mul(1. / n);
				step = 10;
				for (int i = 0; i < 10; i++) {
					for (int count = 0; count < 200; count++) {
						if (!move()) break;
					}
					step /= 5;
				}
				System.out.println(o.dist(mostDistPoint()));
			}
		}
		boolean eq(double a, double b) {
			return abs(a - b) < step / 100;
		}
		P mostDistPoint() {
			int res = 0;
			double dist = 0;
			for (int i = 0; i < n; i++) {
				if (dist < o.dist(ps[i])) {
					dist = o.dist(ps[i]);
					res = i;
				}
			}
			return ps[res];
		}
		boolean move() {
			P p = mostDistPoint();
			if (eq(o.dist(p), 0)) return false;
			P d = p.sub(o);
			d = d.mul(1. * step / d.norm());
			o = o.add(d);
			return true;
		}
		class P {
			double x, y, z;
			P(double x, double y, double z) {
				this.x = x;
				this.y = y;
				this.z = z;
			}
			P add(P p) {
				return new P(x + p.x, y + p.y, z + p.z);
			}
			P sub(P p) {
				return new P(x - p.x, y - p.y, z - p.z);
			}
			P mul(double d) {
				return new P(x * d, y * d, z * d);
			}
			double norm() {
				return sqrt(x * x + y * y + z * z);
			}
			double dist(P p) {
				return sub(p).norm();
			}
		}
	}
}
