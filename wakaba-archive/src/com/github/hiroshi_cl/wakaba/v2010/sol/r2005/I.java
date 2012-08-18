package com.github.hiroshi_cl.wakaba.v2010.sol.r2005;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class I {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			l = sc.nextDouble();
			if (l == 0) return;
			n = sc.nextInt();
			P[] rs = new P[n];
			double minrx = 10000, maxrx = -10000;
			for (int i = 0; i < n; i++) {
				rs[i] = new P(sc.nextDouble(), sc.nextDouble());
				minrx = min(minrx, rs[i].x);
				maxrx = max(maxrx, rs[i].x);
			}
			m = sc.nextInt();
			double minpx = 10000, maxpx = -10000;
			P[] ps = new P[m];
			for (int i = 0; i < m; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());
				minpx = min(minpx, ps[i].x - ps[0].x);
				maxpx = max(maxpx, ps[i].x - ps[0].x);
			}
			ArrayList<Entry> list = new ArrayList<Entry>();
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					double low = -10000, high = 10000;
					double d1 = 0;
					while (high - low > EPS) {
						double m1 = ( low * 2 + high ) / 3;
						double m2 = ( low + high * 2 ) / 3;
						P s1 = new P(m1, 0);
						P s2 = new P(m2, 0);
						d1 = dist_ss(rs[j], rs[( j + 1 ) % n], ps[i].add(s1), ps[( i + 1 ) % m]
						.add(s1));
						double d2 = dist_ss(rs[j], rs[( j + 1 ) % n], ps[i].add(s2), ps[( i + 1 )
						% m].add(s2));
						if (d1 < d2) {
							high = m2;
						} else low = m1;
					}
					if (d1 > l) continue;
					double pole = low;
					low = -10000;
					high = pole;
					while (high - low > EPS) {
						double mid = ( low + high ) / 2;
						P s = new P(mid, 0);
						if (dist_ss(rs[j], rs[( j + 1 ) % n], ps[i].add(s), ps[( i + 1 ) % m]
						.add(s)) < l) {
							high = mid;
						} else {
							low = mid;
						}
					}
					double s = low;
					low = pole;
					high = 10000;
					while (high - low > EPS) {
						double mid = ( low + high ) / 2;
						P s2 = new P(mid, 0);
						if (dist_ss(rs[j], rs[( j + 1 ) % n], ps[i].add(s2), ps[( i + 1 ) % m]
						.add(s2)) < l) {
							low = mid;
						} else {
							high = mid;
						}
					}
					double t = low;
					list.add(new Entry(s, t));
				}
			}
			if (list.size() == 0) {
				System.out.println(max(maxpx - minpx, maxrx - minrx));
				continue;
			}
			sort(list);
			ArrayList<Entry> es = new ArrayList<Entry>();
			double s = list.get(0).s, t = list.get(0).t;
			for (int j = 1; j < list.size(); j++) {
				if (list.get(j).s < t) {
					t = max(t, list.get(j).t);
					if (j == list.size() - 1) {
						es.add(new Entry(s, t));
					}
				} else {
					es.add(new Entry(s, t));
					s = list.get(j).s;
					t = list.get(j).t;
				}
			}
			debug(es);
			double res = 10000;
			debug(maxpx, minpx);
			for (Entry e : es) {
				P p = new P(e.s, 0);
				if (contains(rs, ps[0].add(p)) != 1 && contains(ps, rs[0].sub(p)) != 1) {
					debug(max(maxrx, e.s + maxpx) - min(minrx, e.s + minpx));
					boolean ok = true;
					// loop:for (int j = 0; j < n; j++) {
					// for (int k = 0; k < m; k++) {
					// if(intersect_ss(rs[j], rs[(j+1)%n], ps[k].sub(ps[0]).add(p),
					// ps[(k+1)%m].sub(ps[0]).add(p))){
					// ok=false;
					// break loop;
					// }
					// }
					// }
					if (ok) res = min(res, max(maxrx, e.s + maxpx) - min(minrx, e.s + minpx));
				}
				p = new P(e.t, 0);
				if (contains(rs, ps[0].add(p)) != 1 && contains(ps, rs[0].sub(p)) != 1) {
					debug(max(maxrx, e.t + maxpx) - min(minrx, e.t + minpx));
					boolean ok = true;
					// loop:for (int j = 0; j < n; j++) {
					// for (int k = 0; k < m; k++) {
					// if(intersect_ss(rs[j], rs[(j+1)%n], ps[k].sub(ps[0]).add(p),
					// ps[(k+1)%m].sub(ps[0]).add(p))){
					// ok=false;
					// break loop;
					// }
					// }
					// }
					if (ok) res = min(res, max(maxrx, e.t + maxpx) - min(minrx, e.t + minpx));
				}
			}
			System.out.println(res);
		}
	}
	double dist_ss(P p1, P p2, P q1, P q2) {// 線分と線分の距離
		if (intersect_ss(p1, p2, q1, q2)) return 0;
		double res = Integer.MAX_VALUE;
		res = min(res, dist_sp(p1, p2, q1));
		res = min(res, dist_sp(p1, p2, q2));
		res = min(res, dist_sp(q1, q2, p1));
		res = min(res, dist_sp(q1, q2, p2));
		return res;
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
	int contains(P[] ps, P p) {// 点、多角形包含判定 OUT,ON,IN = -1, 0, 1.
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
	int INF = 1 << 28;
	class Entry implements Comparable<Entry> {
		double s, t;
		@Override
		public String toString() {
			// TODO 自動生成されたメソッド・スタブ
			return s + " " + t;
		}
		public Entry(double s, double t) {
			super();
			this.s = s;
			this.t = t;
		}
		public int compareTo(Entry o) {
			// TODO 自動生成されたメソッド・スタブ
			return s - o.s != 0 ? (int) signum(s - o.s) : (int) signum(t - o.t);
		}
	}
	double l;
	int n;
	int m;
	final double EPS = 1e-7;// problem specific.
	P perpendicularFoot_s(P p1, P p2, P p) {// 線分への垂線の足。存在しないときはnullを返す。
		P pp1 = p1.sub(p), p1p2 = p2.sub(p1);
		double t = -pp1.dot(p1p2) / p1p2.norm2();
		return le(0, t) && le(t, 1) ? p1.add(p1p2.mul(t)) : null;
	}
	double dist_sp(P p1, P p2, P p) {// 線分と点の距離
		P foot = perpendicularFoot_s(p1, p2, p);
		return foot == null ? min(p1.dist(p), p2.dist(p)) : foot.dist(p);
	}
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
		new I().run();
	}
}
