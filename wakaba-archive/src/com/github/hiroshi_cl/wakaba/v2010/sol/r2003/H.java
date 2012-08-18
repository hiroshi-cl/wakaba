package com.github.hiroshi_cl.wakaba.v2010.sol.r2003;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class H {
	/*
	 * 4 -1 1 -1 -1 -1 -1 2 1 2 1 2 -1 2 -1 -1 1
	 */
	void run() {
		Scanner sc = new Scanner();
		for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			P[] ps = new P[n], rs = new P[n];
			for (int i = 0; i < n; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());
				rs[i] = new P(sc.nextDouble(), sc.nextDouble());
			}
			graph = new boolean[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++)
					if (i != j) {
						if (intersect_ss(ps[i], rs[i], ps[j], rs[j])) graph[i][j] = true;
					}
			}
			boolean[] check = new boolean[n];
			boolean[][] mst = new boolean[n][n];
			for (int i = 0; i < n; i++) {
				if (!check[i]) {
					dfs(mst, i, check);
				}
			}
			boolean res = false;
			loop: for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (graph[i][j] && !mst[i][j]) {
						int[] bef = new int[n];
						fill(bef, -1);
						Queue<Integer> que = new LinkedList<Integer>();
						que.offer(i);
						bef[i] = -2;
						while (!que.isEmpty()) {
							int now = que.poll();
							for (int k = 0; k < n; k++) {
								if (mst[now][k] && bef[k] == -1) {
									bef[k] = now;
									que.offer(k);
								}
							}
						}
						int now = j;
						ArrayList<P> list = new ArrayList<P>();
						int k = 0;
						while (bef[now] >= 0) {
							k++;
							int next = bef[now];
							list.add(intersection_ss(ps[now], rs[now], ps[next], rs[next]));
							now = bef[now];
						}
						list.add(intersection_ss(ps[i], rs[i], ps[j], rs[j]));
						if (contains(list.toArray(new P[0]), O) == 1) {
							res = true;
							break loop;
						}
					}
				}
			}
			System.out.println(res ? "yes" : "no");
		}
	}
	P O = new P(0, 0);
	boolean[][] graph;
	void dfs(boolean[][] mst, int id, boolean[] check) {
		check[id] = true;
		for (int i = 0; i < check.length; i++) {
			if (!check[i] && graph[i][id]) {
				mst[id][i] = mst[i][id] = true;
				dfs(mst, i, check);
			}
		}
	}
	int contains(P[] ps, P p) {// 点、多角形方眼判定 OUT,ON,IN = -1, 0, 1.
		int n = ps.length;
		int res = -1;
		for (int i = 0; i < n; i++) {
			P a = ps[i].sub(p), b = ps[( i + 1 ) % n].sub(p);
			if (a.y > b.y) {
				P t = a;
				a = b;
				b = t;
			}
			if (a.y <= 0 && 0 < b.y && a.det(b) < 0) res *= -1;
			if (a.det(b) == 0 && a.dot(b) <= 0) return 0;
		}
		return res;
	}
	P intersection_ss(P p1, P p2, P q1, P q2) {// 線分と線分の交点
		if (!intersect_ss(p1, p2, q1, q2)) return null;
		if (p1.equals(q1) || p1.equals(q2)) return p1;
		if (p2.equals(q1) || p2.equals(q2)) return p2;
		return intersection_ll(p1, p2, q1, q2);
	}
	P intersection_ll(P p1, P p2, P q1, P q2) {// 直線と直線の交点
		P p1p2 = p2.sub(p1), p1q1 = q1.sub(p1), q1q2 = q2.sub(q1);
		double d = q1q2.det(p1p2);
		return eq(d, 0) ? null : p1.add(p1p2.mul(q1q2.det(p1q1) / d));
	}
	boolean intersect_ss(P p1, P p2, P q1, P q2) {// 線分と線分が交わるか。
		if (intersect_sp(p1, p2, q1) || intersect_sp(p1, p2, q2) || intersect_sp(q1, q2, p1)
		|| intersect_sp(q1, q2, p2)) return true; // 端点を共有
		P p1p2 = p2.sub(p1), q1q2 = q2.sub(q1);
		return lt(p1p2.det(q1.sub(p1)) * p1p2.det(q2.sub(p1)), 0) // 真に交わる
		&& lt(q1q2.det(p1.sub(q1)) * q1q2.det(p2.sub(q1)), 0);
	}
	boolean intersect_sp(P p1, P p2, P p) {// pが線分p1p2上にあるか。
		P pp1 = p1.sub(p), pp2 = p2.sub(p);
		return eq(pp1.det(pp2), 0) && le(pp1.dot(pp2), 0);
	}
	final double EPS = 1e-9;// problem specific.
	public class P implements Comparable<P> {
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
		P div(double d) {
			return new P(x / d, y / d);
		}
		double dot(P p) {
			return x * p.x + y * p.y;
		}
		double det(P p) {
			return x * p.y - y * p.x;
		}
		double norm() {
			return sqrt(x * x + y * y);
		}
		double norm2() {
			return x * x + y * y;
		}
		double dist(P p) {
			return sub(p).norm();
		}
		P rot90() {
			return new P(-y, x);
		}
		P rot(double t) {// verified
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}
		double angle() {// verified.
			return atan2(y, x);// [-PI,PI].
		}
		P unit() {// 単位ベクトル
			double d = norm();
			return d < EPS ? null : new P(x / d, y / d);
		}
		int signum(double d) {
			return d < -EPS ? -1 : d > EPS ? 1 : 0;
		}
		public int compareTo(P o) {
			return signum(x - o.x) != 0 ? signum(x - o.x) : signum(y - o.y);
		}
		public boolean equals(Object o) {
			return compareTo((P) o) == 0;
		}
		public int hashCode() {
			return new Double(x).hashCode() * 0x0000f000 + new Double(y).hashCode();
		}
		public String toString() {
			return x + " " + y;
		}
	}
	boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}
	boolean le(double a, double b) {
		return signum(a - b) <= 0;
	}
	boolean lt(double a, double b) {
		return signum(a - b) < 0;
	}
	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
	public static void main(String[] args) {
		new H().run();
	}
	class Scanner {
		int nextInt() {
			try {
				int c = System.in.read();
				while (c != '-' && ( c < '0' || '9' < c ))
					c = System.in.read();
				if (c == '-') return -nextInt();
				int res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		long nextLong() {
			try {
				int c = System.in.read();
				while (c != '-' && ( c < '0' || '9' < c ))
					c = System.in.read();
				if (c == '-') return -nextLong();
				long res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		double nextDouble() {
			return Double.parseDouble(next());
		}
		String next() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (Character.isWhitespace(c))
					c = System.in.read();
				do {
					res.append((char) c);
				} while (!Character.isWhitespace(c = System.in.read()));
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
		String nextLine() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (c == '\r' || c == '\n')
					c = System.in.read();
				do {
					res.append((char) c);
					c = System.in.read();
				} while (c != '\r' && c != '\n');
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
	}
}
