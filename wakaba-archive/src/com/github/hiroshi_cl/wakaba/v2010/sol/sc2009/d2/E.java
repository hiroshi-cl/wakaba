package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d2;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class E {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (int o = 1;; o++) {
			 try {
			 sc = new Scanner(new File(o + ".in"));
			 System.setOut(new PrintStream(o + ".out"));
			 } catch (Exception e) {
			 return;
			 }
			int n = sc.nextInt();
			P[] init = new P[n];
			TreeSet<P> set = new TreeSet<P>();
			for (int i = 0; i < n; i++) {
				init[i] = new P(sc.nextDouble(), sc.nextDouble());
			}
			P[] cs = convexHull(init);
			debug(cs);
			int cn = cs.length;
			if(cn>n){
				System.out.println("No");
				continue;
			}
			for (int i = 0; i < cn; i++) {
				set.add(cs[i]);
			}
			TreeSet<P> inset = new TreeSet<P>();

			P[] in = new P[n - cn];
			for (int i = 0, j = 0; i < n; i++) {
				if (!set.contains(init[i])) {
					in[j] = init[i];
					inset.add(in[j]);
				}
			}
			debug(in);
			P[] ps = new P[cn * 2];
			for (int i = 0; i < cn; i++) {
				ps[i * 2] = cs[i];
				ps[i * 2 + 1] = cs[i].add(cs[( i + 1 ) % cn]).div(2);
			}
			// P[] ps=convexHull(qs);
			 debug(ps);
			boolean res = false;
			for (int i = 0; i < cn; i++) {
				P p1 = ps[i], p2 = ps[i + cn].sub(p1);
				boolean ok = true;
				for (int j = 1; j < cn; j++) {
					P r1 = ps[( i + j ) % ( cn * 2 )].sub(p1);
					P r2 = ps[( i - j + cn * 2 ) % ( cn * 2 )].sub(p1);
					if (!eq(r1.norm(), r2.norm()) || !eq(r1.angle(p2), p2.angle(r2))) {
						ok = false;
						break;
					}
				}
				if (ok) {
//					debug("ok");
					int count = 0;
					if(i%2==0)count++;
					if((i+cn)%2==0)count++;
					for (int j = 0; j < in.length; j++) {
						P r1 = in[j].sub(p1);
						P r2 = p1.add(r1.rot(p2.angle(r1) * 2));
						if (r1.equals(r2)) {
							count++;
						}
						if(count>2){
							ok=false;
							break;
						}
						if (!inset.contains(r2)) {
							ok = false;
							break;
						}
					}
					if (ok) res = true;
					break;
				}
			}
			System.out.println(res ? "Yes" : "No");
			Scanner sc1, sc2;
			try {
				sc1 = new Scanner(new File(o + ".out"));
				sc2 = new Scanner(new File(o + ".diff"));
				boolean ok = true;
				while (sc1.hasNext()) {
					if (!sc1.next().equals(sc2.next())) {
						ok = false;
						break;
					}
				}
				if (sc2.hasNext()) ok = false;
				System.err.println(ok);
			} catch (Exception e) {}
		}
	}
	double EPS = 1e-7;
	boolean eq(double a, double b) {
		return abs(a - b) < EPS;
	}
	boolean lt(double a, double b) {
		return b - a > EPS;
	}
	P[] convexHull(P[] ps) {
		int n = ps.length;
		sort(ps);
		P[] ds = new P[n];
		P[] us = new P[n];
		int dn = 0;
		int un = 0;
		for (int i = 0; i < n; ds[dn++] = ps[i++])
			while (dn >= 2 && lt(ds[dn - 1].sub(ds[dn - 2]).det(ps[i].sub(ds[dn - 2])), 0))
				dn--;
		for (int i = n - 1; i >= 0; us[un++] = ps[i--])
			while (un >= 2 && lt(us[un - 1].sub(us[un - 2]).det(ps[i].sub(us[un - 2])), 0))
				un--;
		P[] res = new P[dn + un - 2];
		System.arraycopy(ds, 0, res, 0, dn - 1);
		System.arraycopy(us, 0, res, dn - 1, un - 1);
		return res;
	}
	class P implements Comparable<P> {
		public int compareTo(P p) {
			// TODO 自動生成されたメソッド・スタブ
			return x == p.x ? (int) signum(y - p.y) : (int) signum(x - p.x);
		}
		@Override
		public String toString() {
			// TODO 自動生成されたメソッド・スタブ
			return "( " + x + " , " + y + " )";
		}
		P rot(double t) {
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}
		double x, y;
		P(double x, double y) {
			this.x = x;
			this.y = y;
		}
		boolean equals(P p) {
			return eq(dist(p), 0);
		}
		P sub(P o) {
			return new P(x - o.x, y - o.y);
		}
		double dist(P p) {
			return sub(p).norm();
		}
		P add(P p) {
			return new P(x + p.x, y + p.y);
		}
		P div(double d) {
			return new P(x / d, y / d);
		}
		double norm() {
			return sqrt(x * x + y * y);
		}
		double det(P p) {
			return x * p.y - y * p.x;
		}
		double angle(P p) {
			double res = angle() - p.angle();
			if (res < -PI) res += 2 * PI;
			if (res > PI) res -= 2 * PI;
			return res;
		}
		double angle() {
			return atan2(y, x);
		}
	}
	public static void main(String[] args) {
		new E().run();
	}
}
