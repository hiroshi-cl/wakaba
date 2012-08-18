package com.github.hiroshi_cl.wakaba.v2010.sol.d2007;

import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import java.math.*;
import java.util.*;

class Main {
	public static void main(String[] args) {
		new C().run();
	}

	static void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner();
		for (;;) {
			l = sc.nextDouble();
			r = sc.nextDouble() * 2 * PI;
			n = sc.nextInt();
			if (l == 0 && r == 0 && n == 0)
				return;
			ps = new P[n];
			for (int i = 0; i < n; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());
			}
			Stick now = new Stick(new P(0, 0), new P(0, 0), new P(0, l), 0);
			while (now.canRot()) {
				debug(now);
				debug("sum", now.sum);
				now = now.nextCond();
				if (eq(r, 0))
					break;
			}
			debug(now);
			System.out.printf("%.06f %.06f%n", now.a.x, now.a.y);
		}
	}

	double l, r;
	int n;
	P[] ps;
	double EPS = 0.00001;

	class Stick {
		public String toString() {
			return "st : (" + a.toString() + ") (" + c.toString() + ") ("
					+ b.toString() + ")";
		}

		P c;
		P a, b;
		double sum;

		public Stick(P c, P a, P b, double sum) {
			super();
			this.c = c.clone();
			this.a = a.clone();
			this.b = b.clone();
			this.sum = sum;
		}

		boolean canRot() {// O(n).
			Stick ns = rot(EPS * 2);
			for (int i = 0; i < n; i++) {
				if (intersect_ss(ns.a, ns.b, ps[i], ps[(i + 1) % n]))
					return false;
			}
			return true;
		}

		Stick nextCond() {
			TreeSet<P> candA = new TreeSet<P>();
			TreeSet<P> candB = new TreeSet<P>();
			for (int i = 0; i < n; i++) {
				P[] cps = intersection_sc(ps[i], ps[(i + 1) % n], c, c.dist(a));
				if (cps != null) {
					for (P cp : cps) {
						// debug("cpa: " + cp.toString());
						candA.add(cp);
					}
				}
				if (le(ps[i].dist(c), c.dist(a))) {
					candA.add(ps[i]);
				}
				cps = intersection_sc(ps[i], ps[(i + 1) % n], c, c.dist(b));
				if (cps != null) {
					for (P cp : cps) {
						candB.add(cp);
					}
				}
				if (ps[i].dist(c) < c.dist(b)) {
					candB.add(ps[i]);
				}
			}
			debug(candA);
			debug(candB);
			double th = r;
			double len = 0;
			P nc = c;
			for (P na : candA) {
				if (c.equals(na))
					continue;
				double nth = a.sub(b).angle(na.sub(c));
				if (eq(nth, 0))
					continue;
				if (lt(nth, 0))
					nth += 2 * PI;
				if (lt(nth, th) || eq(nth, th) && len < na.dist(c)) {
					th = nth;
					len = na.dist(c);
					nc = na;
				}
			}
			for (P nb : candB) {
				if (c.equals(nb))
					continue;
				double nth = b.sub(a).angle(nb.sub(c));

				// debug(nth);

				if (nth < 0)
					nth += 2 * PI;
				if (eq(nth, 0))
					continue;
				if (lt(nth, th) || eq(nth, th) && len < nb.dist(c)) {
					th = nth;
					len = nb.dist(c);
					nc = nb;
				}
			}
			debug(th);
			debug(nc);
			Stick res = rot(th);
			res.c = nc.clone();
			r -= th;
			return res;
		}

		Stick rot(double th) {
			P na = a.sub(c).rot(-th).add(c);
			P nb = b.sub(c).rot(-th).add(c);
			return new Stick(c, na, nb, sum + th);
		}
	}

	P[] intersection_sc(P p1, P p2, P o, double r) {// 線分と円の交点
		P op1 = p1.sub(o), p1p2 = p2.sub(p1);
		double a = p1p2.norm2();
		double b = op1.dot(p1p2);
		double c = op1.norm2() - r * r;
		double d = b * b - a * c;
		if (eq(d, 0))
			d = 0;
		else if (le(d, 0))
			return null;
		double t1 = (-b + sqrt(d)) / a, t2 = (-b - sqrt(d)) / a;
		ArrayList<P> res = new ArrayList<P>();
		if (le(0, t1) && le(t1, 1))
			res.add(p1.add(p1p2.mul(t1)));
		if (t1 != t2 && le(0, t2) && le(t2, 1))
			res.add(p1.add(p1p2.mul(t2)));
		return res.toArray(new P[0]);
	}

	boolean intersect_ss(P p1, P p2, P q1, P q2) {// 線分と線分が交わるか。
		// if (intersect_sp(p1, p2, q1) || intersect_sp(p1, p2, q2) ||
		// intersect_sp(q1,
		// q2, p1) || intersect_sp(q1, q2, p2)) return true; // 端点を共有
		P p1p2 = p2.sub(p1), q1q2 = q2.sub(q1);
		return lt(p1p2.det(q1.sub(p1)) * p1p2.det(q2.sub(p1)), 0) // 真に交わる
				&& lt(q1q2.det(p1.sub(q1)) * q1q2.det(p2.sub(q1)), 0);
	}

	class P implements Comparable<P> {
		double x, y;

		public P(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return x + " " + y;
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

		double angle() {
			return atan2(y, x);
		}

		double angle(P p) {
			double res = angle() - p.angle();
			if (res < -PI)
				res += 2 * PI;
			if (res > PI)
				res -= 2 * PI;
			return res;
		}

		public int compareTo(P o) {
			return !eq(x - o.x, 0) ? (int) signum(x - o.x) : (int) signum(y
					- o.y);
		}

		P rot(double t) {// verified
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}

		protected P clone() {
			return new P(x, y);
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

		boolean equals(P p) {
			return eq(x, p.x) && eq(y, p.y);
		}
	}

	boolean eq(double a, double b) {
		return abs(a - b) < EPS;
	}

	boolean lt(double a, double b) {
		return b - a > EPS;
	}

	boolean le(double a, double b) {
		return b - a > -EPS;
	}

	class Scanner {
		int nextInt() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextInt();
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
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextLong();
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