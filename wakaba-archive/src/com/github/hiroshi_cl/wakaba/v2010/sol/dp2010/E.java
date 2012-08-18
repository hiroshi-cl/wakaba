package com.github.hiroshi_cl.wakaba.v2010.sol.dp2010;

import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class E {
	public static void main(String[] args) {
		new E().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			// String s = this.getClass().getSimpleName();
			// sc = new Scanner(new File(s));
			// System.setOut(new PrintStream(s+".out"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		for (;;) {
			N = sc.nextInt();
			if (N == 0)
				return;
			ps = new P[N];
			rs = new double[N];
			ms = new double[N];
			for (int i = 0; i < N; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());
				rs[i] = sc.nextDouble();
				ms[i] = sc.nextDouble();
			}
			int res = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					P[][] ss = sessen(ps[i], rs[j], ps[j], rs[j]);
					for (P[] s : ss) {
						res = max(res, calc(s));
					}
					ss = sessen(ps[i], rs[j] + ms[i], ps[j], rs[j]);
					for (P[] s : ss) {
						res = max(res, calc(s));
					}
					ss = sessen(ps[i], rs[j], ps[j], rs[j] + ms[j]);
					for (P[] s : ss) {
						res = max(res, calc(s));
					}
					ss = sessen(ps[i], rs[j] + ms[i], ps[j], rs[j] + ms[j]);
					for (P[] s : ss) {
						res = max(res, calc(s));
					}
				}
			}
			System.out.println(res);
		}

	}

	int N;
	double[] rs;
	double[] ms;
	P[] ps;
	double EPS = 1e-7;

	int calc(P[] ss) {
		int res = 0;
		for (int i = 0; i < N; i++) {
			double d = dist(ps[i], ss[0], ss[1]);
			if (rs[i] - EPS < d && d < rs[i] + ms[i] + EPS)
				res++;
		}
		return res;
	}

	double dist(P p, P r1, P r2) {
		P r12 = r2.sub(r1);
		double t = (p.dot(r12) - r1.dot(r12)) / r12.norm2();
		P foot = r1.add(r12.mul(t));
		return foot.dist(p);
	}

	P[][] sessen(P p, double rp, P r, double rr) {
		ArrayList<P[]> list = new ArrayList<P[]>();
		P[][] ss = naisetu(p, rp, r, rr);
		for (P[] s : ss)
			list.add(s);
		ss = gaisetu(p, rp, r, rr);
		for (P[] s : ss)
			list.add(s);
		return list.toArray(new P[0][]);
	}

	P[][] gaisetu(P o1, double r1, P o2, double r2) {
		double th = asin(abs(r2 - r1) / o1.dist(o2));
		P oo = r2 > r1 ? o2.sub(o1) : o1.sub(o2);
		P d1 = oo.rot(th + PI / 2).unit();
		P d2 = oo.rot(-th - PI / 2).unit();
		return new P[][] { { o1.add(d1.mul(r1)), o2.add(d1.mul(r2)) },
				{ o1.add(d2.mul(r1)), o2.add(d2.mul(r2)) } };
	}

	P[][] naisetu(P o1, double r1, P o2, double r2) {
		double D = o1.dist(o2);
		double d = r1 + r2;
		double l = sqrt(D * D + d * d);
		double t = atan2(l, d);
		P p1 = o1.add(o2.sub(o1).rot(t).unit(r1));
		P p2 = o2.add(o1.sub(o2).rot(t).unit(r2));
		ArrayList<P[]> res = new ArrayList<P[]>();
		res.add(new P[] { p1, p2 });
		p1 = o1.add(o2.sub(o1).rot(-t).unit(r1));
		p2 = o2.add(o1.sub(o2).rot(-t).unit(r2));
		res.add(new P[] { p1, p2 });
		return res.toArray(new P[0][]);

	}

	class P {
		double x, y;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P add(P o) {
			return new P(x + o.x, y + o.y);
		}

		P sub(P o) {
			return new P(x - o.x, y - o.y);
		}

		P mul(double m) {
			return new P(x * m, y * m);
		}

		P div(double m) {
			return new P(x / m, y / m);
		}

		double dot(P o) {
			return x * o.x + y * o.y;
		}

		double det(P o) {
			return x * o.y - y * o.x;
		}

		double norm2() {
			return x * x + y * y;
		}

		double norm() {
			return sqrt(norm2());
		}

		double dist(P o) {
			return sub(o).norm();
		}

		P rot(double theta) {
			return new P(x * cos(theta) - y * sin(theta), x * sin(theta) + y
					* cos(theta));
		}

		P unit() {
			return div(norm());
		}

		P unit(double d) {
			return unit().mul(d);
		}

		@Override
		public String toString() {
			return String.format("(%.2f,%.2f)\n", x, y);
		}
	}
}
