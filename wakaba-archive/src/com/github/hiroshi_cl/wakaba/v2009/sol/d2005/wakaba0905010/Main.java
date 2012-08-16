package com.github.hiroshi_cl.wakaba.v2009.sol.d2005.wakaba0905010;
import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class Main {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("A.in"));
			System.setOut(new PrintStream("A.out"));
		} catch (Exception e) {}
		new E().run(sc);
	}
	static void debug(Object... os) {
		// System.err.println(deepToString(os));
	}
	static int INF = 1 << 27;
	static double EPS = 1e-9;
	static int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
	static boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}
	static class A {
		void run(Scanner sc) {
			int o=sc.nextInt();
			while(o-->0){
				int first=sc.nextInt();
				int year=sc.nextInt();
				int n=sc.nextInt();
				int res=0;
				for(int i=0;i<n;i++){
					boolean tanri=sc.nextInt()==0;
					double riritu=sc.nextDouble();
					int tesu=sc.nextInt();
					int money=first;
					int risi=0;
					for(int j=0;j<year;j++){
						if(tanri){
							risi += (int) money * riritu;
							money -= tesu;
						}
						else{
							money += money * riritu - tesu;
							
						}
					}
					res = max(money + risi, res);
				}
				System.out.println(res);
			}
		}
	}
	static class B {
		void run(Scanner sc) {
			// int o = sc.nextInt();
			while (true) {
				int n = sc.nextInt();
				if (n == 0) return;
				P[][] ps = new P[n + 1][];
				P[][] psrev = new P[n + 1][];
				for (int i = 0; i <= n; i++) {
					int m = sc.nextInt();
					ps[i] = new P[m];
					psrev[i] = new P[m];
					for (int j = 0; j < m; j++) {
						ps[i][j] = new P(sc.nextInt(), sc.nextInt());
					}
					for (int j = 0; j < m; j++) {
						psrev[i][m - 1 - j] = ps[i][j];
					}
				}
				for (int i = 1; i <= n; i++) {
					if (equivalent(ps[0], ps[i]) || equivalent(ps[0], psrev[i]))
						System.out.println(i);
				}
				System.out.println("+++++");
			}
		}
		boolean equivalent(P[] ps, P[] qs) {
			int n = ps.length;
			if (ps.length != qs.length) return false;
			for (int i = 1; i < n; i++) {
				if (ps[i].sub(ps[i - 1]).norm2() != qs[i].sub(qs[i - 1]).norm2()) return false;
			}
			for (int i = 2; i < n; i++) {
				if (ps[i].sub(ps[i - 1]).dot(ps[i - 2].sub(ps[i - 1])) != qs[i].sub(qs[i - 1]).dot(
				qs[i - 2].sub(qs[i - 1]))) return false;
			}
			return true;
		}
		class P {
			int x, y;
			P(int x, int y) {
				this.x = x;
				this.y = y;
			}
			P sub(P p) {
				return new P(x - p.x, y - p.y);
			}
			int dot(P p) {
				return x * p.y - y * p.x;
			}
			int norm2() {
				return x * x + y * y;
			}
		}
	}
	static class C {
		void run(Scanner sc) {
			int o = sc.nextInt();
			while (o-- > 0) {
				char[] cs = sc.next().toCharArray();
				char[] ds = sc.next().toCharArray();
				int res = c2i(cs) + c2i(ds);
				i2c(res);
			}
		}
		final char[] tani = new char[] { 'm', 'c', 'x', 'i' };
		int c2i(char[] cs) {
			int n = cs.length;
			int p = 0;
			int res = 0;
			while (p < n) {
				if (Character.isDigit(cs[p])) {
					switch (cs[p + 1]) {
					case 'm':
						res += (cs[p] - '0') * 1000;
						break;
					case 'c':
						res += (cs[p] - '0') * 100;
						break;
					case 'x':
						res += (cs[p] - '0') * 10;
						break;
					case 'i':
						res += (cs[p] - '0') * 1;
						break;
					}
					p += 2;
				} else {
					switch (cs[p]) {
					case 'm':
						res += 1000;
						break;
					case 'c':
						res += 100;
						break;
					case 'x':
						
						res += 10;
						break;
					case 'i':
						
						res += 1;
						break;
					}
					p++;
				}
			}
			return res;
		}
		void i2c(int n) {
			while(n>0){
				if (n >= 1000) {
					if (n / 1000 > 1) {
						System.out.print(n / 1000);
					}
					n %= 1000;
					System.out.print(tani[0]);
				}
				if (n >= 100) {
					if (n / 100 > 1) {
						System.out.print(n / 100);
					}
					n %= 100;
					System.out.print(tani[1]);
				}
				if (n >= 10) {
					if (n / 10 > 1) {
						System.out.print(n / 10);
					}
					n %= 10;
					System.out.print(tani[2]);
				}
				if (n >= 1) {
					if (n / 1 > 1) {
						System.out.print(n / 1);
					}
					n %= 1;
					System.out.print(tani[3]);
				}
			}
			System.out.println();
		}
	}
	static class D {
		void run(Scanner sc) {
			for (;;) {
				int n = sc.nextInt(), m = sc.nextInt(), p = sc.nextInt(), a = sc.nextInt(), b = sc
				.nextInt();
				if (n + m + p + a + b == 0) return;
				a--;b--;
				double[] ts = new double[n];
				for (int i = 0; i < n; i++) {
					ts[i] = sc.nextDouble();
				}
				double[][] es = new double[m][m];
				for (int i = 0; i < m; i++)
					fill(es[i], INF);
				V[][] vss=new V[m][1<<n];
				for(int i=0;i<m;i++){
					for(int j=0;j<(1<<n);j++){
						vss[i][j]=new V(i,j);
						vss[i][j].cost=INF;
					}
				}
				
				for (int i = 0; i < p; i++) {
					int from = sc.nextInt() - 1, to = sc.nextInt() - 1;
					double cost=sc.nextDouble();
					for (int j = 0; j < (1 << n); j++) {
						for (int k = 0; k < n; k++) {
							if ((j & (1 << k)) == 0) {
								vss[from][j].es.add(new E(vss[to][j | (1 << k)], cost / ts[k]));
								vss[to][j].es.add(new E(vss[from][j | (1 << k)], cost / ts[k]));
							}
						}
					}
				}
				vss[a][0].cost=0;
				PriorityQueue<E> q = new PriorityQueue<E>();
				q.offer(new E(vss[a][0], 0));
				double res = INF;
				while (!q.isEmpty()) {
					V v = q.poll().to;
					// debug(v.node, v.cost);
					if (v.node == b) res = min(res, v.cost);
					for (E e : v.es) {
						if (e.to.cost > v.cost + e.cost) {
							e.to.cost = v.cost + e.cost;
							q.offer(e);
						}
					}
				}
				System.out.println(res == INF ? "Impossible" : res);
			}
		}
		
		class V {
			ArrayList<E> es = new ArrayList<E>();
			int id;
			int node;
			double cost;
			V(int node, int id) {
				this.id = id;
				this.node = node;
			}
		}
		class E implements Comparable<E> {
			V to;
			double cost;
			E(V to, double cost) {
				this.to = to;
				this.cost = cost;
			}
			public int compareTo(E o) {
				return signum(to.cost + cost - (o.to.cost + o.cost));
			}
		}
	}
	static class E {
		int R;
		void run(Scanner sc) {
			for (;;) {
				int n = sc.nextInt(), t = sc.nextInt(), r = sc.nextInt();
				if (n + t + r == 0) return;
				R = r;
				robotinfo = new RobotInfo[n];
				robots = new Robot[n];
				for (int i = 0; i < n; i++) {
					robotinfo[i] = new RobotInfo(sc, t);
					robots[i] = new Robot(robotinfo[i]);
				}
				ArrayList<Integer> times_int = new ArrayList<Integer>();
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < robotinfo[i].k; j++) {
						times_int.add(robotinfo[i].ts[j]);
					}
				}
				sort(times_int);
				debug(times_int);
				times_double = new TreeSet<Double>();
				for (int i = 0; i < times_int.size(); i++) {
					int time = times_int.get(i);
					if (i > 0 && time == (times_int.get(i - 1))) continue;
					times_double.add((double) time);
					simu(time);
				}
				for (int i = 0; i < n; i++) {
					robots[i] = new Robot(robotinfo[i]);
				}
				robots[0].hasinfo = true;
				for (double ct : times_double) {
					for (int j = 0; j < n; j++)
						robots[j].simu(ct);
					boolean[][] bss = new boolean[n][n];
					for (int l = 0; l < n; l++) {
						// if (robots[l].hasinfo) {
						for (int m = 0; m < n; m++) {
							if (robots[l].intersect(robots[m])) {
								// robots[m].hasinfo = true;
								bss[l][m] = true;
							}
						}
						// }
					}
					for (int k = 0; k < n; k++) {
						for (int l = 0; l < n; l++) {
							for (int m = 0; m < n; m++) {
								bss[l][m] |= bss[l][k] & bss[k][m];
							}
						}
					}
					for(int k=0;k<n;k++){
						for(int l=0;l<n;l++){
							if(robots[k].hasinfo && bss[k][l])robots[l].hasinfo=true;
						}
					}
				}
				ArrayList<String> ns = new ArrayList<String>();
				for (int i = 0; i < n; i++) {
					if (robots[i].hasinfo) ns.add(robots[i].info.nickname);
				}
				sort(ns);
				for (String s : ns)
					System.out.println(s);
			}
		}
		RobotInfo[] robotinfo;
		Robot[] robots;
		TreeSet<Double> times_double;
		void simu(double t) {
			int n = robots.length;
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					double intersect = robots[i].t
					+ intersect(robots[i], robots[j], t - robots[i].t);
					if (intersect >= 0) times_double.add(intersect);
				}
			}
			for (int i = 0; i < n; i++) {
				robots[i].simu(t);
			}
		}
		double intersect(Robot p, Robot q, double interval) {
			double a = pow((p.vx - q.vx), 2) + pow((p.vy - q.vy), 2);
			double b = (p.x - q.x) * (p.vx - q.vx) + (p.y - q.y) * (p.vy - q.vy);
			double c = pow(p.x - q.x, 2) + pow(p.y - q.y, 2) - R * R;
			double d = b * b - a * c;
			debug(a, b, p.x, p.y);
			if (d <= 0) return -1;
			double t1 = (-b - sqrt(d)) / a;
			double t2 = (-b + sqrt(d)) / a;
			debug(t1, t2);
			if (0 <= t1 && t1 <= interval) return t1;
			if (0 <= t2 && t2 <= interval) return t2;
			return -1;
		}
		
		class Robot {
			RobotInfo info;
			int k;
			double x, y;
			double vx, vy;
			double t;
			boolean hasinfo;
			Robot(RobotInfo info) {
				x = info.nx;
				y = info.ny;
				t = 0;
				this.k = info.k;
				this.vx = info.nvx;
				this.vy = info.nvy;
				this.info = info;
			}
			void simu(double after) {
				for (int i = 1; i < k; i++) {
					if (t < info.ts[i]) {
						x += info.vxs[i] * (after - t);
						y += info.vys[i] * (after - t);
						if (after < info.ts[i]) ;
						else {
							vx = info.vxs[i + 1];
							vy = info.vys[i + 1];
						}
						t = after;
						break;
					}
				}
			}
			boolean intersect(Robot o) {
				return (x - o.x) * (x - o.x) + (y - o.y) * (y - o.y) < R * R + 0.00000001;
			}
		}
		class RobotInfo {
			String nickname;
			int nx, ny;
			int nvx, nvy;
			int[] ts;
			int[] vxs;
			int[] vys;
			int k = 1;
			RobotInfo(Scanner sc, int t) {
				nickname=sc.next();
				ts[0] = sc.nextInt();
				nx = sc.nextInt();
				ny = sc.nextInt();
				for (;;) {
					ts[k] = sc.nextInt();
					vxs[k] = sc.nextInt();
					vys[k] = sc.nextInt();
					if (ts[k++] == t) break;
				}
				nvx = vxs[1];
				nvy = vys[1];
			}
		}
	}
	static class F {
		boolean nextPermutation(int[] is) {
			int n = is.length;
			for (int i = n - 1; i > 0; i--) {
				if (is[i - 1] < is[i]) {
					int j = n;
					while (is[i - 1] >= is[--j]);
					swap(is, i - 1, j);
					rev(is, i, n);
					return true;
				}
			}
			rev(is, 0, n);
			return false;
		}
		void swap(int[] is, int i, int j) {
			int t = is[i];
			is[i] = is[j];
			is[j] = t;
		}
		void rev(int[] is, int s, int t) {
			while (s < --t)
				swap(is, s++, t);
		}
		int[] dx = new int[] { 1, 0, -1, 0 };
		int[] dy = new int[] { 0, 1, 0, -1 };
		void run(Scanner sc) {
			for (;;) {
				int w = sc.nextInt(), h = sc.nextInt();
				if (w + h == 0) return;
				char[][] css = new char[h][w];
				int m = 1;
				for (int i = 0; i < h; i++) {
					css[i] = sc.next().toCharArray();
					for (int j = 0; j < w; j++)
						if (css[i][j] == '*') m++;
				}
				int[] xs = new int[m], ys = new int[m];
				for (int i = 0, k = 1; i < h; i++) {
					for (int j = 0; j < w; j++) {
						if (css[i][j] == '*') {
							xs[k] = i;
							ys[k++] = j;
						}
						if (css[i][j] == 'o') {
							xs[0] = i;
							ys[0] = j;
						}
					}
				}
				int[][][][] f = new int[h][w][h][w];
				for (int i = 0; i < h; i++)
					for (int j = 0; j < w; j++)
						for (int k = 0; k < h; k++)
							fill(f[i][j][k], INF);
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						if (css[i][j] != 'x')
							for (int d = 0; d < 4; d++) {
							int ni = i + dx[d];
							int nj = j + dy[d];
							if (0 <= ni && ni < h && 0 <= nj && nj < w) {
								if (css[ni][nj] != 'x') {
									f[i][j][ni][nj] = 1;
								}
								}
							}
					}
				}
				for (int k1 = 0; k1 < h; k1++) {
					for (int k2 = 0; k2 < w; k2++) {
						for (int i1 = 0; i1 < h; i1++) {
							for (int i2 = 0; i2 < w; i2++) {
								for (int j1 = 0; j1 < h; j1++) {
									for (int j2 = 0; j2 < w; j2++) {
										f[i1][i2][j1][j2] = min(f[i1][i2][j1][j2],
										f[i1][i2][k1][k2] + f[k1][k2][j1][j2]);
									}
								}
							}
						}
					}
				}
				// debug(f);
				int[][] dist = new int[m][m];
				for (int i = 0; i < m; i++) {
					for (int j = 0; j < m; j++) {
						dist[i][j] = f[xs[i]][ys[i]][xs[j]][ys[j]];
					}
				}
				int[] perm = new int[m - 1];
				for (int i = 1; i < m; i++)
					perm[i - 1] = i;
				int res = INF;
				do {
					int tmp = 0;
					tmp += dist[0][perm[0]];
					for (int i = 1; i < m - 1; i++) {
						tmp += dist[perm[i - 1]][perm[i]];
					}
					res = min(res, tmp);
				} while (nextPermutation(perm));
				System.out.println(res == INF ? -1 : res);
			}
		}
	}
}
