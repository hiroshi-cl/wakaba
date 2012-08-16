package com.github.hiroshi_cl.wakaba.v2009.sol.r2007.i;
import static java.lang.Math.*;
import java.util.*;
/*
 * 三線分に接する円の中心が、候補点となる。線分に接する円の中心は、角の二等分線の交点ということから求めればよい。
 * 平行の場合でも、交わる２点から、二等分線を引けばよい。
 */
class Main {
	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			n = sc.nextInt();
			if (n == 0) return;
			ps = new P[n + 1];
			for (int i = 0; i < n; i++)
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());
			ps[n] = ps[0];
			double res = 0;
			for (int i = 0; i < n; i++)
				for (int j = i + 1; j < n; j++)
					for (int k = j + 1; k < n; k++) {
						P c = center(ps[i], ps[i + 1], ps[j], ps[j + 1], ps[k], ps[k + 1]);
						res = max(res, dist(c));
					}
			System.out.printf("%.6f%n", res);
		}
	}
	double EPS = 1e-6;
	int n;
	int INF = 1 << 27;
	P[] ps;
	P center(P a1, P a2, P b1, P b2, P c1, P c2) {
		P[] ps = new P[5];
		ps[0] = ps[3] = intersection_ll(a1, a2, b1, b2);
		ps[1] = ps[4] = intersection_ll(b1, b2, c1, c2);
		ps[2] = intersection_ll(c1, c2, a1, a2);
		P[] ms = new P[5];
		ms[0] = ms[3] = a1.add(a2).div(2);
		ms[1] = ms[4] = b1.add(b2).div(2);
		ms[2] = c1.add(c2).div(2);
		for (int i = 0;; i++)
			if (ps[i] == null || i == 2) {
				P d1 = ms[i + 1].sub(ps[i + 1]).unit(), d2 = ms[i + 2].sub(ps[i + 1]).unit();
				P d3 = ms[i + 2].sub(ps[i + 2]).unit(), d4 = ms[i].sub(ps[i + 2]).unit();
				return intersection_ll(ps[i + 1], ps[i + 1].add(d1.add(d2).unit()), ps[i + 2], ps[i + 2].add(d3.add(d4).unit()));
			}
	}
	double dist(P p) {
		if (contains(ps, p) == -1) return 0;
		double res = INF;
		for (int i = 0; i < n; i++)
			res = min(res, dist_lp(ps[i], ps[i + 1], p));
		return res;
	}
	double dist_lp(P p1, P p2, P p) {// 点と直線の距離
		P p1p2 = p2.sub(p1), p1p = p.sub(p1);
		return abs(p1p2.det(p1p)) / p1p2.norm();
	}
	P intersection_ll(P p1, P p2, P q1, P q2) {// 直線と直線の交点
		P p1p2 = p2.sub(p1);
		P q1q2 = q2.sub(q1);
		double d = q1q2.det(p1p2);
		return eq(d, 0) ? null : p1.add(p1p2.mul(q1q2.det(q1.sub(p1)) / d));
	}
	int contains(P[] ps, P p) {// 点、多角形包含判定 OUT,ON,IN = -1, 0, 1.
		int n = ps.length;
		int res = -1;
		for (int i = 0; i < n; i++) {
			P a = ps[i].sub(p), b = ps[(i + 1) % n].sub(p);
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
	boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}
	public class P {
		double x, y;
		P(double x, double y) {
			this.x = x;
			this.y = y;
		}
		P add(P p) {
			return new P(x + p.x, y + p.y);
		}
		double det(P p) {
			return x * p.y - y * p.x;
		}
		P div(double d) {
			return new P(x / d, y / d);
		}
		double dot(P p) {
			return x * p.x + y * p.y;
		}
		P mul(double d) {
			return new P(x * d, y * d);
		}
		double norm() {
			return sqrt(x * x + y * y);
		}
		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}
		P unit() {
			double d = norm();
			return new P(x / d, y / d);
		}
	}
	static public void main(String[] args) {
		new Main().run();
	}
}
