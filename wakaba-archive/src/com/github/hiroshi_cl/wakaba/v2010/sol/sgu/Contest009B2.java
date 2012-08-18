package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.math.*;
import java.util.*;
//import contest009C.Solution.P;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class Contest009B2 {
	static final double EPS = 1e-7;

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		P[] ps = new P[3 * N];
		P[] os = new P[N];
		double[] rs = new double[N];
		for (int i = 0; i < 3 * N; i++)
			ps[i] = new P(sc.nextDouble(), sc.nextDouble());
		for (int i = 0; i < N; i++)
			os[i] = cirnumcenter(ps[3 * i], ps[3 * i + 1], ps[3 * i + 2]);
		for (int i = 0; i < N; i++)
			rs[i] = ps[3 * i].dist(os[i]);

		double[][] args = new double[N][3];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < 3; j++) {
				P p = ps[3 * i + j].sub(os[i]);
				args[i][j] = atan2(p.y, p.x);
			}
		double[] as = new double[N];
		double[] bs = new double[N];
		for (int i = 0; i < N; i++) {
			if (args[i][0] > args[i][2]) {
				double tmp = args[i][0];
				args[i][0] = args[i][2];
				args[i][2] = tmp;
			}
			if (args[i][0] < args[i][1] && args[i][1] < args[i][2]) {
				as[i] = args[i][0];
				bs[i] = args[i][2];
			} else {
				as[i] = args[i][2];
				bs[i] = args[i][0] + 2 * PI;
			}
		}
		ArrayList<P> alist = new ArrayList<P>();
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				P[] icc = intersection_cc(os[i], rs[i], os[j], rs[j]);
				if (icc == null)
					continue;
				for (int k = 0; k < icc.length; k++) {
					P p = icc[k].sub(os[j]);
					// double x = atan2()
				}
			}
		}
	}

	// boolean intersect(double ax, ) {
	//
	// }
	P[] intersection_cc(P c1, double r1, P c2, double r2) {
		P c1c2 = c2.sub(c1);
		double r = c1c2.norm();
		double x = (r * r + r1 * r1 - r2 * r2) / (2 * r);
		double t = r1 * r1 - x * x;
		if (t < 0)
			return null;
		P q1 = c1.add(c1c2.mul(x / r)), q2 = c1c2.rot(PI / 2).mul(sqrt(t) / r);
		if (t < EPS)
			return new P[] { q1 };
		return new P[] { q1.add(q2), q1.sub(q2) };
	}

	P cirnumcenter(P A, P B, P C) {
		P AB = B.sub(A), BC = C.sub(B), CA = A.sub(C);
		return (A.add(B).sub(AB.rot(PI / 2).mul(BC.dot(CA) / AB.det(BC))))
				.div(2);
	}

	class P {
		public String toString() {
			return x + " " + y;
		}

		double x, y;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P mul(double d) {
			return new P(x * d, y * d);
		}

		double dot(P p) {
			return x * p.x + y * p.y;
		}

		double det(P p) {
			return x * p.y - y * p.x;
		}

		P add(P p) {
			return new P(x + p.x, y + p.y);
		}

		P div(double d) {
			return new P(x / d, y / d);
		}

		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}

		P rot(double t) {
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}

		double norm() {
			return hypot(x, y);
		}

		double dist(P p) {
			return hypot(x - p.x, y - p.y);
		}
		// P
	}

	public static void main(String[] args) {
		new Contest009B2().run();
	}
}
