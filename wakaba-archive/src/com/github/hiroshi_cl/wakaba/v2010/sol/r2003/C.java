package com.github.hiroshi_cl.wakaba.v2010.sol.r2003;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class C {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		// try {
		// sc = new Scanner(new File("A"));
		// System.setOut(new PrintStream("A.out"));
		// } catch (Exception e) {}
		for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			ps = new P[n + 1];
			int min = INF, max = -INF;
			for (int i = 0; i < n; i++) {
				int x = sc.nextInt(), y = sc.nextInt();
				min = min(x, min);
				max = max(x, max);
				ps[i] = new P(x, y);
			}
			ps[n] = ps[0];
			int res = 0;
			for (int x = min; x < max; x++) {
				P p1 = new P(x + EPS * 2, 0);
				P p2 = new P(x + EPS * 2, 1);
				P q1 = new P(x + 1 - EPS * 2, 0);
				P q2 = new P(x + 1 - EPS * 2, 1);
				ArrayList<Entry> list = new ArrayList<Entry>();
				for (int i = 0; i < n; i++) {
					P itr1 = intersection_ls(p1, p2, ps[i], ps[i + 1]);
					P itr2 = intersection_ls(q1, q2, ps[i], ps[i + 1]);
					if (itr1 != null) {
						debug(x, i);
						debug(itr1, itr2);
						list.add(new Entry(min(itr1.y, itr2.y), max(itr1.y, itr2.y)));
					}
				}
				sort(list);
				int tmp = 0;
				int t = -INF;
				for (int i = 0; i < list.size() / 2; i++) {
					int s = (int) floor(list.get(i * 2).s);
					int nt = (int) ceil(list.get(i * 2 + 1).t);
					tmp += nt - max(s, t);
					t = nt;
				}
				// debug(x);
				// debug(list);
				// debug(tmp);
				// debug();
				res += tmp;
			}
			System.out.println(res);
		}
	}
	static double EPS = 1e-7;
	class Entry implements Comparable<Entry> {
		double s, t;
		Entry(double s, double t) {
			this.s = s;
			this.t = t;
		}
		public int compareTo(Entry o) {
			return (int) signum(s - o.s);
		}
		public String toString() {
			return s + " " + t;
		}
	}
	static P intersection_ls(P l1, P l2, P s1, P s2) {
		return intersect_ls(l1, l2, s1, s2) ? intersection_ll(l1, l2, s1, s2) : null;
	}
	static P intersection_ll(P p1, P p2, P q1, P q2) {// 直線と直線の交点
		P p1p2 = p2.sub(p1), p1q1 = q1.sub(p1), q1q2 = q2.sub(q1);
		double d = q1q2.det(p1p2);
		return eq(d, 0) ? null : p1.add(p1p2.mul(q1q2.det(p1q1) / d));
	}
	static boolean intersect_ls(P l1, P l2, P s1, P s2) {
		if (intersect_lp(l1, l2, s1) || intersect_lp(l1, l2, s2)) return true;
		P l1l2 = l2.sub(l1);
		return l1l2.det(s1.sub(l1)) * l1l2.det(s2.sub(l1)) < 0;
	}
	static boolean intersect_lp(P p1, P p2, P p) {// pが直線p1p2上にあるか。
		return eq(p1.sub(p).det(p2.sub(p)), 0);
	}
	static boolean eq(double a, double b) {
		return abs(a - b) < EPS;
	}
	int INF = 1 << 27;
	P[] ps;

	public static void main(String[] args) {
		new C().run();
	}
}
class P implements Comparable<P> {
	double x, y;
	static private final double EPS = 1e-9;
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
	P unit() {
		double d = norm();
		return new P(x / d, y / d);
	}
	P unit(double d) {
		return mul(d / norm());
	}
	double angle() {// verified.
		return atan2(y, x);// [-PI,PI].
	}
	double angle(P p) {// not verified.
		return (p.angle() - angle() + 3 * PI) % (2 * PI) - PI;// [-PI,PI].
	}
	static private int signum(double d) {
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
